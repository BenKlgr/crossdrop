package socketserver.server;

import java.net.*;

import socketserver.utils.Logger;

import java.io.*;

public class Server implements Runnable {
		
	@SuppressWarnings("unused")
	private String address;
	private int port;
	
	@SuppressWarnings("unused")
	private boolean productionMode = false;
	
	private ServerSocket serverSocket;
	
	private boolean isRunning = true;
	
	public Server(String address, int port) {
		this.address = address;
		this.port = port;
	}
	
	private void createServer() {
		try {
			this.setServerSocket(new ServerSocket(this.port));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void startServer() {
		this.createServer();
	}
	
	public void startServerListening() {
		ServerSocket server = this.getServerSocket();

		while (this.isRunning) {
			Socket client = null;
			try {
				client = server.accept();
			} catch (IOException e) {
				Logger.log("Server Socket was not longer able to accept.");
				continue;
			}
			
			String threadName = client.getInetAddress().toString();
			Connection socketConnection = new Connection(client, threadName);
			socketConnection.startThread();
			
			
			ConnectionHandler.addConnection(socketConnection);
		}
	}
	
	public void setIsRunning(boolean state ) {
		this.isRunning = state;
		
		if(!state) {
			try {
				this.serverSocket.close();
			} catch (IOException e) {
				Logger.log("Error while closing Server Socket!");
			}
		}
	}

	public ServerSocket getServerSocket() {
		return serverSocket;
	}

	public void setServerSocket(ServerSocket serverSocket) {
		this.serverSocket = serverSocket;
	}

	@Override
	public void run() {
		this.startServer();
		this.startServerListening();
	}
}
