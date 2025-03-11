package ru.isshepelev.note.infrastructure.persistance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.isshepelev.note.infrastructure.persistance.entity.Attachment;


@Repository
public interface AttachmentRepository extends JpaRepository<Attachment, Long> {

}
