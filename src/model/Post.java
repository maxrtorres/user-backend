package model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.sql.Timestamp;
import java.util.UUID;

public class Post {
    private final String id;
    private final String author;
    private final Timestamp timePosted;
    private final String content;

    public Post() {
        this.id = "";
        this.author = "";
        this.timePosted = new Timestamp(0);
        this.content = "";
    }

    public Post(
            @JsonProperty("author") String author,
            @JsonProperty("timePosted") String timePosted,
            @JsonProperty("content") String content) {
        this.id = UUID.randomUUID().toString();
        this.author = author;
        this.timePosted = Timestamp.valueOf(timePosted);
        this.content = content;
    }

    public Post(String id, String author, String timePosted, String content) {
        this.id = id;
        this.author = author;
        this.timePosted = Timestamp.valueOf(timePosted);
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public String getAuthor() {
        return author;
    }

    public String getTimePosted() {
        return timePosted.toString();
    }

    public String getContent() {
        return content;
    }

    @Override
    public String toString() {
        return String.format("(%s, %s, %s, %s)",
                id, author, timePosted.toString(), content);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Post)) return false;
        Post otherPost = (Post) o;
        return id.equals(otherPost.id)
                && author.equals(otherPost.author)
                && timePosted.equals(otherPost.timePosted)
                && content.equals(otherPost.content);
    }
}
