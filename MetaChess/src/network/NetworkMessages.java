package network;

import java.util.Arrays;
import java.util.Map;

import logic.BoardLogic;
import logic.MetaPosition;
import meta.MetaUtil;
import model.ExtendedPieceModel;
import network.client.MetaClient;

public class NetworkMessages {

	// translate incoming message into a method call
	// clientid has already been parsed
	public static void inMessage(String message) {
		String[] stringArray = message.split(";");
		String decision = stringArray[0];
		int[] xfrom = MetaUtil.parseIntArray(stringArray[1]);
		int[] yfrom = MetaUtil.parseIntArray(stringArray[2]);
		int range = Integer.parseInt(stringArray[3]);
		String direction = stringArray[4];
		// use these parameters to make the correct method call

	}

	// message of decision
	public static String decisionOutMessage(String decision,
			ExtendedPieceModel piece) {
		MetaPosition pos = BoardLogic.getTilePost(piece.getTilePosition());
		int[] iFrom = pos.getI();
		int[] jFrom = pos.getJ();
		int range = piece.getRange();
		return MetaClient.getClientId() + "::" + decision + ";"
				+ Arrays.toString(iFrom) + ";" + Arrays.toString(jFrom);
	}

	// client messages
	public static String introMessage() {
		return "hello";
	}

	public static String clientReadyToStart() {
		return MetaClient.getClientId() + "::ready";
	}

	public static String noMessage() {
		return MetaClient.getClientId() + "::nomessage";
	}

	// server messages
	// returns all the clients positions
	public static String readyResponse(Map<Integer, MetaPosition> startPositions) {
		String respons = "start";
		for (Map.Entry<Integer, MetaPosition> clientPosition : startPositions
				.entrySet()) {
			respons += "::" + clientPosition.getKey() + "::"
					+ clientPosition.getValue().getI()
					+ clientPosition.getValue().getJ();
		}
		return respons;
	}

	public static String introResponseMessage(int clientId) {
		return "welcome::" + clientId;
	}

	public static String noData() {
		return "nodata";
	}

	
}
