package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Random;

public class Room {
    public Position position;
    public int width;
    public int height;

    public Room(Position p, int w, int h) {
        this.position = p;
        this.width = w;
        this.height = h;

    }

    public static void DrawRoom(TETile[][] world, Position p, int h, int w) {
        for (int x = 0; x < w; x += 1) {
            world[p.x + x][p.y] = Tileset.WALL;
            world[p.x + x][p.y + h - 1] = Tileset.WALL;
        }
        for (int y = 0; y < h; y += 1) {
            world[p.x][p.y + y] = Tileset.WALL;
            world[p.x + w - 1][p.y + y] = Tileset.WALL;
        }
        for (int x = 1; x < w - 1; x += 1) {
            for (int y = 1; y < h - 1; y += 1) {
                world[p.x + x][p.y + y] = Tileset.FLOOR;
            }
        }
    }

    public static Boolean overlap(TETile[][] world, Room newroom) {
        for (int yi = 0; yi < newroom.height; yi++) {
            for (int xi = 0; xi < newroom.width; xi++) {
                if (world[newroom.position.x + xi][newroom.position.y + yi] != Tileset.NOTHING) {
                    return true;
                }
            }
        }
        return false;
    }
    public Boolean overindex(int Worldheight, int Worldwidth) {
        if (this.position.x >= 0 && this.position.y >= 0
                && (this.position.x + this.width) <= Worldwidth
                && (this.position.y + this.height) <= Worldheight) {
            return false;
        }
        return true;
    }
    public static Position innerRand(Room r, Random rand) {
        int innerX = rand.nextInt(r.width - 2) + r.position.x + 1;
        int innerY = rand.nextInt(r.height - 2) + r.position.y + 1;
        Position innerPosit = new Position(innerX, innerY);
        return innerPosit;
    }

    public static void sortRoomList(Room[] x, int start) {
        if (start == x.length) {
            return;
        }
        int smallestIndex = findSmallest(x, start);
        swap(x, start, smallestIndex);
        sortRoomList(x, start + 1);
    }

    public static int findSmallest(Room[] x, int start) {
        int smallestIndex = start;
        for (int i = start; i < x.length; i += 1) {
            if (x[i] == null) {
                break;
            }
            int cmp = x[i].position.x + x[i].position.y - x[smallestIndex].position.x - x[smallestIndex].position.y;
            if (cmp < 0) {
                smallestIndex = i;
            }
        }
        return smallestIndex;
    }

    public static void swap(Room[] x, int a, int b) {
        Room temp = x[a];
        x[a] = x[b];
        x[b] = temp;
    }

}
