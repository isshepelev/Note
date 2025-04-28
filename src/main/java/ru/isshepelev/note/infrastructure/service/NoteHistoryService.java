package ru.isshepelev.note.infrastructure.service;

import jakarta.transaction.Transactional;

public interface NoteHistoryService {

    @Transactional
    void saveNoteHistory(Long noteId, String oldContent, String newContent, String username);
}
