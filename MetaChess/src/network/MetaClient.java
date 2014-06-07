package network;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.ArrayBlockingQueue;

public class MetaClient {
	private static volatile ArrayBlockingQueue<String> messages = new ArrayBlockingQueue<String>(
			10);
	private static int clientId;
	private static Thread clientThread;
	private static Socket clientSocket;
	private static BufferedReader inFromServer;
	private static PrintWriter outToServer;

	// loop communication call in different thread
	public static void startClient() {
		// create new socket on servernumber port
		try {
			clientSocket = new Socket("localhost", 6789);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// add initial message to queue
		// manditory to register client
		addMessage("hello\n");

		// start an connection thread
		clientThread = new Thread(new Runnable() {
			public void run() {
				try {
					// The following test is necessary to get fast interrupts.
					// If
					// it is replaced with 'true', the queue will be drained
					// before
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

	// add message to queue
	public static void addMessage(String message) {
		messages.add(message);
	}

	// return and remove message form queue
	// this method blocks the thread if the queue is empty and waits until a new
	// message is added
	public static String getMessage() {
		return messages.poll();
	}

	// handel uitgoing client communication
	public static void handleClientComm() {
		try {

			// out
			outToServer = new PrintWriter(clientSocket.getOutputStream(), true);
			String sentence = getMessage();
			// a message to send
			if (sentence != null) {
				System.out.print(sentence);
				// send message
				outToServer.println(clientId + "::" + sentence);
			}
			// nothing to send
			else {
				outToServer.println(clientId + "::nomessage");
			}

			// in
			inFromServer = new BufferedReader(new InputStreamReader(
					clientSocket.getInputStream()));
			// readline() block until message from server is received
			String serverMessage = inFromServer.readLine();

			// process received message
			if (serverMessage != null) {
				// welcome message
				if (serverMessage.startsWith("welcome::")) {
					clientId = Integer.parseInt(serverMessage.split("::")[1]);
				} else if (!serverMessage.equals("nodata")) {
					// received normal message
					// handle it
					//serverMessage is null ???
					//System.out.println(serverMessage);
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// to stop client do something similar to server
	// close open socket
	// interrupt running in and out thread, if not already happened by the
	// closure of the socket
	public static void stopClient() {
		if (clientThread != null) {
			try {
				clientSocket.close();
				clientThread.interrupt();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

}
