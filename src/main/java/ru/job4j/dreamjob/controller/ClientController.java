package ru.job4j.dreamjob.controller;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.job4j.dreamjob.model.Candidate;
import ru.job4j.dreamjob.model.Client;
import ru.job4j.dreamjob.service.ClientService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

@ThreadSafe
@Controller
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }


    @GetMapping("/clients")
    public String users(Model model, HttpSession session) {
        Client client = (Client) session.getAttribute("client");
        if (client == null) {
            client = new Client();
            client.setName("Гость");
        }
        model.addAttribute("client", client);
        model.addAttribute("clients", clientService.findAll());
        return "clients";
    }

    @GetMapping("/addClient")
    public String addClient(Model model, HttpSession session) {
        Client client = (Client) session.getAttribute("client");
        if (client == null) {
            client = new Client();
            client.setName("Гость");
        }
        model.addAttribute("client", client);
        model.addAttribute("clients", clientService.findAll());
        return "addClient";
    }

    @GetMapping("/failClient")
    public String failClient(Model model) {
        return "failClient";
    }


    @GetMapping("/loginPage")
    public String loginPage(Model model, @RequestParam(name = "fail", required = false) Boolean fail) {
        model.addAttribute("fail", fail != null);
        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/index";
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

    @PostMapping("/login")
    public String login(@ModelAttribute Client client, HttpServletRequest req) {
        Optional<Client> clientDb = clientService.findUserByEmailAndPwd(
                client.getEmail(), client.getPassword()
        );
        if (clientDb.isEmpty()) {
            return "redirect:/loginPage?fail=true";
        }
        HttpSession session = req.getSession();
        session.setAttribute("client", clientDb.get());
        return "redirect:/index";
    }
}

