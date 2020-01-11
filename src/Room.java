public class Room {
    private Room[] connections = new Room[4];
    private boolean visited;
    int id;

    public Room(int id) {
        super();
        this.id = id;
    }
    public Room() {
        this.visited = false;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public Room[] getConnections() {
        return connections;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setConnection(Direction direction, Room neighbour) {
        switch (direction) {
            case UP:
                this.connections[0] = neighbour;
                break;
            case RIGHT:
                this.connections[1] = neighbour;
                break;
            case DOWN:
                this.connections[2] = neighbour;
                break;
            case LEFT:
                this.connections[3] = neighbour;
                break;
        }
    }
}
