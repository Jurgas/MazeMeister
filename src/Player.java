import org.json.JSONObject;

public class Player {
    RequestHandler requestHandler = new RequestHandler();
    Room[][] mappedMaze;
    String map;
    int[] startingPossition = new int[2];
    int[] currentPossition = new int[2];


    public void createEmptyMappedMaze() {
        String wymiary = this.requestHandler.size("bd5f6f92", "1")[1].toString();
        int xPos = 0;
        for (int i = 0; i < wymiary.length(); i++) {
            if(wymiary.charAt(i) == 'x') {
                xPos = i;
                break;
            }
        }
        int width = Integer.parseInt(wymiary.substring(0, xPos));
        int height = Integer.parseInt(wymiary.substring(xPos + 1));
        this.mappedMaze = new Room[height][width];
    }

    public void getStartingpossition() {
        String startingPossition = this.requestHandler.startPoss("bd5f6f92", "1")[1].toString();
        int pos = 0;
        for (int i = 0; i < startingPossition.length(); i++) {
            if(startingPossition.charAt(i) == ',') {
                pos = i;
                break;
            }
        }
        this.startingPossition[0] = Integer.parseInt(startingPossition.substring(0, pos)) - 1;
        this.startingPossition[1] = Integer.parseInt(startingPossition.substring(pos + 1)) - 1;
        this.currentPossition = this.startingPossition;
    }

    public void createFirstRoom() {
        this.mappedMaze[this.startingPossition[1]][this.startingPossition[0]] = new Room();
        String possitions = this.requestHandler.getPoss("bd5f6f92", "1")[1].toString();
        if(possitions.charAt(9) == '0') {

        }
    }

    public static void main(String[] args) {
        Player player = new Player();
        player.createEmptyMappedMaze();
        player.getStartingpossition();
        System.out.println(player.startingPossition[0]);
        System.out.println(player.startingPossition[1]);
    }

}
