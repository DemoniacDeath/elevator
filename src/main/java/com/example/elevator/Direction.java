package com.example.elevator;

public enum Direction {
    UP,
    DOWN;

    public static class FloorComparator {
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
}
