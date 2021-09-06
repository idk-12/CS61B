package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.io.Serializable;
import java.util.Random;

public class World implements Serializable {
    private static final int WIDTH = 80;
    private static final int HEIGHT = 30;
    public Position player;
    public TETile[][] world;
    private static final long serialVersionUID = 9555;

    public World(TETile[][] world, Position player) {
        this.world = world;
        this.player = player;
    }

    public static Room[] RoomGenerate(TETile[][] world, long Seed) {
        Room[] existingRoom = new Room[100];
        int count = 0;
        Random r = new Random(Seed + 10);

        Position ranP = new Position(r.nextInt(80), r.nextInt(30));
        int ranW = r.nextInt(7) + 3;
        int ranH = r.nextInt(7) + 3;
        int num = r.nextInt(16) + 5;

        Room randomRoom = new Room(ranP, ranW, ranH);

        for (int i = 0; i < num; i++) {
            while (randomRoom.overindex(30, 80) || Room.overlap(world, randomRoom)) {
                ranP = new Position(r.nextInt(80), r.nextInt(30));
                ranW = r.nextInt(7) + 3;
                ranH = r.nextInt(7) + 3;
                randomRoom = new Room(ranP, ranW, ranH);
            }
            existingRoom[count] = randomRoom;
            count++;
            Room.DrawRoom(world, randomRoom.position, randomRoom.height, randomRoom.width);
        }
        Room.sortRoomList(existingRoom, 0);
        return existingRoom;
    }

    public static void HallWayGenerate(TETile[][] world, Room r1, Room r2, long Seed) {
        Random rand = new Random(Seed + 180);

        Position p1 = Room.innerRand(r1, rand);
        Position p2 = Room.innerRand(r2, rand);
        while (p1.x == p2.x || p1.y == p2.y) {
            p1 = Room.innerRand(r1, rand);
            p2 = Room.innerRand(r2, rand);
        }
        int way = rand.nextInt(2);

        switch (way) {
            case 0: { //draw horizontal hallway first.
                Position start = Position.smallerX(p1, p2);
                Position end = Position.largerX(p1, p2);
                Position corner = new Position(end.x, start.y);
                HallWay hw = new HallWay(corner, start, end, 0);
                HallWay.DrawHallWay(world, hw);
            }
            case 1: { //draw vertical way first.
                Position start = Position.smallerY(p1, p2);
                Position end = Position.largerY(p1, p2);
                Position corner = new Position(start.x, end.y);
                HallWay hw = new HallWay(corner, start, end, 1);
                HallWay.DrawHallWay(world, hw);
            }
            default: {
                return;
            }
        }
    }
    public static void AddDoor(TETile[][] world, long Seed) {
        Random rand = new Random(Seed + 50);
        int w = rand.nextInt(78) + 1;
        int h = rand.nextInt(28) + 1;
        while (true) {
            if ((world[w - 1][h] == Tileset.NOTHING && world[w + 1][h] == Tileset.FLOOR)
                    || (world[w][h - 1] == Tileset.NOTHING && world[w][h + 1] == Tileset.FLOOR)
                    || (world[w + 1][h] == Tileset.NOTHING && world[w - 1][h] == Tileset.FLOOR)
                    || (world[w][h + 1] == Tileset.NOTHING && world[w][h - 1] == Tileset.FLOOR)) {
                world[w][h] = Tileset.LOCKED_DOOR;
                break;
            } else {
                w = rand.nextInt(78) + 1;
                h = rand.nextInt(28) + 1;
            }
        }
    }
    public static Position AddPlayer(TETile[][] world, long Seed) {
        Random rand = new Random(Seed + 500);
        while (true) {
            int w = rand.nextInt(78) + 1;
            int h = rand.nextInt(28) + 1;
            if (world[w][h] == Tileset.FLOOR) {
                world[w][h] = Tileset.PLAYER;
                return new Position(w, h);
            }
        }
    }

    public  World WorldGenerate(long Seed) {
        TETile[][] innerworld = new TETile[80][30];
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                innerworld[x][y] = Tileset.NOTHING;
            }
        }
        Room[] room = RoomGenerate(innerworld, Seed);
        int i = 0;
        while (room[i + 1] != null) {
            HallWayGenerate(innerworld, room[i], room[i + 1], Seed);
            i++;
        }
        AddDoor(innerworld, Seed);
        player = AddPlayer(innerworld, Seed);
        World play = new World(innerworld, player);
        return play;
    }
}
