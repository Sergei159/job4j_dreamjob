package ru.job4j.dreamjob.persistence;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import ru.job4j.dreamjob.model.Candidate;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@ThreadSafe
@Repository
public class CandidateStore {

    private final Map<Integer, Candidate> candidates = new ConcurrentHashMap<>();

    private final AtomicInteger ids = new AtomicInteger(4);

    private CandidateStore() {
        candidates.put(1, new Candidate(
                1,
                "Sergei",
                "2 years of java developer experience")
        );
        candidates.put(
                2,
                new Candidate(2,
                        "Alex",
                        "without experience")
        );
        candidates.put(3, new Candidate(
                3,
                "Max",
                "5 years of java developer experience")
        );
    }

    public Collection<Candidate> findAll() {
        return candidates.values();
    }

    public Candidate add(Candidate candidate) {
        candidate.setId(ids.get());
        Candidate result = candidates.putIfAbsent(ids.get(), candidate);
        if (result != null) {
            ids.getAndIncrement();
        }
        return result;
    }


    public Candidate findById(int id) {
        return candidates.get(id);
    }

    public void update(Candidate candidate) {
        candidates.put(candidate.getId(), candidate);
    }
}
