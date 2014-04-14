package editor;

import meta.MetaMapping;
import meta.MetaMapping.GUIPosition;
import model.ExtendedGUI;
import userinterface.generic.GUITile;
import userinterface.specific.GTTurn;
import userinterface.specific.GUIPanelMetaAction;

public class GUIEditor extends Editor {
	public static void init() {
		int playerColor = MetaMapping.getBoardModel().getPlayer().getColor();
		int oppositePlayerColor = (playerColor+1)%2;
		GUITile rootLeft = new GUITile(8, 4, playerColor, null, 0, 0);
		rootLeft.addElement( new GTTurn(oppositePlayerColor, rootLeft, 0, 0));
		rootLeft.addElement( new GUIPanelMetaAction(oppositePlayerColor, rootLeft, 0, 4));
		
		
		ExtendedGUI.addGuiBlock(GUIPosition.LEFT,rootLeft);
	}
}
