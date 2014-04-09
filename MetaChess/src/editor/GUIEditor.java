package editor;

import graphic.GUI;
import graphic.GUIElement;
import graphic.GUIElementTurn;
import meta.MetaMapping.GUIPosition;
import model.GUIExtendedModel;
import model.MetaModel;

public class GUIEditor extends Editor {
	public static void init() {
		GUI gui1 = new GUI(2,2);
		GUI gui2 = new GUI(2,2);
		
		GUIElement turn = new GUIElementTurn(1,1,1);
		gui1.addElement(0, 0, turn);
		GUIExtendedModel model1 = new GUIExtendedModel(gui1, null,GUIPosition.LEFT);
		//GUIExtendedModel model2 = new GUIExtendedModel(gui2, null,GUIPosition.RIGHT);
		MetaModel.addGuiModel(model1);
		//MetaModel.addGuiModel(model2);
	}
}
