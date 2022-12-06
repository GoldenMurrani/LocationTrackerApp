package edu.msu.murraniy.project3.Cloud.Models;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

@Root(name = "gps")
public class CreateUser {

    @Attribute
    private String status;

    @Attribute(name = "msg", required = false)
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}