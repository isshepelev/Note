package ru.isshepelev.note.infrastructure.persistance.repository;

import org.aspectj.weaver.ast.Not;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.isshepelev.note.infrastructure.persistance.entity.Note;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {

}
