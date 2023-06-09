package edu.msu.murraniy.project3.Cloud.Models;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

/**
 * Nested class to store one catalog row
 */
@Root(name = "comments")
public final class Comment {
    @Attribute(name = "user")
    private String user;

    @Attribute(name = "comment")
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
