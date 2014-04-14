package model;

import java.util.HashMap;
import java.util.Map;

import meta.MetaMapping.GUIPosition;
import userinterface.generic.GUITile;

public class ExtendedGUI {
	//list of guimodels
	private static Map<GUIPosition,GUITile> guis=new HashMap<>();
	
	public static void addGuiBlock(GUIPosition pos, GUITile tile) {
		guis.put(pos,tile);
	}

	
	public static Map<GUIPosition,GUITile> getGuis() {
		return guis;
	}
}
