package ru.isshepelev.note.infrastructure.service.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.isshepelev.note.infrastructure.persistance.repository.NoteRepository;
import ru.isshepelev.note.infrastructure.service.NoteService;

@Service
@RequiredArgsConstructor
public class NoteServiceImpl implements NoteService {
    private final NoteRepository noteRepository;

}
