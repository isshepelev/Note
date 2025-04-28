package ru.isshepelev.note.infrastructure.service.Impl;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.isshepelev.note.infrastructure.persistance.entity.Note;
import ru.isshepelev.note.infrastructure.persistance.entity.NoteHistory;
import ru.isshepelev.note.infrastructure.persistance.entity.User;
import ru.isshepelev.note.infrastructure.persistance.repository.NoteHistoryRepository;
import ru.isshepelev.note.infrastructure.persistance.repository.NoteRepository;
import ru.isshepelev.note.infrastructure.persistance.repository.UserRepository;
import ru.isshepelev.note.infrastructure.service.NoteHistoryService;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class NoteHistoryServiceImpl implements NoteHistoryService {

    private final NoteHistoryRepository noteHistoryRepository;
    private final NoteRepository noteRepository;
    private final UserRepository userRepository;

    @Transactional
    @Override
    public void saveNoteHistory(Long noteId, String oldContent, String newContent, String username) {
        Note note = noteRepository.findById(noteId)
                .orElseThrow(() -> new EntityNotFoundException("Заметка не найдена: " + noteId));

        User user = userRepository.findByUsername(username);

        NoteHistory noteHistory = new NoteHistory();
        noteHistory.setOldContent(oldContent);
        noteHistory.setNewContent(newContent);
        noteHistory.setChangedAt(LocalDateTime.now());
        noteHistory.setNote(note);
        noteHistory.setUser(user);

        noteHistoryRepository.save(noteHistory);
        log.info("История изменений для заметки {} сохранена", noteId);
    }
}
