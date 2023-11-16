package rs.raf.projekatispit.repository.tag;

import rs.raf.projekatispit.entities.News;
import rs.raf.projekatispit.entities.Tag;
import rs.raf.projekatispit.requests.TagRequest;

import java.util.List;

public interface TagRepo {
    List<News> getAllNewsWithTag(String keyword);
    List<String> getAllTagsFromNews(int idNews);
    boolean replaceTags(TagRequest tagRequest); //brise sve tagove i secka rec po rec iz tag requesta i dodaje
    List<News> readMore(int idNews);
}
