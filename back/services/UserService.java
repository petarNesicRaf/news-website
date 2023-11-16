package rs.raf.projekatispit.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.apache.commons.codec.digest.DigestUtils;
import rs.raf.projekatispit.entities.User;
import rs.raf.projekatispit.repository.user.UserRepo;
import rs.raf.projekatispit.requests.EditUserRequest;
import rs.raf.projekatispit.requests.LoginRequest;

import javax.inject.Inject;
import java.util.Date;
import java.util.List;

public class UserService {
    @Inject
    UserRepo userRepo;

    public String login(LoginRequest loginRequest)
    {
        String hashedPassword = DigestUtils.sha256Hex(loginRequest.getPassword());
        loginRequest.setPassword(hashedPassword);

        User user = this.userRepo.login(loginRequest);
        if(user == null || !user.getPassword().equals(hashedPassword))
        {
            return null;
        }
        Date issuedAt = new Date();
        Date expiresAt = new Date(issuedAt.getTime() + 24*60*60*1000);

        Algorithm algorithm = Algorithm.HMAC256("secret");

        return JWT.create()
                .withIssuedAt(issuedAt)
                .withExpiresAt(expiresAt)
                .withSubject(loginRequest.getEmail())
                .withClaim("role", user.getRole())
                .withClaim("status", user.getStatus())
                .withClaim("info",user.getName()+" "+user.getSurname())
                .sign(algorithm);
    }

    public String createUser(User user)
    {
        String hashedPassword = DigestUtils.sha256Hex(user.getPassword());
        user.setPassword(hashedPassword);
        User createdUser = userRepo.createUser(user);
        if(createdUser == null)
        {
            return null;
        }

        Date issuedAt = new Date();
        Date expiresAt = new Date(issuedAt.getTime() + 24*60*60*1000);

        Algorithm algorithm = Algorithm.HMAC256("secret");

        return JWT.create()
                .withIssuedAt(issuedAt)
                .withExpiresAt(expiresAt)
                .withSubject(createdUser.getEmail())
                .withClaim("role", createdUser.getRole())
                .sign(algorithm);
    }

    public List<User> getAllUsers()
    {
        return this.userRepo.getAllUsers();
    }

    public User findUser(int id)
    {
        return this.userRepo.findUser(id);
    }

    public boolean editUser(EditUserRequest editUserRequest)
    {
        String hashedPassword = DigestUtils.sha256Hex(editUserRequest.getPassword());
        editUserRequest.setPassword(hashedPassword);
        return this.userRepo.editUser(editUserRequest);
    }
    public String setStatus(int id)
    {
        return this.userRepo.setStatus(id);
    }

    public boolean isAuthorized(String token)
    {
        Algorithm algorithm = Algorithm.HMAC256("secret");
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT jwt = verifier.verify(token);

        String username = jwt.getSubject();
        User user = this.userRepo.findUser(username);
        if(jwt.getClaim("role").asString().equals("CONTENT_CREATOR"))
        {
            return false;
        }

        if(user == null)
            return false;

        return true;
    }
}
