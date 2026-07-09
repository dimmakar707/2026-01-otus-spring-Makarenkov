package ru.otus.hw.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.hw.dao.QuestionDao;
import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Question;
import ru.otus.hw.domain.Student;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class TestServiceImplTest {
    @Mock
    private LocalizedIOService ioService;

    @Mock
    private QuestionDao questionDao;

    @Mock
    private Student student;

    @InjectMocks
    private TestServiceImpl testService;

    @DisplayName("Should check calling method findAll() of QuestionDao")
    @Test
    void shouldCheckOfCallingQuestionDaoFindAllMethod() {
        Question question = new Question("What is Maven?", List.of(
                new Answer("Test framework", false),
                new Answer("Build tool for Java projects", true),
                new Answer("Plugin for compiling JAR files", false)
        ));

        given(questionDao.findAll()).willReturn(List.of(question));

        testService.executeTestFor(student);

        verify(questionDao, times(1)).findAll();
    }
}
