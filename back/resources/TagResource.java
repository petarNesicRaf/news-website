package rs.raf.projekatispit.resources;

import rs.raf.projekatispit.entities.News;
import rs.raf.projekatispit.entities.Tag;
import rs.raf.projekatispit.requests.TagRequest;
import rs.raf.projekatispit.services.TagService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path("/tags")
public class TagResource {
    @Inject
    TagService tagService;

    @GET
    @Path("/getAllNewsWithTag/{tag}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getNewsByTag(@PathParam("tag") String tag)
    {
        Map<String, String> response = new HashMap<>();
        List<News> newsByTag = this.tagService.getAllNewsWithTag(tag);
        if(newsByTag == null)
        {
            response.put("message", "No news with this tag have been found");
            return Response.status(404, "Not found").entity(response).build();
        }
        return Response.ok(newsByTag).build();
    }

    @GET
    @Path("/getAllTagsFromNews/{id_news}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getAllTagsFromNews(@PathParam("id_news") int idNews)
    {
        Map<String, String> response = new HashMap<>();
        List<String> tagByNews = this.tagService.getAllTagsFromNews(idNews);
        if(tagByNews == null)
        {
            response.put("message", "No tags have been found");
            return Response.status(404, "Not found").entity(response).build();
        }
        return Response.ok(tagByNews).build();
    }

    @POST
    @Path("/replaceTags")
    @Produces({MediaType.APPLICATION_JSON})
    public Response replaceTags(TagRequest tagRequest)
    {
        Map<String, String> response = new HashMap<>();
        boolean outcome = this.tagService.replaceTags(tagRequest);
        if(!outcome)
        {
            response.put("message", "Tag replacement failed.");
            return Response.status(422).entity(response).build();
        }
        response.put("message",  "Tags successfully replaced.");
        return Response.status(200).entity(response).build();
    }

    @GET
    @Path("/readMore/{id_news}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response readMore(@PathParam("id_news") int id_news)
    {
        Map<String, String> response = new HashMap<>();
        List<News> readMore = this.tagService.readMore(id_news);
        if(readMore == null)
        {
            response.put("message", "No news with matching tags have been found");
            return Response.status(404, "Not found").entity(response).build();
        }
        return Response.ok(readMore).build();
    }

}
