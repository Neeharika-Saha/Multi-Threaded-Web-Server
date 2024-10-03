import java.io.IOException;                 // Import for handling IO exceptions
import java.io.PrintWriter;               // Import for writing text to the output stream
import java.net.ServerSocket;             // Import for creating a server socket
import java.net.Socket;                   // Import for handling client connections
import java.util.concurrent.ExecutorService; // Import for managing a pool of threads
import java.util.concurrent.Executors;     // Import for creating thread pool instances

public class Server {
    private final ExecutorService threadPool; // Field to hold the thread pool

    // Constructor to initialize the thread pool with a given size
    public Server(int poolSize) {
        // Create a fixed thread pool with the specified number of threads
        this.threadPool = Executors.newFixedThreadPool(poolSize);
    }

    // Method to handle client connections
    public void handleClient(Socket clientSocket) {
        try (PrintWriter toSocket = new PrintWriter(clientSocket.getOutputStream(), true)) {
            // Send a welcome message to the client
            toSocket.println("Hello from server " + clientSocket.getInetAddress());
        } catch (IOException ex) {
            // Print stack trace if an IOException occurs
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        int port = 8010; // Port number on which the server will listen for incoming connections
        int poolSize = 10; // Number of threads in the thread pool, adjust as needed
        Server server = new Server(poolSize); // Create an instance of the Server class with the specified thread pool size

        try {
            // Create a ServerSocket to listen for incoming client connections on the specified port
            ServerSocket serverSocket = new ServerSocket(port);
            // Set a timeout for socket operations (70 seconds in this case)
            serverSocket.setSoTimeout(70000);
            System.out.println("Server is listening on port " + port);

            // Infinite loop to keep the server running and accepting new client connections
            while (true) {
                // Wait for a client connection and accept it
                Socket clientSocket = serverSocket.accept();

                // Submit a task to the thread pool to handle the client connection
                // The task is a lambda expression that calls the handleClient method
                server.threadPool.execute(() -> server.handleClient(clientSocket));
            }
        } catch (IOException ex) {
            // Print stack trace if an IOException occurs
            ex.printStackTrace();
        } finally {
            // Shutdown the thread pool gracefully when the server exits
            server.threadPool.shutdown();
        }
    }
}
