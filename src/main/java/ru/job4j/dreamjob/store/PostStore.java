package ru.job4j.dreamjob.store;


import ru.job4j.dreamjob.model.Post;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class PostStore {

    private static final PostStore INST = new PostStore();

    private final Map<Integer, Post> posts = new ConcurrentHashMap<>();

    private int ids = 4;


    private PostStore() {
        posts.put(1, new Post(
                1,
                "Junior Java Job",
                "without experience",
                "23.03.2022")
        );
        posts.put(2, new Post(2,
                        "Middle Java Job",
                        "1-3 years of experience",
                        "23.03.2022")
        );
        posts.put(3, new Post(
                3,
                "Senior Java Job",
                "3 and more years of experience",
                "23.03.2022")
        );
    }

    public static PostStore instOf() {
        return INST;
    }

    public Collection<Post> findAll() {
        return posts.values();
    }

    public Post add(Post post) {
        post.setId(ids);
        Post result = posts.putIfAbsent(ids, post);
        if (result != null) {
            ids++;
        }
        return result;
    }


    public Post findById(int id) {
        return posts.getOrDefault(id, add(
                new Post(ids++, "", "", "")));
    }


    public Post create() {
        Post result = new Post(ids, "", "", "");
        posts.put(ids++, result);
        return result;

    }

    public void update(Post post) {
        posts.put(post.getId(), post);
    }
}