package imsapp;

/**
 * Child class for parts bought from vendors.
 * FUTURE ENHANCEMENT Class loads persistent data.
 * @author Robin Jiang
 */
public class Outsourced extends Part{
    private String companyName;

    /**default constructor*/
    public Outsourced() {
        id = 0;
        name = "";
        price = 0.0;
        stock = 0;
        min = 0;
        max = 0;
        companyName="";
    }

    /**constructor*/
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super();
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
        this.companyName = companyName;
    }

    /**
     * @param companyName companyName to set.
     * RUNTIME ERROR Attempt to call with non String arg. Made sure parameter were correct.
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     * @return the companyName
     * RUNTIME ERROR Attempt to call without initializing new object. Made sure a part object was initialized first.
     */
    public String getCompanyName() {
        return companyName;
    }
}
