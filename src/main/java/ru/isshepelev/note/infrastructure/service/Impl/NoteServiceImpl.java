package ru.isshepelev.note.infrastructure.service.Impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.isshepelev.note.infrastructure.persistance.entity.Note;
import ru.isshepelev.note.infrastructure.persistance.entity.User;
import ru.isshepelev.note.infrastructure.persistance.repository.NoteRepository;
import ru.isshepelev.note.infrastructure.persistance.repository.UserRepository;
import ru.isshepelev.note.infrastructure.service.NoteService;
import ru.isshepelev.note.ui.dto.NoteDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class NoteServiceImpl implements NoteService {
    private final NoteRepository noteRepository;
    private final UserRepository userRepository;

    @Override
    public List<Note> getAllNotes(String username) {
        return noteRepository.findByUserUsername(username);
    }

    @Override
    public void update(Long noteId, NoteDto noteDto) {
        Optional<Note> noteOpt = noteRepository.findById(noteId);
        if (noteOpt.isEmpty()){
            log.error("заметка с " + noteId + " не найдена");
            throw new RuntimeException("note not found " + noteId);
        }
        Note note = noteOpt.get();
        note.setUpdatedAt(LocalDateTime.now());
        note.setContent(noteDto.getContent());
        note.setTitle(noteDto.getTitle());
        note.setFontFamily(noteDto.getFontFamily());
        note.setFontSize(noteDto.getFontSize());
        note.setPhotoPaths(noteDto.getPhotoPaths());
        noteRepository.save(note);
    }

    @Override
    public void deleteById(Long noteId) {
        noteRepository.deleteById(noteId);
    }

    @Override
    public void create(NoteDto noteDto, String username) {
        if (noteDto == null){
            log.warn("список пустой " + noteDto);
            return;
        }
        User user = userRepository.findByUsername(username);
        if (user == null){
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
    }

}
