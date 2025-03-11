package ru.isshepelev.note.infrastructure.persistance.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.context.SecurityContext;

import java.io.Serializable;

@Entity
@Data
public class Attachment implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String filePath;
    private String fileType;

    @ManyToOne
    @JoinColumn(name = "note_id")
    private Note note;
}
