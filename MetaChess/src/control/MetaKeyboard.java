package control;

import org.lwjgl.input.Keyboard;

/*
 * This class handles all and every input
 */
public class MetaKeyboard {
	// return input sequence
	public static String processInput() {
		String inputSequence = "";
		int key = -1;
		String keyPos;
		while (Keyboard.next()) {
			
			// in each iteration, each key is either pressed, released or held
			// the loop is so fast only 1 key max is found when iterating
			// keyboard state
			key = Keyboard.getEventKey();

			if (Keyboard.getEventKeyState()) {
				// Key held down
				if (Keyboard.isRepeatEvent()) {
					keyPos = "DOWN:";
				}
				// Key pressed
				else {
					keyPos = "PRESS:";
				}

			}
			// Key released
			else {
				keyPos = "RELEASE:";
			}
			// save for this iteration the key sequence
			inputSequence +=keyPos + key +";";

		}
		// now iterate to check for more, multiple, pushed down keys
		// don't handle input of an already processed key
		Keyboard.poll();
		for (int i = 0; i < 256; i++) {
			//don't register double input
			if (i != key) {
				if (Keyboard.isKeyDown(i)) {
					inputSequence += "DOWN:" + i + ";";
				}
			}
		}
		//remove last semicolon
		if(inputSequence.length()>0){
			inputSequence = inputSequence.substring(0, inputSequence.length()-1);
		}
		return inputSequence;
	}
}
