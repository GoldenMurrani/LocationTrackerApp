package edu.msu.murraniy.project3.Cloud.Models;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

/**
 * Nested class to store one catalog row
 */
@Root(name = "gps")
public final class Comment {
    @Attribute
    private String user;

    @Attribute
    private String comment;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Comment(String user, String comment) {
        this.user = user;
        this.comment = comment;
    }

    public Comment() {}

}
