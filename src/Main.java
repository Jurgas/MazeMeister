import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) {
//        String uid = "f46f777f";    //Piotr
        String uid = "bd5f6f92";  //Sebastian

        for (int i = 1; i < 5; i++) {
            try {
                Player.solve(uid, Integer.toString(i));
                System.out.println("Mapa nr " + i + " została rozwiązana.");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
