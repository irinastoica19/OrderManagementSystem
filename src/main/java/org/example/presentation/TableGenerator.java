package org.example.presentation;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * This class is used for generating a table through the reflection technique
 *
 * @author Stoica Irina
 * @since Apr 12, 2022
 * @param <T> This parameter is the class whose fields will determine the tables columns.
 *           The table will be generated through reflection.
 *
 */

public class TableGenerator<T> {

    private final ObservableList<T> observableList = FXCollections.observableArrayList();

    /**
     * This method receives a List of objects of type T and generates through reflection the
     * necessary columns for the TableView. After generating the columns, they are added to
     * the table and each object field will be put in one of the columns.
     *
     * @param objects List of objects to populate the TableView
     * @param tableView TableView to add the objects to
     */
    public void generateTableHeader(List<T> objects, TableView<T> tableView){
        ArrayList<TableColumn<T,Object>> columns = new ArrayList<>();
        for(Field field: objects.get(0).getClass().getDeclaredFields()){
            TableColumn<T, Object> newColumn = new TableColumn<>(field.getName());
            newColumn.setCellValueFactory(new PropertyValueFactory<>(field.getName()));
            columns.add(newColumn);
        }
        tableView.getColumns().addAll(columns);
        observableList.addAll(objects);
        tableView.setItems(observableList);
    }

    /**
     * This method receives a list of objects and adds them to the TableView without creating
     * the columns
     *
     * @param objects List of objects to be added in the table
     * @param tableView TableView to add the objects to
     */
    public void reloadTableValues(List<T> objects, TableView<T> tableView){
        observableList.clear();
        tableView.getItems().clear();
        observableList.addAll(objects);
        tableView.setItems(observableList);
    }

    /**
     * This method gets entry from the table selected by the user and returns it as an object.
     *
     * @param tableView TableView to select the entry from
     * @return The object T selected by the user
     */
    public T showSelectedRow(TableView<T> tableView){
        ObservableList<T> list;
        list = tableView.getSelectionModel().getSelectedItems();
        if(list.isEmpty()){
            throw new NullPointerException("Please select a ");
        } else {
            return list.get(0);
        }
    }

}
