package Model;

import java.io.Serializable;

public class Property implements Serializable {
    protected int reference;
    protected String type;
    protected String address;
    protected int noRooms;
    protected double sellingPrice;
    protected double soldPrice;

    //Constructor
    public Property(Property p) {
        this.reference = p.reference;
        this.type = p.type;
        this.address = p.address;
        this.noRooms = p.noRooms;
        this.sellingPrice = p.sellingPrice;
        this.soldPrice = p.soldPrice;
    }

    //Constructor
    public Property(int ref, String a, int nr, double p1, double p2) {
        reference = ref;
        address = a;
        noRooms = nr;
        sellingPrice = p1;
        soldPrice = p2;
    }

    //Setters and Getters
    public void setAddress(String a) {
        address = a;
    }

    public void setNoRooms(int noRooms) {
        this.noRooms = noRooms;
    }

    public void setSellingPrice(double sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public void setSoldPrice(double soldPrice) {
        this.soldPrice = soldPrice;
    }

    public String getAddress() {
        return address;
    }

    public int getNoRooms() {
        return noRooms;
    }

    public double getSellingPrice() {
        return sellingPrice;
    }

    public double getSoldPrice() {
        return soldPrice;
    }

    public String getType() {
        return type;
    }

    public void setType(String t) {
        this.type = t;
    }

    public int getReference() {
        return reference;
    }

    public void setReference(int reference) {
        this.reference = reference;
    }
}
