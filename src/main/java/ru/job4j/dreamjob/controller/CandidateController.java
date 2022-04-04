package ru.job4j.dreamjob.controller;

import net.jcip.annotations.ThreadSafe;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.job4j.dreamjob.model.Candidate;
import ru.job4j.dreamjob.persistence.CandidateStore;
import ru.job4j.dreamjob.service.CandidateService;

import java.io.IOException;

@ThreadSafe
@Controller
public class CandidateController {
    private final CandidateService candidateService;

    public CandidateController(CandidateService candidateService) {
        this.candidateService = candidateService;
    }


    @GetMapping("/candidates")
    public String candidates(Model model) {
        model.addAttribute("candidates", candidateService.findAll());
        return "candidates";
    }

    @GetMapping("/addCandidate")
    public String addCandidate(Model model) {
        model.addAttribute("candidate", new Candidate());
        return "addCandidate";
    }

    @GetMapping("/formAddCandidate")
    public String formAddCandidate(Model model) {
        return "addCandidate";
    }

    @GetMapping("/formUpdateCandidate/{candidateId}")
    public String formUpdateCandidate(Model model, @PathVariable("candidateId") int id) {
        model.addAttribute("candidate", candidateService.findById(id));
        return "updateCandidate";
    }

    @PostMapping("/updateCandidate")
    public String updateCandidate(@ModelAttribute Candidate candidate,
                                  @RequestParam("file") MultipartFile file) throws IOException {
        candidate.setPhoto(file.getBytes());
        candidateService.update(candidate);
        return "redirect:/candidates";
    }

    @PostMapping("/createCandidate")
    public String createCandidate(@ModelAttribute Candidate candidate,
                                  @RequestParam("file") MultipartFile file) throws IOException {
        candidate.setPhoto(file.getBytes());
        candidateService.add(candidate);
        return "redirect:/candidates";
    }

    /**
     * Контроллер загружает фотографию
     * Здесь используется класс ResponseEntity для формирования ответа.
     *
     *В нем нужно указать тип ответа content-type для любого файла это будет "application/octet-stream".
     *
     * Когда браузер загружает тег img он отправляет новый запрос на сервер по адресу указанному в атрибуте src.
     *
     * Сервер преобразует массив байт в строку в кодировке BASE64. В свою очередь браузер преобразует ее в изображение.
     *
     * @param candidateId
     * @return
     */
    @GetMapping("/photoCandidate/{candidateId}")
    public ResponseEntity<Resource> download(@PathVariable("candidateId") Integer candidateId) {
        Candidate candidate = candidateService.findById(candidateId);
        return ResponseEntity.ok()
                .headers(new HttpHeaders())
                .contentLength(candidate.getPhoto().length)
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(new ByteArrayResource(candidate.getPhoto()));
    }
}
