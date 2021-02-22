package socketserver.server;

import java.net.Socket;

import socketserver.utils.BufferHandler;
import socketserver.utils.TransferHandler;

public class Connection implements Runnable {
	
	private ConnectionConfig config;
	
	private boolean isReader = false;
	
	private Thread thread;
	private Thread aliveThread;
	private boolean threadRunning;
	
	private Socket socketConnection;
	private String threadName = "unnamed thread";
	
	private BufferHandler bufferHandler;
	private TransferHandler transferHandler;
	
	public Connection(Socket socket, String threadName) {
		this.socketConnection = socket;
		this.threadName = threadName;
		
		this.bufferHandler = new BufferHandler(this);
		this.transferHandler = new TransferHandler(this);
		this.config = new ConnectionConfig(this.socketConnection.getInetAddress().toString());
	}
	
	public void initSocket(String id) {
		this.config.getUuid().setUUID(id);
	}
	
	public void startThread() {
		this.thread = new Thread(this, this.threadName);
		this.thread.setDaemon(true);
		this.setThreadRunning(true);
		this.thread.start();
		
		this.aliveThread = new Thread(new ConnectionAliveThread(this), "aliveThread");
		this.aliveThread.setDaemon(true);
		this.aliveThread.start();
		
		this.printMessage("Started Thread");
	}
	
	public void closeConnection() {
		if(this.thread != null && this.thread.isAlive()) {
			this.setThreadRunning(false);
			this.thread = null;
		}
		if(this.aliveThread != null && this.aliveThread.isAlive()) {
			this.setThreadRunning(false);
			this.aliveThread = null;
		}
		
		this.printMessage("Stopped Thread due to no socket connection");
		ConnectionHandler.removeConnection(this);
		System.gc();
	}
	
	@Override
	public void run() {
		this.bufferHandler.readBuffer();
	}
	
	public ConnectionConfig getConnectionConfig() {
		return this.config;
	}
	
	public void printMessage(String message) {
		// DEBUG: Remove later
		System.out.println("[" + this.config.getUuid().getHoleUUID() + this.config.getIpaddress() + "] " + message);
	}

	public boolean isThreadRunning() {
		return threadRunning;
	}

	public void setThreadRunning(boolean threadRunning) {
		this.threadRunning = threadRunning;
	}
	
	public Socket getSocketConnection() {
		return this.socketConnection;
	}
	
	public TransferHandler getTransferHandler() {
		return this.transferHandler;
	}
	
	public void setReader(boolean state) {
		this.isReader = state;
	}
	
	public boolean getReader() {
		return this.isReader;
	}
}
