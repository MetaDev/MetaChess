package userinterface.generic;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

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
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	
	

	private static String readFile(String pathname) throws IOException {

	    File file = new File(pathname);
	    StringBuilder fileContents = new StringBuilder((int)file.length());
	    Scanner scanner = new Scanner(file);
	    String lineSeparator = System.getProperty("line.separator");

	    try {
	        while(scanner.hasNextLine()) {        
	            fileContents.append(scanner.nextLine() + lineSeparator);
	        }
	        return fileContents.toString();
	    } finally {
	        scanner.close();
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
