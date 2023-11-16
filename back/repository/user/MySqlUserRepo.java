package rs.raf.projekatispit.repository.user;


import rs.raf.projekatispit.entities.User;
import rs.raf.projekatispit.repository.MySqlAbstractRepo;
import rs.raf.projekatispit.requests.EditUserRequest;
import rs.raf.projekatispit.requests.LoginRequest;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySqlUserRepo extends MySqlAbstractRepo implements UserRepo {
    private String findEmail=null;

    @Override
    public User login(LoginRequest loginRequest) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        User user = null;

        try {
            connection = this.newConnection();
            preparedStatement = connection.prepareStatement(
                    "SELECT * FROM users WHERE email=? AND password=?"
            );
            preparedStatement.setString(1,loginRequest.getEmail());
            preparedStatement.setString(2,loginRequest.getPassword());
            resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                user = new User(
                        resultSet.getInt("id"),
                        resultSet.getString("email"),
                        resultSet.getString("name"),
                        resultSet.getString("surname"),
                        resultSet.getString("role"),
                        resultSet.getInt("status"),
                        resultSet.getString("password")
                );
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

        return user;

    }

    @Override
    public User createUser(User user) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = this.newConnection();

            String[] generatedColumns = {"id"};

            preparedStatement = connection.prepareStatement(
                    "INSERT INTO users (email, name, surname, role, status, password) VALUES (?,?,?, ?,?,?)",
                    generatedColumns
            );

            preparedStatement.setString(1,user.getEmail());
            preparedStatement.setString(2, user.getName());
            preparedStatement.setString(3,user.getSurname());
            preparedStatement.setString(4, user.getRole());
            preparedStatement.setInt(5, 1);
            preparedStatement.setString(6, user.getPassword());

            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();

            if(resultSet.next()){
                user.setId(resultSet.getInt(1));
            }
            if(resultSet == null)
            {

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }
        return user;
    }

    @Override
    public List<User> getAllUsers() {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        List<User> allUsers = new ArrayList<>();
        try {
            connection = this.newConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM users");
            while(resultSet.next())
            {
                allUsers.add(new User(
                        resultSet.getInt("id"),
                        resultSet.getString("email"),
                        resultSet.getString("name"),
                        resultSet.getString("surname"),
                        resultSet.getString("role"),
                        resultSet.getInt("status"),
                        resultSet.getString("password")
                ));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            this.closeStatement(statement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }
        return allUsers;
    }

    //todo bool
    @Override
    public boolean editUser(EditUserRequest editUserRequest) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = this.newConnection();
            preparedStatement = connection.prepareStatement(
                    "UPDATE users SET email = ?, name = ?, surname = ?, role = ?, password = ? WHERE id = ?"
            );
            preparedStatement.setString(1,editUserRequest.getEmail());
            preparedStatement.setString(2,editUserRequest.getName());
            preparedStatement.setString(3,editUserRequest.getSurname());
            preparedStatement.setString(4, editUserRequest.getRole());
            preparedStatement.setString(5,editUserRequest.getPassword());
            preparedStatement.setInt(6, editUserRequest.getId());
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
    public User findUser(int id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        User user = null;

        try {
            connection = this.newConnection();
            preparedStatement = connection.prepareStatement(
                    "SELECT * FROM users WHERE id = ?");

            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next())
            {
                user = new User(
                        resultSet.getInt("id"),
                        resultSet.getString("email"),
                        resultSet.getString("name"),
                        resultSet.getString("surname"),
                        resultSet.getString("role"),
                        resultSet.getInt("status"),
                        resultSet.getString("password")
                );
                findEmail=user.getEmail();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }
        return user;
    }
    @Override
    public User findUser(String email) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        User user = null;

        try {
            connection = this.newConnection();
            preparedStatement = connection.prepareStatement(
                    "SELECT * FROM users WHERE email = ?");

            preparedStatement.setString(1, email);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next())
            {
                user = new User(
                        resultSet.getInt("id"),
                        resultSet.getString("email"),
                        resultSet.getString("name"),
                        resultSet.getString("surname"),
                        resultSet.getString("role"),
                        resultSet.getInt("status"),
                        resultSet.getString("password")
                );
                this.findEmail = email;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }
        return user;
    }

    @Override
    public String setStatus(int id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        User user = null;
        String status = null;
        try {
            connection = this.newConnection();
            preparedStatement = connection.prepareStatement(
                    "UPDATE users SET status = CASE WHEN status = 1 THEN 0 WHEN status = 0 THEN 1 END WHERE id = ?"
            );
            preparedStatement.setInt(1,id);
            preparedStatement.executeUpdate();
            status = "success";
            return status;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            this.closeStatement(preparedStatement);
            this.closeConnection(connection);
        }
    }

    @Override
    public User deleteUser(int id) {
        return null;
    }
}
