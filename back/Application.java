package rs.raf.projekatispit;

import org.glassfish.jersey.internal.inject.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import rs.raf.projekatispit.repository.category.CategoryRepo;
import rs.raf.projekatispit.repository.category.MySqlCategoryRepo;
import rs.raf.projekatispit.repository.comment.CommentRepo;
import rs.raf.projekatispit.repository.comment.MySqlCommentRepo;
import rs.raf.projekatispit.repository.news.MySqlNewsRepo;
import rs.raf.projekatispit.repository.news.NewsRepo;
import rs.raf.projekatispit.repository.tag.MySqlTagRepo;
import rs.raf.projekatispit.repository.tag.TagRepo;
import rs.raf.projekatispit.repository.user.MySqlUserRepo;
import rs.raf.projekatispit.repository.user.UserRepo;
import rs.raf.projekatispit.services.*;

import javax.inject.Singleton;
import javax.ws.rs.ApplicationPath;

@ApplicationPath("/api")
public class Application extends ResourceConfig {
    public Application(){
        AbstractBinder binder=new AbstractBinder() {
            @Override
            protected void configure() {
                this.bind(MySqlCategoryRepo.class).to(CategoryRepo.class).in(Singleton.class);
                this.bindAsContract(CategoryService.class);

                this.bind(MySqlUserRepo.class).to(UserRepo.class).in(Singleton.class);
                this.bindAsContract(UserService.class);

                this.bind(MySqlNewsRepo.class).to(NewsRepo.class).in(Singleton.class);
                this.bindAsContract(NewsService.class);

                this.bind(MySqlCommentRepo.class).to(CommentRepo.class).in(Singleton.class);
                this.bindAsContract(CommentService.class);

                this.bind(MySqlTagRepo.class).to(TagRepo.class).in(Singleton.class);
                this.bindAsContract(TagService.class);
            }
        };
        register(binder);
        packages("rs.raf.projekatispit");
    }
}