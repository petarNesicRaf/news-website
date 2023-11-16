package rs.raf.projekatispit.repository.category;

import rs.raf.projekatispit.entities.Category;
import rs.raf.projekatispit.repository.MySqlAbstractRepo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySqlCategoryRepo extends MySqlAbstractRepo implements CategoryRepo{
    @Override
    public List<Category> getAllCategories() {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        List<Category> allCategories = new ArrayList<>();

        try {
            connection = this.newConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(
                    "SELECT * FROM categories");
            while(resultSet.next())
            {
                allCategories.add(new Category(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("description")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return allCategories;
    }

    @Override
    public Category createCategory(Category category) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = this.newConnection();
            String[] generatedColumns = {"id"};

            preparedStatement = connection.prepareStatement(
                    "INSERT INTO categories (name, description) VALUES (?, ?)"
                    ,generatedColumns
            );
            preparedStatement.setString(1, category.getName().trim());
            preparedStatement.setString(2, category.getDescription().trim());

            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();
            if(resultSet.next())
            {
                category.setId(resultSet.getInt(1));
            }
        } catch (SQLException e) {
            return null;
        } finally {
            this.closeStatement(preparedStatement);
            this.closeConnection(connection);
        }
        return category;
    }

    @Override
    public boolean editCategory(Category category) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = this.newConnection();
            preparedStatement = connection.prepareStatement(
                    "UPDATE categories SET name = ?, description = ? WHERE id = ?"
            );
            preparedStatement.setString(1,category.getName());
            preparedStatement.setString(2,category.getDescription());
            preparedStatement.setInt(3, category.getId());
            preparedStatement.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }finally {
            this.closeStatement(preparedStatement);
            this.closeConnection(connection);
        }
    }

    @Override
    public Category findCategory(int id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Category category = null;

        try {
            connection = this.newConnection();
            preparedStatement = connection.prepareStatement(
                    "SELECT * FROM categories WHERE id = ?");

            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next())
            {
                category = new Category(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("description"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }
        return category;
    }

    @Override
    public boolean deleteCategory(int id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        ResultSet resultSet = null;
        PreparedStatement checkStatement = null;

        try {
            connection = this.newConnection();
            //todo: check if category has news, prevent delete if true
            checkStatement = connection.prepareStatement(
                    "SELECT * FROM news WHERE id_category = ?");
            checkStatement.setInt(1,id);
            resultSet = checkStatement.executeQuery();
            if (resultSet.isBeforeFirst() ) { //ako result set nije prazan ne brisi
                return false;
            }


            preparedStatement = connection.prepareStatement(
                    "DELETE FROM categories WHERE id = ?"
            );
            preparedStatement.setInt(1,id);
            preparedStatement.executeUpdate();
            return true;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
