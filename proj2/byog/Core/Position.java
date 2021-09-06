package byog.Core;

import java.io.Serializable;

public class Position implements Serializable {
    public int x;
    public int y;
    public Position(int xi, int yi) {
        x = xi;
        y = yi;
    }
    public static Position smallerX(Position p1, Position p2) {
        if (p1.x < p2.x) {
            return p1;
        }
        return p2;
    }
    public static Position smallerY(Position p1, Position p2) {
        if (p1.y < p2.y) {
            return p1;
        }
        return p2;
    }
    public static Position largerX(Position p1, Position p2) {
        if (p1.x > p2.x) {
            return p1;
        }
        return p2;
    }
    public static Position largerY(Position p1, Position p2) {
        if (p1.y > p2.y) {
            return p1;
        }
        return p2;
    }
}
