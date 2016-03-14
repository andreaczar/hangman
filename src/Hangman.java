import javax.swing.*;
import java.awt.*;
import java.awt.font.TextAttribute;
import java.util.*;

/**
 * Final Project: Hangman
 * @author  Andrea on 2016-02-28.
 *
 * A Hangman game that can be played as a single player or 2 player game. For the single player game, a word is randomly
 * selected from an array of words, and in the 2 player, one player types in a word and the other player must guess it.
 * The player is not penalized for guessing duplicate letters, they are reprompted for a valid character.
 *
 */
public class Hangman extends Canvas {
    /**
     * Max number of incorrect guesses
     */
    private final int MAX_INCORRECT = 6;
    /**
     * Array of guess words
     */
    private static String[] words = {"alligator", "camel", "cheetah", "chicken", "chimpanzee", "crocodile", "dolphin", "eagle", "elephant", "giraffe", "goldfish", "hamster", "hippopotamus", "horse", "kangaroo", "kitten", "lion", "lobster", "monkey", "octopus", "puppy", "rabbit", "scorpion", "seal", "shark", "sheep", "snail", "snake", "spider", "squirrel", "tiger", "turtle", "wolf", "zebra"};
    /**
     * Stores the character array of word to be guessed
     */
    private char[] guessWord;
    /**
     * A list of character to store previous guesses
     */
    private ArrayList<Character> guesses = new ArrayList<Character>();
    /**
     * Count the number of guesses
     */
    private int counter = 0;
    /**
     * The word to be guessed extracted from the words array
     */
    private String word;

    private Scanner scanner = new Scanner(System.in);

    /**
     * Constructor to create a single player instance of the hangman game selecting a random word
     *
     */
    public Hangman(){
        int wordIndex = new Random().nextInt(words.length);
        word = words[wordIndex];
        System.out.println(word);
        setupGuessWord();
    }

    /**
     * Creates an 2 player instance of hangman and uses the given word
     *
     * @param word word entered by user to be guessed
     */
    public Hangman(String word){
        this.word = word;
        setupGuessWord();
    }

    /**
     * Converts the guess word into the correct number of '_'s to be displayed to the board
     */
    private void setupGuessWord(){
        guessWord = new char[word.length()];
        for(int i = 0; i < guessWord.length; i++){
            guessWord[i] = '_';
        }
    }

    /**
     *  Checks if the current character has already been guessed
     *
     * @param guess the guess
     * @return whether the guess was valid.
     */
    public boolean checkDuplicateGuess(char guess){
        return guesses.contains(guess);
    }

    /**
     * Checks if the guessed letter is in the word. If it is, replaces the '_' with the letter.
     * If not, adds guess to previous guesses and increments the incorrect counter.
     *
     * @param guess the character input from user
     */
    private void addGuess(char guess){

        if(word.indexOf(guess) >= 0){
            System.out.print("Great guess!  ");
            for(int i = 0; i < word.length(); i++){
                if(word.charAt(i) == guess){
                    guessWord[i] = guess;
                }
            }

        } else {
            System.out.print("Too bad. ");
            counter++;

            if(!gameOver()){
                System.out.print("Try again.  ");
            }
        }

        // add guess to previous guesses
        guesses.add(guess);
        repaint();

    }

    /**
     * Draws the graphics on the canvas: head, body, legs, arms, gallows.
     *
     * @param graphics graphics to create images on canvas
     */
    public void paint(Graphics graphics){

        drawGallows(graphics);

        // Create a new font with more letter spacing
        Map<TextAttribute, Object> attributes = new HashMap<TextAttribute, Object>();
        attributes.put(TextAttribute.TRACKING, 0.75);

        Font font = new Font("TimesRoman", Font.PLAIN, 30);
        Font WideFont = font.deriveFont(attributes);
        graphics.setFont(WideFont);


        // location on canvas for the guess word
        graphics.drawChars(guessWord, 0, guessWord.length, 100, 500);

        // Set up area to track previous guesses
        graphics.setFont(font);
        graphics.setColor(Color.darkGray);
        graphics.drawString("Previous Guesses", 500, 50);
        int guessY = 100;


        // Previous guesses printed to screen
        for(int i = 0; i < guesses.size(); i++ ){
            Character c = guesses.get(i);
            graphics.drawString(c.toString(), 600, guessY);
            guessY += 35;
        }

        /**
         * Add the body parts depending on number of wrong guesses
         */
        graphics.setColor(Color.RED);
        if(counter > 0){
            drawHead(graphics);
        }

        if(counter > 1){
            drawBody(graphics);
        }

        if(counter > 2){
            drawLeftArm(graphics);
        }

        if(counter > 3){
            drawRightArm(graphics);
        }

        if(counter > 4){
            drawLeftLeg(graphics);
        }

        if(counter > 5){
            drawRightLeg(graphics);
        }
    }

    /**
     * Draw gallows
     *
     * @param graphics canvas graphics
     */
    private void drawGallows(Graphics graphics){
        graphics.setColor(Color.BLACK);
        graphics.drawLine(220, 170, 220, 200);
        graphics.drawLine(220, 170, 300, 170);
        graphics.drawLine(300, 170, 300, 390);
        graphics.drawRect(160, 390, 140, 20);
    }

    /**
     * Draw head
     *
     * @param graphics canvas graphics
     */
    private void drawHead(Graphics graphics){
        graphics.drawOval(200, 200, 40, 40);
    }
    /**
     * Draw body
     *
     * @param graphics canvas graphics
     */
    private void drawBody(Graphics graphics){
        graphics.drawLine(220, 240, 220, 300);
    }
    /**
     * Draw left arm
     *
     * @param graphics canvas graphics
     */
    private void drawLeftArm(Graphics graphics){
        graphics.drawLine(220, 260, 190, 240);
    }
    /**
     * Draw right arm
     *
     * @param graphics canvas graphics
     */
    private void drawRightArm(Graphics graphics){
        graphics.drawLine(220, 260, 250, 240);
    }
    /**
     * Draw left leg
     *
     * @param graphics canvas graphics
     */
    private void drawLeftLeg(Graphics graphics){
        graphics.drawLine(220, 300, 190, 330);
    }
    /**
     * Draw right leg
     *
     * @param graphics canvas graphics
     */
    private void drawRightLeg(Graphics graphics){
        graphics.drawLine(220, 300, 250, 330);
    }

    /**
     * Returns true if the game is won and the incorrect guesses are less than the allowed number
     *
     * @return true if game won and below number of guess threshold
     */
    private boolean gameWon(){

        return String.valueOf(guessWord).equals(word) && (counter <= MAX_INCORRECT);

    }

    /**
     * Determines if the game is over -- checking if the game is won
     *
     * @return true if game is over
     */
    private boolean gameOver(){

        if(gameWon()){
            return true;
        }

        return counter >= 6;
    }

    /**
     * Runs the hangman input loop validating guesses and checking if the game is over
     */
    public void startGame(){

        while(!this.gameOver()) {

            System.out.println("Enter a letter (a-z): ");

            String guess = scanner.next();

            // Convert string into char array
            char[] charGuess = guess.toCharArray();

            // Checks for duplicate guess
            if(this.checkDuplicateGuess(charGuess[0])){
                System.out.print("You've already guessed " + charGuess[0] + "!  ");
                continue;
            }

            this.addGuess(charGuess[0]);
        }
        if(gameWon()){
            System.out.println("Congratulations, you WIN!!");
        } else {
            System.out.println("You lose!");
        }

    }


    /**
     * main method
     *
     * @param args command line arguments
     */
    public static void main(String[] args){
        Hangman hangman;

        JFrame jFrame = new JFrame();
        jFrame.setSize(800, 800);
        jFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        Container container = jFrame.getContentPane();

        System.out.println("Rules of the game:\n" +
                "Your job is to try to find out the secret word by guessing one letter at a time. \n" +
                "But beware! If you incorrectly select a letter that is not in the secret word, \n" +
                "you will be one step closer to hanging on the gallows!\n" +
                "The secret word is made up of letters a-z.\n");


        Scanner scanner = new Scanner(System.in);
        System.out.println("Do you want to play?\nEnter '1' for single player, '2' for two player, or 'q' to quit." );

        String input = scanner.next();

        while(!input.equals("1") && !input.equals("2") && !input.equals("q")){
            System.out.println("Invalid Input. Enter '1', '2' or 'q'.");
            input = scanner.next();
        }

        // Doesn't start game if 'q' selected
        if (input.equals("q")) {
            System.out.println("Goodbye.");

        } else {
            // Start a two player game
            if(input.equals("2")){

                while(true){
                    // prompts for player to input a word
                    System.out.print("Enter a word.");
                    input = scanner.next();

                    // checks the length of the word inserted
                    if(input.length() < 4){
                        System.out.println("Enter a longer word.");
                        continue;
                    }
                    break;
                }
                hangman = new Hangman(input);

            } else {
                hangman = new Hangman();
            }
            // size of hangman canvas
            hangman.setSize(800, 800);
            container.add(hangman);

            jFrame.setVisible(true);

            hangman.startGame();
        }

        // close game when lost or won
        jFrame.setVisible(false);
        jFrame.dispose();

        System.out.println("Thanks for playing!");
    }

}
