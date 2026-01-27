package com.example.karya_lab10.repository;

import com.example.karya_lab10.model.Note;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoteRepository extends JpaRepository<Note, Long> {

    // Kullanıcının sadece kendi notlarını alması için
    List<Note> findByUserId(Long userId);
}
