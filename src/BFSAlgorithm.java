import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class BFSAlgorithm {

    private class BFSRoom {
        Room room;
        ArrayList<Direction> previousDirections;

        BFSRoom(Room room, ArrayList<Direction> previousParentDirections, Direction previousDirection) {
            this.room = room;
            this.previousDirections = previousParentDirections;
            this.previousDirections.add(previousDirection);
        }
    }

    private Queue<BFSRoom> queue = new LinkedList<>();

    private boolean checkIfFinal(Room currentRoom, List<Room> finalRooms) {
        return finalRooms.contains(currentRoom);
    }

    public ArrayList<Direction> solve(Room startingRoom, List<Room> finalRooms) {
        ArrayList<Direction> answer;
        boolean solved = false;
        BFSRoom removed = null;
        Direction previousDirection = null;
        queue.add(new BFSRoom(startingRoom, null, null));
        while (!solved) {
            removed = queue.remove();
            for (int i = 0; i < 4; i++) {
                if (removed.room.getConnections()[i] != null) {
                    switch (i) {
                        case 0:
                            previousDirection = Direction.DOWN;
                            break;
                        case 1:
                            previousDirection = Direction.LEFT;
                            break;
                        case 2:
                            previousDirection = Direction.UP;
                            break;
                        case 3:
                            previousDirection = Direction.RIGHT;
                            break;
                    }
                    if (checkIfFinal(removed.room.getConnections()[i], finalRooms)) {
                        solved = true;
                        break;
                    }
                    queue.add(new BFSRoom(removed.room.getConnections()[i], removed.previousDirections, previousDirection));
                }
            }
        }
        answer = removed.previousDirections;
        answer.add(previousDirection);
        return answer;
    }
}