package ru.job4j.dreamjob.service;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.dreamjob.model.City;
import ru.job4j.dreamjob.persistence.CityStore;


import java.util.*;

@ThreadSafe
@Service
public class CityService {
    private final  CityStore cityStore;

    public CityService(CityStore cityStore) {
        this.cityStore = cityStore;
    }

    public Collection<City> getAllCities() {
        return cityStore.getAllCities();
    }

    public City findById(int id) {
        return cityStore.findById(id);
    }

}