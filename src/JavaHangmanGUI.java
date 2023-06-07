import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class JavaHangmanGUI extends JFrame {
    // Array of words for the game
    private String[] words;
    // Array of hints corresponding to the words
    private String[] hints;
    // Score variables
    private int score;
    private int points;
    private int attempts;
    private int initial = 0;

    // Game variables
    private int remainingAttempts;
    private Random random;
    private String word;
    private String hint;
    private char[] guessedLetters;

    // GUI components
    private JLabel wordLabel;
    private JLabel hintLabel;
    private JLabel attemptsLabel;
    private JLabel scoreLabel;
    private JLabel pointsLabel;
    private JLabel messageLabel;
    private JTextField guessTextField;
    private JButton guessButton;
    private JButton nextButton;

    public JavaHangmanGUI(int score, int points, int attempts) {
        // Initialize the word and hint arrays
        words = new String[]{
                "variable",
                "method",
                "class",
                "object",
                "inheritance",
                "polymorphism",
                "interface",
                "constructor",
                "array",
                "loop",
                "recursion",
                "string",
                "parameter",
                "static",
                "overloading",
                "overriding",
                "casting",
                "abstraction",
                "package",
                "constructor",
                "encapsulation",
                "conditional",
                "interface",
                "exception",
                "boolean",
                "byte",
                "double",
                "final"
        };

        hints = new String[]{
                "A container that holds a value.",
                "A set of code which performs a specific task.",
                "A blueprint or template for creating objects.",
                "An instance of a class.",
                "The mechanism of creating a new class from an existing class.",
                "The ability of an object to take on many forms.",
                "A contract specifying a set of methods that a class must implement.",
                "A special method used to initialize objects.",
                "A data structure that stores multiple values of the same type.",
                "A control structure for executing a block of code repeatedly.",
                "A function that calls itself.",
                "A sequence of characters.",
                "A value passed to a method.",
                "A keyword used to create variables and methods that belong to a class.",
                "Having multiple methods with the same name but different parameters.",
                "Replacing a method in a superclass with a new implementation in a subclass.",
                "Converting an object of one type to another type.",
                "exposing only necessary information and hiding the implementation details.",
                "A way to organize classes and interfaces into groups.",
                "A special method used to initialize objects.",
                "A mechanism of bundling data and methods together.",
                "A control structure for making decisions based on conditions.",
                "A contract specifying a set of methods that a class must implement.",
                "An event that occurs during the execution of a program.",
                " only a true or false value.",
                "A sequence of eight bits. Java provides a corresponding byte type",
                "A Java keyword used to define a variable of type double.",
                "You define an entity once and cannot change it or derive from it later."

        };


        // Initialize the score variables
        this.score = score;
        this.points = points;
        this.attempts = attempts;

        random = new Random();

        setTitle("Hangman Game");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(9, 1));

        // Create and configure GUI components
        wordLabel = new JLabel();
        wordLabel.setFont(new Font("Arial", Font.BOLD, 18));
        wordLabel.setHorizontalAlignment(SwingConstants.CENTER);

        hintLabel = new JLabel();
        hintLabel.setHorizontalAlignment(SwingConstants.CENTER);

        attemptsLabel = new JLabel();
        attemptsLabel.setHorizontalAlignment(SwingConstants.CENTER);

        scoreLabel = new JLabel("Score: " + initial);
        scoreLabel.setHorizontalAlignment(SwingConstants.CENTER);

        pointsLabel = new JLabel("Points: " + points);
        pointsLabel.setHorizontalAlignment(SwingConstants.CENTER);

        messageLabel = new JLabel();
        messageLabel.setHorizontalAlignment(SwingConstants.CENTER);

        guessTextField = new JTextField();
        guessTextField.setHorizontalAlignment(SwingConstants.CENTER);

        guessButton = new JButton("Guess");
        guessButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                processGuess();
            }
        });

        nextButton = new JButton("Next");
        nextButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                newGame();
            }
        });
        nextButton.setEnabled(false);

        // Add GUI components to the JFrame
        add(wordLabel);
        add(hintLabel);
        add(attemptsLabel);
        add(scoreLabel);
        add(pointsLabel);
        add(messageLabel);
        add(guessTextField);
        add(guessButton);
        add(nextButton);

        newGame();
    }

    private void newGame() {
        guessTextField.setEnabled(true);
        guessButton.setEnabled(true);
        nextButton.setEnabled(false);

        // Choose a random word and hint
        int randomIndex = random.nextInt(words.length);
        word = words[randomIndex];
        hint = hints[randomIndex];

        // Initialize the guessedLetters array with underscores
        guessedLetters = new char[word.length()];
        for (int i = 0; i < guessedLetters.length; i++) {
            guessedLetters[i] = '_';
        }

        remainingAttempts = attempts;

        updateDisplay();
    }

    private void updateDisplay() {
        // Update the labels with the current game state
        wordLabel.setText(new String(guessedLetters));
        hintLabel.setText("Hint: " + hint);
        attemptsLabel.setText("Attempts remaining: " + remainingAttempts);
        scoreLabel.setText("Score: " + initial);
        pointsLabel.setText("Points: " + points);
        messageLabel.setText("");

        guessTextField.setText("");
        guessTextField.requestFocus();

        if (remainingAttempts == 0) {
            // Display game over message if no attempts remaining
            messageLabel.setText("Game over! The word was: " + word);
            initial -= points;
            scoreLabel.setText("Score: " + initial);
            guessTextField.setEnabled(false);
            guessButton.setEnabled(false);
            nextButton.setEnabled(true);
        }

        if (new String(guessedLetters).equals(word)) {
            // Display congratulations message if word is guessed correctly
            messageLabel.setText("Congratulations! You guessed the word: " + word);
            initial += points;
            scoreLabel.setText("Score: " + initial);
            guessTextField.setEnabled(false);
            guessButton.setEnabled(false);
            nextButton.setEnabled(true);
        }

        if (initial >= score) {
            // Disable guess and next buttons if score is reached
            messageLabel.setText("Congratulations! You reached a score of " + score + " or more!");
            guessTextField.setEnabled(false);
            guessButton.setEnabled(false);
        }
    }

    private void processGuess() {
        String guessText = guessTextField.getText().toLowerCase();

        if (guessText.length() != 1) {
            messageLabel.setText("Please enter a single letter.");
            return;
        }

        char guess = guessText.charAt(0);
        boolean correctGuess = false;

        // Check if the guess is correct and update guessedLetters accordingly
        for (int i = 0; i < word.length(); i++) {
            if (word.charAt(i) == guess) {
                guessedLetters[i] = guess;
                correctGuess = true;
            }
        }

        if (!correctGuess) {
            // Decrease remaining attempts if the guess is incorrect
            remainingAttempts--;
        }

        updateDisplay();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                // Prompt the user for game settings
                int score = Integer.parseInt(JOptionPane.showInputDialog("Enter desired score:"));
                int points = Integer.parseInt(JOptionPane.showInputDialog("Enter point value:"));
                int attempts = Integer.parseInt(JOptionPane.showInputDialog("Enter maximum number of attempts:"));

                // Create and show the Hangman GUI
                JavaHangmanGUI hangmanGUI = new JavaHangmanGUI(score, points, attempts);
                hangmanGUI.setVisible(true);
            }
        });
    }
}



