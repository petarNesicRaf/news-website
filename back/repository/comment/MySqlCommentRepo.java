package rs.raf.projekatispit.repository.comment;

import rs.raf.projekatispit.entities.Comment;
import rs.raf.projekatispit.repository.MySqlAbstractRepo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MySqlCommentRepo extends MySqlAbstractRepo implements CommentRepo {
    @Override
    public List<Comment> getCommentsByNews(int idNews) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Comment> allComments = new ArrayList<>();

        try {
            connection = this.newConnection();
            preparedStatement = connection.prepareStatement(
                    "SELECT * FROM comments WHERE id_news = ? ");

            preparedStatement.setInt(1,idNews);

            resultSet = preparedStatement.executeQuery();

            while(resultSet.next())
            {
                allComments.add( new Comment(
                        resultSet.getInt("id"),
                        resultSet.getString("author"),
                        resultSet.getString("comment"),
                        resultSet.getInt("id_news")

                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return allComments;
    }

    @Override
    public Comment createComment(Comment comment) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = this.newConnection();
            String[] generatedColumns = {"id"};

            preparedStatement = connection.prepareStatement(
                    "INSERT INTO comments (author, comment, id_news) VALUES (?,?,?)"
                    , generatedColumns
            );
            preparedStatement.setString(1, comment.getAuthor().trim());
            preparedStatement.setString(2, comment.getComment().trim());
            preparedStatement.setInt(3, comment.getIdNews());

            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                comment.setId(resultSet.getInt(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return comment;
    }

    @Override
    public boolean like(int id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement(
                    "UPDATE comments SET likes = likes + 1 WHERE id = ?"
            );
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }finally {
            this.closeStatement(preparedStatement);
            this.closeConnection(connection);
        }
        return true;
    }

    @Override
    public boolean dislike(int id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement(
                    "UPDATE comments SET dislikes = dislikes + 1 WHERE id = ?"
            );
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }finally {
            this.closeStatement(preparedStatement);
            this.closeConnection(connection);
        }
        return true;
    }
}
