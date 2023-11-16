package rs.raf.projekatispit.services;

import rs.raf.projekatispit.entities.Comment;
import rs.raf.projekatispit.repository.comment.CommentRepo;

import javax.inject.Inject;
import java.util.List;

public class CommentService {
    @Inject
    CommentRepo commentRepo;

    public List<Comment> getCommentsByNews(int idNews)
    {
        return this.commentRepo.getCommentsByNews(idNews);
    }

    public Comment createComment(Comment comment)
    {
        return this.commentRepo.createComment(comment);
    }

    public boolean like(int id) {return this.commentRepo.like(id);}

    public boolean dislike(int id) {return this.commentRepo.dislike(id);}
}
