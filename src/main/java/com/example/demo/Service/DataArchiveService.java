package com.example.demo.Service;

import com.example.demo.Component.CustomSearchManager;
import com.example.demo.Dto.DataArchiveDto.DataArchiveRequestDto;
import com.example.demo.Repository.DataArchiveRepository;
import com.example.demo.domain.DataArchive;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DataArchiveService {
    @Autowired
    DataArchiveRepository dataArchiveRepository;
    @Autowired
    CustomSearchManager customSearchManager;

    @Transactional
    public Long saveWriteData(DataArchiveRequestDto.WriteData writeData) {
        DataArchive dataArchive = DataArchive.builder()
                .writeData(writeData.getData())
                .build();
        return dataArchiveRepository.save(dataArchive).getId();
    }

    public void searchData(String keyword, String googleKey, String cx) {
        customSearchManager.customSearch(keyword, googleKey, cx);
    }
}
