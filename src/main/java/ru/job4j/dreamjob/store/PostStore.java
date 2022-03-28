package ru.job4j.dreamjob.store;


import ru.job4j.dreamjob.model.Post;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class PostStore {

    private static final PostStore INST = new PostStore();

    private final Map<Integer, Post> posts = new ConcurrentHashMap<>();

    private int ids = 1;

    private PostStore() {
        posts.put(ids,
                new Post(
                ids++,
                "Junior Java Job",
                "without experience",
                "23.03.2022")
        );
        posts.put(
                ids,
                new Post(ids++,
                        "Middle Java Job",
                        "1-3 years of experience",
                        "23.03.2022")
        );
        posts.put(ids,
                new Post(
                ids++,
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

    public void add(Integer id, Post post) {
        posts.putIfAbsent(id, post);
    }
    public Post findById(int id) {
        return posts.getOrDefault(id, new Post(
                0, "", "", ""
        ));
    }

    public void create(Post post) {
        posts.put(ids, new Post(ids++, "", "", ""));
    }

    public void update(Post post) {
        posts.put(post.getId(), post);
    }
}