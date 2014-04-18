package userinterface.specific;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import meta.MetaMapping;

public class IconLoader {
	public static void loadIcons() {
		// use map from MetaMapping to draw icons in
		int[][] icon;
		try {
			icon = stringToDrawing(readFile("res/bitgrid/tileview.txt"));
			MetaMapping.setIcon("TILEVIEW", icon);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			icon = stringToDrawing(readFile("res/bitgrid/movement.txt"));
			MetaMapping.setIcon("MOVRANGE", icon);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			icon = stringToDrawing(readFile("res/bitgrid/lives.txt"));
			MetaMapping.setIcon("LIVES", icon);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			icon = stringToDrawing(readFile("res/bitgrid/lives.txt"));
			MetaMapping.setIcon("LIVES", icon);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			icon = stringToDrawing(readFile("res/bitgrid/maxmovement.txt"));
			MetaMapping.setIcon("MAXMOVEMENT", icon);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			icon = stringToDrawing(readFile("res/bitgrid/maxmovement.txt"));
			MetaMapping.setIcon("MAXMOVEMENT", icon);
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
