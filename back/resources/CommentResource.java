package rs.raf.projekatispit.resources;

import rs.raf.projekatispit.entities.Comment;
import rs.raf.projekatispit.services.CommentService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path("/comments")
public class CommentResource {
    @Inject
    CommentService commentService;

    @GET
    @Path("{idNews}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response findNews(@PathParam("idNews") int idNews)
    {
        Map<String, String> response = new HashMap<>();
        List<Comment> comments = this.commentService.getCommentsByNews(idNews);
        if(comments == null)
        {
            response.put("message", "Comments on this news doesn't exist");
            return Response.status(404, "Not found").entity(response).build();
        }

        return Response.ok(comments).build();
    }
    @POST
    @Path("/createComment")
    @Produces({MediaType.APPLICATION_JSON})
    public Response createComment( Comment comment)
    {
        Comment com = this.commentService.createComment(comment);
        return Response.ok(com).build();
    }

    @PATCH
    @Path("/like")
    @Produces({MediaType.APPLICATION_JSON})
    public Response like(int id)
    {
        Map<String, String> response = new HashMap<>();
        boolean outcome = this.commentService.like(id);
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
        boolean outcome = this.commentService.dislike(id);
        if(!outcome)
        {
            response.put("message", "Dislike failed.");
            return Response.status(422).entity(response).build();
        }
        response.put("message",  "Disliked successfully.");
        return Response.status(200).entity(response).build();
    }

}
