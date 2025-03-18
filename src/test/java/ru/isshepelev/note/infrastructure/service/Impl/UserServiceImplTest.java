package ru.isshepelev.note.infrastructure.service.Impl;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.isshepelev.note.infrastructure.exception.UsernameAlreadyExistsException;
import ru.isshepelev.note.infrastructure.persistance.entity.Note;
import ru.isshepelev.note.infrastructure.persistance.entity.User;
import ru.isshepelev.note.infrastructure.persistance.repository.NoteRepository;
import ru.isshepelev.note.infrastructure.persistance.repository.UserRepository;
import ru.isshepelev.note.ui.dto.SignUpDto;

@Testcontainers
@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @Container
    private static final PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:13")
            .withDatabaseName("testdb")
            .withUsername("test")
            .withPassword("test");

    @Mock
    private UserRepository userRepository;

    @Mock
    private NoteRepository noteRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void testRegisterUser() {
        SignUpDto signUpDto = new SignUpDto();
        signUpDto.setUsername("testUser");
        signUpDto.setPassword("testPassword");

        when(userRepository.existsByUsername("testUser")).thenReturn(false);
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> {
            User user = invocation.getArgument(0);
            user.setId(1L);
            return user;
        });

        userService.registerUser(signUpDto);

        verify(userRepository, times(1)).existsByUsername("testUser");
        verify(userRepository, times(1)).save(any(User.class));

        ArgumentCaptor<Note> noteCaptor = ArgumentCaptor.forClass(Note.class);
        verify(noteRepository, times(1)).save(noteCaptor.capture());

        Note savedNote = noteCaptor.getValue();
        assertNotNull(savedNote);
        assertEquals("Пример текста...", savedNote.getContent());
    }

    @Test
    void testRegisterUserWithExistingUsername() {
        SignUpDto signUpDto = new SignUpDto();
        signUpDto.setUsername("existingUser");
        signUpDto.setPassword("testPassword");

        when(userRepository.existsByUsername("existingUser")).thenReturn(true);

        assertThrows(UsernameAlreadyExistsException.class, () -> {
            userService.registerUser(signUpDto);
        });

        verify(userRepository, times(1)).existsByUsername("existingUser");
        verify(userRepository, never()).save(any(User.class));
        verify(noteRepository, never()).save(any(Note.class));
    }
}