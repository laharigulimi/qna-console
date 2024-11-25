# Implementation Details:

* `Language`-  Java 17
* `Design Patterns`: Encapsulation of logic within a dedicated `QnAConsoleProgram` class.
* `Validation`: Thorough checks using regular expressions and string operations.
* `Immutability`: Answers are stored as immutable lists to prevent unintended modifications.

## Project Structure

* src/
* ├── org.example.Main                          // Entry point for the program.
* ├── org.example.practice.QnAConsoleProgram   // Core logic for managing QnA.
* ├── org.example.practice.exception.QnAValidationException  // Custom exception for validation.
* └── test/
* └── org.example.practice.QnAConsoleProgramTest  // JUnit 5 tests for program validation.

## Usage Instructions

* Java Development Kit (JDK): Version 17 or higher.
* A terminal or command-line interface.

## How to run

1. Compile and run the program

2. Using the Program:
    
    Select an option from the menu:
    - Add a question with its answers.
    - Query answers for a specific question.
    - Exit the program.
