package userinterface.init;

import java.io.IOException;
import java.io.InputStream;

import meta.MetaConfig;

public class IconLoader {
	public static void loadIcons() {
		// use map from MetaMapping to draw icons in
		int[][] icon;
		try {
			icon = stringToDrawing(readFile("res/bitgrid/tileview.txt"));
			MetaConfig.setIcon("TILEVIEW", icon);
			icon = stringToDrawing(readFile("res/bitgrid/lives.txt"));
			MetaConfig.setIcon("LIVES", icon);
			icon = stringToDrawing(readFile("res/bitgrid/movement.txt"));
			MetaConfig.setIcon("MOVRANGE", icon);
			icon = stringToDrawing(readFile("res/bitgrid/lives.txt"));
			MetaConfig.setIcon("LIVES", icon);
			icon = stringToDrawing(readFile("res/bitgrid/maxmovement.txt"));
			MetaConfig.setIcon("MAXMOVEMENT", icon);
			icon = stringToDrawing(readFile("res/bitgrid/maxmovement.txt"));
			MetaConfig.setIcon("MAXMOVEMENT", icon);
			icon = stringToDrawing(readFile("res/bitgrid/yourturn.txt"));
			MetaConfig.setIcon("YOURTURN", icon);
			icon = stringToDrawing(readFile("res/bitgrid/notyourturn.txt"));
			MetaConfig.setIcon("NOTYOURTURN", icon);
			icon = stringToDrawing(readFile("res/bitgrid/decisiononboard.txt"));
			MetaConfig.setIcon("DECISIONONBOARD", icon);
			icon = stringToDrawing(readFile("res/bitgrid/bishop.txt"));
			MetaConfig.setIcon("BISHOP", icon);
			icon = stringToDrawing(readFile("res/bitgrid/king.txt"));
			MetaConfig.setIcon("KING", icon);
			icon = stringToDrawing(readFile("res/bitgrid/queen.txt"));
			MetaConfig.setIcon("QUEEN", icon);
			icon = stringToDrawing(readFile("res/bitgrid/pawn.txt"));
			MetaConfig.setIcon("PAWN", icon);
			icon = stringToDrawing(readFile("res/bitgrid/knight.txt"));
			MetaConfig.setIcon("KNIGHT", icon);
			icon = stringToDrawing(readFile("res/bitgrid/rook.txt"));
			MetaConfig.setIcon("ROOK", icon);
			icon = stringToDrawing(readFile("res/bitgrid/harald.txt"));
			MetaConfig.setIcon("HARALD", icon);
			icon = stringToDrawing(readFile("res/bitgrid/rogue.txt"));
			MetaConfig.setIcon("ROGUE", icon);
			icon = stringToDrawing(readFile("res/bitgrid/horizon.txt"));
			MetaConfig.setIcon("HORIZON", icon);
			icon = stringToDrawing(readFile("res/bitgrid/switch.txt"));
			MetaConfig.setIcon("SWITCH", icon);
			icon = stringToDrawing(readFile("res/bitgrid/turn.txt"));
			MetaConfig.setIcon("TURN", icon);
			icon = stringToDrawing(readFile("res/bitgrid/playerTurn.txt"));
			MetaConfig.setIcon("PLAYERTURN", icon);
			icon = stringToDrawing(readFile("res/bitgrid/dragon.txt"));
			MetaConfig.setIcon("DRAGON", icon);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private static String readFile(String pathname) throws IOException {
		InputStream inputStream = IconLoader.class.getClassLoader()
				.getResourceAsStream(pathname);
		try (java.util.Scanner s = new java.util.Scanner(inputStream)) {
			return s.useDelimiter("\\A").hasNext() ? s.next() : "";
		}
	}

	public static int[][] stringToDrawing(String text) {
		String[] rows = text.split("\n");
		int[][] drawing = null;
		for (int i = 0; i < rows.length; i++) {

			String row = rows[i];
			for (int j = 0; j < rows.length; j++) {
				if (drawing == null) {
					drawing = new int[rows.length][rows.length];
				}
				char c = row.toCharArray()[j];
				drawing[j][i] = Integer.parseInt(c + "");
			}

		}

		return drawing;
	}

}
