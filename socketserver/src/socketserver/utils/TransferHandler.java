package socketserver.utils;

import java.io.IOException;
import java.io.OutputStream;

import socketserver.server.Connection;

public class TransferHandler {
	private Connection transferTarget;
	private Connection connection;
	
	public TransferHandler(Connection connection) {
		this.connection = connection;
	}
	
	public Connection getTransferTarget() {
		return this.transferTarget;
	}
	
	public void setTransferTarget(Connection connection) {
		this.transferTarget = connection;
	}
	
	public void transferBuffer(byte[] buffer) {
		if(this.transferTarget == null || !this.transferTarget.isThreadRunning()) {
			return;
		}
		Connection target = this.transferTarget;
		
		try {
			OutputStream stream = target.getSocketConnection().getOutputStream();
			
			stream.write(buffer);
			
			this.connection.printMessage("Sending Buffer to [" + target.getConnectionConfig().getUuid().getHoleUUID() + "]");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
