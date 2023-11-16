package rs.raf.projekatispit.requests;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class TagRequest {
    @NotNull(message = "Email is required")
    @NotEmpty(message = "Email is required")
    private int idNews;
    @NotNull(message = "Password is required")
    @NotEmpty(message = "Password is required")
    private String allTags;

    public TagRequest() {
    }

    public TagRequest(int idNews, String allTags) {
        this.idNews = idNews;
        this.allTags = allTags;
    }

    public int getIdNews() {
        return idNews;
    }

    public void setIdNews(int idNews) {
        this.idNews = idNews;
    }

    public String getAllTags() {
        return allTags;
    }

    public void setAllTags(String allTags) {
        this.allTags = allTags;
    }
}
