package com.example.demo.Repository;

import com.example.demo.domain.DataArchive;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.xml.crypto.Data;

@Repository
public interface DataArchiveRepository extends JpaRepository<DataArchive, Long> {
}
