package rs.raf.projekatispit.resources;

import rs.raf.projekatispit.entities.User;
import rs.raf.projekatispit.requests.EditUserRequest;
import rs.raf.projekatispit.requests.LoginRequest;
import rs.raf.projekatispit.services.UserService;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;

@Path("/users")
public class UserResource {
    @Inject
    private UserService userService;

    @POST
    @Path("/login")
    @Produces({MediaType.APPLICATION_JSON})
    public Response login(@Valid LoginRequest loginRequest)
    {
        Map<String, String> response = new HashMap<>();
        String jwt = this.userService.login(loginRequest);
        if(jwt == null)
        {
            response.put("message", "These credentials do not match out records (Login)");
            return Response.status(422, "Unprocessable entity").entity(response).build();
        }
        response.put("jwt", jwt);
        return Response.ok(response).build();
    }

    @POST
    @Path("/create")
    @Produces({MediaType.APPLICATION_JSON})
    public Response createUser(@Valid User user)
    {
        Map<String, String> response = new HashMap<>();
        String jwt = this.userService.createUser(user);
        if(jwt == null)
        {
            response.put("message", "These credentials do not match out records (Login)");
            return Response.status(422, "Unprocessable entity").entity(response).build();
        }
        response.put("jwt", jwt);
        return Response.ok(response).build();
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response getAllUsers()
    {
        return Response.ok(this.userService.getAllUsers()).build();
    }

    @GET
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response findUser(@PathParam("id") int id)
    {
        Map<String, String> response = new HashMap<>();
        User user = this.userService.findUser(id);
        if(user == null)
        {
            response.put("message", "User with this email doesn't exist");
            return Response.status(404, "Not found").entity(response).build();
        }

        return Response.ok(user).build();
    }

    @POST
    @Path("/editUser")
    @Produces({MediaType.APPLICATION_JSON})
    public Response editUser(@Valid EditUserRequest editUserRequest)
    {
        Map<String, String> response = new HashMap<>();
        boolean outcome = this.userService.editUser(editUserRequest);
        if(!outcome)
        {
            response.put("message", "User modification failed.");
            return Response.status(422).entity(response).build();
        }
        response.put("message",  "User successfully modified.");
        return Response.status(200).entity(response).build();
    }

    @POST
    @Path("/status")
    @Produces({MediaType.APPLICATION_JSON})
    public Response setStatus(int id)
    {
        Map<String, String> response = new HashMap<>();

        String outcome = this.userService.setStatus(id);
        if(outcome == null)
        {
            response.put("message", "Status modification failed.");
            return Response.status(422).entity(response).build();
        }
        response.put("message",  "Status successfully modified.");
        return Response.status(200).entity(response).build();
    }
}
