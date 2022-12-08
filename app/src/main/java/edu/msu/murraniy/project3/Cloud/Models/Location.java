package edu.msu.murraniy.project3.Cloud.Models;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

/**
 * Nested class to store one catalog row
 */
@Root(name = "location")
public final class Location {
    @Attribute
    private String id;

    @Attribute
    private String name;

    @Attribute
    private double lat;

    @Attribute
    private double lng;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double name) {
        this.lat = name;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double name) {
        this.lng = name;
    }

    public Location(String id, String name, double lat, double lng) {
        this.id = id;
        this.name = name;
        this.lat = lat;
        this.lng = lng;
    }

    public Location() {}

}