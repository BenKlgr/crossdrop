package socketserver.server;

import java.util.ArrayList;

public class ConnectionHandler {
	private static ArrayList<Connection> connections = new ArrayList<Connection>();
	
	public static void addConnection(Connection connection) {
		ConnectionHandler.connections.add(connection);
	}
	
	public static void removeConnection(Connection connection) {
		ConnectionHandler.connections.remove(connection);
	}
	
	public static Connection getConnection(String uuid) {
		for(Connection connection: ConnectionHandler.connections) {
			if(connection.getConnectionConfig().getUuid().getUUID().equals(uuid)) return connection;
		}
		return null;
	}
}
