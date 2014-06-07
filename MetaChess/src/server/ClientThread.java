package server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ClientThread extends Thread {

	private Socket connectionSocket = null;

	public ClientThread(Socket socket) {

		super("MiniServer");
		this.connectionSocket = socket;

	}

	public void run() {
		try {
			String clientSentence;
			BufferedReader inFromClient = new BufferedReader(
					new InputStreamReader(connectionSocket.getInputStream()));
			DataOutputStream outToClient = new DataOutputStream(
					connectionSocket.getOutputStream());
			System.out.println(inFromClient);
			clientSentence = inFromClient.readLine();
			System.out.println(clientSentence);
			if (clientSentence != null) {
				//first introduction of client
				if(clientSentence.equals("hello")){
					int id = MetaServer.addClient();
					//send id to client
					outToClient.writeBytes("welcome::"+id + "\n");
				}
				
				else{
					//it's a normal message, save it for all other registered clients
					//message structure clientid::message
					
					//parse client id from sentence
					int clientId = Integer.parseInt(clientSentence.split("::")[0]);
					String message = clientSentence.split("::")[1];
					
					MetaServer.addMessage(message, clientId);
					
					//send one message from the queue to client
					//message already containes newline
					outToClient.writeBytes(MetaServer.getMessage(clientId));
				}
				
				
			}
			//close client socket after receiving and sending all data
			connectionSocket.close();	
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		finally{
			//clean up in any case
			if(connectionSocket!= null && !connectionSocket.isClosed()){
				try {
					connectionSocket.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
	}
	

}
