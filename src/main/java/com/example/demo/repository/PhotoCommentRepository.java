package com.example.demo.repository;

import com.example.demo.model.PhotoBoard;
import com.example.demo.model.PhotoComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PhotoCommentRepository extends JpaRepository<PhotoComment, Integer> {

    public List<PhotoComment> findAllByPboardNo(PhotoBoard pBoard);
    public abstract java.util.Optional<PhotoComment> findByPcommentNoAndPboardNo(Integer pcommentNo, PhotoBoard pBoard);



}