package rs.raf.projekatispit.entities;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class Tag {
    @NotNull(message = "News id is required")
    @NotEmpty(message = "News id is required")
    private int idNews;

    @NotNull(message = "Keyword is required")
    @NotEmpty(message = "Keyword is required")
    private String keyword;

    public Tag() {
    }

    public Tag(int idNews, String keyword) {
        this.idNews = idNews;
        this.keyword = keyword;
    }

    public int getIdNews() {
        return idNews;
    }

    public void setIdNews(int idNews) {
        this.idNews = idNews;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}
