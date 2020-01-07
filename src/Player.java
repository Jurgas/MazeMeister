import org.json.JSONObject;

public class Player {
    private RequestHandler requestHandler = new RequestHandler();
    private Room[][] mappedMaze;
    private String map = "1";
    private String uid = "bd5f6f92";
    private int[] startingPossition = new int[2];
    private int[] currentPossition = new int[2];


    public Player setMap(String map) {
        this.map = map;
        return this;
    }

    public Player setUid(String uid) {
        this.uid = uid;
        return this;
    }

    public void createEmptyMappedMaze() {
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

    public void getStartingPossition() {
        String startingPossition = this.requestHandler.startPoss(this.uid, this.map)[1].toString();
        int pos = 0;
        for (int i = 0; i < startingPossition.length(); i++) {
            if (startingPossition.charAt(i) == ',') {
                pos = i;
                break;
            }
        }
        this.startingPossition[0] = Integer.parseInt(startingPossition.substring(0, pos)) - 1;
        this.startingPossition[1] = Integer.parseInt(startingPossition.substring(pos + 1)) - 1;
        this.currentPossition = this.startingPossition;
    }

    public void fillCurrentRoomDirections() {
        String directions[] = new String[4];
        JSONObject jsonObject = new JSONObject((String) this.requestHandler.getPoss(this.uid, this.map)[1]);
        directions[0] = jsonObject.getString("up");
        directions[1] = jsonObject.getString("right");
        directions[2] = jsonObject.getString("down");
        directions[3] = jsonObject.getString("left");

        for (int i = 0; i < 4; i++) {
            if (directions[i].equals("0")) {
                switch (i) {
                    case 0:
                        if(this.mappedMaze[this.currentPossition[1] - 1][this.currentPossition[0]] == null) {
                            this.mappedMaze[this.currentPossition[1] - 1][this.currentPossition[0]] = new Room();
                        }
                        this.mappedMaze[this.currentPossition[1] - 1][this.currentPossition[0]].setConnection('d', this.mappedMaze[this.currentPossition[1]][this.currentPossition[0]]);
                        this.mappedMaze[this.currentPossition[1]][this.currentPossition[0]].setConnection('u', this.mappedMaze[this.currentPossition[1] - 1][this.currentPossition[0]]);
                        break;
                    case 1:
                        if(this.mappedMaze[this.currentPossition[1]][this.currentPossition[0] + 1] == null) {
                            this.mappedMaze[this.currentPossition[1]][this.currentPossition[0] + 1] = new Room();
                        }
                        this.mappedMaze[this.currentPossition[1]][this.currentPossition[0] + 1].setConnection('l', this.mappedMaze[this.currentPossition[1]][this.currentPossition[0]]);
                        this.mappedMaze[this.currentPossition[1]][this.currentPossition[0]].setConnection('r', this.mappedMaze[this.currentPossition[1]][this.currentPossition[0] + 1]);
                        break;
                    case 2:
                        if(this.mappedMaze[this.currentPossition[1] + 1][this.currentPossition[0]] == null) {
                            this.mappedMaze[this.currentPossition[1] + 1][this.currentPossition[0]] = new Room();
                        }
                        this.mappedMaze[this.currentPossition[1] + 1][this.currentPossition[0]].setConnection('u', this.mappedMaze[this.currentPossition[1]][this.currentPossition[0]]);
                        this.mappedMaze[this.currentPossition[1]][this.currentPossition[0]].setConnection('d', this.mappedMaze[this.currentPossition[1] + 1][this.currentPossition[0]]);
                        break;
                    case 3:
                        if(this.mappedMaze[this.currentPossition[1]][this.currentPossition[0] - 1] == null) {
                            this.mappedMaze[this.currentPossition[1]][this.currentPossition[0] - 1] = new Room();
                        }
                        this.mappedMaze[this.currentPossition[1]][this.currentPossition[0] - 1].setConnection('r', this.mappedMaze[this.currentPossition[1]][this.currentPossition[0]]);
                        this.mappedMaze[this.currentPossition[1]][this.currentPossition[0]].setConnection('l', this.mappedMaze[this.currentPossition[1]][this.currentPossition[0] - 1]);
                        break;
                }
            }
        }
    }

    public void createFirstRoom() {
        this.mappedMaze[this.startingPossition[1]][this.startingPossition[0]] = new Room();
        this.mappedMaze[this.startingPossition[1]][this.startingPossition[0]].setVisited(true);
        fillCurrentRoomDirections();
    }

    public static void main(String[] args) {
        Player player = new Player();
        player.setMap("1");
        player.setUid("bd5f6f92");
        player.createEmptyMappedMaze();
        player.getStartingPossition();
//        System.out.println(player.startingPossition[0]);
//        System.out.println(player.startingPossition[1]);
        player.createFirstRoom();
        System.out.println();
    }

}
