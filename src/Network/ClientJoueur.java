package Network;
import java.io.*;
import java.net.*;

public class ClientJoueur extends Thread{

	//Variables client
	private Socket socket;
    private ServeurCentral server;
    private BufferedReader in;
    private PrintWriter out;
	
	
	
	//Constructeur
	public ClientJoueur(Socket socket, ServeurCentral server) {
		this.socket = socket;
        this.server = server;
        
		try {
       
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
		
	}
	
	public void sendMessage(String message) {
        out.println(message);
    }
	
	// Methods to send player actions to the server
    public void sendMoveLeftCommand() {
        sendMessage("MOVE_LEFT");
    }

    public void sendMoveRightCommand() {
        sendMessage("MOVE_RIGHT");
    }

    public void sendJumpCommand() {
        sendMessage("JUMP");
    }

    public void sendAttackCommand() {
        sendMessage("ATTACK");
    }
	
	@Override
    public void run() {
        try {
            while (true) {
                String message = in.readLine();
                if (message != null) {
                    // Handle incoming messages from client
                    System.out.println("Received from client: " + message);
                    // Example: server.broadcastMessage(message, this);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	

}
