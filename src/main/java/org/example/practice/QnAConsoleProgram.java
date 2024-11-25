package org.example.practice;

import org.apache.commons.lang3.StringUtils;
import org.example.practice.exception.QnAValidationException;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class QnAConsoleProgram {
    private static final int MAX_LENGTH = 255;
    private static final String DEFAULT_ANSWER = "the answer to life, universe and everything is 42";
    private static final Pattern ANSWER_PATTERN = Pattern.compile("[“\"](.*?)[”\"]");
    private final Map<String, List<String>> qaStore = new HashMap<>();

    // Retrieves answers for a given question
    public List<String> getAnswers(String question) {

        validateQuestionInput(question);
        // Strip the "?" from the question if it exists and Normalize the question key (case-insensitive matching)
        String normalizedQuestion = question.endsWith("?")
                ? question.substring(0, question.length() - 1).trim().toLowerCase()
                : question.trim().toLowerCase();


        // Return answers from the map or the default answer
        return qaStore.getOrDefault(normalizedQuestion, Collections.singletonList(DEFAULT_ANSWER));
    }

    // Adds a question with its answers
    public void addQuestion(String input) {
        // Validate input for null
        if (input == null) {
            throw new QnAValidationException("Input cannot be null.");
        }

        // Splitting into question and answers
        String[] questionAndAnswers = input.split("\\?", 2);
        if (questionAndAnswers.length < 2) {
            throw new QnAValidationException("The input must contain a '?' separating the question from the answers.");
        }

        String question = questionAndAnswers[0].trim();
        String answersPart = questionAndAnswers[1].trim();


        validateQuestionInput(question);

        // Extract and validate individual answers
        List<String> answers = extractAnswers(answersPart);

        // Check for duplicate questions
        String normalizedQuestion = question.toLowerCase();
        if (qaStore.containsKey(normalizedQuestion)) {
            throw new QnAValidationException("The question already exists. Duplicate questions are not allowed.");
        }

        // Store the question and answers
        qaStore.put(normalizedQuestion, List.copyOf(answers));
    }

    // Extracts answers from the input string
    private List<String> extractAnswers(String answersPart) {
        // Validate answers
        if (answersPart.isEmpty()) {
            throw new QnAValidationException("The answers section cannot be empty. Provide at least one answer.");
        }

        List<String> answers = new ArrayList<>();

        Matcher matcher = ANSWER_PATTERN.matcher(answersPart);

        List<String> invalidAnswers = new ArrayList<>();

        // Extracts all answers between quotes
        while (matcher.find()) {
            String answer = matcher.group(1).trim();
            if (!answer.isEmpty() && answer.length() <= MAX_LENGTH) {
                answers.add(answer);
            } else if (answer.length() > MAX_LENGTH) {
                invalidAnswers.add(answer);
            }
        }

        // Throw exception if any answer exceeds the max length
        if (!invalidAnswers.isEmpty()) {
            throw new QnAValidationException("The following answers exceed the maximum length of " + MAX_LENGTH + " characters: " + invalidAnswers);
        }

        // Throw exception if no answers were extracted
        if (answers.isEmpty()) {
            throw new QnAValidationException("At least one valid answer must be provided. Ensure answers are enclosed in quotes.");
        }

        return answers;
    }

    // Validates the question input (checks for null, blank, and length)
    private void validateQuestionInput(String question) {
        if (StringUtils.isBlank(question)) {
            throw new QnAValidationException("Question cannot be null, blank, or whitespace.");
        }

        // Ignore the trailing '?' for length validation
        String effectiveQuestion = question.endsWith("?") ? question.substring(0, question.length() - 1).trim() : question;

        if (effectiveQuestion.length() > MAX_LENGTH) {
            throw new QnAValidationException("Question must not exceed " + MAX_LENGTH + " characters (excluding '?').");
        }
    }
}
