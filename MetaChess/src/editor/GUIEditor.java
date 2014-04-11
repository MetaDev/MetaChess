package editor;

import meta.MetaMapping.GUIPosition;
import model.ExtendedGUI;
import model.ExtendedGUIModel;
import userinterface.GUI;
import userinterface.GUIElement;
import userinterface.GUIElementTurn;

public class GUIEditor extends Editor {
	public static void init() {
		GUI gui1 = new GUI(2,2);
		GUI gui2 = new GUI(2,2);
		
		GUIElement turn = new GUIElementTurn(1,1,1);
		gui1.addElement(0, 0, turn);
		ExtendedGUIModel model1 = new ExtendedGUIModel(gui1,GUIPosition.LEFT);
		//GUIExtendedModel model2 = new GUIExtendedModel(gui2, null,GUIPosition.RIGHT);
		ExtendedGUI.addGuiModel(model1);
		//MetaModel.addGuiModel(model2);
	}
}
