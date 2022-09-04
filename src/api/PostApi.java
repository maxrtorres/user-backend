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
        String author = req.queryParams("author");
        try {
            if (author == null) {
                return mapper.writeValueAsString(postDao.readAll());
            }
            return mapper.writeValueAsString(
                    postDao.readAllByAuthor(author));
        }
        catch (DaoException e) {
            halt(500,"Internal Server Error");
            return "";
        }
    };
}
