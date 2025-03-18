package ru.isshepelev.note.infrastructure.service.Impl;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.web.multipart.MultipartFile;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.isshepelev.note.TestContainersConfig;
import ru.isshepelev.note.infrastructure.persistance.entity.Note;
import ru.isshepelev.note.infrastructure.persistance.entity.User;
import ru.isshepelev.note.infrastructure.persistance.repository.NoteRepository;
import ru.isshepelev.note.infrastructure.persistance.repository.UserRepository;
import ru.isshepelev.note.ui.dto.NoteDto;

import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Testcontainers
@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = TestContainersConfig.class)
public class NoteServiceImplTest {

    @Mock
    private NoteRepository noteRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private NoteServiceImpl noteService;

    private final static String UPLOAD_DIR = "src/test/resources/static/images/";

    @BeforeEach
    void setUp() {
        noteService = new NoteServiceImpl(noteRepository, userRepository);
        File directory = new File(UPLOAD_DIR);
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }


    @Test
    void testGetAllNotes() {
        String username = "testUser";
        Note note = new Note();
        note.setId(1L);
        note.setTitle("Test Note");
        note.setContent("Test Content");
        note.setUser(new User());

        when(noteRepository.findByUserUsername(username)).thenReturn(Collections.singletonList(note));

        List<Note> notes = noteService.getAllNotes(username);

        assertNotNull(notes);
        assertEquals(1, notes.size());
        assertEquals("Test Note", notes.get(0).getTitle());
        verify(noteRepository, times(1)).findByUserUsername(username);
    }

    @Test
    void testUpdate() {
        Long noteId = 1L;
        NoteDto noteDto = new NoteDto();
        noteDto.setTitle("Updated Title");
        noteDto.setContent("Updated Content");

        Note note = new Note();
        note.setId(noteId);
        note.setTitle("Old Title");
        note.setContent("Old Content");

        when(noteRepository.findById(noteId)).thenReturn(Optional.of(note));

        noteService.update(noteId, noteDto);

        assertEquals("Updated Title", note.getTitle());
        assertEquals("Updated Content", note.getContent());
        verify(noteRepository, times(1)).findById(noteId);
        verify(noteRepository, times(1)).save(note);
    }

    @Test
    void testDeleteById() {
        Long noteId = 1L;

        noteService.deleteById(noteId);

        verify(noteRepository, times(1)).deleteById(noteId);
    }

    @Test
    void testCreate() {
        NoteDto noteDto = new NoteDto();
        noteDto.setTitle("New Note");
        noteDto.setContent("New Content");

        String username = "testUser";
        User user = new User();
        user.setId(1L);
        user.setUsername(username);

        when(userRepository.findByUsername(username)).thenReturn(user);

        noteService.create(noteDto, username);

        verify(userRepository, times(1)).findByUsername(username);
        verify(noteRepository, times(1)).save(any(Note.class));
    }

    @Test
    void uploadPhoto_shouldThrowException_whenFileIsEmpty() {
        MultipartFile file = new MockMultipartFile("file", new byte[0]);
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            noteService.uploadPhoto(file);
        });
        assertEquals("Файл пустой", thrown.getMessage());
    }
}