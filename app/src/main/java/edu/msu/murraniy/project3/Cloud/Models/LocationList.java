package edu.msu.murraniy.project3.Cloud.Models;

import androidx.annotation.Nullable;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.ArrayList;
import java.util.List;

@Root(name = "gps")
public final class LocationList {
    @Attribute
    private String status;

    @ElementList(name = "location", inline = true, required = false, type = Location.class)
    private List<Location> locations;

    @Attribute(name = "msg", required = false)
    private String message;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Location> getItems() {
        return locations;
    }

    public void setItems(List<Location> items) {
        this.locations = items;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocationList(String status, ArrayList<Location> locations, @Nullable String msg) {
        this.status = status;
        this.locations = locations;
        this.message = msg;
    }

    public LocationList() {
    }
}