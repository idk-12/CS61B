package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import edu.princeton.cs.introcs.StdDraw;

import java.awt.*;
import java.io.*;

public class Game implements Serializable {
    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 80;
    public static final int HEIGHT = 30;
    public static String filename = "GameLoad.txt";

    /**
     * Method used for playing a fresh game. The game should start from the main menu.
     */
    public void playWithKeyboard() {
        World world;

        Begin();
        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                switch (StdDraw.nextKeyTyped()) {
                    case 'n':
                    case 'N': {
                        String Strseed = NEWworld();
                        int Seed = readNumb(Strseed);
                        world = new World(null, null);
                        world = world.WorldGenerate(Seed);
                        drawWorld(world.world);
                        StdDraw.show();
                        WSAD(world);
                        break;
                    }
                    case 'L': {
                        world = readWorld();
                        drawWorld(world.world);
                        StdDraw.show();
                        WSAD(world);
                        break;
                    }
                    case 'Q': {
                        StdDraw.clear(Color.black);
                        StdDraw.show();
                        break;
                    }
                }
            }
        }


    }
    public static void MouseInterface() {

    }
    public static void WSAD(World doo) {
        TETile[][] w = doo.world;
        Boolean c = true;
        char temp;

        while (c) {
            if (StdDraw.hasNextKeyTyped()) {
                temp = StdDraw.nextKeyTyped();
                if (temp == ':') {
                    while (true) {
                        if (StdDraw.hasNextKeyTyped()) {
                            if (StdDraw.nextKeyTyped() == 'Q') {
                                writeWorld(doo);
                                StdDraw.clear(Color.black);
                                StdDraw.show();
                                c = false;
                                break;
                            }
                        }
                    }
                } else {
                    doo.player = Move(w, temp, doo.player);
                    drawWorld(w);
                    StdDraw.show();
                    if (doo.world[doo.player.x][doo.player.y].equals(Tileset.LOCKED_DOOR)) {
                        StdDraw.clear(Color.black);
                        StdDraw.setPenColor(Color.white);
                        StdDraw.setFont(new Font("Arial", Font.PLAIN, 30));
                        StdDraw.text(WIDTH * 0.5, HEIGHT * 0.5, "YOU WIN!");
                        StdDraw.show();
                        break;
                    }
                }
            }
            MouseInterface(w);
        }
    }
    public static void MouseInterface(TETile[][] wo) {
        drawWorld(wo);
        StdDraw.setPenColor(Color.white);
        try {
            StdDraw.textLeft(0, HEIGHT - 0.5, wo[(int) StdDraw.mouseX()][(int) StdDraw.mouseY()].description());
        } catch (Exception e) {
            ;
        }
        StdDraw.show();
    }
    public static String NEWworld() {
        StdDraw.clear(Color.black);
        StdDraw.text(WIDTH * 0.5, HEIGHT * 0.75, "Type A Seed");
        StdDraw.show();
        String Sseed = "";
        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                char temp = StdDraw.nextKeyTyped();
                Sseed += temp;
                if (temp == 'S') {
                    break;
                }
            }
        }
        return Sseed;
    }
    public static void drawWorld(TETile[][] world) {
        Font font = new Font("Monaco", Font.BOLD, 14);
        StdDraw.setFont(font);
        int numXTiles = world.length;
        int numYTiles = world[0].length;
        StdDraw.clear(new Color(0, 0, 0));
        for (int x = 0; x < numXTiles; x += 1) {
            for (int y = 0; y < numYTiles; y += 1) {
                if (world[x][y] == null) {
                    throw new IllegalArgumentException("Tile at position x=" + x + ", y=" + y
                            + " is null.");
                }
                world[x][y].draw(x, y);
            }
        }
    }
    public static int toInt(char input) {
        int output = 0;
        if (input <= 57 && input >= 48) {
            output = output * 10 + (int) input - 48;
        }
        return output;
    }
    public static void Begin() {
        StdDraw.setCanvasSize(WIDTH * 16, HEIGHT * 16);
        Font cs61b = new Font("Monaco", Font.BOLD, 60);
        Font defaultgame = new Font("Arial", Font.PLAIN, 30);
        StdDraw.setPenColor(Color.white);
        StdDraw.setXscale(0, WIDTH);
        StdDraw.setYscale(0, HEIGHT);

        StdDraw.clear(new Color(0, 0, 0));
        StdDraw.setFont(cs61b);
        StdDraw.text(WIDTH * 0.5, HEIGHT * 0.75, "CS61B: THE GAME" );
        StdDraw.setFont(defaultgame);
        StdDraw.text(WIDTH * 0.5, HEIGHT * 0.5, "New Game (N)");
        StdDraw.text(WIDTH * 0.5, HEIGHT * 0.5 - 2, "Load Game (L)");
        StdDraw.text(WIDTH * 0.5, HEIGHT * 0.5 - 4, "Quit (Q)");

        StdDraw.enableDoubleBuffering();
        StdDraw.show();
    }

    /**
     * Method used for autograding and testing the game code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The game should
     * behave exactly as if the user typed these characters into the game after playing
     * playWithKeyboard. If the string ends in ":q", the same world should be returned as if the
     * string did not end with q. For example "n123sss" and "n123sss:q" should return the same
     * world. However, the behavior is slightly different. After playing with "n123sss:q", the game
     * should save, and thus if we then called playWithInputString with the string "l", we'd expect
     * to get the exact same world back again, since this corresponds to loading the saved game.
     *
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public TETile[][] playWithInputString(String input) {
        // and return a 2D tile representation of the world that would have been
        // drawn if the same inputs had been given to playWithKeyboard().
        int start = 0;
        TETile[][] finalWorldFrame;
        World Dworld;

        if (input.charAt(0) == 'N' || input.charAt(0) == 'n') {
            int Seed = readNumb(input);
            World w = new World(null, null);
            Dworld = w.WorldGenerate(Seed);
            finalWorldFrame = Dworld.world;
            start = Getstart(input);
        } else {
                Dworld = readWorld();
                finalWorldFrame = Dworld.world;
                start = 1;
        }
        for (int i = start; i < input.length(); i++) {
            if (input.charAt(i) == ':') {
                if (input.charAt(i + 1) == 'Q') {
                    writeWorld(Dworld);
                }
                return finalWorldFrame;
            } else {
                Dworld.player = Move(finalWorldFrame, input.charAt(i), Dworld.player);
                if (Dworld.world[Dworld.player.x][Dworld.player.y].equals(Tileset.LOCKED_DOOR)) {
                    System.out.println("YOU WIN!");
                    return finalWorldFrame;
                }
            }
        }
        return finalWorldFrame;
    }
    public static World readWorld() {
        World readworld = null;
        try {
            FileInputStream fs = new FileInputStream(filename);
            ObjectInputStream load = new ObjectInputStream(fs);
            readworld = (World) load.readObject();
            load.close();
            return readworld;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public static void writeWorld(World world) {
        try {
            FileOutputStream fs = new FileOutputStream(filename);
            ObjectOutputStream os = new ObjectOutputStream(fs);
            os.writeObject(world);
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static int readNumb(String input) {
        int output = 0;
        for (int i = 0; i < input.length(); i++) {
            char temp = input.charAt(i);
            if (temp <= 57 && temp >= 48) {
                output = output * 10 + (int)temp - 48;
            }
        }
        return output;
    }
    public static Position Move(TETile[][] world, char key, Position player) {
        Position newplayer = player;
        switch (key) {
            case 'W': {
                if (isDoor(world, 'W', player)) {
                    world[player.x][player.y] = Tileset.FLOOR;
                    newplayer = new Position(player.x, player.y + 1);
                    return newplayer;
                }
                if (!isWall(world, 'W', player)) {
                    world[player.x][player.y] = Tileset.FLOOR;
                    world[player.x][player.y + 1] = Tileset.PLAYER;
                    newplayer = new Position(player.x, player.y + 1);
                }
                return newplayer;
            }
            case 'S': {
                if (isDoor(world, 'S', player)) {
                    world[player.x][player.y] = Tileset.FLOOR;
                    newplayer = new Position(player.x, player.y - 1);
                    return newplayer;
                }
                if (!isWall(world, 'S', player)) {
                    world[player.x][player.y] = Tileset.FLOOR;
                    world[player.x][player.y - 1] = Tileset.PLAYER;
                    newplayer = new Position(player.x, player.y - 1);
                }
                return newplayer;
            }
            case 'A': {
                if (isDoor(world, 'A', player)) {
                    world[player.x][player.y] = Tileset.FLOOR;
                    newplayer = new Position(player.x - 1, player.y);
                    return newplayer;
                }
                if (!isWall(world, 'A', player)) {
                    world[player.x][player.y] = Tileset.FLOOR;
                    world[player.x - 1][player.y] = Tileset.PLAYER;
                    newplayer = new Position(player.x - 1, player.y);
                }
                return newplayer;
            }
            case 'D': {
                if (isDoor(world, 'D', player)) {
                    world[player.x][player.y] = Tileset.FLOOR;
                    newplayer = new Position(player.x + 1, player.y);
                    return newplayer;
                }
                if (!isWall(world, 'D', player)) {
                    world[player.x][player.y] = Tileset.FLOOR;
                    world[player.x + 1][player.y] = Tileset.PLAYER;
                    newplayer = new Position(player.x + 1, player.y);
                }
                return newplayer;
            }
        }
        return newplayer;
    }
    public static boolean isWall(TETile[][] world, char key, Position now) {
        switch (key) {
            case 'W': {
                if (world[now.x][now.y + 1].equals(Tileset.WALL)) {
                    return true;
                }
                return false;
            }
            case 'S': {
                if (world[now.x][now.y - 1].equals(Tileset.WALL)) {
                    return true;
                }
                return false;
            }
            case 'D': {
                if (world[now.x + 1][now.y].equals(Tileset.WALL)) {
                    return true;
                }
                return false;
            }
            case 'A': {
                if (world[now.x - 1][now.y].equals(Tileset.WALL)) {
                    return true;
                }
                return false;
            }
        }
        return true;
    }
    public static boolean isDoor(TETile[][] world, char key, Position now) {
        switch (key) {
            case 'W': {
                if (world[now.x][now.y + 1].equals(Tileset.LOCKED_DOOR)) {
                    return true;
                }
                return false;
            }
            case 'S': {
                if (world[now.x][now.y - 1].equals(Tileset.LOCKED_DOOR)) {
                    return true;
                }
                return false;
            }
            case 'D': {
                if (world[now.x + 1][now.y].equals(Tileset.LOCKED_DOOR)) {
                    return true;
                }
                return false;
            }
            case 'A': {
                if (world[now.x - 1][now.y].equals(Tileset.LOCKED_DOOR)) {
                    return true;
                }
                return false;
            }
        }
        return true;
    }
    public static int Getstart(String input) {
        for (int i = 0; i < input.length(); i++) {
            if (input.charAt(i) == 'S') {
                return i + 1;
            }
        }
        return 0;
    }
}
