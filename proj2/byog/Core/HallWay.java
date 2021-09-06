package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

public class HallWay {
    public Position corner;
    public Position start;
    public Position end;
    int way;

    public HallWay(Position c, Position s, Position e, int way) {
        this.corner = c;
        this.start = s;
        this.end = e;
        this.way = way;
    }
    public static void DrawVerticalHallway(TETile[][] world, Position p, int h) {
        for (int y = 0; y < h; y += 1) {
            if (world[p.x - 1][p.y + y].equals(Tileset.NOTHING)) {
                world[p.x - 1][p.y + y] = Tileset.WALL;
            }
            if (world[p.x + 1][p.y + y].equals(Tileset.NOTHING)){
                world[p.x + 1][p.y + y] = Tileset.WALL;
            }
        }
    }

    public static void DrawHorizontalHallway(TETile[][] world, Position p, int w) {
        for (int x = 0; x < w; x += 1) {
            //System.out.println("world: "+world.length+" w: "+w+" p.x: "+p.xPos+" p.y: "+p.yPos);
            if(world[p.x + x][p.y - 1].equals(Tileset.NOTHING)) {
                world[p.x + x][p.y - 1] = Tileset.WALL;
            }
            if (world[p.x + x][p.y + 1].equals(Tileset.NOTHING)) {
                world[p.x + x][p.y + 1] = Tileset.WALL;
            }
        }
    }

    private static void fillVerticalHallway(TETile[][] world, Position p, int h) {
        for (int y = 0; y < h; y += 1) {
            world[p.x][p.y + y] = Tileset.FLOOR;
        }
    }

    private static void fillHorizontalHallway(TETile[][] world, Position p, int w) {
        for (int x = 0; x < w; x += 1) {
            world[p.x + x][p.y] = Tileset.FLOOR;
        }
    }

    public static void DrawHallWay(TETile[][] world, HallWay hw) {
        switch (hw.way) {
            case 0: { //Horizontal first
                fillHorizontalHallway(world, Position.smallerX(hw.start, hw.corner),
                        Math.abs(hw.corner.x - hw.start.x) + 1);
                fillVerticalHallway(world, Position.smallerY(hw.corner, hw.end),
                        Math.abs(hw.corner.y - hw.end.y) + 1);
                DrawHorizontalHallway(world, Position.smallerX(hw.start, hw.corner),
                        Math.abs(hw.corner.x - hw.start.x) + 1);
                DrawVerticalHallway(world, Position.smallerY(hw.end, hw.corner),
                        Math.abs(hw.end.y - hw.corner.y) + 1);
                int dirH = (hw.start.x - hw.corner.x) / Math.abs(hw.start.x - hw.corner.x);
                int dirV = (hw.end.y - hw.corner.y) / Math.abs(hw.end.y - hw.corner.y);
                if (world[hw.corner.x - dirH][hw.corner.y - dirV] == Tileset.NOTHING) {
                    world[hw.corner.x - dirH][hw.corner.y - dirV] = Tileset.WALL;
                }
                break;
            }
            case 1: { //Vertical first
                fillVerticalHallway(world, Position.smallerY(hw.start, hw.corner),
                        Math.abs(hw.corner.y - hw.start.y) + 1);
                fillHorizontalHallway(world, Position.smallerX(hw.corner, hw.end),
                        Math.abs(hw.corner.x - hw.end.x) + 1);
                DrawVerticalHallway(world, Position.smallerY(hw.corner, hw.start),
                        Math.abs(hw.corner.y - hw.start.y) + 1);
                DrawHorizontalHallway(world, Position.smallerX(hw.corner, hw.end),
                        Math.abs(hw.end.x - hw.corner.x) + 1);
                int dirV = (hw.start.y - hw.corner.y) / Math.abs(hw.start.y - hw.corner.y);
                int dirH = (hw.end.x - hw.corner.x) / Math.abs(hw.end.x - hw.corner.x);

                if (world[hw.corner.x - dirH][hw.corner.y - dirV] == Tileset.NOTHING) {
                    world[hw.corner.x - dirH][hw.corner.y - dirV] = Tileset.WALL;
                }
                break;
            }
            default: break;
        }
    }


}
