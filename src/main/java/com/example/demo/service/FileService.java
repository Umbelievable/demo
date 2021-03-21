package com.example.demo.service;


import com.example.demo.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.demo.model.FileModel;
import com.example.demo.repository.FileRepository;

@Service
public class FileService {
    @Autowired
    private FileRepository fileRepository;
}
