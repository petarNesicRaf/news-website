package rs.raf.projekatispit.services;

import rs.raf.projekatispit.entities.Category;
import rs.raf.projekatispit.repository.category.CategoryRepo;

import javax.inject.Inject;
import java.util.List;

public class CategoryService {
    @Inject
    CategoryRepo categoryRepo;

    public List<Category> getAllCategories()
    {
        return this.categoryRepo.getAllCategories();
    }

    public Category createCategory(Category category)
    {
        return this.categoryRepo.createCategory(category);
    }

    public Category findCategory(int id)
    {
        return this.categoryRepo.findCategory(id);
    }

    public boolean editCategory(Category category)
    {
        return this.categoryRepo.editCategory(category);
    }

    public boolean deleteCategory(int id){
        return this.categoryRepo.deleteCategory(id);
    }
}
