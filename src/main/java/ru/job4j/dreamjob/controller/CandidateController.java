package ru.job4j.dreamjob.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.job4j.dreamjob.model.Candidate;
import ru.job4j.dreamjob.persistence.CandidateStore;

@Controller
public class CandidateController {
    private final CandidateStore store;

    public CandidateController(CandidateStore store) {
        this.store = store;
    }

    @GetMapping("/candidates")
    public String candidates(Model model) {
        model.addAttribute("candidates", store.findAll());
        return "candidates";
    }

    @GetMapping("/addCandidate")
    public String addCandidate(Model model) {
        model.addAttribute("candidate", new Candidate(
                0, "Заполните поле", "Заполните поле", ""));
        return "addCandidate";
    }

    @GetMapping("/formAddCandidate")
    public String formAddCandidate(Model model) {
        return "addCandidate";
    }

    @GetMapping("/formUpdateCandidate/{candidateId}")
    public String formUpdateCandidate(Model model, @PathVariable("candidateId") int id) {
        model.addAttribute("candidate", store.findById(id));
        return "updateCandidate";
    }

    @PostMapping("/updateCandidate")
    public String updateCandidate(@ModelAttribute Candidate candidate) {
        store.update(candidate);
        return "redirect:/candidates";
    }

    @PostMapping("/createCandidate")
    public String createCandidate(@ModelAttribute Candidate candidate) {
        store.add(candidate);
        return "redirect:/candidates";
    }
}
