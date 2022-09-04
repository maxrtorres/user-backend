package api;

import com.fasterxml.jackson.databind.ObjectMapper;
import dao.PostDao;
import exceptions.DaoException;
import model.Post;
import spark.Route;
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

    public static Route getPost = (req, res) -> {
        String id = req.params("id");
        try {
            Post post = postDao.read(id);
            if (post == null)  {
                halt(404, "Not Found");
                return "";
            }
            return mapper.writeValueAsString(post);
        }
        catch (DaoException e) {
            halt(500, "Internal Server Error");
            return "";
        }
    };

    public static Route postPost = (req, res) -> {
        return "";
    };

    public static Route putPost = (req, res) -> {
        return "";
    };

    public static Route deletePost = (req, res) -> {
        return "";
    };
}
