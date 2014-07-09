package editor;

import meta.MetaConfig;
import meta.MetaConfig.GUIPosition;
import model.ExtendedGUI;
import userinterface.generic.GUITile;
import userinterface.specific.GTTurn;
import userinterface.specific.GUIPanelDecision;
import userinterface.specific.GUIPanelStats;

public class GUIEditor extends Editor {
	public static void init() {
		int playerColor = MetaConfig.getBoardModel().getPlayer().getControlledModel().getColor();
		int oppositePlayerColor = (playerColor+1)%2;
		GUITile rootLeft = new GUITile(3, 6, playerColor, null, 0, 0);
		GUITile rootRight = new GUITile(2, 6, playerColor, null, 0, 0);
		rootRight.addElement( new GUIPanelStats(playerColor, rootRight, 0,0));
		rootLeft.addElement( new GUIPanelDecision(oppositePlayerColor, rootLeft, 0, 0));
		
		
		ExtendedGUI.addGuiBlock(GUIPosition.LEFT,rootLeft);
		ExtendedGUI.addGuiBlock(GUIPosition.RIGHT,rootRight);
	}
}
