package ru.otus.hw.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hw.dao.QuestionDao;
import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Question;
import ru.otus.hw.domain.Student;
import ru.otus.hw.domain.TestResult;

import java.util.List;

@RequiredArgsConstructor
@Service
public class TestServiceImpl implements TestService {

    private final IOService ioService;

    private final QuestionDao questionDao;

    @Override
    public TestResult executeTestFor(Student student) {
        ioService.printLine("");
        ioService.printFormattedLine("Please answer the questions below%n");
        var questions = questionDao.findAll();
        var testResult = new TestResult(student);
        for (var question: questions) {
            var isAnswerValid = checkUserAnswerIsCorrect(question); // Задать вопрос, получить ответ
            testResult.applyAnswer(question, isAnswerValid);
        }
        return testResult;
    }

    private boolean checkUserAnswerIsCorrect(Question question) {
        System.out.println("Question: " + question.text());
        System.out.println("Answers:");
        List<Answer> answers = question.answers();
        int answerNumber = 0;
        int rightAnswerNumber = -1;
        for (Answer answer : answers) {
            answerNumber++;
            System.out.println("\t" + answerNumber + ". "  + answer.text());
            if (answer.isCorrect()) {
                rightAnswerNumber = answerNumber;
            }
        }
        int userAnswerNumber = ioService.readIntForRangeWithPrompt(
            1,
            answers.size(),
            "Enter a number from 1 to " + answers.size(),
            "Not valid value");
        return userAnswerNumber == rightAnswerNumber;
    }
}
