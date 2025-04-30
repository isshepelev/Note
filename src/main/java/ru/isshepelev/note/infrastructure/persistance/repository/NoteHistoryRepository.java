package ru.isshepelev.note.infrastructure.persistance.repository;

import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.isshepelev.note.infrastructure.persistance.entity.NoteHistory;

import java.time.LocalDateTime;
import java.util.List;


@Repository
public interface NoteHistoryRepository extends JpaRepository<NoteHistory, Long> {


    //Запрос для получения истории изменений заметки с фильтрацией по дате:
    @Query(value =
            "SELECT * FROM note_history nh " +
            "WHERE nh.note_id = :noteId AND nh.changed_at " +
            "BETWEEN :startDate AND :endDate " +
            "ORDER BY nh.changed_at DESC", nativeQuery = true)
    List<NoteHistory> findHistoryByNoteIdAndDateRange(
            @Param("noteId") Long noteId,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate
    );
}
