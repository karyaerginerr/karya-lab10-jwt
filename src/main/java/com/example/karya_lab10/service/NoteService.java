package com.example.karya_lab10.service;

import com.example.karya_lab10.model.Note;
import com.example.karya_lab10.repository.NoteJdbcRepository;
import com.example.karya_lab10.repository.NoteRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NoteService {

    private final NoteRepository noteRepository;
    private final NoteJdbcRepository noteJdbcRepository;
    private final UserService userService;

    public NoteService(NoteRepository noteRepository,
                       NoteJdbcRepository noteJdbcRepository,
                       UserService userService) {
        this.noteRepository = noteRepository;
        this.noteJdbcRepository = noteJdbcRepository;
        this.userService = userService;
    }


    private Long getCurrentUserId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        return userService.getUserIdByEmail(email);
    }


    public Note createNote(Note note) {
        note.setUserId(getCurrentUserId());
        return noteRepository.save(note);
    }


    public List<Note> getMyNotes() {
        return noteRepository.findByUserId(getCurrentUserId());
    }


    public List<Note> getMyNotesRaw() {
        return noteJdbcRepository.findNotesByUserIdRaw(getCurrentUserId());
    }


    public Note getMyNoteById(Long noteId) {
        Optional<Note> noteOpt = noteRepository.findById(noteId);

        if (noteOpt.isEmpty()) {
            throw new RuntimeException("Note not found");
        }

        Note note = noteOpt.get();

        if (!note.getUserId().equals(getCurrentUserId())) {
            throw new RuntimeException("Access denied");
        }

        return note;
    }


    public void deleteMyNote(Long noteId) {
        Note note = getMyNoteById(noteId);
        noteRepository.delete(note);
    }
}
