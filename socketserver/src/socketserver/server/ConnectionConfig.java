package socketserver.server;

import socketserver.utils.UUID;

public class ConnectionConfig {
	private UUID uuid;
	private String ipaddress;
	private int[] informationBufferId = new int[] {98, 117, 102, 102, 101, 114, 117, 110, 105, 113, 117, 101, 105, 100, 115, 107};
	
	public ConnectionConfig(String ipaddress) {
		this.ipaddress = ipaddress;
	}
	
	public UUID getUuid() {
		if(uuid == null) {
			this.uuid = new UUID("unregistrated");
		}
		return uuid;
	}
	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}
	public String getIpaddress() {
		return ipaddress;
	}
	public void setIpaddress(String ipaddress) {
		this.ipaddress = ipaddress;
	}
	public int[] getInformationBufferId() {
		return informationBufferId;
	}
	public void setInformationBufferId(int[] informationBufferId) {
		this.informationBufferId = informationBufferId;
	}
}
