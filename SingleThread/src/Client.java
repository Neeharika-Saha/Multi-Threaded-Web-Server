import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {

    public void run() throws UnknownHostException, IOException {
        int port = 8010; // The port to connect to on the server
        InetAddress address = InetAddress.getByName("localhost"); // Server address (localhost for local testing)
        Socket socket = new Socket(address, port); // Create a socket to connect to the server
        PrintWriter toSocket = new PrintWriter(socket.getOutputStream(), true); // Output stream to send data to server
        BufferedReader fromSocket = new BufferedReader(new InputStreamReader(socket.getInputStream())); // Input stream to read data from server

        Scanner scanner = new Scanner(System.in); // Scanner to read user input from the console
        String userInput;

        System.out.println("Enter messages to send to the server (type 'exit' to quit):");
        while (true) {
            System.out.print("Client: ");
            userInput = scanner.nextLine(); // Read user input
            if (userInput.equalsIgnoreCase("exit")) {
                break; // Exit the loop if user types "exit"
            }
            toSocket.println(userInput); // Send user input to server
            String serverResponse = fromSocket.readLine(); // Read server response
            System.out.println("Server: " + serverResponse); // Print server response
        }

        // Close resources
        toSocket.close();
        fromSocket.close();
        socket.close();
        scanner.close();
    }

    public static void main(String[] args) {
        Client singleThreadedWebServer_Client = new Client();
        try {
            singleThreadedWebServer_Client.run(); // Run the client
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}

/*
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {

    public void run() throws UnknownHostException, IOException{
        int port = 8090;
        InetAddress address = InetAddress.getByName("localhost");
        Socket socket = new Socket(address, port);
        PrintWriter toSocket = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader fromSocket = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        toSocket.println("Hello World from socket "+socket.getLocalSocketAddress());
        String line = fromSocket.readLine();
        toSocket.close();
        fromSocket.close();
        socket.close();
    }

    public static void main(String[] args) {
        Client singleThreadedWebServer_Client = new Client();
        try{
            singleThreadedWebServer_Client.run();
        } catch(Exception ex){
            ex.printStackTrace();
        }
    }
}

// This is for jmeter checking
 */

