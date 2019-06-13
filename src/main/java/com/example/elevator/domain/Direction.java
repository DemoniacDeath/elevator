package com.example.elevator.domain;

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
}
