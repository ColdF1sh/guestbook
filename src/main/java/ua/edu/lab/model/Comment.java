package ua.edu.lab.model;

public class Comment {
    public long id;
    public String author;
    public String text;

    public Comment(long id, String author, String text) {
        this.id = id;
        this.author = author;
        this.text = text;
    }
}