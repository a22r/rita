package com.rita.service;

import com.rita.repository.InfoRepository;
import com.rita.model.Info;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import java.util.Optional;

@Service
public class InfoService {
    @Autowired
    private InfoRepository infoRepository;

    public List<Info> getAll() {
        return infoRepository.findAll();
    }

    public Optional<Info> getById(Long id) {
        return infoRepository.findById(id);
    }

    public Info save(Info info) {
        return infoRepository.save(info);
    }

    public void deleteById(Long id) {
        infoRepository.deleteById(id);
    }
}