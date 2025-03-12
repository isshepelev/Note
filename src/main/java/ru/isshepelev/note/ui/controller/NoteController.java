package ru.isshepelev.note.ui.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.isshepelev.note.infrastructure.service.NoteService;
import ru.isshepelev.note.ui.dto.NoteDto;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class NoteController {

    private final NoteService noteService;

    @GetMapping("/")
    public String viewNotes(Model model, Principal principal) {
        model.addAttribute("notes", noteService.getAllNotes(principal.getName()));
        return "main";
    }

    @PostMapping("/create")
    public String createNote(@RequestBody NoteDto noteDto, Principal principal) {
        noteService.create(noteDto, principal.getName());
        return "redirect:/";
    }

    @PostMapping("/update/{noteId}")
    public String updateNote(@PathVariable Long noteId, @RequestBody NoteDto note) {
        noteService.update(noteId, note);
        return "redirect:/";
    }

    @PostMapping("/delete/{noteId}")
    public String deleteNote(@PathVariable Long noteId) {
        noteService.deleteById(noteId);
        return "redirect:/";
    }
}
