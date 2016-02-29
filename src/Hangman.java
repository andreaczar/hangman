import javax.swing.*;
import java.awt.*;
import java.awt.font.TextAttribute;
import java.util.*;

/**
 * Created by Andrea on 2016-02-28.
 */
public class Hangman extends Canvas {

    private static String[] words = {"alligator", "camel", "cheetah", "chicken", "chimpanzee", "crocodile", "dolphin", "eagle", "elephant", "giraffe", "goldfish", "hamster", "hippopotamus", "horse", "kangaroo", "kitten", "lion", "lobster", "monkey", "octopus", "puppy", "rabbit", "scorpion", "seal", "shark", "sheep", "snail", "snake", "spider", "squirrel", "tiger", "turtle", "wolf", "zebra"};
    private char[] guessWord;
    private ArrayList<Character> guesses = new ArrayList<Character>();

    private int counter = 6;
    private int wordIndex;

    public Hangman(){
        wordIndex = new Random().nextInt(words.length);

        String word = words[wordIndex];
        guessWord = new char[word.length()];

        for(int i = 0; i < guessWord.length; i++){
            guessWord[i] = '_';
        }
    }

    /**
     *  Adds guess to list of guesses. Returns false if guess already exists.
     *
     * @param guess the guess
     * @return whether the guess was valid.
     */
    public boolean addGuess(char guess){
        if(guesses.contains(guess)){
            return false;
        }

        guesses.add(guess);
        counter++;
        repaint();

        return true;
    }

    public void paint(Graphics graphics){

        drawGallows(graphics);

        Map<TextAttribute, Object> attributes = new HashMap<TextAttribute, Object>();
        attributes.put(TextAttribute.TRACKING, 0.75);

        Font font = new Font("TimesRoman", Font.PLAIN, 30);
        Font WideFont = font.deriveFont(attributes);
        graphics.setFont(WideFont);



        graphics.drawChars(guessWord, 0, guessWord.length, 100, 500);

        graphics.setFont(font);
        graphics.setColor(Color.darkGray);
        graphics.drawString("Previous Guesses", 500, 50);
        int guessY = 100;

        for(int i = 0; i < guesses.size(); i++ ){
            Character c = guesses.get(i);
            graphics.drawString(c.toString(), 600, guessY);
            guessY += 35;
        }

        graphics.setColor(Color.BLACK);
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

    private void drawGallows(Graphics graphics){
        graphics.setColor(Color.MAGENTA);
        graphics.drawLine(220, 170, 220, 200);
        graphics.drawLine(220, 170, 300, 170);
        graphics.drawLine(300, 170, 300, 390);
        graphics.drawRect(160, 390, 140, 40);
    }

    private void drawHead(Graphics graphics){
        graphics.drawOval(200, 200, 40, 40);
    }

    private void drawBody(Graphics graphics){
        graphics.drawLine(220, 240, 220, 300);
    }

    private void drawLeftArm(Graphics graphics){
        graphics.drawLine(220, 260, 190, 240);
    }

    private void drawRightArm(Graphics graphics){
        graphics.drawLine(220, 260, 250, 240);
    }

    private void drawLeftLeg(Graphics graphics){
        graphics.drawLine(220, 300, 190, 330);
    }
    private void drawRightLeg(Graphics graphics){
        graphics.drawLine(220, 300, 250, 330);
    }

    public static void main(String[] args){

        JFrame jFrame = new JFrame();
        jFrame.setSize(800, 800);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Container container = jFrame.getContentPane();

        Hangman hangman = new Hangman();
        hangman.setSize(800, 800);
        container.add(hangman);

        jFrame.setVisible(true);

        Scanner scanner = new Scanner(System.in);

        for(int i = 0; i < 6; i++){
            String guess = scanner.next();

            char[] charGuess = guess.toCharArray();
            hangman.addGuess(charGuess[0]);
        }

    }

}
