package rs.raf.projekatispit.repository.user;

import rs.raf.projekatispit.entities.User;
import rs.raf.projekatispit.requests.EditUserRequest;
import rs.raf.projekatispit.requests.LoginRequest;

import java.util.List;

public interface UserRepo {
    User login(LoginRequest loginRequest);

    User createUser(User user);

    List<User> getAllUsers();

    boolean editUser(EditUserRequest editUserRequest);

    User findUser(int id);

    User findUser(String email);
    String setStatus(int id);
    public User deleteUser(int id);
}
