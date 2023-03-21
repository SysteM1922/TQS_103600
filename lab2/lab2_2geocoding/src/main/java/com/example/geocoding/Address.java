package com.example.geocoding;

import java.util.Objects;

/**
 * @author ico
 */
public class Address {

    private String road;
    private String cirty;
    private String state;
    private String zip;
    private String houseNumber;

    public Address(String road, String cirty, String state, String zip, String houseNumber) {
        this.road = road;
        this.cirty = cirty;
        this.state = state;
        this.zip = zip;
        this.houseNumber = houseNumber;
    }

    public String getRoad() {
        return road;
    }

    public void setRoad(String road) {
        this.road = road;
    }

    public String getCirty() {
        return cirty;
    }

    public void setCirty(String cirty) {
        this.cirty = cirty;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getzip() {
        return zip;
    }

    public void setzip(String zip) {
        this.zip = zip;
    }

    /**
     * Get the value of houseNumber
     *
     * @return the value of houseNumber
     */
    public String getHouseNumber() {
        return houseNumber;
    }

    /**
     * Set the value of houseNumber
     *
     * @param houseNumber new value of houseNumber
     */
    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    @Override
    public String toString() {
        return "Address{" + "road=" + road + ", cirty=" + cirty + ", state=" + state + ", zip=" + zip + ", houseNumber=" + houseNumber + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Address other = (Address) obj;
        if (!Objects.equals(this.road, other.road)) {
            return false;
        }
        if (!Objects.equals(this.cirty, other.cirty)) {
            return false;
        }
        if (!Objects.equals(this.state, other.state)) {
            return false;
        }
        if (!Objects.equals(this.zip, other.zip)) {
            return false;
        }
        if (!Objects.equals(this.houseNumber, other.houseNumber)) {
            return false;
        }
        return true;
    }


}
