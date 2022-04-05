package ru.job4j.dreamjob.persistence;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.stereotype.Repository;
import ru.job4j.dreamjob.model.Post;

import java.sql.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PostDBStore {

    private static final DateTimeFormatter FORMATTER
            = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");

    private final BasicDataSource pool;

    public PostDBStore(BasicDataSource pool) {
        this.pool = pool;
    }

    public List<Post> findAll() {
        List<Post> posts = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement("SELECT * FROM post")
        ) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    Post post = setPostData(it);
                    posts.add(post);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return posts;
    }


    public Post add(Post post) {
        Timestamp timestamp = Timestamp.valueOf(post.getCreated().format(FORMATTER));
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement(
                     "INSERT INTO post(name, description, created) VALUES (?, ?, ?)",
                     PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            ps.setString(1, post.getName());
            ps.setString(2, post.getDescription());
            ps.setTimestamp(3, timestamp);
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    post.setId(id.getInt(1));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return post;
    }

    public void update(Post post) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement(
                     "UPDATE post SET name = ?, description = ? WHERE id = ?",
                     PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            ps.setString(1, post.getName());
            ps.setString(2, post.getDescription());
            ps.setInt(3, post.getId());
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public Post findById(int id) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement("SELECT * FROM post WHERE id = ?")
        ) {
            ps.setInt(1, id);
            try (ResultSet it = ps.executeQuery()) {
                if (it.next()) {
                    return setPostData(it);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Post setPostData(ResultSet resultSet) throws SQLException {
        Post post = new Post(
                resultSet.getInt("id"),
                resultSet.getString("name"),
                resultSet.getString("description")
        );
        Timestamp timestamp = resultSet.getTimestamp("created");
        post.setCreated(timestamp.toLocalDateTime());
        return post;
    }
}
