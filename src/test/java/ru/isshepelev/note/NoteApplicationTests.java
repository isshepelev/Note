package ru.isshepelev.note;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
@Testcontainers
@ContextConfiguration(classes = TestContainersConfig.class)
class NoteApplicationTests {

    @Test
    void contextLoads() {
    }
}
