import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Paths;

public class RequestHandler {


    public Object[] getPossibilities(String uid, String map) {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://tesla.iem.pw.edu.pl:4444/" + uid + "/" + map + "/possibilities"))
                .build();

        return response(client, request);
    }

    public Object[] move(String uid, String map, Direction direction) {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://tesla.iem.pw.edu.pl:4444/" + uid + "/" + map + "/move/" + direction.toString().toLowerCase()))
                .POST(HttpRequest.BodyPublishers.ofString(""))
                .build();

        return response(client, request);
    }

    public Object[] upload(String uid, String map, String FileName) throws FileNotFoundException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://tesla.iem.pw.edu.pl:4444/" + uid + "/" + map + "/upload"))
                .POST(HttpRequest.BodyPublishers.ofFile(Paths.get(FileName)))
                .build();
        return response(client, request);

    }

    public Object[] reset(String uid, String map){
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://tesla.iem.pw.edu.pl:4444/" + uid + "/" + map + "/reset"))
                .POST(HttpRequest.BodyPublishers.ofString(""))
                .build();
        return response(client, request);

    }

    public Object[] moves(String uid, String map) {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://tesla.iem.pw.edu.pl:4444/" + uid + "/" + map + "/moves"))
                .build();

        return response(client, request);
    }

    public Object[] startPosition(String uid, String map) {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://tesla.iem.pw.edu.pl:4444/" + uid + "/" + map + "/startposition"))
                .build();

        return response(client, request);
    }

    public Object[] size(String uid, String map) {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://tesla.iem.pw.edu.pl:4444/" + uid + "/" + map + "/size"))
                .build();

        return response(client, request);
    }



    private Object[] response(HttpClient client, HttpRequest request) {
        HttpResponse<String> response = null;
        try {
            response = client.send(request,
                    HttpResponse.BodyHandlers.ofString());
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }

        return new Object[]{response.statusCode(), response.body()};
    }
}
