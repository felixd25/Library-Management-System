
# Digital Library System

## Introduction
This project is a Java-based **Digital Library System**, designed to manage a collection of books in a digital environment. Users can log in, search for books, view book details, and manage their library accounts. The system provides an intuitive GUI for easy navigation and functionality.

## Features
- **Login and Account Management**: Users can log in and manage their library accounts.
- **Book Searching**: Users can search through the library’s collection using a search bar.
- **Book Management**: Books can be displayed, searched, and interacted with in the library system.
- **GUI Interface**: A simple and easy-to-use graphical interface allows users to interact with the system.

## Components
The system is divided into three main Java files:
1. **`Book.java`**: This file represents the `Book` class, defining the structure of a book, including attributes like title, author, ISBN, and due date.
   
2. **`DigitalLibrary.java`**: This file manages the collection of books, providing functionalities to add, search, and manage books in the library.

3. **`MainWindow.java`**: This file handles the graphical user interface (GUI). It sets up the main window of the library, including book search functionalities, login pages, and display panels.

## Instructions for Use

### Prerequisites
Ensure you have Java installed on your machine. The project has been developed using Java Swing for the GUI components.

### Steps to Run
1. Clone the repository and navigate to the project directory.
2. Compile the Java files:
   ```bash
   javac Book.java DigitalLibrary.java MainWindow.java
   ```
3. Run the main program:
   ```bash
   java MainWindow
   ```
4. The login page will appear first. If you have an existing account, enter your username and password. If not, click on the "New Account" button to create a new account.
5. After logging in, you can search for books using the search bar in the main window.
6. Books will be displayed in a list, showing their title, author, ISBN, and due date.
