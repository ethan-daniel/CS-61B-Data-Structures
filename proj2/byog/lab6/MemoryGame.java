package byog.lab6;

import edu.princeton.cs.introcs.StdDraw;

import java.awt.Color;
import java.awt.Font;
import java.util.Random;

public class MemoryGame {
    private int width;
    private int height;
    private int round;
    private Random rand;
    private boolean gameOver;
    private boolean playerTurn;
    private static final char[] CHARACTERS = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    private static final String[] ENCOURAGEMENT = {"You can do this!", "I believe in you!",
                                                   "You got this!", "You're a star!", "Go Bears!",
                                                   "Too easy for you!", "Wow, so impressive!"};

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Please enter a seed");
            return;
        }

        int seed = Integer.parseInt(args[0]);
        MemoryGame game = new MemoryGame(40, 40, seed);
        game.startGame();
    }

    public MemoryGame(int width, int height, int seed) {
        /* Sets up StdDraw so that it has a width by height grid of 16 by 16 squares as its canvas
         * Also sets up the scale so the top left is (0,0) and the bottom right is (width, height)
         */
        this.width = width;
        this.height = height;
        StdDraw.setCanvasSize(this.width * 16, this.height * 16);
        Font font = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.setXscale(0, this.width);
        StdDraw.setYscale(0, this.height);
        StdDraw.clear(Color.BLACK);
        StdDraw.enableDoubleBuffering();

        rand = new Random(seed);
    }

    public String generateRandomString(int n) {
        String generatedString = "";
        int index;

        for (int i = 0; i != n; ++i) {
            index = rand.nextInt(CHARACTERS.length);
            generatedString += CHARACTERS[index];
        }

        return generatedString;
    }

    public void drawFrame(String s) {
        StdDraw.clear();
        StdDraw.clear(Color.BLACK);

        if (!gameOver) {
            Font font20 = new Font("Monaco", Font.BOLD, 20);
            StdDraw.setFont(font20);
            StdDraw.textLeft(1, height - 1, "Round: " + round);
            StdDraw.textRight(width - 1, height - 1, ENCOURAGEMENT[round % ENCOURAGEMENT.length]);
            if (playerTurn) {
                StdDraw.text(width / 2, height - 1,"Type!");
            } else {
                StdDraw.text(width / 2, height - 1,"Watch!");
            }
        }

        Font font = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.text(this.width / 2, this.height / 2, s);
        StdDraw.show();

    }

    public void flashSequence(String letters) {
        String input = "";

        for (int i = 0; i != letters.length(); ++i) {
            StdDraw.pause(500);
            input = "" + letters.charAt(i);
            drawFrame(input);
            StdDraw.pause(1000);
        }
    }

    public String solicitNCharsInput(int n) {
        String returnString = "";
        drawFrame("");

        while(returnString.length() != n) {
            if (StdDraw.hasNextKeyTyped()) {
                returnString += StdDraw.nextKeyTyped();
                drawFrame(returnString);
            }
        }

        return returnString;
    }

    public void startGame() {
        round = 1;
        gameOver = false;
        playerTurn = false;

        while (!gameOver) {
            String roundMessage = "Round: " + round;
            drawFrame(roundMessage);
            String randomString = generateRandomString(round);
            flashSequence(randomString);

            playerTurn = true;
            String playerResponse = solicitNCharsInput(round);
            if (!playerResponse.equals(randomString)) {
                String gameOverMessage = "Game Over! You made it to round: " + round;
                drawFrame(gameOverMessage);
                gameOver = true;
            }
            ++round;
            playerTurn = false;
        }
    }

}
