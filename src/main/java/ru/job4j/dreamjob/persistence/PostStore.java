package ru.job4j.dreamjob.persistence;


import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import ru.job4j.dreamjob.model.City;
import ru.job4j.dreamjob.model.Post;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@ThreadSafe
@Repository
public class PostStore {

    private final Map<Integer, Post> posts = new ConcurrentHashMap<>();

    private final AtomicInteger ids = new AtomicInteger(4);

    private final CityStore cityStore = new CityStore();


    private PostStore() {
        posts.put(1, new Post(
                1,
                "Junior Java Job",
                "without experience"
        ));
        posts.get(1).setCity(cityStore.findById(1));

        posts.put(2, new Post(2,
                        "Middle Java Job",
                        "1-3 years of experience"

        ));
        posts.get(2).setCity(cityStore.findById(2));

        posts.put(3, new Post(
                3,
                "Senior Java Job",
                "3 and more years of experience"
        ));
        posts.get(3).setCity(cityStore.findById(3));
    }


    public Collection<Post> findAll() {
        return posts.values();
    }

    public Post add(Post post) {
        post.setId(ids.get());
        Post result = posts.putIfAbsent(ids.get(), post);
        if (result != null) {
            ids.getAndIncrement();
        }
        return result;
    }


    public Post findById(int id) {
        return posts.get(id);
    }

    public void update(Post post) {
        posts.put(post.getId(), post);
    }
}