package ru.isshepelev.note.ui.dto;

import lombok.Data;
import java.util.List;

@Data
public class NoteDto {
    private String title;
    private String content;
    private String fontFamily;
    private String fontSize;
    private List<String> photoPaths;
}
