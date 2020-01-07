import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Paths;

public class RequestHandler {


    public void getPoss(String uid, String map) {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://tesla.iem.pw.edu.pl:4444/" + uid + "/" + map + "/possibilities"))
                .build();

        response(client, request);
    }

    public void move(String uid, String map, String des) {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://tesla.iem.pw.edu.pl:4444/" + uid + "/" + map + "/move/" + des))
                .POST(HttpRequest.BodyPublishers.ofString(""))
                .build();

        response(client, request);
    }

    public void upload(String uid, String map, String FileName) {
        HttpClient client = HttpClient.newHttpClient();
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://tesla.iem.pw.edu.pl:4444/" + uid + "/" + map + "/upload"))
                    .POST(HttpRequest.BodyPublishers.ofFile(Paths.get(FileName)))
                    .build();
            response(client, request);
        } catch (FileNotFoundException ignored) {
        }

    }

    private void response(HttpClient client, HttpRequest request) {
        HttpResponse<String> response = null;
        try {
            response = client.send(request,
                    HttpResponse.BodyHandlers.ofString());
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
        System.out.println(response.statusCode()); //200
        System.out.println(response.body()); // Moved successfully
    }


    public static void main(String[] args) {
        RequestHandler r = new RequestHandler();
        r.getPoss("bd5f6f92" , "1");
        r.move("bd5f6f92", "1" , "left");
        r.getPoss("bd5f6f92" , "1");
    }
}
