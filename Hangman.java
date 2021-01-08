    /**
     * CS312 Assignment 6.
     *
     * On my honor, Julia Joseph, this programming assignment is my own work
     * and I have not shared my solution with any other student in the class.
     *
     * A program to play Hangman.
     *
     *  email address: juliajoseph65@gmail.com
     *  UTEID: jaj4598
     *  Unique 5 digit course ID: 50835
     *  Number of slip days used on this assignment:1
     */

import java.util.Scanner;

    public class Hangman {
        // Main method
        // Calls intro method, initializes PhraseBank and Scanner.
        // Runs loop for the amount of times the user wants to play the hangman game

        public static void main(String[] args) {
            intro();
            PhraseBank phrases = buildPhraseBank(args);
            Scanner keyboard = new Scanner(System.in);

            boolean playGameAgain = true;
            while (playGameAgain) {
                String phrase = chooseTopic(phrases);
                String currentPhrase = getAsterisksLine(phrase);
                updatesPhraseAndGuesses(keyboard, phrase, currentPhrase);

                String keep = "";
                System.out.println("Do you want to play again?");
                System.out.print("Enter 'Y' or 'y' to play again: ");
                keep = keyboard.nextLine();
                if (!(keep.equalsIgnoreCase("Y"))) {
                    playGameAgain = false;
                }
            }
            keyboard.close();
        }

        // Build the PhraseBank.
        // If args is empty or null, build the default phrase bank.
        // If args is not null and has a length greater than 0
        // then the first elements is assumed to be the name of the
        // file to build the PhraseBank from.
        public static PhraseBank buildPhraseBank(String[] args) {
            PhraseBank result;
            if (args == null || args.length == 0
                    || args[0] == null || args[0].length() == 0) {
                result = new PhraseBank();
             //   result = new PhraseBank("HangmanMovies.txt", true); // MUST be
                // commented out in version you submit.
            } else {
                result = new PhraseBank(args[0]);
            }
            return result;
        }
        // Print the intro to the program.
        public static void intro() {
            final int MAX_WRONG = 5;
            System.out.println("This program plays the game of hangman.");
            System.out.println();
            System.out.println("The computer will pick a random phrase.");
            System.out.println("Enter letters for your guess.");
            System.out.println("After " + MAX_WRONG + " wrong guesses you lose.");
        }
        // Chooses a topic and decides current phrase
        public static String chooseTopic(PhraseBank phrases) {
            String phrase = phrases.getNextPhrase();
            String currentPhrase = getAsterisksLine(phrase);
            String topic = phrases.getTopic();
            System.out.println();
            System.out.println("I am thinking of a " + topic + " ...");
            System.out.println();
            System.out.println("The current phrase is " + currentPhrase);
            return phrase;
        }
        //This method contains the loop for the hangman rounds. It calls the
        // correctGuess and incorrectGuess method. It also checks whether the user
        // has exceeded the maximum number of times they can be wrong, as well if they
        // have won.
        public static void updatesPhraseAndGuesses(Scanner keyboard, String phrase, String currentPhrase) {
            String guessedChar = "";
            int numWrongGuesses = 0;
            final int MAX_WRONG = 5;
            boolean didPlayerWin = false;

            while (numWrongGuesses < MAX_WRONG && didPlayerWin == false) {
                if (currentPhrase.equals(phrase) == false) {
                System.out.println();
                String guess = playerGuess(keyboard, guessedChar);
                String userGuessed = "";
                guessedChar += guess.charAt(0);

                int secPhraseIndex = phrase.indexOf(guess.charAt(0));

                    if (secPhraseIndex == -1) {
                        numWrongGuesses++;
                        currentPhrase = incorrectGuess(guess, phrase, currentPhrase,
                                guessedChar, numWrongGuesses, MAX_WRONG);
                    } else {
                        currentPhrase = correctGuess(guess, phrase, currentPhrase,
                                guessedChar, numWrongGuesses, didPlayerWin);
                    }
                }
                else {
                    didPlayerWin = true;
                }
             }

            if(didPlayerWin == false) {
                System.out.println("You lose. The secret phrase was " + phrase);
            }
            else{
                System.out.println("Number of wrong guesses so far: " + numWrongGuesses);
                System.out.println("The phrase is " + currentPhrase + ".");
                System.out.println("You win!!");
            }
        }
        //This is the method that runs if the user inputs an incorrect guess
        public static String incorrectGuess(String guess, String phrase,
                                        String currentPhrase, String guessedChar,
                                            int numWrongGuesses, int MAX_WRONG) {
            System.out.println();
            System.out.println("That is not present in the secret phrase.");
            System.out.println();
            currentPhrase = setGuessed(guess, phrase, currentPhrase, guessedChar);
            System.out.println("Number of wrong guesses so far: " + numWrongGuesses);
            if(numWrongGuesses != MAX_WRONG) {
                System.out.println("The current phrase is " + currentPhrase);
            }
            return currentPhrase;
        }
        //This is the method that runs if the user inputs the correct guess.
        public static String correctGuess(String guess, String phrase,
                                        String currentPhrase, String guessedChar,
                                          int numWrongGuesses, boolean didPlayerWin){
            System.out.println();
            System.out.println("That is present in the secret phrase.");
            System.out.println();

            currentPhrase = setGuessed(guess, phrase, currentPhrase, guessedChar);
            if(currentPhrase.equals(phrase) == false){
                System.out.println("Number of wrong guesses so far: " + numWrongGuesses);
                System.out.println("The current phrase is " + currentPhrase);
            }
            return currentPhrase;
        }
        // This takes in the user's guess and sends it to the enterNextGuess method
        public static String playerGuess(Scanner keyboard, String guessed){
            final String LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
            String guess = "";
            boolean isValid = false;
            return enterNextGuess(keyboard, isValid, LETTERS, guessed, guess);
        }
        //This method contains a while loop that continuously asks for the user next
        // guess until the guess they enter is of a valid format
        public static String enterNextGuess(Scanner keyboard,boolean isValid,
                                            String LETTERS,
                                            String guessed, String guess){
            printNotGuessed(guessed);
            System.out.println();
            System.out.print("Enter your next guess: ");

            while(isValid == false) {
                guess = keyboard.nextLine().toUpperCase();

                if ( (guessed.contains(guess.substring(0,1))) || (LETTERS.indexOf(guess.charAt(0)) < 0)) {
                    System.out.println();
                    System.out.println(guess + " is not a valid guess.");
                    printNotGuessed(guessed);
                    guess = "";
                    System.out.println();
                    System.out.print("Enter your next guess: ");
                } else {
                    System.out.println();
                    System.out.println("You guessed " + guess.charAt(0) + ".");
                    isValid = true;
                    guess = guess.substring(0,1);
                }
            }
            return guess;
        }
        // This method updates the currentPhrase relative to what letters the user has
        // guessed correctly.
        public static String setGuessed(String guess, String phrase, String currPhrase,
         String guessedChar) {
            String r = "";
            boolean didReplaceAsterisk = false;

            for (int i = 0; i < phrase.length(); i++) {
                //System.out.println("i=" + i);
                if (currPhrase.charAt(i) == '*') {
                    for(int j = 0; j < guessedChar.length(); j++) {
                        if (phrase.charAt(i) == guessedChar.charAt(j)) {
                            r += guessedChar.charAt(j);
                            didReplaceAsterisk = true;
                        }
                    }
                    if(didReplaceAsterisk == false) {
                        r += "*";
                    }
                } else {
                    r += phrase.charAt(i);
                }
                didReplaceAsterisk = false;
            }
            return r;
        }
        // This method returns asterisks for the characters the user hasn't entered yet
        public static String getAsterisksLine(String phrase){
            String ret = "";
            for (int i = 0; i < phrase.length(); i++) {
                   if(phrase.charAt(i) == '_')
                   {
                       ret += "_";
                   }
                   else {
                       ret += "*";
                   }
                }
            return ret;
        }
        // This method prints the letters that are not guessed yet.
        public static void printNotGuessed(String guess){
            final String LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
            System.out.println("The letters you have not guessed yet are: ");

            for (int i = 0; i < LETTERS.length(); i++){

                    if (guess.indexOf(LETTERS.charAt(i)) == -1 && i != LETTERS.length() - 1) {
                        System.out.print(LETTERS.charAt(i)+ "--");
                    }
                    else if(i == LETTERS.length() - 1) {
                        System.out.print(LETTERS.charAt(i));
                    }
            }
            System.out.println();
        }
    }



