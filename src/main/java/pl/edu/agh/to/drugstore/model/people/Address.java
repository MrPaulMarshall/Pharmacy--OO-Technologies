package pl.edu.agh.to.drugstore.model.people;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String city;
    private String street;
    private int houseId;
    private int apartmentId;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getHouseId() {
        return houseId;
    }

    public void setHouseId(int houseId) {
        if (houseId <= 0) throw new IllegalArgumentException("House Id must be positive number");
        this.houseId = houseId;
    }

    public int getApartmentId() {
        return apartmentId;
    }

    public void setApartmentId(int apartmentId) {
        if (houseId < 0) throw new IllegalArgumentException("Negative apartment Id isn't allowed");
        this.apartmentId = apartmentId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return city + ", " + street + " " + houseId + (apartmentId > 0 ? ("/" + apartmentId) : "");
    }
}
