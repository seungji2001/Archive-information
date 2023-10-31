package com.example.demo.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "recordword")
public class RecordWord {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="record_word_id")
    @Id
    private Long id;
    private String paragraph;
}
