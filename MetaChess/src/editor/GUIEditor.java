package editor;

import meta.MetaMapping;
import meta.MetaMapping.GUIPosition;
import model.ExtendedGUI;
import userinterface.generic.GUITile;
import userinterface.implementations.GTTurn;

public class GUIEditor extends Editor {
	public static void init() {
		int playerColor = MetaMapping.getBoardModel().getPlayer().getColor();
		int oppositePlayerColor = (playerColor+1)%2;
		GUITile rootLeft = new GUITile(8, 4, playerColor, null, 0, 0);
		rootLeft.addElement(0, 0, new GTTurn(oppositePlayerColor, rootLeft, 0, 0));
		//GUIPanel metaActionViewUp = new GUIPanelMetaAction(1, 1, playerColor, MetaMapping.getMetaAction("TILEVIEW"));
		
		//gui2.addElement(0, 0, metaActionViewUp);

		//ExtendedGUIModel model2 = new ExtendedGUIModel(gui2,GUIPosition.RIGHT);
		ExtendedGUI.addGuiBlock(GUIPosition.LEFT,rootLeft);
		//ExtendedGUI.addGuiModel(model2);
	}
}
