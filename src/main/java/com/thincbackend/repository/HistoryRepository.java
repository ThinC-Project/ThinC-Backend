package com.thincbackend.repository;

import com.thincbackend.domain.History;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HistoryRepository extends JpaRepository<History, Long> {

    List<History> findByDate(String Date);
}