package editor;

import meta.MetaConfig;
import meta.MetaConfig.GUIPosition;
import userinterface.generic.ExtendedGUI;
import userinterface.generic.GUITile;
import userinterface.specific.GTPlayerStatus;
import userinterface.specific.GUISideStatus;

public class GUIEditor extends Editor {
	public static void init() {
		int playerColor = MetaConfig.getBoardModel().getInputPlayer().getControlledModel().getColor();
		int oppositePlayerColor = (playerColor+1)%2;
		GUITile rootLeft = new GUITile(2, 8, playerColor, null, 0, 0);
		GUITile rootRight = new GUITile(2, 8, playerColor, null, 0, 0);
		rootRight.addElement(new GUISideStatus(playerColor, rootRight, 0,0));
		rootLeft.addElement(new GTPlayerStatus(playerColor, rootLeft, 0, 0));
		
		
		ExtendedGUI.addGuiBlock(GUIPosition.LEFT,rootLeft);
		ExtendedGUI.addGuiBlock(GUIPosition.RIGHT,rootRight);
	}
}
