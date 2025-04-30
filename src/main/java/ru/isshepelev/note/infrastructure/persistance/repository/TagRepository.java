package ru.isshepelev.note.infrastructure.persistance.repository;

import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.isshepelev.note.infrastructure.persistance.entity.Note;
import ru.isshepelev.note.infrastructure.persistance.entity.Tag;

import java.util.List;
import java.util.Optional;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {

    Optional<Tag> findByName(String name);

    //Запрос для получения всех заметок с фильтрацией по тегам и сортировкой:
    @Query(value =
            "SELECT n.* FROM note n " +
                    "JOIN note_tags nt ON n.id = nt.note_id " +
                    "JOIN tag t ON nt.tag_id = t.id " +
                    "WHERE t.name = :tagName " +
                    "ORDER BY n.updated_at DESC", nativeQuery = true)
    List<Note> findNotesByTag(@Param("tagName") String tagName);
}
