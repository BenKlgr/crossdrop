package socketserver.utils;

public class ByteHandler {
	public static String getStringFromBytes(byte[] bytes) {
		String returnValue = "";
		for(byte b: bytes) {
			if(b == 0) continue;
			char c = (char) (b & 0xFF);
			returnValue = returnValue + c;
		}
		return returnValue;
	}
}
