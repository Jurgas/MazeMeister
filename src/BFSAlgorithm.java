import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class BFSAlgorithm {
    private Queue<BFSRoom> queue = new LinkedList<>();

    private class BFSRoom {
        Room room;
        ArrayList<Direction> previousDirections;

        BFSRoom(Room room, ArrayList<Direction> previousParentDirections, Direction direction) {
            this.previousDirections = new ArrayList<>();
            previousDirections.addAll(previousParentDirections);
            this.room = room;
            if(direction != null) {
                this.previousDirections.add(direction);
            }
        }
    }


    private boolean checkIfFinal(Room currentRoom, List<Room> finalRooms) {
        return finalRooms.contains(currentRoom);
    }

    private int directionToParentIndex(Direction direction) {
        switch (direction) {
            case UP:
                return 2;
            case RIGHT:
                return 3;
            case DOWN:
                return 0;
            case LEFT:
                return 1;
        }
        return -1;
    }

    public ArrayList<Direction> solve(Room startingRoom, List<Room> finalRooms) {
        ArrayList<Direction> answer;
        boolean solved = false;
        BFSRoom removed = null;
        Direction direction = null;
        this.queue.add(new BFSRoom(startingRoom, new ArrayList<>(), null));
        while (!solved) {
            removed = queue.remove();
            for (int i = 0; i < 4; i++) {
                if (removed.room.getConnections()[i] != null && (removed.previousDirections.isEmpty() || i != directionToParentIndex(removed.previousDirections.get(removed.previousDirections.size() - 1)))) {
                    direction = Player.indexToDirection(i);
                    if (checkIfFinal(removed.room.getConnections()[i], finalRooms)) {
                        solved = true;
                        break;
                    }
                    queue.add(new BFSRoom(removed.room.getConnections()[i], removed.previousDirections, direction));
                }
            }
        }
        answer = removed.previousDirections;
        answer.add(direction);
        return answer;
    }
}