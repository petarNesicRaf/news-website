package rs.raf.projekatispit.entities;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

public class News {
    private int id;
    @NotNull(message = "Title is required")
    @NotEmpty(message = "Title is required")
    private String title;
    @NotNull(message = "Text is required")
    @NotEmpty(message = "Text is required")
    private String text;
    @NotNull(message = "Date is required")
    @NotEmpty(message = "Date is required")
    private Date dateTime;
    @NotNull(message = "Author is required")
    @NotEmpty(message = "Author is required")
    private String author;

    @NotNull(message = "Category is required")
    @NotEmpty(message = "Category is required")
    private int idCategory;

    private int readers;

    private int likes;

    private int dislikes;

    public News() {
    }

    public News(String title, String text, String author) {
        this.title = title;
        this.text = text;
        this.author = author;
    }

    public News(int id, String title, String text, Date dateTime, String author, int idCategory) {
        this.id = id;
        this.title = title;
        this.text = text;
        this.dateTime = dateTime;
        this.author = author;
        this.idCategory = idCategory;
    }

    public News(String title, String text, Date dateTime, String author, int idCategory) {
        this.title = title;
        this.text = text;
        this.dateTime = dateTime;
        this.author = author;
        this.idCategory = idCategory;
    }

    public News(int id, String title, String text, Date dateTime, String author, int idCategory, int readers) {
        this.id = id;
        this.title = title;
        this.text = text;
        this.dateTime = dateTime;
        this.author = author;
        this.idCategory = idCategory;
        this.readers = readers;
    }

    public News(int id, String title, String text, Date dateTime, String author, int idCategory, int readers, int likes, int dislikes) {
        this.id = id;
        this.title = title;
        this.text = text;
        this.dateTime = dateTime;
        this.author = author;
        this.idCategory = idCategory;
        this.readers = readers;
        this.likes = likes;
        this.dislikes = dislikes;
    }

    public int getReaders() {
        return readers;
    }

    public void setReaders(int readers) {
        this.readers = readers;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }


    public int getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(int idCategory) {
        this.idCategory = idCategory;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getDislikes() {
        return dislikes;
    }

    public void setDislikes(int dislikes) {
        this.dislikes = dislikes;
    }
}
