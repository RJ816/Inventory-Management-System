package imsapp;

import javafx.collections.ObservableList;
import javafx.collections.FXCollections;

/**
 *Class for products dependent on parts.
 * FUTURE ENHANCEMENT auto generate product name from an approved list.
 * Auto add parts based on product interfaces.
 * @author Robin Jiang
 */
public class Product {
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    /**default constructor*/
    public Product() {
        id = 0;
        name = "";
        price = 0.0;
        stock = 0;
        min = 0;
        max = 0;
    }

    /**constructor*/
    public Product(int id, String name, double price, int stock, int min, int max) {
        super();
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /**
     * @return the product id
     * RUNTIME ERROR attempt to call method without constructing new object. Initialized product object first.
     */
    public int getId() {
        return id;
    }

    /**
     * @param id product id to set
     * RUNTIME ERROR attempt to initialize with non int type. Make sure arg is int only.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the product name
     * RUNTIME ERROR Attempt to call method without constructing new object. Initialized product object first.
     */
    public String getName() {
        return name;
    }

    /**
     * @param name product name to set
     * RUNTIME ERROR Attempt to initialize with non String type. Make sure arg is String only.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the product price
     * RUNTIME ERROR Attempt to call method without constructing new object. Initialized product object first.
     */
    public double getPrice() {
        return price;
    }

    /**
     * @param price product price to set
     * RUNTIME ERROR attempt to initialize with non-double type. Make sure arg is double only.
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * @return the product stock
     * RUNTIME ERROR Attempt to call method without constructing new object. Initialized product object first.
     */
    public int getStock() {
        return stock;
    }

    /**
     * @param stock product price to set
     * RUNTIME ERROR attempt to initialize with non int type.
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * @return the product min
     * RUNTIME ERROR min greater than max, set proper boundaries/exceptions
     */
    public int getMin() {
        return min;
    }

    /**
     * @param min product min to set
     * RUNTIME ERROR min greater than max, set proper boundaries/exceptions
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * @return the product max
     * RUNTIME ERROR min greater than max, set proper boundaries/exceptions
     */
    public int getMax() {
        return max;
    }

    /**
     * @param max product max to set
     * RUNTIME ERROR min greater than max, set proper boundaries/exceptions
     */
    public void setMax(int max) {
        this.max = max;
    }

    /**
     * @param part add part to associatedParts Observable List
     * RUNTIME ERROR associatedParts not properly initialized, initialized with FXCollections.observableArrayList()
     */
    public void addAssociatedPart(Part part) {
        associatedParts.add(part);
    }

    /**
     * @return Delete part from AssociatedPart list.
     * RUNTIME ERROR associatedParts not properly initialized, initialized with FXCollections.observableArrayList()
     */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart) {
        boolean deleteCheck = true;
        if (associatedParts.contains(selectedAssociatedPart)) {
            deleteCheck = false;
        }
        return deleteCheck;
    }

    /**
     * @return Call list of parts associated with a product.
     * RUNTIME ERROR associatedParts not properly initialized, initialized with FXCollections.observableArrayList().
     */
    public ObservableList<Part> getAllAssociatedParts() {return associatedParts;}

}