package api;

import com.fasterxml.jackson.databind.ObjectMapper;
import dao.PostDao;
import exceptions.DaoException;
import model.Post;
import spark.Route;
import java.util.List;
import static spark.Spark.halt;

public class PostApi {
    public static PostDao postDao;
    public static ObjectMapper mapper;

    public static Route getAllPosts = (req, res) -> {
        try {
            List<Post> posts = postDao.readAll();
            return mapper.writeValueAsString(posts);
        }
        catch (DaoException e) {
            halt(500,"Internal Server Error");
            return "";
        }
    };
}
