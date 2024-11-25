package org.example.practice;

import org.example.practice.exception.QnAValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class QnAConsoleProgramTest {
    private QnAConsoleProgram program;


    @BeforeEach
    void setUp() {
        program = new QnAConsoleProgram();
    }


    @Test
    void testAddQuestion_WithValidInputs() {
        program.addQuestion("What is your favorite color? \"Blue\" \"Green\"");
        List<String> answers = program.getAnswers("What is your favorite color?");
        assertEquals(List.of("Blue", "Green"), answers);
    }

    @Test
    void testGetAnswers_WithNonExistentQuestion_ReturnsDefaultAnswer() {
        List<String> answers = program.getAnswers("What is your favorite food?");
        assertEquals(List.of("the answer to life, universe and everything is 42"), answers);
    }

    @Test
    void testAddQuestion_WithWhitespaceAroundInputs_StoresQuestionAndAnswers() {
        program.addQuestion("  What do you prefer?  \"Morning\" \"Evening\"  ");
        List<String> answers = program.getAnswers("What do you prefer?");
        assertEquals(List.of("Morning", "Evening"), answers);
    }

    @Test
    void testAddQuestion_WithSpecialCharactersInAnswers() {
        program.addQuestion("What symbols do you like? \"@\" \"#\" \"$\"");
        List<String> answers = program.getAnswers("What symbols do you like?");
        assertEquals(List.of("@", "#", "$"), answers);
    }


    @Test
    void testAddQuestion_WithLongQuestion_ThrowsValidationException() {
        String longQuestion = "A".repeat(256);
        Exception exception = assertThrows(QnAValidationException.class, () ->
                program.addQuestion(longQuestion + "? \"Answer\"")
        );
        assertEquals("Question must not exceed 255 characters (excluding '?').", exception.getMessage());
    }

    @Test
    void testAddQuestion_WithLongAnswer_ThrowsValidationException() {
        String longAnswer = "A".repeat(256);
        Exception exception = assertThrows(QnAValidationException.class, () ->
                program.addQuestion("What is your favorite food? \"" + longAnswer + "\"")
        );
        assertTrue(exception.getMessage().contains("The following answers exceed the maximum length"));
    }

    @Test
    void testAddQuestion_WithNoAnswers_ThrowsValidationException() {
        Exception exception = assertThrows(QnAValidationException.class, () ->
                program.addQuestion("What is your favorite hobby?")
        );
        assertEquals("The answers section cannot be empty. Provide at least one answer.", exception.getMessage());
    }

    @Test
    void testAddQuestion_WithEmptyAnswers_ThrowsValidationException() {
        Exception exception = assertThrows(QnAValidationException.class, () ->
                program.addQuestion("What is your favorite movie? \"\" \"\"")
        );
        assertEquals("At least one valid answer must be provided. Ensure answers are enclosed in quotes.", exception.getMessage());
    }

    @Test
    void testAddQuestion_WithDuplicateQuestion_ThrowsValidationException() {
        program.addQuestion("What is your favorite drink? \"Coffee\" \"Tea\"");
        Exception exception = assertThrows(QnAValidationException.class, () ->
                program.addQuestion("What is your favorite drink? \"Water\"")
        );
        assertEquals("The question already exists. Duplicate questions are not allowed.", exception.getMessage());
    }

    @Test
    void testAddQuestion_WithNullInput_ThrowsValidationException() {
        Exception exception = assertThrows(QnAValidationException.class, () ->
                program.addQuestion(null)
        );
        assertEquals("Input cannot be null.", exception.getMessage());
    }

    @Test
    void testGetAnswers_WithNullInput_ThrowsValidationException() {
        Exception exception = assertThrows(QnAValidationException.class, () ->
                program.getAnswers(null)
        );
        assertEquals("Question cannot be null, blank, or whitespace.", exception.getMessage());
    }

    @Test
    void testGetAnswers_WithBlankInput_ThrowsValidationException() {
        Exception exception = assertThrows(QnAValidationException.class, () ->
                program.getAnswers("   ")
        );
        assertEquals("Question cannot be null, blank, or whitespace.", exception.getMessage());
    }

    @Test
    void testAddQuestion_WithCaseInsensitiveDuplicate_ThrowsValidationException() {
        program.addQuestion("What is Java? \"A programming language.\"");
        Exception exception = assertThrows(QnAValidationException.class, () ->
                program.addQuestion("what is java? \"A programming language.\"")
        );
        assertEquals("The question already exists. Duplicate questions are not allowed.", exception.getMessage());
    }

    // Edge Case Tests
    @Test
    void testAddQuestion_WithQuestionAndAnswerLengthAtBoundary_Succeeds() {
        String validQuestion = "A".repeat(255);
        String validAnswer = "B".repeat(255);
        program.addQuestion(validQuestion + "? \"" + validAnswer + "\"");
        List<String> answers = program.getAnswers(validQuestion + "?");
        assertEquals(List.of(validAnswer), answers);
    }

    @Test
    void testAddQuestion_WithMissingAnswersSection_ThrowsValidationException() {
        Exception exception = assertThrows(QnAValidationException.class, () ->
                program.addQuestion("What is your favorite sport?")
        );
        assertEquals("The answers section cannot be empty. Provide at least one answer.", exception.getMessage());
    }

    @Test
    void testAddQuestion_WithImproperQuotes_ThrowsValidationException() {
        Exception exception = assertThrows(QnAValidationException.class, () ->
                program.addQuestion("What is the meaning of life? '42'")
        );
        assertEquals("At least one valid answer must be provided. Ensure answers are enclosed in quotes.", exception.getMessage());
    }

    @Test
    void testAddQuestion_WithTrailingQuestionMarkOnly_ThrowsValidationException() {
        Exception exception = assertThrows(QnAValidationException.class, () ->
                program.addQuestion("What is this?")
        );
        assertEquals("The answers section cannot be empty. Provide at least one answer.", exception.getMessage());
    }

    @Test
    void testAddQuestion_WithWhitespaceOnly_ThrowsValidationException() {
        Exception exception = assertThrows(QnAValidationException.class, () ->
                program.addQuestion("  ?  ")
        );
        assertEquals("Question cannot be null, blank, or whitespace.", exception.getMessage());
    }

    @Test
    void testAddQuestion_WithNoQuestionMark_ThrowsValidationException() {
        String input = "What is your favorite color \"Blue\" \"Green\"";
        Exception exception = assertThrows(QnAValidationException.class, () ->
                program.addQuestion(input)
        );
        assertEquals("The input must contain a '?' separating the question from the answers.", exception.getMessage());
    }

}