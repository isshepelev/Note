package ru.isshepelev.note.infrastructure.persistance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.isshepelev.note.infrastructure.persistance.entity.Note;

import java.util.List;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {

    //Запрос для получения заметок пользователя с фильтрацией и сортировкой:
    @Query(value =
            "SELECT * FROM note n" +
            " WHERE n.user_id = " +
            "(SELECT id FROM users u WHERE u.username = :username) " +
            "ORDER BY n.created_at DESC", nativeQuery = true)
    List<Note> findByUserUsername(String username);

    //Запрос для получения заметок, которые были изменены за последние 30 дней:
    @Query(value =
            "SELECT * FROM note n " +
                    "WHERE n.updated_at > NOW() - INTERVAL 30 DAY", nativeQuery = true)
    List<Note> findNotesUpdatedInLast30Days();
}
