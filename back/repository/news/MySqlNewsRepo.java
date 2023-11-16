package rs.raf.projekatispit.repository.news;

import rs.raf.projekatispit.entities.News;
import rs.raf.projekatispit.repository.MySqlAbstractRepo;
import rs.raf.projekatispit.repository.user.UserRepo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySqlNewsRepo extends MySqlAbstractRepo implements NewsRepo {
    private String foundTitle = null;

    @Override
    public List<News> getAllNews() {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        List<News> allNews = new ArrayList<>();

        try {
            connection = this.newConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(
                    "SELECT * FROM news ORDER BY date_time");

            while(resultSet.next())
            {
                allNews.add(new News(
                        resultSet.getInt("id"),
                        resultSet.getString("title"),
                        resultSet.getString("text"),
                        resultSet.getDate("date_time"),
                        resultSet.getString("author"),
                        resultSet.getInt("id_category"),
                        resultSet.getInt("readers")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return allNews;
    }

    @Override
    public List<News> getNewsByCategory(int id_category) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<News> allNews = new ArrayList<>();

        try {
            connection = this.newConnection();
            preparedStatement = connection.prepareStatement(
                    "SELECT * FROM news WHERE id_category = ? ORDER BY date_time ");

            preparedStatement.setInt(1,id_category);

            resultSet = preparedStatement.executeQuery();

            while(resultSet.next())
            {
                allNews.add(new News(
                        resultSet.getInt("id"),
                        resultSet.getString("title"),
                        resultSet.getString("text"),
                        resultSet.getDate("date_time"),
                        resultSet.getString("author"),
                        resultSet.getInt("id_category"),
                        resultSet.getInt("readers")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return allNews;
    }


    @Override
    public News createNews(News news) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = this.newConnection();
            String[] generatedColumns = {"id"};

            preparedStatement = connection.prepareStatement(
                    "INSERT INTO news (title, text, date_time, author,  id_category) VALUES (?, ?,CURRENT_DATE, ?,?)"
                    , generatedColumns
            );
            preparedStatement.setString(1, news.getTitle().trim());
            preparedStatement.setString(2, news.getText().trim());
            preparedStatement.setString(3, news.getAuthor().trim());
            preparedStatement.setInt(4, news.getIdCategory());

            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                news.setId(resultSet.getInt(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return news;

    }

    @Override
    public boolean editNews(News news) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = this.newConnection();
            String[] generatedColumns = {"id"};

            preparedStatement = connection.prepareStatement(
                    "UPDATE news SET title = ?, text = ?,  author = ?,  id_category = ? WHERE id = ?"
                    ,generatedColumns
            );
            preparedStatement.setString(1, news.getTitle());
            preparedStatement.setString(2, news.getText());
            preparedStatement.setString(3, news.getAuthor());
            preparedStatement.setInt(4, news.getIdCategory());
            preparedStatement.setInt(5, news.getId());

            preparedStatement.executeUpdate();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public News findnews(int id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        News news = null;

        try {
            connection = this.newConnection();
            preparedStatement = connection.prepareStatement(
                    "SELECT * FROM news WHERE id = ?");

            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next())
            {
                news = new News(
                        resultSet.getInt("id"),
                        resultSet.getString("title"),
                        resultSet.getString("text"),
                        resultSet.getDate("date_time"),
                        resultSet.getString("author"),
                        resultSet.getInt("id_category"),
                        resultSet.getInt("readers"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return news;
        }finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }
        return news;
    }

    @Override
    public boolean deleteNews(int idNews) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection =this.newConnection();

            preparedStatement = connection.prepareStatement(
                    "DELETE FROM comments WHERE id_news = ?"
            );
            preparedStatement.setInt(1,idNews);
            preparedStatement.executeUpdate();

            preparedStatement = connection.prepareStatement(
                    "SET FOREIGN_KEY_CHECKS = 0"
            );
            preparedStatement.executeUpdate();

            preparedStatement = connection.prepareStatement(
                    " DELETE FROM news WHERE id = ?"
            );
            preparedStatement.setInt(1,idNews);
            preparedStatement.executeUpdate();

            preparedStatement = connection.prepareStatement(
                    "SET FOREIGN_KEY_CHECKS = 1"
            );
            preparedStatement.executeUpdate();

            preparedStatement = connection.prepareStatement(
                    "DELETE FROM tags WHERE id_news = ?"
            );
            preparedStatement.setInt(1,idNews);
            preparedStatement.executeUpdate();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<News> getMostRead() {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        List<News> allNews = new ArrayList<>();

        try {
            connection = this.newConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(
                    "SELECT * FROM news WHERE date_time >= CURRENT_DATE - INTERVAL 30 DAY ORDER BY readers DESC LIMIT 10");

            while(resultSet.next())
            {
                allNews.add(new News(
                        resultSet.getInt("id"),
                        resultSet.getString("title"),
                        resultSet.getString("text"),
                        resultSet.getDate("date_time"),
                        resultSet.getString("author"),
                        resultSet.getInt("id_category"),
                        resultSet.getInt("readers")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return allNews;
    }

    @Override
    public List<News> getSearch(String query) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<News> newsMatch = new ArrayList<>();
        String queryReady = "%"+query+"%";
        try {
            connection = this.newConnection();
            preparedStatement = connection.prepareStatement(
                    "SELECT * FROM news WHERE title LIKE ? OR text LIKE ?");

            preparedStatement.setString(1,queryReady);
            preparedStatement.setString(2,queryReady);

            resultSet = preparedStatement.executeQuery();

            while(resultSet.next())
            {
                newsMatch.add(new News(
                        resultSet.getInt("id"),
                        resultSet.getString("title"),
                        resultSet.getString("text"),
                        resultSet.getDate("date_time"),
                        resultSet.getString("author"),
                        resultSet.getInt("id_category"),
                        resultSet.getInt("readers")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return newsMatch;
    }

    @Override
    public List<News> getNewest() {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        List<News> allNews = new ArrayList<>();

        try {
            connection = this.newConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(
                    "SELECT * FROM news ORDER BY date_time DESC LIMIT 10");

            while(resultSet.next())
            {
                allNews.add(new News(
                        resultSet.getInt("id"),
                        resultSet.getString("title"),
                        resultSet.getString("text"),
                        resultSet.getDate("date_time"),
                        resultSet.getString("author"),
                        resultSet.getInt("id_category"),
                        resultSet.getInt("readers")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return allNews;
    }

    @Override
    public boolean incrementReadNumber(int id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement(
                    "UPDATE news SET readers = readers + 1 WHERE id = ?"
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
    public boolean like(int id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement(
                    "UPDATE news SET likes = likes + 1 WHERE id = ?"
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
                    "UPDATE news SET dislikes = dislikes + 1 WHERE id = ?"
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
    public List<News> getMostInteracted() {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        List<News> allNews = new ArrayList<>();

        try {
            connection = this.newConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(
                    "SELECT * FROM news ORDER BY (likes + dislikes) DESC LIMIT 3");

            while(resultSet.next())
            {
                allNews.add(new News(
                        resultSet.getInt("id"),
                        resultSet.getString("title"),
                        resultSet.getString("text"),
                        resultSet.getDate("date_time"),
                        resultSet.getString("author"),
                        resultSet.getInt("id_category"),
                        resultSet.getInt("readers")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return allNews;
    }

    @Override
    public List<News> getAllNewsPaginated(int page) {
        Connection connection = null;
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        List<News> allNews = new ArrayList<>();
        int offset=(page-1)*10;

        try {
            connection = this.newConnection();
            preparedStatement = connection.prepareStatement(
                    "SELECT * FROM news ORDER BY date_time LIMIT 10 OFFSET ?"
            );
            preparedStatement.setInt(1, offset);
            resultSet=preparedStatement.executeQuery();

            while(resultSet.next())
            {
                allNews.add(new News(
                        resultSet.getInt("id"),
                        resultSet.getString("title"),
                        resultSet.getString("text"),
                        resultSet.getDate("date_time"),
                        resultSet.getString("author"),
                        resultSet.getInt("id_category"),
                        resultSet.getInt("readers")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return allNews;
    }

    @Override
    public List<News> getNewsByCategoryPaginated(int page, int id_category) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<News> allNews = new ArrayList<>();
        int offset=(page-1)*10;

        try {
            connection = this.newConnection();
            preparedStatement = connection.prepareStatement(
                    "SELECT * FROM news WHERE id_category = ? ORDER BY date_time  LIMIT 10 OFFSET ?");

            preparedStatement.setInt(1,id_category);
            preparedStatement.setInt(2,offset);

            resultSet = preparedStatement.executeQuery();

            while(resultSet.next())
            {
                allNews.add(new News(
                        resultSet.getInt("id"),
                        resultSet.getString("title"),
                        resultSet.getString("text"),
                        resultSet.getDate("date_time"),
                        resultSet.getString("author"),
                        resultSet.getInt("id_category"),
                        resultSet.getInt("readers")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return allNews;
    }

    @Override
    public List<News> getSearchPaginated(int page, String query) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<News> newsMatch = new ArrayList<>();
        String queryReady = "%"+query+"%";
        int offset=(page-1)*10;

        try {
            connection = this.newConnection();
            preparedStatement = connection.prepareStatement(
                    "SELECT * FROM news WHERE title LIKE ? OR text LIKE ? LIMIT 10 OFFSET ?");

            preparedStatement.setString(1,queryReady);
            preparedStatement.setString(2,queryReady);
            preparedStatement.setInt(3,offset);

            resultSet = preparedStatement.executeQuery();

            while(resultSet.next())
            {
                newsMatch.add(new News(
                        resultSet.getInt("id"),
                        resultSet.getString("title"),
                        resultSet.getString("text"),
                        resultSet.getDate("date_time"),
                        resultSet.getString("author"),
                        resultSet.getInt("id_category"),
                        resultSet.getInt("readers")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return newsMatch;
    }
}
