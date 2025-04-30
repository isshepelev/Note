package ru.isshepelev.note.infrastructure.persistance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.isshepelev.note.infrastructure.persistance.entity.User;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);

    boolean existsByUsername(String username);

    //Запрос для получения пользователей с количеством их заметок (группировка по пользователю):
    @Query(value =
            "SELECT u.id, u.username, COUNT(n.id) as note_count " +
                    "FROM users u " +
                    "LEFT JOIN note n ON u.id = n.user_id " +
                    "GROUP BY u.id", nativeQuery = true)
    List<Object[]> getUserNoteCount();
}
