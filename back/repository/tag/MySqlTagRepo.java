package rs.raf.projekatispit.repository.tag;

import rs.raf.projekatispit.entities.News;
import rs.raf.projekatispit.entities.Tag;
import rs.raf.projekatispit.repository.MySqlAbstractRepo;
import rs.raf.projekatispit.requests.TagRequest;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MySqlTagRepo extends MySqlAbstractRepo implements TagRepo {

    @Override
    public List<News> getAllNewsWithTag(String keyword) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<News> allNews = new ArrayList<>();

        try {
            connection = this.newConnection();
            preparedStatement = connection.prepareStatement(
                    "SELECT n.* FROM news n JOIN tags t ON n.id = t.id_news WHERE t.tag = ?");

            preparedStatement.setString(1,keyword.toUpperCase());

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
            e.printStackTrace();
            return null;
        }
        return allNews;
    }

    @Override
    public List<String> getAllTagsFromNews(int idNews) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<String> allTags = new ArrayList<>();

        try {
            connection = this.newConnection();
            preparedStatement = connection.prepareStatement(
                    "SELECT * FROM tags WHERE id_news = ?");

            preparedStatement.setInt(1,idNews);

            resultSet = preparedStatement.executeQuery();

            while(resultSet.next())
            {
                allTags.add(resultSet.getString("tag"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return allTags;
    }

    @Override
    public boolean replaceTags(TagRequest tagRequest) {
        Connection connection = null;
        PreparedStatement deleteStatement = null;
        PreparedStatement insertStatement = null;

        try {
            connection = this.newConnection();

            deleteStatement = connection.prepareStatement(
                    "DELETE FROM tags WHERE id_news = ?");
            deleteStatement.setInt(1, tagRequest.getIdNews());
            deleteStatement.executeUpdate();

            String[] newTags = tagRequest.getAllTags().split(",");

            insertStatement = connection.prepareStatement(
                    "INSERT INTO tags (id_news, tag) VALUES (?, ?)");

            for (String newTag : newTags) {
                if(!newTag.equals("")){
                    insertStatement.setInt(1, tagRequest.getIdNews());
                    insertStatement.setString(2, newTag.trim().toUpperCase());
                    insertStatement.executeUpdate();
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            this.closeStatement(deleteStatement);
            this.closeStatement(insertStatement);
            this.closeConnection(connection);
        }
        return true;
    }

    @Override
    public List<News> readMore(int idNews) {
        List<News> newsList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        News news = null;

        try {
            connection = this.newConnection();
            preparedStatement = connection.prepareStatement("SELECT n.* FROM news n WHERE n.id IN (SELECT nt.id_news FROM tags nt WHERE nt.tag IN (SELECT tag FROM tags WHERE id_news = ?)) LIMIT 3");

            preparedStatement.setInt(1, idNews);

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                newsList.add(new News(
                        resultSet.getInt("id"),
                        resultSet.getString("title"),
                        resultSet.getString("text"),
                        resultSet.getDate("date_time"),
                        resultSet.getString("author"),
                        resultSet.getInt("id_category"),
                        resultSet.getInt("readers")));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            this.closeStatement(preparedStatement);
            this.closeConnection(connection);
        }
        return newsList;
    }
//    @Override
//    public List<Tag> getAllTags() {
//        Connection connection = null;
//        Statement statement = null;
//        ResultSet resultSet = null;
//        List<Tag> allTags = new ArrayList<>();
//
//        try {
//            connection = this.newConnection();
//            statement = connection.createStatement();
//            resultSet = statement.executeQuery(
//                    "SELECT * FROM tags");
//            while(resultSet.next())
//            {
//                allTags.add(new Tag(
//                        resultSet.getInt("id"),
//                        resultSet.getString("keyword")
//                ));
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//        return allTags;
//    }
//
//    @Override
//    public Tag createTag(Tag tag) {
//        Connection connection = null;
//        PreparedStatement preparedStatement = null;
//        ResultSet resultSet = null;
//
//        try {
//            connection = this.newConnection();
//            String[] generatedColumns = {"id"};
//
//            preparedStatement = connection.prepareStatement(
//                    "INSERT INTO tags (keyword) VALUES (?)"
//                    ,generatedColumns
//            );
//            preparedStatement.setString(1, tag.getKeyword().trim());
//
//            preparedStatement.executeUpdate();
//            resultSet = preparedStatement.getGeneratedKeys();
//            if(resultSet.next())
//            {
//                tag.setId(resultSet.getInt(1));
//            }
//        } catch (SQLException e) {
//            return null;
//        }
//        return tag;
//    }
//
//    @Override
//    public boolean addTagToNews(TagRequest tagRequest) {
//        Connection connection = null;
//        PreparedStatement preparedStatement = null;
//        ResultSet resultSet = null;
//
//        try {
//            connection = this.newConnection();
//            String[] generatedColumns = {"id"};
//            preparedStatement = connection.prepareStatement(
//                    "INSERT INTO news_tags (id_news, id_tag) VALUES (?,?)",
//                    generatedColumns
//            );
//            preparedStatement.setInt(1,tagRequest.getIdNews());
//            preparedStatement.setInt(2, tagRequest.getIdTag());
//            preparedStatement.executeUpdate();
//            return true;
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return false;
//        }
//
//    }
//
//    @Override
//    public List<Tag> getTagsByNews(int idNews) {
//        Connection connection = null;
//        PreparedStatement preparedStatement = null;
//        ResultSet resultSet = null;
//        ResultSet res = null;
//        List<Tag> tags = new ArrayList<>();
//        try {
//            connection = this.newConnection();
//            preparedStatement = connection.prepareStatement(
//                    "SELECT * FROM news_tags WHERE id_news =?"
//            );
//            preparedStatement.setInt(1,idNews);
//            resultSet = preparedStatement.executeQuery();
//
//            while(resultSet.next())
//            {
//                int idTag = resultSet.getInt(3);
//                preparedStatement =connection.prepareStatement(
//                        "SELECT * FROM tags WHERE id= ?"
//                );
//                preparedStatement.setInt(1,idTag);
//                res = preparedStatement.executeQuery();
//                if(res.next())
//                {
//                    tags.add(new Tag(
//                            res.getInt(1),
//                            res.getString(2)
//                    ));
//                }
//            }
//
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//        return tags;
//    }
//
//    @Override
//    public List<News> getNewsByTag(int idTag) {
//        Connection connection = null;
//        PreparedStatement preparedStatement = null;
//        ResultSet resultSet = null;
//        ResultSet res = null;
//        List<News> news = new ArrayList<>();
//
//        try {
//            connection = this.newConnection();
//            preparedStatement = connection.prepareStatement(
//                    "SELECT * FROM news_tags WHERE id_tag =?"
//            );
//            preparedStatement.setInt(1, idTag);
//            resultSet = preparedStatement.executeQuery();
//
//            while(resultSet.next())
//            {
//                int idNews = resultSet.getInt(2);
//                preparedStatement =connection.prepareStatement(
//                        "SELECT * FROM news WHERE id= ?"
//                );
//                preparedStatement.setInt(1,idNews);
//
//                res = preparedStatement.executeQuery();
//                if(res.next())
//                {
//
//                    news.add(new News(
//                            res.getInt("id"),
//                            res.getString("title"),
//                            res.getString("text"),
//                            res.getDate("date_time"),
//                            res.getString("author"),
//                            res.getInt("id_category"),
//                            res.getInt("readers")
//                    ));
//                }
//            }
//
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//        return news;
//
//    }


}
