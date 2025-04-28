package ru.isshepelev.note.infrastructure.service;

import jakarta.transaction.Transactional;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import ru.isshepelev.note.infrastructure.persistance.entity.Note;
import ru.isshepelev.note.ui.dto.NoteDto;

import java.util.List;

public interface NoteService {

    List<Note> getAllNotes(String username);

    void update(Long noteId, NoteDto noteDto);

    void deleteById(Long noteId);

    void create(NoteDto noteDto, String username);

    String uploadPhoto(MultipartFile file);

    ResponseEntity<Resource> getImage(String filename);
}
