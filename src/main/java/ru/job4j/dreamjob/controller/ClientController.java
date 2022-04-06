package ru.job4j.dreamjob.controller;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.job4j.dreamjob.model.Client;
import ru.job4j.dreamjob.service.ClientService;

import java.util.Optional;

@ThreadSafe
@Controller
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }


    @GetMapping("/clients")
    public String users(Model model) {
        model.addAttribute("clients", clientService.findAll());
        return "clients";
    }

    @GetMapping("/addClient")
    public String addClient(Model model) {
        model.addAttribute("clients", clientService.findAll());
        return "addClient";
    }

    @GetMapping("/failClient")
    public String failClient(Model model) {
        return "failClient";
    }

    @PostMapping("/failRedirect")
    public String failRedirect(Model model) {
        return "redirect:/clients";
    }


    @PostMapping("/registrationClient")
    public String registration(Model model, @ModelAttribute Client client) {
        Optional<Client> regUser = clientService.add(client);
        if (regUser.isEmpty()) {
            model.addAttribute("message", "Пользователь с такой почтой уже существует");
            return "redirect:/failClient";
        }
        return "redirect:/clients";
    }
}

