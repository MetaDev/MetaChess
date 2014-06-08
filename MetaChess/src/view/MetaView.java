/*
 * Dependencies: WorldPhysicsModel,  WorldGrpahicsModel
 * Responsibilities: drawing drawable objects, following player, render everything that's inside the world
 * Capabilities:
 */
package view;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.GL_SCISSOR_TEST;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glOrtho;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glScalef;
import static org.lwjgl.opengl.GL11.glScissor;
import static org.lwjgl.opengl.GL11.glTranslatef;
import static org.lwjgl.opengl.GL11.glViewport;

import java.util.Map;

import meta.MetaMapping;
import meta.MetaMapping.GUIPosition;
import model.ExtendedBoardModel;
import model.ExtendedGUI;
import model.ExtendedPlayerModel;
import model.ExtendedTileModel;

import org.lwjgl.opengl.Display;

import userinterface.generic.GUITile;

public class MetaView {

	public static void show() {
		float width = Display.getWidth();
		float height = Display.getHeight();
		float centerBoardX = 0;
		float centerBoardY = 0;
		if (width > height) {
			centerBoardX = (width - height) / 2;
		} else {
			centerBoardY = (height - width) / 2;
		}

		float min = Math.min(height, width);
		float startSize = Display.getDisplayMode().getWidth();
		float resizeToDisplay = min / startSize;

		ExtendedBoardModel board = MetaMapping.getBoardModel();
		ExtendedPlayerModel player = board.getPlayer();
		ExtendedTileModel PlayerTile = board.getPiecePosition(player.getControlledModel());
		// set the drawing as absolute
		glClear(GL_COLOR_BUFFER_BIT);
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0.0, Display.getWidth(), 0, Display.getHeight(), -1.0, 1.0);

		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();

		glLoadIdentity();
		glViewport(0, 0, Display.getWidth(), Display.getHeight());
		glScissor((int) centerBoardX, (int) centerBoardY, (int) min, (int) min);
		glEnable(GL_SCISSOR_TEST);

		glPushMatrix();

		// move board to center of display
		glTranslatef(centerBoardX, centerBoardY, 0);
		// scale to display
		glScalef(resizeToDisplay, resizeToDisplay, 0);

		// (zoom in) scale to 1 tile in playerview
		int zoomIn = PlayerTile.absoluteFraction();
		glScalef(zoomIn, zoomIn, 1);
		// zoom out depending on nr of tiles allowed to see
		int tiles = 1;
		if (player != null) {
			tiles = player.getControlledModel().getNrOfViewTiles();
		}
		glScalef((float) 1 / (2 * tiles + 1), (float) 1 / (2 * tiles + 1), 1);
		// move player to center
		float currentTileSize = PlayerTile.getAbsSize();
		float centerPlayer = tiles * currentTileSize;
		glTranslatef(-PlayerTile.getAbsX() + centerPlayer, -PlayerTile.getAbsY()
				+ centerPlayer, 0);
		// render board
		MetaMapping.getBoardRenderer().render(board);

		glPopMatrix();
		glPushMatrix();
		glDisable(GL_SCISSOR_TEST);

		// draw UI
		// ipv de width en height mee te geven beter herschalen
		// different depending if GUI draws left, right, top or bottom
		// an optimisation could be to only resize when the window resizes
		Map<GUIPosition, GUITile> guis = ExtendedGUI.getGuis();

		for (Map.Entry<GUIPosition, GUITile> gui : guis.entrySet()) {
			GUIPosition position = gui.getKey();
			GUITile guiBlock = gui.getValue();
			if (position == GUIPosition.LEFT) {
				//position 0,0
				guiBlock.setWidth(centerBoardX);
				guiBlock.setHeight(Display.getHeight());
			} else if (position == GUIPosition.BOTTOM) {
				//position 0,0
				guiBlock.setWidth(Display.getWidth());
				guiBlock.setHeight(centerBoardY);
			} else if (position == GUIPosition.RIGHT) {
				guiBlock.setWidth(centerBoardX);
				guiBlock.setHeight(Display.getHeight());
				guiBlock.setX(((Display.getWidth() - centerBoardX)));
			} else if (position == GUIPosition.TOP) {
				guiBlock.setWidth(Display.getWidth());
				guiBlock.setHeight(centerBoardY);
				guiBlock.setY((Display.getHeight() - centerBoardY));
			}
			MetaMapping.getGuiRenderer().render(guiBlock);

		}
		glPopMatrix();

	}
}
