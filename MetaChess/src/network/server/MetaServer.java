package network.server;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/*
 * This class starts a dedicated server
 * 
 */
public class MetaServer {
	// we use a map instead of a list because the client id does'nt define an
	// order
	private static volatile Map<Integer, ArrayBlockingQueue<String>> clientsMessages = new HashMap<>();
	private static volatile ServerSocket serverSocket = null;
	private static volatile Thread serverThread;
	private static volatile Socket clientSocket;

	public static void main(String argv[]) {
		buildGui();
	}

	private static void buildGui() {
		// start gui
		SwingUtilities.invokeLater(new Runnable() { // Note 1
					public void run() {
						JFrame window = new JFrame();
						JPanel mainframe = new JPanel(new GridLayout(0, 2));
						JButton start = new JButton("Start");
						JButton stop = new JButton("Stop");
						mainframe.add(start);
						mainframe.add(stop);
						start.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								start();
							}
						});
						stop.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								stop();
							}
						});
						window.setSize(new Dimension(200, 100));
						window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
						window.getContentPane().add(mainframe);
						window.setVisible(true);
					}
				});
	}

	private static void start() {
		// the serverthread will always terminate by an exception, either
		// voluntary or not
		//start a thread for every incoming client
		serverThread = new Thread(new Runnable() {
			public void run() {
				try {
					serverSocket = new ServerSocket(6789);
					System.out.println("running");
					ClientThread clientThread;
					while (!Thread.interrupted()) {
						System.out.println("waiting for client");
						clientSocket = serverSocket.accept();

						clientThread = new ClientThread(clientSocket);
						clientThread.start();

					}
				} catch (Exception e) {
					// prints errors
					e.printStackTrace();
				} finally {
					System.out.println("clean up");
					// if the server thread hasn't failed on purpose clean up
					stop();
					// else the server has been ended purpose and the clean has
					// already been done
					System.out.println("stopped");
				}
			}
		});
		serverThread.start();

	}

	// adds new client and returns id
	public static int addClient() {
		int id = clientsMessages.size();
		System.out.println(id+ "test");
		clientsMessages.put(id, new ArrayBlockingQueue<String>(10));
		return id;
	}

	public static String getMessage(int clientId) {
		return clientsMessages.get(clientId).poll();
	}

	public static void addMessage(String message, int clientId) {
		for (int otherId : clientsMessages.keySet()) {
			if (otherId != clientId) {
				clientsMessages.get(otherId).add(message);
			}
		}
	}

	// maybe should be done with try with resources, because every clean can in
	// his turn throw exceptions
	// this method is pure on own thinking, needs to be checked
	public static void stop() {
		// closing the server socket will terminate alle server activities by
		// throwing a socket exception on next .accept() call
		if (serverSocket != null && !serverSocket.isClosed()) {
			try {
				serverSocket.close();
				// terminate client socket if necessary
				if (clientSocket != null && !clientSocket.isClosed()) {
					clientSocket.close();
				}
				
				// and finally terminate serverthread
				if (serverThread != null && !serverThread.isInterrupted()) {
					serverThread.interrupt();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public static int nrOfClients(){
		return clientsMessages.keySet().size();
	}

}
