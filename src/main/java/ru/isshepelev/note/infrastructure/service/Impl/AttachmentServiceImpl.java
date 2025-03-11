package ru.isshepelev.note.infrastructure.service.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.isshepelev.note.infrastructure.persistance.repository.AttachmentRepository;
import ru.isshepelev.note.infrastructure.service.AttachmentService;

@Service
@RequiredArgsConstructor
public class AttachmentServiceImpl implements AttachmentService {

    private final AttachmentRepository attachmentRepository;

}
