package com.example.demo.controller;
import java.io.IOException;

import com.example.demo.service.PhotoBoardService;
import com.example.demo.service.QNABoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


import com.example.demo.service.FileService;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class FileController {

    @Autowired
    private QNABoardService boardService;

    @Autowired
    private PhotoBoardService photoBoardService;

    @Autowired
    private FileService fileService;

    //get file
    @GetMapping("/file/{qboardNo}")
    public ResponseEntity<Object> qnaDownload(@PathVariable Integer qboardNo) throws IOException {
        //logger.info("HIT -/download | File Name : {}", fileName);
        String path = boardService.getFileUrl(qboardNo);
        return ResponseEntity.ok(fileService.download(path));
    }

    //get file
    @GetMapping("/file/photo/{pboardNo}")
    public ResponseEntity<Object> photoDownload(@PathVariable Integer pboardNo) throws IOException {
        //logger.info("HIT -/download | File Name : {}", fileName);
        String path = photoBoardService.getFileUrl(pboardNo);
        return ResponseEntity.ok(fileService.download(path));
    }


    @PostMapping(value = "/file")
    public Object upload(@RequestParam("file") MultipartFile multipartFile) {
        //logger.info("HIT -/upload | File Name : {}", multipartFile.getOriginalFilename());
        return fileService.upload(multipartFile);
    }

}
