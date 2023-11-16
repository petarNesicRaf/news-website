package rs.raf.projekatispit.resources;

import rs.raf.projekatispit.entities.News;
import rs.raf.projekatispit.services.NewsService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path("/news")
public class NewsResource {
    @Inject
    NewsService newsService;

    @GET
    @Path("/allNews")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getAllNews(@QueryParam("page") int page)
    {
        Map<String, String> response = new HashMap<>();
        List<News> allNews;

        if(page!=0){
            allNews = this.newsService.getAllNewsPaginated(page);
        }
        else{
            allNews = this.newsService.getAllNews();
        }

        if(allNews == null)
        {
            response.put("message", "No news have been found");
            return Response.status(404, "Not found").entity(response).build();
        }

        return Response.ok(allNews).build();
    }

    @POST
    @Path("/createNews")
    @Produces({MediaType.APPLICATION_JSON})
    public Response createNews(News news)
    {
        News ne = this.newsService.createNews(news);
        return Response.ok(ne).build();
    }

    @POST
    @Path("/editNews")
    @Produces({MediaType.APPLICATION_JSON})
    public Response editNews(News news)
    {
        Map<String, String> response = new HashMap<>();
        boolean outcome = this.newsService.editNews(news);
        if(!outcome)
        {
            response.put("message", "News modification failed.");
            return Response.status(422).entity(response).build();
        }
        response.put("message",  "News successfully modified.");
        return Response.status(200).entity(response).build();
    }

    @GET
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response findNews(@PathParam("id") int id)
    {
        Map<String, String> response = new HashMap<>();
        News news = this.newsService.findNews(id);
        if(news == null)
        {
            response.put("message", "News with this title doesn't exist");
            return Response.status(404, "Not found").entity(response).build();
        }

        return Response.ok(news).build();
    }

    @GET
    @Path("/getNewsByCat/{id_category}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getNewsByCategory(@PathParam("id_category") int idCategory, @QueryParam("page") int page)
    {
        Map<String, String> response = new HashMap<>();
        List<News> newsByCategory;

        if(page!=0){
            newsByCategory = this.newsService.getNewsByCategoryPaginated(page, idCategory);
        }
        else{
            newsByCategory = this.newsService.getNewsByCategory(idCategory);
        }

        if(newsByCategory == null)
        {
            response.put("message", "No news in this category have been found");
            return Response.status(404, "Not found").entity(response).build();
        }

        return Response.ok(newsByCategory).build();
    }

    @POST
    @Path("/deleteNews")
    @Produces({MediaType.APPLICATION_JSON})
    public Response deleteNews(int idNews)
    {
        Map<String, String> response = new HashMap<>();
        boolean outcome = this.newsService.deleteNews(idNews);
        if(!outcome)
        {
            response.put("message", "News and comments removal failed.");
            return Response.status(422).entity(response).build();
        }
        response.put("message",  "News and comments successfully removed.");
        return Response.status(200).entity(response).build();
    }

    @GET
    @Path("/mostRead")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getMostRead()
    {
        Map<String, String> response = new HashMap<>();
        List<News> allNews = this.newsService.getMostRead();
        if(allNews == null)
        {
            response.put("message", "No news have been found");
            return Response.status(404, "Not found").entity(response).build();
        }

        return Response.ok(allNews).build();
    }

    @GET
    @Path("/search")
    @Produces({MediaType.APPLICATION_JSON})
    public Response searchNews(@QueryParam("query") String query, @QueryParam("page") int page) {
        List<News> searchResults;

        if(page!=0){
             searchResults = this.newsService.getSearchPaginated(page, query);
        }
        else{
             searchResults = this.newsService.searchNews(query);
        }

        if (searchResults.isEmpty()) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "No news found for the given query");
            return Response.status(404, "Not found").entity(response).build();
        }

        return Response.ok(searchResults).build();
    }

    @GET
    @Path("/newest")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getNewest()
    {
        Map<String, String> response = new HashMap<>();
        List<News> allNews = this.newsService.getNewest();
        if(allNews == null)
        {
            response.put("message", "No news have been found");
            return Response.status(404, "Not found").entity(response).build();
        }

        return Response.ok(allNews).build();
    }

    @PATCH
    @Path("/read")
    @Produces({MediaType.APPLICATION_JSON})
    public Response incrementReaders(int id)
    {
        Map<String, String> response = new HashMap<>();
        boolean outcome = this.newsService.incrementReadNumber(id);
        if(!outcome)
        {
            response.put("message", "Read number modification failed.");
            return Response.status(422).entity(response).build();
        }
        response.put("message",  "Read number successfully modified.");
        return Response.status(200).entity(response).build();
    }


    @PATCH
    @Path("/like")
    @Produces({MediaType.APPLICATION_JSON})
    public Response like(int id)
    {
        Map<String, String> response = new HashMap<>();
        boolean outcome = this.newsService.like(id);
        if(!outcome)
        {
            response.put("message", "Like failed.");
            return Response.status(422).entity(response).build();
        }
        response.put("message",  "Liked successfully.");
        return Response.status(200).entity(response).build();
    }

    @PATCH
    @Path("/dislike")
    @Produces({MediaType.APPLICATION_JSON})
    public Response dislike(int id)
    {
        Map<String, String> response = new HashMap<>();
        boolean outcome = this.newsService.dislike(id);
        if(!outcome)
        {
            response.put("message", "Dislike failed.");
            return Response.status(422).entity(response).build();
        }
        response.put("message",  "Disliked successfully.");
        return Response.status(200).entity(response).build();
    }

    @GET
    @Path("/mostInteracted")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getMostInteracted()
    {
        Map<String, String> response = new HashMap<>();
        List<News> allNews = this.newsService.getMostInteracted();
        if(allNews == null)
        {
            response.put("message", "No news have been found");
            return Response.status(404, "Not found").entity(response).build();
        }

        return Response.ok(allNews).build();
    }

}
