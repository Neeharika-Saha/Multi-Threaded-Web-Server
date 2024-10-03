import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

public class Server {

    private static final int PORT = 8010; // Server will listen on this port
    private static final int TIMEOUT = 20000; // Socket timeout duration in milliseconds
    private static final int MAX_TIMEOUTS = 5; // Maximum number of timeouts before the server terminates

    public void run() throws IOException, UnknownHostException {
        ServerSocket serverSocket = new ServerSocket(PORT); // Create a server socket listening on PORT
        serverSocket.setSoTimeout(TIMEOUT); // Set timeout for the server socket
        int timeoutCount = 0; // Initialize timeout counter

        while (timeoutCount < MAX_TIMEOUTS) {
            System.out.println("Server is listening on port: " + PORT);
            try {
                Socket acceptedConnection = serverSocket.accept(); // Accept incoming connection
                System.out.println("Connected to " + acceptedConnection.getRemoteSocketAddress());

                // Create streams for communication
                PrintWriter toClient = new PrintWriter(acceptedConnection.getOutputStream(), true);
                BufferedReader fromClient = new BufferedReader(new InputStreamReader(acceptedConnection.getInputStream()));

                // Read and respond to messages in a loop
                String clientMessage;
                while ((clientMessage = fromClient.readLine()) != null) {
                    System.out.println("Received from client: " + clientMessage);
                    toClient.println("Server response to: " + clientMessage); // Send response to client
                }

                acceptedConnection.close(); // Close the connection
                timeoutCount = 0; // Reset the timeout count after a successful connection
            } catch (SocketTimeoutException e) {
                timeoutCount++; // Increment timeout counter
                System.out.println("Socket timed out. Waiting for next connection... (" + timeoutCount + ")");
            }
        }

        System.out.println("Maximum timeouts reached. Terminating server.");
        serverSocket.close(); // Close the server socket
    }

    public static void main(String[] args) {
        Server server = new Server();
        try {
            server.run(); // Run the server
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}

/*
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class Server {

    public void run() throws IOException, UnknownHostException{
        int port = 8010;
        ServerSocket socket = new ServerSocket(port);
        socket.setSoTimeout(20000);
        while(true){
            System.out.println("Server is listening on port: "+port);
            Socket acceptedConnection = socket.accept();
            System.out.println("Connected to "+acceptedConnection.getRemoteSocketAddress());
            PrintWriter toClient = new PrintWriter(acceptedConnection.getOutputStream(), true);
            BufferedReader fromClient = new BufferedReader(new InputStreamReader(acceptedConnection.getInputStream()));
            toClient.println("Hello World from the server");
        }
    }

    public static void main(String[] args){
        Server server = new Server();
        try{
            server.run();
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

}

// This is for jmeter checking
 */

