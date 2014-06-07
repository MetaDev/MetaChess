package server;

import java.util.Arrays;

public class MessageTranslator {
	// translate incoming message into a method call
	// clientid has already been parsed
	public static void inMessage(String message) {
		String[] stringArray = message.split(";");
		if(stringArray[0].equals("movement")){
			int[] xfrom = parseIntArray(stringArray[1]);
			int[] yfrom = parseIntArray(stringArray[2]);
			int range = Integer.parseInt(stringArray[3]);
			String direction = stringArray[4];
		}
		
	}
	//message of decision
	public static String decisionOutMessage(int clientid, String decision,int[] xfrom, int[] yfrom,
			int range, String direction) {
		return clientid + "::" +decision+";" + Arrays.toString(xfrom) + ";"
				+ Arrays.toString(yfrom) + ";" + range + ";"
				+ direction;
	}
	
	
	private static int[] parseIntArray(String array){
		String[] items = array.replaceAll("\\[", "").replaceAll("\\]", "").split(",");

		int[] results = new int[items.length];

		for (int i = 0; i < items.length; i++) {
		    try {
		        results[i] = Integer.parseInt(items[i]);
		    } catch (NumberFormatException nfe) {};
		}
		return results;
	}
	public static String outMessage(String result) {
		return result;
	}
}
