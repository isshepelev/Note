package ru.isshepelev.note.infrastructure.service.Impl;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.isshepelev.note.infrastructure.persistance.entity.Attachment;
import ru.isshepelev.note.infrastructure.persistance.entity.Note;
import ru.isshepelev.note.infrastructure.persistance.repository.AttachmentRepository;
import ru.isshepelev.note.infrastructure.persistance.repository.NoteRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class AttachmentServiceImpl {

    private final AttachmentRepository attachmentRepository;
    private final NoteRepository noteRepository;

    @Transactional
    public void addAttachmentToNote(Long noteId, String filePath, String fileType) {
        Note note = noteRepository.findById(noteId)
                .orElseThrow(() -> new EntityNotFoundException("Заметка не найдена: " + noteId));

        Attachment attachment = new Attachment();
        attachment.setFilePath(filePath);
        attachment.setFileType(fileType);
        attachment.setNote(note);

        attachmentRepository.save(attachment);
        log.info("Вложение добавлено к заметке {}", noteId);
    }
}
