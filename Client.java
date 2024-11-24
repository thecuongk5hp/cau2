import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 8080);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {
            Scanner scanner = new Scanner(System.in);
            System.out.println(in.readLine());
            String studentId = scanner.nextLine();
            out.println(studentId);
            System.out.println(in.readLine());
            System.out.println(in.readLine());
        } catch (IOException e) {
            System.out.println("Error connecting to server: " + e.getMessage());
        }
    }
}
