package com.example.elevator.domain;

import java.util.ArrayList;
import java.util.List;

public enum Direction {
    UP,
    DOWN;

    @Override
    public String toString() {
        switch (this) {
            case UP:
                return "Up";
            case DOWN:
                return "Down";
        }
        return "UNKNOWN DIRECTION";
    }

    public static Direction compareFloors(int fromFloor, int toFloor) {
        if (fromFloor < toFloor) {
            return Direction.UP;
        } else if (fromFloor > toFloor) {
            return Direction.DOWN;
        } else {
            return null;
        }
    }

    public static List<Integer> countFloorsFromCurrentInDirection(int currentFloor, Direction direction, int maximumFloors) {
        List<Integer> floors = new ArrayList<>();
        switch (direction) {
            case UP:
                for (int i = currentFloor; i <= maximumFloors; i++) {
                    floors.add(i);
                }
                break;
            case DOWN:
                for (int i = currentFloor; i >= 1; i--) {
                    floors.add(i);
                }
                break;
        }
        return floors;
    }

    public static List<Integer> countFloorsBetweenTwoFloors(int fromFloor, int toFloor) {
        List<Integer> floors = new ArrayList<>();
        Direction direction = compareFloors(fromFloor, toFloor);
        if (direction == null) {
            return floors;
        }
        switch (direction) {
            case UP:
                for (int i = fromFloor; i < toFloor; i++) {
                    floors.add(i);
                }
                break;
            case DOWN:
                for (int i = fromFloor; i > toFloor; i--) {
                    floors.add(i);
                }
                break;
        }
        return floors;
    }
}
