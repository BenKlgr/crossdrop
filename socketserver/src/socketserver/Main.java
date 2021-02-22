package socketserver;

import socketserver.server.Server;
import socketserver.utils.Logger;

import java.util.Scanner;

public class Main {
	
	private static Server server;
	private static Thread serverThread;

	public static void main(String[] args) {
		
		Logger.log("Server Manager started; Ready to listen to commands");
		
		Scanner inputScanner = new Scanner(System.in);

		while(inputScanner.hasNext()) {
			String input = inputScanner.nextLine();
			handleCommand(input);
		}
	}
	
	private static void startServer() {
		if(server != null) {
			Logger.log("Server already running!");
			return;
		}
		Logger.log("Starting Server Process");
		server = new Server("0.0.0.0", 7777);
		serverThread = new Thread(server);
		serverThread.setDaemon(true);
		serverThread.start();
		Logger.log("Finished Starting Process | Server running at 0.0.0.0:7777");
	}
	
	private static void stopServer() {
		if(server == null) {
			Logger.log("Server is not running!");
			return;
		}
		
		server.setIsRunning(false);
		server = null;
		serverThread = null;
		
		System.gc();
		
		Logger.log("Stopped Server");
	}
	
	private static void handleCommand(String command) {
		switch(command) {
			case "start":
				startServer();
				break;
			case "stop":
				stopServer();
				break;
			default:
				Logger.log("Cannot find command '" + command + "'");
				break;
		}
	}

}
