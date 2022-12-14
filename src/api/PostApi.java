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

    public static final Route getAllPosts = (req, res) -> {
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

    public static final Route getPost = (req, res) -> {
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

    public static final Route postPost = (req, res) -> {
        try {
            Post post = mapper.readValue(req.body(), Post.class);
            postDao.create(post.getId(), post.getAuthor(),
                    post.getTimePosted(), post.getContent());
            res.status(201);
            return mapper.writeValueAsString(post);
        }
        catch (DaoException e) {
            halt(500, "Internal Server Error");
            return "";
        }
        catch (Exception e) {
            halt(400, "Bad Request");
            return "";
        }
    };

    public static final Route putPost = (req, res) -> {
        String id = req.params("id");
        try {
            Post post = mapper.readValue(req.body(), Post.class);
            postDao.update(id, post.getContent());
            return mapper.writeValueAsString(post);
        }
        catch (DaoException e) {
            halt(500, "Internal Server Error");
            return "";
        }
        catch (Exception e) {
            halt(400, "Bad Request");
            return "";
        }
    };

    public static final Route deletePost = (req, res) -> {
        String id = req.params("id");
        try {
            Post post = postDao.read(id);
            if (post == null || !postDao.delete(id)) {
                halt(404, "Not Found");
                return "";
            }
            return mapper.writeValueAsString(post);
        } catch (DaoException e) {
            halt(500, "Internal Server Error");
            return "";
        }
    };
    
}
