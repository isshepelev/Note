package ru.isshepelev.note.infrastructure.service.Impl;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.isshepelev.note.infrastructure.persistance.entity.Note;
import ru.isshepelev.note.infrastructure.persistance.entity.User;
import ru.isshepelev.note.infrastructure.persistance.repository.NoteRepository;
import ru.isshepelev.note.infrastructure.persistance.repository.UserRepository;
import ru.isshepelev.note.infrastructure.service.NoteService;
import ru.isshepelev.note.ui.dto.NoteDto;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class NoteServiceImpl implements NoteService {

    private final NoteRepository noteRepository;
    private final UserRepository userRepository;

    @Value("${upload.dir}")
    private String UPLOAD_DIR;

    @Override
    public List<Note> getAllNotes(String username) {
        return noteRepository.findByUserUsername(username);
    }

    @Override
    @Transactional
    public void update(Long noteId, NoteDto noteDto) {
        Note note = noteRepository.findById(noteId)
                .orElseThrow(() -> new EntityNotFoundException("Заметка не найдена: " + noteId));

        note.setUpdatedAt(LocalDateTime.now());
        note.setContent(noteDto.getContent());
        note.setTitle(noteDto.getTitle());
        note.setFontFamily(noteDto.getFontFamily());
        note.setFontSize(noteDto.getFontSize());
        note.setPhotoPaths(noteDto.getPhotoPaths());
        noteRepository.save(note);
        log.info("обновление заметки №" + noteId);
    }

    @Override
    @Transactional
    public void deleteById(Long noteId) {
        noteRepository.deleteById(noteId);
        log.info("удаление заметки №{}", noteId);
    }

    @Override
    @Transactional
    public void create(NoteDto noteDto, String username) {
        if (noteDto == null) {
            log.warn("список пустой " + noteDto);
            return;
        }
        User user = userRepository.findByUsername(username);
        if (user == null) {
            log.warn("пользователь не найден " + username);
            return;
        }
        Note note = new Note();
        note.setUser(user);
        note.setCreatedAt(LocalDateTime.now());
        note.setUpdatedAt(LocalDateTime.now());
        note.setTitle(noteDto.getTitle());
        note.setContent(noteDto.getContent());
        note.setFontFamily(noteDto.getFontFamily());
        note.setFontSize(noteDto.getFontSize());
        note.setPhotoPaths(noteDto.getPhotoPaths());
        noteRepository.save(note);
        log.info("создание заметки {}", note);
    }

    @Override
    public String uploadPhoto(MultipartFile file) {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("Файл пустой");
        }

        try {
            String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
            Path path = Paths.get(UPLOAD_DIR).resolve(filename);
            Files.createDirectories(path.getParent());
            Files.write(path, file.getBytes());
            return "/images/" + filename;
        } catch (IOException e) {
            log.error("Ошибка сохранения файла", e);
            throw new RuntimeException("Ошибка сохранения файла", e);
        }
    }

    @Override
    public ResponseEntity<Resource> getImage(String filename) {
        Path imagePath = Paths.get(UPLOAD_DIR).resolve(filename);

        if (!Files.exists(imagePath)) {
            return ResponseEntity.notFound().build();
        }

        try {
            Resource resource = new UrlResource(imagePath.toUri());
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(resource);
        } catch (MalformedURLException e) {
            log.error("Ошибка загрузки изображения", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
