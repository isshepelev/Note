package ru.isshepelev.note.ui.dto;

import lombok.Data;
import ru.isshepelev.note.infrastructure.persistance.entity.Note;

import java.util.List;

@Data
public class SignUpDto {

    private Long id;
    private String username;
    private String password;
    List<Note> notes;
}
