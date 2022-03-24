package ru.job4j.dreamjob.store;

import ru.job4j.dreamjob.model.Candidate;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CandidateStore {

    private static final CandidateStore INST = new CandidateStore();

    private final Map<Integer, Candidate> candidates = new ConcurrentHashMap<>();

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
}