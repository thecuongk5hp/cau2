import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

public class Server {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(8080)) {
            System.out.println("Server is running on port 8080...");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected!");
                Thread clientHandler = new Thread(() -> handleClient(clientSocket));
                clientHandler.start();
            }
        } catch (IOException e) {
            System.out.println("Error starting server: " + e.getMessage());
        }
    }
    private static void handleClient(Socket clientSocket) {
        try (
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)
        ) {
            out.println("Enter your student ID: ");
            String studentId = in.readLine();
            System.out.println("Received Student ID: " + studentId);
            int evenSum = calculateEvenSum(studentId);
            List<Integer> primeNumbers = findPrimeNumbers(studentId);
            out.println("Sum of even digits: " + evenSum);
            out.println("Prime numbers: " + primeNumbers);
        } catch (IOException e) {
            System.out.println("Error handling client: " + e.getMessage());
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                System.out.println("Error closing client socket: " + e.getMessage());
            }
        }
    }
    private static int calculateEvenSum(String studentId) {
        int sum = 0;
        for (char c : studentId.toCharArray()) {
            if (Character.isDigit(c)) {
                int digit = Character.getNumericValue(c);
                if (digit % 2 == 0) {
                    sum += digit;
                }
            }
        }
        return sum;
    }
    private static List<Integer> findPrimeNumbers(String studentId) {
        List<Integer> primes = new ArrayList<>();
        for (char c : studentId.toCharArray()) {
            if (Character.isDigit(c)) {
                int digit = Character.getNumericValue(c);
                if (isPrime(digit)) {
                    primes.add(digit);
                }
            }
        }
        return primes;
    }
    private static boolean isPrime(int num) {
        if (num < 2) return false;
        for (int i = 2; i <= Math.sqrt(num); i++) {
            if (num % i == 0) return false;
        }
        return true;
    }
}
