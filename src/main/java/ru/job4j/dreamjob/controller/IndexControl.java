package ru.job4j.dreamjob.controller;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.job4j.dreamjob.model.Client;

import javax.servlet.http.HttpSession;

@ThreadSafe
@Controller
public class IndexControl {

    @GetMapping("/index")
    public String index(Model model, HttpSession session) {
        Client client = (Client) session.getAttribute("client");
        if (client == null) {
            client = new Client();
            client.setName("Гость");
        }
        model.addAttribute("client", client);
        return "index";
    }
}
