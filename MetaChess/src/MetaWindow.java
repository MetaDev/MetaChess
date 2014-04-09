import logic.MetaClock;
import meta.MetaMapping;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import view.MetaView;
import control.MetaLoop;
import display.WindowUtil;
import editor.BoardEditor;
import editor.GUIEditor;
import editor.MetaAcionKeyAndPieceMappingEditor;
import editor.PlayerEditor;

/**
 * If this application shows a blank and responsive window and doesn't throw any
 * errors, you know you have installed lwjgl correctly.
 * 
 * @author Oskar Veerhoek
 */
public class MetaWindow {

	public static void main(String[] args) {
		create();
		init();
		
		while (!Display.isCloseRequested()) {
		
			// While no attempt to close the display is made..
			// Put render code here.
			// Put input handling code here.
			update();
			render();
			if (Display.wasResized()) {
				resize();
			}
		}
		dispose();

	}

	// Initialize here
	private static void init() {
		try {
			Display.setDisplayMode(new DisplayMode(8 * 64, 8 * 64));
			Display.setTitle("MetaChess");
			Display.setResizable(true);
			Display.setFullscreen(true);

			Display.create();
			//enable repeat keys
			Keyboard.enableRepeatEvents(true);
		} catch (LWJGLException e) {
			System.err.println("Display wasn't initialized correctly.");
			System.exit(1);
		}

	}

	private static void create() {

		// create and save all initial constants
		MetaMapping.initConstants();
		
		
		//initialize editors
		BoardEditor.init();
		GUIEditor.init();
		PlayerEditor.init();
		
		// EnemyBuilder enemyBuilder = new EnemyBuilder();
		// enemyBuilder.assemble(worldBuilder);
	}

	// Update logic and control here
	private static void update() {
		MetaLoop.decisionTurn();
	}

	// Render to the screen here
	private static void render() {
		MetaView.show();

		Display.update();
		// Refresh the display and poll input.
		Display.sync(60);
		// Wait until 16.67 milliseconds have passed. (Maintain 60
		// frames-per-second)
	}

	private static void resize() {
		WindowUtil.resize();
	}

	private static void dispose() {
		Display.destroy();
		System.exit(0);
	}
}