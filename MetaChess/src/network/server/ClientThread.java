package network.server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientThread extends Thread {

	private Socket clientSocket = null;
	public ClientThread(Socket socket) {

		super("MiniServer");
		this.clientSocket = socket;

	}

	public void run() {
		try {
			System.out.println("thread for client started");
			while (!Thread.interrupted()) {

				// in
				String clientSentence;
				String response = null;
				BufferedReader inFromClient = new BufferedReader(
						new InputStreamReader(clientSocket.getInputStream()));
				clientSentence = inFromClient.readLine();
				// handle client message
				if (clientSentence != null) {
					// first introduction of client
					if (clientSentence.startsWith("hello")) {
						int id = MetaServer.addClient();
						response = "welcome::" + id;
						System.out.println("client added: " + MetaServer.nrOfClients());
					}
					else {
						int clientId = Integer
								.parseInt(clientSentence.split("::")[0]);
						String clientMessage = clientSentence.split("::")[1];
						// it's a normal message, save it for all other
						// registered clients
						// message structure clientid::message
						// parse client id from sentence
						
						// if the message is meaningfull add to queue
						if (!clientMessage.equals("nomessage")) {
							MetaServer.addMessage(clientMessage, clientId);
							System.out.println(clientMessage);
						}
						response = MetaServer.getMessage(clientId);
					} 
				}

				// if no messages for client, respond with nodata
				if (response == null) {
					response = "nodata";
				}
				// out
				PrintWriter outToClient = new PrintWriter(
						clientSocket.getOutputStream(), true);
				outToClient.println(response);

			}

		} catch (Exception e) {

			e.printStackTrace();
		} finally {
			// clean up in any case
			if (clientSocket != null && !clientSocket.isClosed()) {
				try {
					clientSocket.close();

				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
	}
}
