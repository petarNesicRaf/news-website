package rs.raf.projekatispit.services;

import rs.raf.projekatispit.entities.News;
import rs.raf.projekatispit.repository.news.NewsRepo;

import javax.inject.Inject;
import java.util.List;

public class NewsService {
    @Inject
    NewsRepo newsRepo;

    public List<News> getAllNews()
    {
        return this.newsRepo.getAllNews();
    }
    public News createNews(News news)
    {
        return this.newsRepo.createNews(news);
    }

    public boolean editNews(News news)
    {
        return this.newsRepo.editNews(news);
    }

    public News findNews(int id)
    {
        return this.newsRepo.findnews(id);
    }

    public List<News> getNewsByCategory(int idCategory)
    {
        return this.newsRepo.getNewsByCategory(idCategory);
    }

    public boolean deleteNews(int idNews)
    {
        return this.newsRepo.deleteNews(idNews);
    }

    public List<News> getMostRead()
    {
        return this.newsRepo.getMostRead();
    }

    public List<News> searchNews(String query){return this.newsRepo.getSearch(query);}

    public List<News> getNewest() {return this.newsRepo.getNewest();}

    public boolean incrementReadNumber(int id) {return this.newsRepo.incrementReadNumber(id);}

    public boolean like(int id) {return this.newsRepo.like(id);}

    public boolean dislike(int id) {return this.newsRepo.dislike(id);}

    public List<News> getMostInteracted(){return this.newsRepo.getMostInteracted();}

    public List<News> getAllNewsPaginated(int page){return this.newsRepo.getAllNewsPaginated(page);}

    public List<News> getNewsByCategoryPaginated(int page, int id_category){return this.newsRepo.getNewsByCategoryPaginated(page, id_category);}

    public List<News> getSearchPaginated(int page, String query){return this.newsRepo.getSearchPaginated(page,query);}
}
