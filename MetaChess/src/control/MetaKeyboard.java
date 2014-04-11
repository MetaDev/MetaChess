package control;

import model.ExtendedPieceModel;

import org.lwjgl.input.Keyboard;

public class MetaKeyboard {
	public static void processInput(ExtendedPieceModel model) {
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
			
		}
	}

}
