package org.example;

import org.example.practice.QnAConsoleProgram;
import org.example.practice.exception.QnAValidationException;

import java.util.List;
import java.util.Scanner;

public class Main {
    private static final QnAConsoleProgram program = new QnAConsoleProgram();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Welcome to the QnA Console Program!");
        boolean running = true;

        while (running) {
            showMenu();
            String choice = scanner.nextLine();

            running = switch (choice) {
                case "1" -> {
                    handleAddQuestion();
                    yield true; // Continue the loop
                }
                case "2" -> {
                    handleGetAnswers();
                    yield true; // Continue the loop
                }
                case "3" -> {
                    handleExit();
                    yield false; // Exit the loop
                }
                default -> {
                    System.err.println("Invalid choice. Please enter 1, 2, or 3.");
                    yield true; // Continue the loop for invalid inputs
                }
            };
        }

        scanner.close();
    }

    // Displays the menu options
    private static void showMenu() {
        System.out.println("\nChoose an option:");
        System.out.println("1. Add a question and its answers");
        System.out.println("2. Get answers to a question");
        System.out.println("3. Exit");
        System.out.print("Enter your choice: ");
    }

    // Handles adding a question and its answers
    private static void handleAddQuestion() {
        System.out.println("Enter the question and answers (in format: 'question? \"answer1\" \"answer2\" ...'):");
        String input = scanner.nextLine();
        try {
            program.addQuestion(input);
            System.out.println("Question and answers added successfully.");
        } catch (QnAValidationException e) {
            System.err.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("An unexpected error occurred: " + e.getMessage());
        }
    }

    // Handles retrieving answers for a question
    private static void handleGetAnswers() {
        System.out.print("Enter the question: ");
        String question = scanner.nextLine();
        try {
            List<String> answers = program.getAnswers(question);
            System.out.println("Answers:");
            answers.forEach(answer -> System.out.println("- " + answer));
        } catch (QnAValidationException e) {
            System.err.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("An unexpected error occurred: " + e.getMessage());
        }
    }

    // Handles exiting the program
    private static void handleExit() {
        System.out.println("Thank you for using the QnA Console Program. Goodbye!");
    }
}

