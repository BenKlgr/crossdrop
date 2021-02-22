package socketserver.utils;

import java.util.Random;

public class UUID {
	
	private	String	UUID	;
	private String	suffix	;
	
	public UUID(String uuid) {
		this.UUID = uuid;
		this.generateRandomSuffix();
	}
	
	public String getHoleUUID() {
		return this.UUID + "@" + this.suffix;
	}
	
	public String getUUID() {
		return this.UUID;
	}
	
	public void setUUID(String uuid) {
		this.UUID = uuid;
	}
	
	private void generateRandomSuffix() {
		int leftLimit = 97;
	    int rightLimit = 122;
	    int targetStringLength = 4;
	    Random random = new Random();

	    String generatedString = random.ints(leftLimit, rightLimit + 1)
	      .limit(targetStringLength)
	      .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
	      .toString();
	    
	    this.suffix = generatedString;
	}
}
