package ru.job4j.dreamjob.store;

import ru.job4j.dreamjob.model.Candidate;
import ru.job4j.dreamjob.model.Post;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CandidateStore {

    private static final CandidateStore INST = new CandidateStore();

    private final Map<Integer, Candidate> candidates = new ConcurrentHashMap<>();

    private int ids = 4;

    private CandidateStore() {
        candidates.put(1, new Candidate(
                1,
                "Sergei",
                "2 years of java developer experience",
                "23.03.2022")
        );
        candidates.put(
                2,
                new Candidate(2,
                        "Alex",
                        "without experience",
                        "23.03.2022")
        );
        candidates.put(3, new Candidate(
                3,
                "Max",
                "5 years of java developer experience",
                "23.03.2022")
        );
    }

    public static CandidateStore instOf() {
        return INST;
    }

    public Collection<Candidate> findAll() {
        return candidates.values();
    }

    public Candidate add(Candidate candidate) {
        candidate.setId(ids);
        Candidate result = candidates.putIfAbsent(ids, candidate);
        if (result != null) {
            ids++;
        }
        return result;
    }


    public Candidate findById(int id) {
        return candidates.getOrDefault(id, add(
                new Candidate(ids++, "", "", "")));
    }


    public Candidate create() {
        Candidate result = new Candidate(ids, "", "", "");
        candidates.put(ids++, result);
        return result;

    }

    public void update(Candidate candidate) {
        candidates.put(candidate.getId(), candidate);
    }
}
