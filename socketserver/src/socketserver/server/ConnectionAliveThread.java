package socketserver.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

public class ConnectionAliveThread implements Runnable {

	private Connection connection;
	
	public ConnectionAliveThread(Connection connection) {
		this.connection = connection;
	}
	
	@Override
	public void run() {
		while(this.connection.isThreadRunning()) {
			Socket socket = this.connection.getSocketConnection();
			
			try {
				
			} catch (Exception e1) {
				this.connection.closeConnection();
			}
			
			try {
				TimeUnit.SECONDS.sleep(6);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
}
