package edu.msu.murraniy.project3.Cloud.Models;

import androidx.annotation.Nullable;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.ArrayList;
import java.util.List;

@Root(name = "gps")
public final class CommentCatalog {
    @Attribute
    private String status;

    @ElementList(name = "comments", inline = true, required = false, type = Comment.class)
    private List<Comment> comments;

    @Attribute(name = "msg", required = false)
    private String message;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Comment> getItems() {
        return comments;
    }

    public void setItems(List<Comment> items) {
        this.comments = items;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public CommentCatalog(String status, ArrayList<Comment> comments, @Nullable String msg) {
        this.status = status;
        this.comments = comments;
        this.message = msg;
    }

    public CommentCatalog() {
    }
}
