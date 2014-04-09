package control;

import meta.MetaMapping;
import model.PieceExtendedModel;

import org.lwjgl.input.Keyboard;

import action.MetaAction;

public class MetaKeyboard {
	public static void processInput(PieceExtendedModel model) {
		while (Keyboard.next()) {
			String MetaInput = Integer.toString(Keyboard.getEventKey());
			if (Keyboard.getEventKeyState()) {
				// Key held down
				if (Keyboard.isRepeatEvent()) {
					MetaInput += "hold";
				}
				// Key pressed
				else {
					MetaInput += "press";
				}

			}
			// Key released
			else {
				MetaInput += "release";
			}
			MetaAction action = MetaMapping.getMetaActionFromKey(MetaInput);
			if (action != null) {				
				if (MetaMapping.getPieceMetaActions(model.getControllerType()).contains(action.getName()))
					action.act(model);
			}
		}
	}

}
