import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Arrays;

public class Writer {

    public void mapToFile(Room[][] mappedMaze, String mapIndex) throws FileNotFoundException {
        String filePath = "./mazes/" + mapIndex + ".txt";
        int height = mappedMaze.length;
        int width = mappedMaze[0].length;
        char[][] maze = new char[height * 2 + 1][width * 2 + 1];
        for (int i = 0; i < height * 2 + 1; i++) {
            Arrays.fill(maze[i], '+');
        }
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (mappedMaze[y][x] != null && mappedMaze[y][x].isVisited()) {
                    maze[y * 2 + 1][x * 2 + 1] = '0';
                    for (int i = 0; i < 4; i++) {
                        if (mappedMaze[y][x].getConnections()[i] != null) {
                            switch (i) {
                                case 0:
                                    maze[y * 2][x * 2 + 1] = '0';
                                    break;
                                case 1:
                                    maze[y * 2 + 1][x * 2 + 2] = '0';
                                    break;
                                case 2:
                                    maze[y * 2 + 2][x * 2 + 1] = '0';
                                    break;
                                case 3:
                                    maze[y * 2 + 1][x * 2] = '0';
                                    break;
                            }
                        }
                    }

                }
            }
        }
        System.out.println();
        PrintWriter printWriter = new PrintWriter(new File(filePath));
        for (int i = 0; i < height * 2 + 1; i++) {
            for (int j = 0; j < width * 2 + 1; j++) {
                printWriter.print(maze[i][j]);
            }
            if (i != height * 2) {
                printWriter.println();
            }
        }
        printWriter.close();
    }
}
