package ru.otus.hw.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.hw.config.TestFileNameProvider;
import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Question;
import ru.otus.hw.exceptions.QuestionReadException;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class CsvQuestionDaoTest {

    @Mock
    private TestFileNameProvider fileNameProvider;

    @InjectMocks
    private CsvQuestionDao questionDao;

    @DisplayName("Should check throw QuestionReadException")
    @Test
    void shouldCheckThrowQuestionReadException() {
        String FAKE_FILE_NAME = "not_existed.csv";

        given(fileNameProvider.getTestFileName()).willReturn(FAKE_FILE_NAME);

        Exception exception = assertThrows(QuestionReadException.class, () -> {
            questionDao.findAll();
        });

        assertEquals("file not found! " + FAKE_FILE_NAME, exception.getMessage());

    }

    @DisplayName("Should check correct answer from QuestionDao")
    @Test
    void shouldCheckCorrectGetQuestionsFromResource() {
        Question question = new Question("What is Maven?", List.of(
                new Answer("Test framework", false),
                new Answer("Build tool for Java projects", true),
                new Answer("Plugin for compiling JAR files", false)
        ));

        given(fileNameProvider.getTestFileName()).willReturn("questions.csv");

        List<Question> expectedQuestions = List.of(question);

        List<Question> actualQuestions = questionDao.findAll();

        assertThat(actualQuestions).isEqualTo(expectedQuestions);
    }

}
