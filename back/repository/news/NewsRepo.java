package rs.raf.projekatispit.repository.news;

import rs.raf.projekatispit.entities.News;

import java.util.List;

public interface NewsRepo {
    List<News> getAllNews();

    List<News> getNewsByCategory(int idCategory);
    News createNews(News news);

    boolean editNews(News news);

    News findnews(int id);

    boolean deleteNews(int idNews); //todo vrati na int id kad ubacis komentare

    List<News> getMostRead();

    List<News> getSearch(String query);

    List<News> getNewest();

    boolean incrementReadNumber(int id);

    boolean like(int id);

    boolean dislike(int id);

    List<News> getMostInteracted();

    List<News> getAllNewsPaginated(int page);

    List<News> getNewsByCategoryPaginated(int page, int id_category);

    List<News> getSearchPaginated(int page, String query);
}
