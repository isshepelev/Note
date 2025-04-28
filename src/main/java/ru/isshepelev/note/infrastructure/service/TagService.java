package ru.isshepelev.note.infrastructure.service;

import jakarta.transaction.Transactional;

import java.util.List;

public interface TagService {

    @Transactional
    void addTagsToNote(Long noteId, List<String> tagNames);
}
