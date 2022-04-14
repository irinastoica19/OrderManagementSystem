package org.example.dao;

import org.example.connection.ConnectionFactory;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * The class performs the basic abstract operations for a database like insert, update, delete
 *
 *  @author Stoica Irina
 * @since Apr 12, 2022
 * @param <T> Parameter T is a class which corresponds to a table in the database
 *
 */

public class AbstractDAO<T> {
    protected static final Logger LOGGER = Logger.getLogger(AbstractDAO.class.getName());

    private final Class<T> type;

    @SuppressWarnings("unchecked")
    public AbstractDAO() {
        this.type = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    /**
     * @return A string with the query for selecting an item by id
     */
    private String createSelectQuery() {
        return "SELECT " +
                " * " +
                " FROM " +
                type.getSimpleName() +
                " WHERE id = ?";
    }

    /**
     * @return A string with the query for selecting all items
     */
    private String createSelectAllQuery(){
        return "SELECT " +
                " * " +
                " FROM " +
                type.getSimpleName();
    }

    /**
     * @return A string with the query for deleting an item
     */
    private String createDeleteQuery(){
        return "DELETE FROM " +
                type.getSimpleName() +
                " WHERE id = ?";
    }

    /**
     * @return A string with the query for inserting an item
     */
    private String createInsertQuery(){
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO ").append(type.getSimpleName());
        sb.append(" ( ");
        for (Field field : type.getDeclaredFields()){
            field.setAccessible(true);
            sb.append(field.getName()).append(",");
        }
        sb.deleteCharAt(sb.length()-1).append(" )");
        sb.append(" VALUES ( ");
        for (Field field : type.getDeclaredFields()) {
            field.setAccessible(true);
            sb.append("?,");
        }
        sb.deleteCharAt(sb.length()-1).append(" )");
        return sb.toString();
    }

    /**
     * @return A string with the query for updating an item
     */
    private String createUpdateQuery(){
        StringBuilder sb = new StringBuilder();
        sb.append("UPDATE ");
        sb.append(type.getSimpleName());
        sb.append(" SET ");
        for (Field field : type.getDeclaredFields()){
            field.setAccessible(true);
            sb.append(field.getName()).append(" = ? ,");
        }
        sb.deleteCharAt(sb.length()-1);
        sb.append(" WHERE ").append("id").append("= ?");
        return sb.toString();
    }

    /**
     * Executes a query for finding all entries of the database end returns a list of corresponding
     * objects.
     *
     * @return A list of objects containing all the entries of the table determined by
     * the parameter T of the database
     */
    public List<T> findAll() {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = createSelectAllQuery();
        try{
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();

            return createObjects(resultSet);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + " DAO:findAll " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }

    /**
     * Executes a query for finding an entry of the database given the id end returns a list of
     * corresponding objects.
     *
     * @param id Given id
     * @return  List of objects containing all the entries of the table determined by
     * the parameter T of the database
     */
    public T findById(int id) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = createSelectQuery();
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();

            return createObjects(resultSet).get(0);
        } catch (SQLException | IndexOutOfBoundsException e) {
            LOGGER.log(Level.WARNING, type.getName() + " DAO:findById " + e.getMessage());
            throw new NoSuchElementException("The client with id =" + id + " was not found!");
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
    }

    /**
     * Converts a given result set into a list of objects with the same fields as the table columns
     *
     * @param resultSet The result set generated in the query
     * @return A list of objects of type T converted from the result set parameter
     */
    private List<T> createObjects(ResultSet resultSet) {
        List<T> list = new ArrayList<T>();
        Constructor[] ctors = type.getDeclaredConstructors();
        Constructor ctor = null;
        for (Constructor constructor : ctors) {
            ctor = constructor;
            if (ctor.getGenericParameterTypes().length == 0)
                break;
        }
        try {
            while (resultSet.next()) {
                assert ctor != null;
                ctor.setAccessible(true);
                T instance = (T)ctor.newInstance();
                for (Field field : type.getDeclaredFields()) {
                    String fieldName = field.getName();
                    Object value = resultSet.getObject(fieldName);
                    PropertyDescriptor propertyDescriptor = new PropertyDescriptor(fieldName, type);
                    Method method = propertyDescriptor.getWriteMethod();
                    method.invoke(instance, value);
                }
                list.add(instance);
            }
        } catch (InstantiationException | IllegalArgumentException | IllegalAccessException | SecurityException | InvocationTargetException | SQLException | IntrospectionException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Receives an object and inserts it in the database in the table with the same name as the
     * object.
     *
     * @param t The object corresponding to the entry to be added to the table
     */
    public void insert(T t) {
        Connection connection = null;
        PreparedStatement statement = null;
        String query = createInsertQuery();
        ArrayList<Object> values = new ArrayList<>();
        for (Field field : type.getDeclaredFields()) {
            field.setAccessible(true);
            try {
                values.add(field.get(t));
            } catch (IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            for(int i = 1; i<=values.size(); i++){
                statement.setObject(i, values.get(i-1));
            }
            statement.execute();
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + " DAO:insert " + e.getMessage());
        } finally {
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
    }

    /**
     * Receives an id and deletes the corresponding database entry.
     *
     * @param id An integer id which uniquely determines the entry to be deleted
     */
    public void delete(int id){
        Connection connection = null;
        PreparedStatement statement = null;
        String query = createDeleteQuery();
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            statement.execute();

        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + " DAO:delete " + e.getMessage());
        } finally {
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
    }

    /**
     * Receives an object and updates the entry in the database with the corresponding id with
     * the values of the given object.
     *
     * @param t An object T which will be searched for and updated in the database
     */
    public void update(T t) {
        Connection connection = null;
        PreparedStatement statement = null;
        String query = createUpdateQuery();
        ArrayList<Object> values = new ArrayList<>();
        for (Field field : type.getDeclaredFields()) {
            field.setAccessible(true);
            try {
                values.add(field.get(t));
            } catch (IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            for(int i = 1; i<=values.size(); i++) {
                statement.setObject(i, values.get(i-1));
            }
            statement.setObject(values.size()+1, values.get(0));
            statement.execute();
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + " DAO:update " + e.getMessage());
        } finally {
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
    }

}