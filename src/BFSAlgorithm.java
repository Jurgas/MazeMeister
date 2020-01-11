import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class BFSAlgorithm {

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

    private Queue<BFSRoom> queue = new LinkedList<>();

    private boolean checkIfFinal(Room currentRoom, List<Room> finalRooms) {
        return finalRooms.contains(currentRoom);
    }

    public ArrayList<Direction> solve(Room startingRoom, List<Room> finalRooms) {
        ArrayList<Direction> answer;
        boolean solved = false;
        BFSRoom removed = null;
        Direction direction = null;
        queue.add(new BFSRoom(startingRoom, new ArrayList<>(), null));
        while (!solved) {
            System.out.println("Działam sobie dzałam...");
            removed = queue.remove();
            for (int i = 0; i < 4; i++) {
                if (removed.room.getConnections()[i] != null) {
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

    public static void main(String[] args) {
        BFSAlgorithm bfsAlgorithm = new BFSAlgorithm();
        Room room1 = new Room(1);
        Room room2 = new Room(2);
        Room room3 = new Room(3);
        Room room4 = new Room(4);
        Room room5 = new Room(5);
        Room room6 = new Room(6);
        Room room7 = new Room(7);
        Room room8 = new Room(8);
        room1.setConnection(Direction.UP, room2);
        room2.setConnection(Direction.DOWN, room1);

        room2.setConnection(Direction.LEFT, room3);
        room3.setConnection(Direction.RIGHT, room2);

        room1.setConnection(Direction.DOWN, room4);
        room4.setConnection(Direction.UP, room1);

        room4.setConnection(Direction.DOWN, room5);
        room5.setConnection(Direction.UP, room4);

        room5.setConnection(Direction.RIGHT, room6);
        room6.setConnection(Direction.LEFT, room5);

        room5.setConnection(Direction.DOWN, room7);
        room7.setConnection(Direction.UP, room5);

        room6.setConnection(Direction.RIGHT, room8);
        room8.setConnection(Direction.LEFT, room6);

        ArrayList<Room> unexplored = new ArrayList<>();
     //   unexplored.add(room3);
        unexplored.add(room8);
    //    unexplored.add(room7);
        System.out.println(bfsAlgorithm.solve(room4, unexplored).toString());
    }
}