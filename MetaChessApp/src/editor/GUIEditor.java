package editor;

import engine.board.ExtendedBoardModel;
import meta.MetaConfig;
import meta.MetaConfig.GUIPosition;
import userinterface.generic.ExtendedGUI;
import userinterface.generic.GUITile;
import userinterface.specific.GTPlayerStatus;
import userinterface.specific.GUISideStatus;

public class GUIEditor {
	public static void init(ExtendedBoardModel board) {
		float playerColor = board.getInputPlayer().getControlledModel().getColor();
		GUITile rootLeft = new GUITile(2, 8, playerColor, null, 0, 0);
		GUITile rootRight = new GUITile(2, 8, playerColor, null, 0, 0);
		rootRight.addElement(new GUISideStatus(playerColor, rootRight, 0,0));
		rootLeft.addElement(new GTPlayerStatus(playerColor, rootLeft, 0, 0));
		
		
		ExtendedGUI.addGuiBlock(GUIPosition.LEFT,rootLeft);
		ExtendedGUI.addGuiBlock(GUIPosition.RIGHT,rootRight);
	}
}
