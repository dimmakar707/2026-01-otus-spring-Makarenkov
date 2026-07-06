package ru.otus.hw.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hw.dao.QuestionDao;
import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Question;
import ru.otus.hw.domain.Student;
import ru.otus.hw.domain.TestResult;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TestServiceImpl implements TestService {

    private final LocalizedIOService ioService;

    private final QuestionDao questionDao;

    @Override
    public TestResult executeTestFor(Student student) {
        ioService.printLine("");
        ioService.printLineLocalized("TestService.answer.the.questions");
        ioService.printLine("");

        var questions = questionDao.findAll();
        var testResult = new TestResult(student);

        for (var question: questions) {
            var isAnswerValid = checkUserAnswerIsCorrect(question);
            testResult.applyAnswer(question, isAnswerValid);
        }
        return testResult;
    }

    private boolean checkUserAnswerIsCorrect(Question question) {
        ioService.printFormattedLineLocalized("TestService.answer.the.question", question.text());
        ioService.printLineLocalized("TestService.answer.the.answers");
        //System.out.println("Question: " + question.text());
        //System.out.println("Answers:");
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
        int userAnswerNumber = ioService.readIntForRangeWithPromptLocalized(
                1,
                answers.size(),
                "TestService.answer.range.message",
                "TestService.answer.error.message");
        return userAnswerNumber == rightAnswerNumber;
    }

}
