package control;

import meta.MetaMapping;
import model.ExtendedPieceModel;

import org.lwjgl.input.Keyboard;

import decision.Decision;

public class MetaKeyboard {
	public static void processInput() {
		while (Keyboard.next()) {
			int key = Keyboard.getEventKey();
			int keyPos;
			if (Keyboard.getEventKeyState()) {
				// Key held down
				if (Keyboard.isRepeatEvent()) {
					keyPos = 0;
				}
				// Key pressed
				else {
					keyPos = -1;
				}

			}
			// Key released
			else {
				keyPos = 1;
			}
			// use the input to get the decision
			// let the decision handle the input
			Decision decision = MetaMapping.getKeyBinding(key);
			if (decision != null)
				decision.handleInput(keyPos);
		}
	}

}
