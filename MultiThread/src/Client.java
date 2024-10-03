
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {

    // This method returns a Runnable that establishes a connection to the server and handles communication
    public Runnable getRunnable() throws UnknownHostException, IOException {
        return new Runnable() {
            @Override
            public void run() {
                int port = 8010;
                try {
                    InetAddress address = InetAddress.getByName("localhost");
                    // Create a socket to connect to the server
                    Socket socket = new Socket(address, port);
                    // Try-with-resources to ensure the socket and streams are closed automatically
                    try (
                            PrintWriter toSocket = new PrintWriter(socket.getOutputStream(), true);
                            BufferedReader fromSocket = new BufferedReader(new InputStreamReader(socket.getInputStream()))
                    ) {
                        // Send a message to the server
                        toSocket.println("Hello from Client " + socket.getLocalSocketAddress());
                        // Read and print the server's response
                        String line = fromSocket.readLine();
                        System.out.println("Response from Server " + line);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    // The socket will be closed automatically when leaving the try-with-resources block
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
    }

    public static void main(String[] args){
        Client client = new Client();
        // Create and start 100 threads, each executing the getRunnable method
        for(int i = 0; i < 100; i++){
            try {
                Thread thread = new Thread(client.getRunnable());
                thread.start();
            } catch(Exception ex){
                return;
            }
        }
    }
}

