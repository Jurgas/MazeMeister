public class Room {
    private Room[] connections = new Room[4];
    private boolean visited;
    private boolean[] analysedNeighbours = new boolean[4];

    public Room() {
        this.visited = false;
        for (int i = 0; i < 4; i++) {
            this.analysedNeighbours[i] = false;
        }
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

    public void setAnalysedNeighbour(Direction direction) {
        switch (direction) {
            case UP:
                this.analysedNeighbours[0] = true;
                break;
            case RIGHT:
                this.analysedNeighbours[1] = true;
                break;
            case DOWN:
                this.analysedNeighbours[2] = true;
                break;
            case LEFT:
                this.analysedNeighbours[3] = true;
                break;
        }
        int counter = 0;
        for (int i = 0; i < 4; i++) {
            if (analysedNeighbours[i])
                counter++;

        }
        if (counter == 4)
            this.visited = true;
    }
}
