package rs.raf.projekatispit.repository.category;

import rs.raf.projekatispit.entities.Category;

import java.util.List;

public interface CategoryRepo {
    List<Category> getAllCategories();
    Category createCategory(Category category);
    boolean editCategory(Category category);
    Category findCategory(int id);
    boolean deleteCategory(int id);
}
