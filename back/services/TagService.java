package rs.raf.projekatispit.services;

import rs.raf.projekatispit.entities.News;
import rs.raf.projekatispit.entities.Tag;
import rs.raf.projekatispit.repository.tag.TagRepo;
import rs.raf.projekatispit.requests.TagRequest;

import javax.inject.Inject;
import java.util.List;

public class TagService {
    @Inject
    TagRepo tagRepo;

    public List<News> getAllNewsWithTag(String keyword){return tagRepo.getAllNewsWithTag(keyword);}
    public List<String> getAllTagsFromNews(int idNews){return tagRepo.getAllTagsFromNews(idNews);}
    public boolean replaceTags(TagRequest tagRequest){return tagRepo.replaceTags(tagRequest);}
    public List<News> readMore(int idNews){return tagRepo.readMore(idNews);};
}
