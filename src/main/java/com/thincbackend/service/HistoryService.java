package com.thincbackend.service;

import com.thincbackend.domain.History;
import com.thincbackend.repository.HistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
public class HistoryService {
    private final HistoryRepository historyRepository;

    public History saveHistory(History history){
        return historyRepository.save(history);
    }

    public List<History> findHistoryByDate(String Date){
        return historyRepository.findByDate(Date);
    }

    public List<History> findAllHistory(){
        return historyRepository.findAll();
    }
}
