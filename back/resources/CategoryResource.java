package rs.raf.projekatispit.resources;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import rs.raf.projekatispit.entities.Category;
import rs.raf.projekatispit.services.CategoryService;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path("/categories")
public class CategoryResource {
    @Inject
    CategoryService categoryService;

    @GET
    @Path("/allCategories")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getAllCategories()
    {
        Map<String, String> response = new HashMap<>();
        List<Category> allCategories = this.categoryService.getAllCategories();
        if(allCategories == null)
        {
            response.put("message", "No categories have been found");
            return Response.status(404, "Not found").entity(response).build();
        }

        return Response.ok(allCategories).build();
    }

    @POST
    @Path("/createCategory")
    @Produces({MediaType.APPLICATION_JSON})
    public Response createCategory(Category category)
    {
        Map<String, String> response = new HashMap<>();

        Category cat = this.categoryService.createCategory(category);
        if(cat!=null){
            return Response.ok(cat).build();
        }
        else{
            response.put("message", "Category creation failed. Check if category with this name already exists.");
            return Response.status(422).entity(response).build();
        }
    }
    @GET
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response findCategory(@PathParam("id") int id)
    {
        Map<String, String> response = new HashMap<>();

        Category category = this.categoryService.findCategory(id);
        if(category == null)
        {
            response.put("message", "Category with this name doesn't exist");
            return Response.status(404, "Not found").entity(response).build();
        }
        return Response.ok(category).build();
    }

    @POST
    @Path("/editCategory")
    @Produces({MediaType.APPLICATION_JSON})
    public Response editCategory(@Valid Category category)
    {
        Map<String, String> response = new HashMap<>();
        boolean outcome = this.categoryService.editCategory(category);
        if(!outcome)
        {
            response.put("message", "Category modification failed. Check if new category name is already in use.");
            return Response.status(422).entity(response).build();
        }
        response.put("message",  "Category successfully modified.");
        return Response.status(200).entity(response).build();
    }

    @POST
    @Path("/deleteCategory")
    @Produces({MediaType.APPLICATION_JSON})
    public Response deleteCategory(int id)
    {
        Map<String, String> response = new HashMap<>();

        boolean outcome = this.categoryService.deleteCategory(id);
        if(!outcome)
        {
            response.put("message", "Category removal failed. Check if category contains news.");
            return Response.status(422).entity(response).build();
        }
        response.put("message",  "Category successfully removed.");
        return Response.status(200).entity(response).build();

    }
}
