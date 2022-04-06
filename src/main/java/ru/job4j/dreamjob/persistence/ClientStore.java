package ru.job4j.dreamjob.persistence;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import ru.job4j.dreamjob.model.Client;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@ThreadSafe
@Repository
public class ClientStore {

    private  final Map<Integer, Client> users  = new ConcurrentHashMap<>();

    private final AtomicInteger ids = new AtomicInteger(1);

    public ClientStore() {

    }

    public Collection<Client> getAllCities() {
        return new ArrayList<>(users.values());
    }

    public Client findById(int id) {
        return users.get(id);
    }


    public Client add(Client client) {
        client.setId(ids.get());
        Client result = users.putIfAbsent(ids.get(), client);
        if (result != null) {
            ids.getAndIncrement();
        }
        return result;
    }


    public void update(Client client) {
        users.put(client.getId(), client);
    }
}


