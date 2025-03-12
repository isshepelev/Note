package ru.isshepelev.note.infrastructure.service;

import ru.isshepelev.note.ui.dto.SignUpDto;

public interface UserService {

    void registerUser(SignUpDto signUpDto);
}
