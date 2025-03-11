package ru.isshepelev.note.infrastructure.service.Impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.isshepelev.note.infrastructure.exception.UsernameAlreadyExistsException;
import ru.isshepelev.note.infrastructure.persistance.entity.Note;
import ru.isshepelev.note.infrastructure.persistance.entity.User;
import ru.isshepelev.note.infrastructure.persistance.repository.NoteRepository;
import ru.isshepelev.note.infrastructure.persistance.repository.UserRepository;
import ru.isshepelev.note.infrastructure.service.UserService;
import ru.isshepelev.note.ui.dto.SignUpDto;

import java.time.LocalDateTime;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final NoteRepository noteRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void registerUser(SignUpDto signUpDto) {
        if (userRepository.findByUsername(signUpDto.getUsername()) != null) {
            throw new UsernameAlreadyExistsException("Пользователь уже существует");
        }

        User user = new User();
        user.setUsername(signUpDto.getUsername());
        user.setPassword(passwordEncoder.encode(signUpDto.getPassword()));
        userRepository.save(user);
        log.info("регистрация пользователя {}", user);
        createNote("Пример заголовка...", "Пример текста...", user);

    }

    private void createNote(String title, String content, User user) {
        Note note = new Note();
        note.setUser(user);
        note.setTitle(title);
        note.setContent(content);
        note.setCreatedAt(LocalDateTime.now());
        note.setUpdatedAt(LocalDateTime.now());
        log.info("Создание новой заметки у " + user);
        noteRepository.save(note);
    }

    @Override
    public User getUserByUsername(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            log.error("пользователь с " + username + " не найден");
            throw new UsernameNotFoundException("user not found " + username);
        }
        return user;
    }
}
