import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) {
        String uid = "f46f777f";    //ja
//        String uid = "bd5f6f92";  //Sebix

        Player player = new Player();
        for (int i = 4; i < 5; i++) {
            try {
                player.solve(uid, Integer.toString(i));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
