package Model;

        import java.io.Serializable;

//use enum types for garden?
public class House extends Property implements Serializable {
    private int floors;
    private String garden;
    private String garage;
    private int floor = 0;
    private double monthlyCharge = 0.0;

    //Constructor.
    public House(int ref, String a, int nr, double p1, double p2, int f, String garden, String garage) {
        super(ref, a, nr, p1, p2);
        floors = f;
        this.garage = garage;
        this.garden = garden;
        this.type = this.getClass().getName().substring(6);

    }

    //Setters.
    public void setFloors(int floors) {
        this.floors = floors;
    }

    public void setGarage(String garage) {
        this.garage = garage;
    }

    public void setGarden(String garden) {
        this.garden = garden;
    }

    //Getters.
    public int getFloors() {
        return floors;
    }

    public String getGarage() {
        return garage;
    }

    public String getGarden() {
        return garden;
    }

    public double getMonthlyCharge() {
        return monthlyCharge;
    }

    public int getFloor() {
        return floor;
    }


}
