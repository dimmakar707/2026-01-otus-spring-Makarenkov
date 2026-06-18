package ru.otus.hw.service;

import lombok.RequiredArgsConstructor;
import ru.otus.hw.dao.QuestionDao;
import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Question;

import java.util.List;

@RequiredArgsConstructor
public class TestServiceImpl implements TestService {

    private final IOService ioService;

    private final QuestionDao questionDao;

    @Override
    public void executeTest() {
        ioService.printLine("");
        ioService.printFormattedLine("Please answer the questions below%n");

        List<Question> questions = questionDao.findAll();
        if (questions.isEmpty()) {
            System.out.println("List of questions is empty.");
        } else {
            for (Question question : questions) {
                System.out.println("Question: " + question.text());
                System.out.println("Answers:");
                List<Answer> answers = question.answers();
                for (Answer answer : answers) {
                    System.out.println("\t - " + answer.text());
                }
                System.out.println();
            }
        }

    }
}
