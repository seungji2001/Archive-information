package com.example.demo.Repository;

import com.example.demo.domain.RecordWord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecordWordRepository extends JpaRepository<RecordWord, Long> {
}
