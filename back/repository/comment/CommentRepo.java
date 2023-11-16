package rs.raf.projekatispit.repository.comment;

import rs.raf.projekatispit.entities.Comment;

import java.util.List;

public interface CommentRepo {
    List<Comment> getCommentsByNews(int idNews);

    Comment createComment(Comment comment);

    boolean like(int id);

    boolean dislike(int id);
}
