public class Room {
    private Room[] connections = new Room[4];
    private boolean visited;
    private int availableDirections;

    public Room() {
        this.visited = false;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public void setConnection(char direction, Room neighbour) {
        switch (direction) {
            case 'u':
                this.connections[0] = neighbour;
                break;
            case 'r':
                this.connections[1] = neighbour;
                break;
            case 'd':
                this.connections[2] = neighbour;
                break;
            case 'l':
                this.connections[3] = neighbour;
                break;
        }
    }
}
