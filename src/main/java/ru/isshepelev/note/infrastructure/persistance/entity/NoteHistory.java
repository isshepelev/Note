package ru.isshepelev.note.infrastructure.persistance.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Data
public class NoteHistory implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String oldContent;
    private String newContent;
    private LocalDateTime changedAt;

    @ManyToOne
    @JoinColumn(name = "note_id")
    private Note note;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Override
    public String toString() {
        return "NoteHistory{id=" + id + ", oldContent='" + oldContent + "', newContent='" + newContent + "', changedAt=" + changedAt + "}";
    }
}
