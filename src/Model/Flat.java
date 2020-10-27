package Model;

import java.io.Serializable;

public class Flat extends Property implements Serializable {
    private int floor;
    private double monthlyCharge;
    private int floors = 0;
    private String garden = "N/A";
    private String garage = "N/A";

    //Constructor
    public Flat(int ref, String a, int nr, double p1, double p2, int f, double c) {
        super(ref, a, nr, p1, p2);
        floor = f;
        monthlyCharge = c;
        this.type = this.getClass().getName().substring(6);

    }

    //Setters
    public void setFloor(int floor) {
        this.floor = floor;
    }

    public void setMonthlyCharge(double monthlyCharge) {
        this.monthlyCharge = monthlyCharge;
    }

    //Getters
    public int getFloor() {
        return floor;
    }

    public double getMonthlyCharge() {
        return monthlyCharge;
    }

    public int getFloors() {
        return floors;
    }

    public String getGarden() {
        return garden;
    }

    public String getGarage() {
        return garage;
    }


}
