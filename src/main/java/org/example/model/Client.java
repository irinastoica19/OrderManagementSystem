package org.example.model;

/**
 * This class contains the fields related to the client and getters and setters for them. These
 * are necessary for the database operations. This class name and field names correspond to the
 * table and table columns present in the database, but also to the TableView from the interface
 * and its columns.
 *
 * @author Stoica Irina
 * @since Apr 12, 2022
 */
public class Client {
    private Integer id;
    private String name;
    private String address;
    private String email;

    public Client(){ }

    public Client(String name, String address, String email) {
        this.name = name;
        this.address = address;
        this.email = email;
    }

    public Client(Integer id, String name, String address, String email) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.email = email;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Client [id=" + id + ", name=" + name + ", address=" + address + ", email=" + email + "]";
    }
}
