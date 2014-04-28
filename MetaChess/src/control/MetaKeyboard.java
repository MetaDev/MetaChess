package control;

import meta.MetaMapping;

import org.lwjgl.input.Keyboard;

import decision.Decision;

public class MetaKeyboard {
	public static void processInput() {
		int key=-1;
		while (Keyboard.next()) {

			// in each iteration, each key is either pressed, released or held
			// the loop is so fast only 1 key max is found when iterating
			// keyboard state
			key = Keyboard.getEventKey();
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
			if (decision != null) {
				decision.handleInput(keyPos);
			}
		}
		// now iterate to check for more pushed down keys
		// don't handle ipnut of an already processed key
		Keyboard.poll();
		for (int i = 0; i < 256; i++) {
			if (i != key) {
				if (Keyboard.isKeyDown(i)) {
					Decision decision = MetaMapping.getKeyBinding(i);
					if (decision != null) {
						decision.handleInput(0);
					}
				}
			}
		}
	}

}
