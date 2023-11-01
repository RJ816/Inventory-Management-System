package imsapp;

import javafx.collections.ObservableList;
import javafx.collections.FXCollections;

/**
 *Class for current inventory of products and parts.
 * FUTURE ENHANCEMENT Class loads persistent data.
 * Lookup parts with other Part fields.
 * Lookup products with other Product fields.
 * @author Robin Jiang
 */
public class Inventory {
    protected static final ObservableList<Part> allParts = FXCollections.observableArrayList();
    protected static final ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /**default constructor*/
    public Inventory() {}

    /**
     * @param newPart add part to allParts.
     * RUNTIME ERROR allParts not properly initialized, initialized with FXCollections.observableArrayList().
     */
    public static void addPart(Part newPart) {
        allParts.add(newPart);
    }

    /**
     * @return Lookup part in allParts with part id.
     * RUNTIME ERROR Attempt to call with non int type. Made sure args were int types.
     */
    public static Part lookupPart(int partId) {
        for (Part part: allParts) {
            if (part.getId() == partId) {
                return part;
            }
        }
        return null;
    }

    /**
     * @return Lookup part in allParts with part name.
     * RUNTIME ERROR Attempt to call with non string type. Made sure args were string types.
     */
    public static ObservableList<Part> lookupPart(String partName) {
        ObservableList<Part> partObservableList = FXCollections.observableArrayList();
            for (Part part: allParts) {
                if (part.getName().contains(partName)) {
                    partObservableList.add(part);
                }
            }
            if (partObservableList.isEmpty()) {
                return null;
        } else {return partObservableList;}
    }

    /**
     * @param selectedPart Update selected part in allParts list.
     * RUNTIME ERROR allParts not properly initialized, initialized with FXCollections.observableArrayList()
     */
    public static void updatePart(int index, Part selectedPart) {
        Inventory.allParts.set(index, selectedPart);
    }

    /**
     * @return Boolean flag to confirm if part can be deleted.
     * RUNTIME ERROR No exception handling to check if parts were associated with products. Added warning prompts if a part is bound to a product.
     */
    public static boolean deletePart(Part selectedPart) {
        boolean deleteCheck = true;
        for (Product product: allProducts) {
            if (product.getAllAssociatedParts().contains(selectedPart)) {
                deleteCheck = false;
                break;
            }
        }
        return deleteCheck;
    }

    /**
     * @return Call static observable list allParts.
     * RUNTIME ERROR Attempted to call allParts with private modifier. Changed to protected to allow access in module.
     */
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    /**
     * @param newProduct add product to allProducts.
     * RUNTIME ERROR allProducts not properly initialized, initialized with FXCollections.observableArrayList().
     */
    public static void addProduct(Product newProduct) { allProducts.add(newProduct); }

    /**
     * @return Lookup product in allProducts with product id.
     * RUNTIME ERROR Attempt to call with non int type. Made sure args were int types.
     */
    public static Product lookupProduct(int productId) {
        for (Product product: allProducts) {
            if (product.getId() == productId) {
                return product;
            }
        }
        return null;
    }

    /**
     * @return Lookup product in allProducts with product name.
     * RUNTIME ERROR Attempt to call with non string type. Made sure args were string types.
     */
    public static ObservableList<Product> lookupProduct(String productName) {
        ObservableList<Product> productObservableList = FXCollections.observableArrayList();
        for (Product product: allProducts) {
            if (product.getName().contains(productName)) {
                productObservableList.add(product);
            }
        }
        if (productObservableList.isEmpty()) {
            return null;
        } else {return productObservableList;}
    }

    /**
     * @param newProduct Update selected product in allProducts list.
     * RUNTIME ERROR allProducts not properly initialized, initialized with FXCollections.observableArrayList()
     */
    public static void updateProduct(int index, Product newProduct ) { Inventory.allProducts.set(index, newProduct);}

    /**
     * @return Delete product from allProducts list.
     * RUNTIME ERROR allProducts not properly initialized, initialized with FXCollections.observableArrayList()
     */
    public static boolean deleteProduct(Product selectedProduct) {
        boolean deleteCheck = false;
        if (selectedProduct.getAllAssociatedParts().isEmpty()) {
            deleteCheck = true;
            System.out.print(deleteCheck);
        }
        return deleteCheck;
    }

    /**
     * @return static observable list allProducts.
     * RUNTIME ERROR Attempted to call allProducts with private modifier. Changed to protected to allow access in module.
     */
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }
}

