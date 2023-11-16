package rs.raf.projekatispit.entities;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class Comment {
    private int id;
    @NotNull(message = "Author is required")
    @NotEmpty(message = "Author is required")
    private String author;
    @NotNull(message = "Comment is required")
    @NotEmpty(message = "Comment is required")
    private String comment;
    @NotNull(message = "Title is required")
    @NotEmpty(message = "Title is required")
    private int idNews;

    private int likes;

    private int dislikes;

    public Comment() {
    }

    public Comment(String author, String comment) {
        this.author = author;
        this.comment = comment;
    }

    public Comment(int id, String author, String comment) {
        this.id = id;
        this.author = author;
        this.comment = comment;
    }

    public Comment(int id, String author, String comment, int idNews) {
        this.id = id;
        this.author = author;
        this.comment = comment;
        this.idNews = idNews;
    }

    public Comment(String author, String comment, int idNews) {
        this.author = author;
        this.comment = comment;
        this.idNews = idNews;
    }


    public Comment(int id, String author, String comment, int idNews, int likes, int dislikes) {
        this.id = id;
        this.author = author;
        this.comment = comment;
        this.idNews = idNews;
        this.likes = likes;
        this.dislikes = dislikes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getIdNews() {
        return idNews;
    }

    public void setIdNews(int idNews) {
        this.idNews = idNews;
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
