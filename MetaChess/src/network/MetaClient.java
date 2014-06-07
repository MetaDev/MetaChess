package network;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;

public class MetaClient {
	private static ArrayBlockingQueue<String> messages = new ArrayBlockingQueue<String>(
			10);
	private static int clientId=-1;
	private static Thread clientThread;
	
	//add message to queue
	public static void addMessage(String message) {
		messages.add(message);
	}
	//return and remove message form queue
	public static String getMessage() {
		return messages.poll();
	}
	//client loop
	//sends 1 to and receives 1 message from server
	public static void handleClientComm(){
		//if this client hesn't received it's id yet
		//add an introduction message to the queue
		if(clientId==-1){
			addMessage("hello\n");
		}
		//in this code the client should send it's current board state
		//and receive the state from the other clients
		String sentence;
		String serverMessage;
		
		Socket clientSocket;
		try {
			clientSocket = new Socket("localhost", 6789);
			//out
			
			DataOutputStream outToServer = new DataOutputStream(
					clientSocket.getOutputStream());
			
			sentence = getMessage();
			if(sentence!=null){
				outToServer.writeBytes(sentence);
			}

			//in
			BufferedReader inFromServer = new BufferedReader(
					new InputStreamReader(clientSocket.getInputStream()));
			serverMessage = inFromServer.readLine();
			//handle message
			//welcome message
			System.out.println("test");
			if(serverMessage.startsWith("welcome::")){
				clientId = Integer.parseInt(serverMessage.split("::")[1]);
			}
			System.out.println("FROM SERVER: " + serverMessage);
			clientSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//loop communication call in different thread
	public static void startClient(){
		clientThread = new Thread(new Runnable() {
			public void run() {
				try {
				       // The following test is necessary to get fast interrupts.  If
				       // it is replaced with 'true', the queue will be drained before
				       // the interrupt is noticed. 
				       while (!Thread.interrupted()) {
				    	   handleClientComm();
				       }
				   } catch (Exception ex) {
				       // We are done.
				   }
			}
		});
		clientThread.start();
	}
	public static void stopClient(){
		if (clientThread!=null)
			clientThread.interrupt();
	}

}
