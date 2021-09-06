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

    public MemoryGame(int width, int height, int Seed) {
        /* Sets up StdDraw so that it has a width by height grid of 16 by 16 squares as its canvas
         * Also sets up the scale so the top left is (0,0) and the bottom right is (width, height)
         */
        this.width = width;
        this.height = height;
        rand = new Random(Seed);
        StdDraw.setCanvasSize(this.width * 16, this.height * 16);
        Font font = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.setXscale(0, this.width);
        StdDraw.setYscale(0, this.height);
        StdDraw.clear(Color.BLACK);
        StdDraw.enableDoubleBuffering();


    }

    public String generateRandomString(int n) {
        String randomString = "";
        int temp;
        for (int i = 0; i < n; i++) {
            temp = rand.nextInt(26);
            randomString = randomString + CHARACTERS[temp];
        }
        return randomString;
    }

    public void drawFrame(String s) {
        StdDraw.clear(Color.black);
        StdDraw.setFont(new Font("Monaco", Font.BOLD, 30));
        StdDraw.setPenColor(Color.white);
        for (int i = 0; i < s.length(); i++) {
            StdDraw.text(width / 2, height / 2, String.valueOf(s.charAt(i)));
            StdDraw.text(width / 2, height - 1, ENCOURAGEMENT[i]);
            StdDraw.show();
            StdDraw.pause(1000);
            StdDraw.clear(Color.black);
            StdDraw.pause(500);
        }

        //TODO: If game is not over, display relevant game information at the top of the screen
    }

    public void flashSequence(String letters) {
        drawFrame(letters);
    }

    public String solicitNCharsInput(int n) {
        String Usertype = "";
        while (!(n == 0)) {
            if (StdDraw.hasNextKeyTyped()) {
                char temp = StdDraw.nextKeyTyped();
                Usertype = Usertype + temp;
                StdDraw.clear(Color.black);
                StdDraw.text(width / 2, height / 2, Usertype);
                StdDraw.show();
                n -= 1;
            }
        }
        return Usertype;
    }

    public void startGame() {
        round = 1;
        String RandString;
        String User;
        while (true) {
            StdDraw.clear(Color.black);
            StdDraw.text(width / 2,  height / 2, "Round:" + round);
            StdDraw.show();
            StdDraw.pause(1500);
            RandString = generateRandomString(round);
            flashSequence(RandString);
            User = solicitNCharsInput(round);
            if (User.equals(RandString)) {
                round += 1;
            } else {
                StdDraw.text(width / 2, height / 2, "Game Over! You made it to round:" + round);
                StdDraw.show();
                break;
            }
        }
    }

}
