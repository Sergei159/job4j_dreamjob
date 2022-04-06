package ru.job4j.dreamjob.service;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.dreamjob.model.Client;
import ru.job4j.dreamjob.persistence.ClientDBStore;

import java.util.Collection;
import java.util.Optional;

@ThreadSafe
@Service
public class ClientService {

    private final ClientDBStore store;

    public ClientService(ClientDBStore store) {
        this.store = store;

    }

    public Collection<Client> findAll() {
        return store.findAll();
    }

    public Optional<Client> add(Client client) {
        return store.add(client);
    }

    public void update(Client client) {
        store.update(client);
    }

    public Client findById(int id) {
        return store.findById(id);
    }

    public Optional<Client> findUserByEmailAndPwd(String email, String password) {
        return store.findUserByEmailAndPwd(email, password);
    }

}
