import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.util.ArrayList;


public class Player {
    private RequestHandler requestHandler = new RequestHandler();
    private Room[][] mappedMaze;
    private String map = "1";
    private String uid = "bd5f6f92";
    private int[] startingPosition = new int[2];
    private int[] currentPosition = new int[2];
    private int unvisitedNeighbours;
    private Room currentRoom;
    private ArrayList<Room> unexplored = new ArrayList<>();


    public Player setMap(String map) {
        this.map = map;
        return this;
    }

    public Player setUid(String uid) {
        this.uid = uid;
        return this;
    }

    private void createEmptyMappedMaze() {
        String wymiary = this.requestHandler.size(this.uid, this.map)[1].toString();
        int xPos = 0;
        for (int i = 0; i < wymiary.length(); i++) {
            if (wymiary.charAt(i) == 'x') {
                xPos = i;
                break;
            }
        }
        int width = Integer.parseInt(wymiary.substring(0, xPos));
        int height = Integer.parseInt(wymiary.substring(xPos + 1));
        this.mappedMaze = new Room[height][width];
    }

    private void checkUnvisitedNeighbours() {
        this.unvisitedNeighbours = 0;
        for (int i = 0; i < 4; i++) {
            if (this.currentRoom.getConnections()[i] != null && !this.currentRoom.getConnections()[i].isVisited()) {
                this.unvisitedNeighbours++;
            }
        }
    }

    private void getStartingPosition() {
        String startingPosition = this.requestHandler.startPosition(this.uid, this.map)[1].toString();
        int pos = 0;
        for (int i = 0; i < startingPosition.length(); i++) {
            if (startingPosition.charAt(i) == ',') {
                pos = i;
                break;
            }
        }
        this.startingPosition[0] = Integer.parseInt(startingPosition.substring(0, pos)) - 1;
        this.startingPosition[1] = Integer.parseInt(startingPosition.substring(pos + 1)) - 1;
        this.currentPosition = this.startingPosition;
        this.currentRoom = this.mappedMaze[this.currentPosition[1]][this.currentPosition[0]];
    }

    public static Direction indexToDirection(int index) {
        switch (index) {
            case 0:
                return Direction.UP;
            case 1:
                return Direction.RIGHT;
            case 2:
                return Direction.DOWN;
            case 3:
                return Direction.LEFT;
        }
        return null;
    }

    private void calculateNextMove() {
        if (this.unvisitedNeighbours > 0) {
            if (this.unvisitedNeighbours > 1) {
                this.unexplored.add(this.currentRoom);
            }

            for (int i = 0; i < 4; i++) {
                if (this.currentRoom.getConnections()[i] != null && !this.currentRoom.getConnections()[i].isVisited()) {
                    move(indexToDirection(i));
                    fillCurrentRoomNeighbours();
                    break;
                }
            }
        } else if (updateUnexploredList() > 0) {
            BFSAlgorithm bfsAlgorithm = new BFSAlgorithm();
            bfsAlgorithm.solve(currentRoom, unexplored).forEach(this::move);
            checkUnvisitedNeighbours();
        }
    }

    private void move(Direction direction) {
        switch (direction) {
            case UP:
                this.currentPosition[1]--;
                break;
            case RIGHT:
                this.currentPosition[0]++;
                break;
            case DOWN:
                this.currentPosition[1]++;
                break;
            case LEFT:
                this.currentPosition[0]--;
                break;
        }
        this.requestHandler.move(this.uid, this.map, direction);
        this.currentRoom = this.mappedMaze[this.currentPosition[1]][this.currentPosition[0]];
        this.currentRoom.setVisited(true);
    }

    private int updateUnexploredList() {
        int unvisitedNeighboursCounter;
        for (int i = 0; i < this.unexplored.size(); i++) {
            unvisitedNeighboursCounter = 0;
            for (int j = 0; j < 4; j++) {
                if (this.unexplored.get(i).getConnections()[j] != null && !this.unexplored.get(i).getConnections()[j].isVisited()) {
                    unvisitedNeighboursCounter++;
                }
            }
            if (unvisitedNeighboursCounter == 0) {
                this.unexplored.remove(i);
                i--;
            }
        }
        return this.unexplored.size();
    }

    private void fillCurrentRoomNeighbours() {
        String directions[] = new String[4];
        JSONObject jsonObject = new JSONObject((String) this.requestHandler.getPossibilities(this.uid, this.map)[1]);
        directions[0] = jsonObject.getString("up");
        directions[1] = jsonObject.getString("right");
        directions[2] = jsonObject.getString("down");
        directions[3] = jsonObject.getString("left");
        for (int i = 0; i < 4; i++) {
            if (directions[i].equals("0")) {
                switch (i) {
                    case 0:
                        if (this.mappedMaze[this.currentPosition[1] - 1][this.currentPosition[0]] == null) {
                            this.mappedMaze[this.currentPosition[1] - 1][this.currentPosition[0]] = new Room();
                        }
                        this.mappedMaze[this.currentPosition[1] - 1][this.currentPosition[0]].setConnection(Direction.DOWN, this.currentRoom);
                        this.currentRoom.setConnection(Direction.UP, this.mappedMaze[this.currentPosition[1] - 1][this.currentPosition[0]]);
                        break;
                    case 1:
                        if (this.mappedMaze[this.currentPosition[1]][this.currentPosition[0] + 1] == null) {
                            this.mappedMaze[this.currentPosition[1]][this.currentPosition[0] + 1] = new Room();
                        }
                        this.mappedMaze[this.currentPosition[1]][this.currentPosition[0] + 1].setConnection(Direction.LEFT, this.currentRoom);
                        this.currentRoom.setConnection(Direction.RIGHT, this.mappedMaze[this.currentPosition[1]][this.currentPosition[0] + 1]);
                        break;
                    case 2:
                        if (this.mappedMaze[this.currentPosition[1] + 1][this.currentPosition[0]] == null) {
                            this.mappedMaze[this.currentPosition[1] + 1][this.currentPosition[0]] = new Room();
                        }
                        this.mappedMaze[this.currentPosition[1] + 1][this.currentPosition[0]].setConnection(Direction.UP, this.currentRoom);
                        this.currentRoom.setConnection(Direction.DOWN, this.mappedMaze[this.currentPosition[1] + 1][this.currentPosition[0]]);
                        break;
                    case 3:
                        if (this.mappedMaze[this.currentPosition[1]][this.currentPosition[0] - 1] == null) {
                            this.mappedMaze[this.currentPosition[1]][this.currentPosition[0] - 1] = new Room();
                        }
                        this.mappedMaze[this.currentPosition[1]][this.currentPosition[0] - 1].setConnection(Direction.RIGHT, this.currentRoom);
                        this.currentRoom.setConnection(Direction.LEFT, this.mappedMaze[this.currentPosition[1]][this.currentPosition[0] - 1]);
                        break;
                }
            }
        }
        checkUnvisitedNeighbours();
    }

    private void createFirstRoom() {
        this.mappedMaze[this.startingPosition[1]][this.startingPosition[0]] = new Room();
        this.mappedMaze[this.startingPosition[1]][this.startingPosition[0]].setVisited(true);
        this.currentRoom = this.mappedMaze[this.currentPosition[1]][this.currentPosition[0]];
        fillCurrentRoomNeighbours();
    }

    public static void solve(String uid, String mapID) throws FileNotFoundException {
        RequestHandler requestHandlerReset = new RequestHandler();
        requestHandlerReset.reset(uid, mapID);
        Player player = new Player();
        player.setMap(mapID);
        player.setUid(uid);
        player.createEmptyMappedMaze();
        player.getStartingPosition();
        player.createFirstRoom();
        while (player.unvisitedNeighbours > 0 || !player.unexplored.isEmpty()) {
            player.calculateNextMove();
        }
        Writer writer = new Writer();
        writer.mapToFile(player.mappedMaze, mapID);
        requestHandlerReset.upload(uid, mapID, "./mazes/" + mapID + ".txt");
    }
}
