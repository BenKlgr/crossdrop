package socketserver.utils;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.Arrays;

import socketserver.server.Connection;
import socketserver.server.ConnectionHandler;

public class BufferHandler {
	
	Connection connection;
	
	public BufferHandler(Connection connection) {
		this.connection = connection;
	}
	
	public void readBuffer() {
		BufferedInputStream dataStream;
		try {
			dataStream = new BufferedInputStream(this.connection.getSocketConnection().getInputStream());
			while (this.connection.isThreadRunning()) {
				if(!this.connection.getSocketConnection().isConnected()) {
					this.connection.closeConnection();
					break;
				}
				
				if(!(dataStream.available() > 0)) continue;
				
				byte[] buffer = new byte[dataStream.available()];
				
				@SuppressWarnings("unused")
				int data = dataStream.read(buffer);
				int bufferInformationLength = 16 + 2 + 32;
				
				if(buffer.length == bufferInformationLength && this.isValidInformationBuffer(buffer)) {
					handleInformationBuffer(buffer);
				} else {
					transferBuffer(buffer);
				}
			}
		} catch (IOException e) {
			this.connection.closeConnection();
		}
	}
	
	private boolean isValidInformationBuffer(byte[] buffer) {
		int[] correctBytes = this.connection.getConnectionConfig().getInformationBufferId();
		for(int i=0; i<16; i++) {
			if(buffer[i] != correctBytes[i]) return false;
		}
		return true;
	}
	
	private void handleInformationBuffer(byte[] buffer) {
		int typeSum = buffer[16] + buffer[17];
		
		if(typeSum == 233) { // Register Writer
			String uuid = ByteHandler.getStringFromBytes(Arrays.copyOfRange(buffer, 18, buffer.length-1));
			this.connection.initSocket(uuid);
			this.connection.setReader(false);
			this.connection.printMessage("Register Socket as Writer");
		} else if(typeSum == 228) { // Register Receiver
			String uuid = ByteHandler.getStringFromBytes(Arrays.copyOfRange(buffer, 18, buffer.length-1));
			this.connection.initSocket(uuid);
			this.connection.setReader(true);
			this.connection.printMessage("Register Socket as Receiver");
		} else if(typeSum == 230) { // Register Target
			String uuid = ByteHandler.getStringFromBytes(Arrays.copyOfRange(buffer, 18, buffer.length-1));
			Connection target = ConnectionHandler.getConnection(uuid);
			this.connection.getTransferHandler().setTransferTarget(target);
			this.connection.printMessage("Registered Target");
		}
		return;
	}
	
	private void transferBuffer(byte[] buffer) {
		this.connection.getTransferHandler().transferBuffer(buffer);
	}
}
