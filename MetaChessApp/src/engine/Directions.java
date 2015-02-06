/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package engine;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Harald
 */
public class Directions {

    public static class Direction {

        private int x;

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public String getName() {
            return x + "" + y;
        }

        public Direction(int x, int y) {
            this.x = x;
            this.y = y;
        }
        private int y;
        private String name;
    }

    //for turn;
    private static List<Direction> directionsOrthDiagOrder = new ArrayList<>();
    private static Map<String, Direction> allDirections = new HashMap<>();

    public static Direction getDirection(int x, int y) {
        String name = x + "" + y;
        if (allDirections.isEmpty()) {
            init();
        }
        return allDirections.get(name);
    }

    public static Direction turnDirection(Direction direction, int turn) {
        return directionsOrthDiagOrder.get((directionsOrthDiagOrder.indexOf(allDirections.get(direction.getName())) + turn) % directionsOrthDiagOrder.size());
    }

    private static void init() {
        Direction d;
        //orhtogonal and diagonal directions
        //all knight directions
        int[] indices;
        //downleft, left, upleft
        indices = new int[]{-1, 0, 1};
        for (int y : indices) {
            d = new Direction(-1, y);
            allDirections.put(d.getName(), d);
            directionsOrthDiagOrder.add(d);
        }
        //up
        d = new Direction(0, 1);
        allDirections.put(d.getName(), d);
        directionsOrthDiagOrder.add(d);
        //upright,right,downright
        indices = new int[]{-1, 0, 1};
        for (int y : indices) {
            d = new Direction(1, y);
            allDirections.put(d.getName(), d);
            directionsOrthDiagOrder.add(d);
        }
        //down
        d = new Direction(0, -1);
        allDirections.put(d.getName(), d);
        directionsOrthDiagOrder.add(d);

         //all knight directions
        indices = new int[]{-2, -1, 1, 2};
        for (int x : indices) {
            for (int y : indices) {
                if (Math.abs(x) != Math.abs(y)) {
                     d = new Direction(x,y);
                     allDirections.put(d.getName(), d);
                }
            }
        }

    }

    public static void getKnightDirections(Collection<Direction> coll) {
        //all knight directions
        int[] indices = new int[]{-2, -1, 1, 2};
        for (int x : indices) {
            for (int y : indices) {
                if (Math.abs(x) != Math.abs(y)) {
                    coll.add(getDirection(x, y));
                }
            }
        }
    }

    public static void getOrthoDirections(Collection<Direction> coll) {
        //all knight directions
        int[] indices = new int[]{-1, 0,1};
        for (int x : indices) {
            for (int y : indices) {
                if (Math.abs(x) != Math.abs(y)) {
                    coll.add(getDirection(x, y));
                }
            }
        }
    }
    public static void getDiagDirections(Collection<Direction> coll) {
        //all knight directions
        int[] indices = new int[]{-1,1};
        for (int x : indices) {
            for (int y : indices) {
                
                    coll.add(getDirection(x, y));
                
            }
        }
    }

}
