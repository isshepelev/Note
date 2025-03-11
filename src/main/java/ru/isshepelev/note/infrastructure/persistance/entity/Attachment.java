package ru.isshepelev.note.infrastructure.persistance.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Attachment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String filePath;
    private String fileType;

    @ManyToOne
    @JoinColumn(name = "note_id")
    private Note note;
}
