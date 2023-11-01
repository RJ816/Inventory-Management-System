package imsapp;

/**
 *Child class for parts produced in house.
 * FUTURE ENHANCEMENT Class loads persistent data.
 * @author Robin Jiang
 */
public class InHouse extends Part{
    private int machineId;

    /**default constructor*/
    public InHouse() {
        id = 0;
        name = "";
        price = 0.0;
        stock = 0;
        min = 0;
        max = 0;
        machineId=0;
    }

    /**constructor*/
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
        super();
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
        this.machineId = machineId;
    }

    /**
     * @param machineId machineId to set
     * RUNTIME ERROR attempt to call with non int arg. Made sure parameter were correct.
     */
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }

    /**
     * @return the machineId
     * RUNTIME ERROR Attempt to call without initializing new object. Made sure a part object was initialized first.
     */
    public int getMachineId() {
        return machineId;
    }
}
