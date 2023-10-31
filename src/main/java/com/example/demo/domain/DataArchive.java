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
@Entity(name = "dataarchive")
public class DataArchive {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="data_archive_id")
    @Id
    private Long id;
    private String writeData;
}
