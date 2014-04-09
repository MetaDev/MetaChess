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
import graphic.Graphic;
import graphic.PieceGraphic;
import graphic.TileGraphic;
import meta.MetaMapping;
import meta.MetaMapping.GUIPosition;
import meta.MetaMapping.PieceRendererType;
import model.MetaModel;

import org.lwjgl.opengl.Display;

public class MetaView {

	public static void show() {
		float width = Display.getWidth();
		float height = Display.getHeight();
		float centerBoardX = 0;
		float centerBoardY = 0;
		boolean horGUI;
		if (width > height) {
			horGUI = true;
			centerBoardX = (width - height) / 2;
		} else {
			horGUI = false;
			centerBoardY = (height - width) / 2;
		}

		float min = Math.min(height, width);
		float startSize = Display.getDisplayMode().getWidth();
		float resizeToDisplay = min / startSize;
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
		int zoomIn = ((PieceGraphic) MetaModel.getPlayer().getGraphic())
				.getTile().absoluteFraction();
		glScalef(zoomIn, zoomIn, 1);
		// zoom out depending on nr of tiles allowed to see
		int tiles=1;
		if(MetaModel.getPlayer()!=null){
			tiles = MetaModel.getPlayer().getNrOfViewTiles();
		}
		glScalef((float) 1 / (2 * tiles + 1), (float) 1 / (2 * tiles + 1), 1);
		// move player to center
		float currentTileSize = ((PieceGraphic) MetaModel.getPlayer()
				.getGraphic()).getTile().getHeight();
		float centerPlayer = tiles * currentTileSize;
		TileGraphic PlayerTile = ((PieceGraphic)MetaModel.getPlayer().getGraphic()).getTile();
		glTranslatef(-PlayerTile.getX() + centerPlayer, -PlayerTile.getY()
				+ centerPlayer, 0);
		// render board
		MetaMapping.getBoardRenderer().render(
				MetaModel.getBoardModel().getGraphic());
		// render entities
		// iterate the list of entity models
		for (int i = 0; i < MetaModel.getEntityModels().size(); i++) {
			PieceRendererType piece = ((PieceGraphic) MetaModel
					.getEntityModels().get(i).getGraphic()).getPiece();
			MetaMapping.getPieceRenderer(piece).render(
					MetaModel.getEntityModels().get(i).getGraphic());
		}
		glPopMatrix();
		glDisable(GL_SCISSOR_TEST);
		// draw UI
		//ipv de width en height mee te geven beter herschalen
		// different depending if GUI draws left, right, top or bottom
		//an optimisation could be to only resize when the window resizes
		for (int i = 0; i < MetaModel.getGuiModels().size(); i++) {
			

			GUIPosition position = MetaModel.getGuiModels().get(i)
					.getPosition();
			Graphic guiGraphic = MetaModel.getGuiModels().get(i)
					.getGraphic();
			if (position == GUIPosition.LEFT || position == GUIPosition.BOTTOM) {
				guiGraphic.setWidth(centerBoardX);
				guiGraphic.setHeight(centerBoardX);
			} else if (position == GUIPosition.RIGHT) {
				guiGraphic.setWidth(centerBoardX);
				guiGraphic.setHeight(centerBoardX);
				guiGraphic.setX(((Display.getWidth() - centerBoardX)));
			} else if (position == GUIPosition.TOP) {
				guiGraphic.setWidth(centerBoardY);
				guiGraphic.setHeight(centerBoardY);
				guiGraphic.setY((Display.getHeight() - centerBoardY));
			}
			MetaMapping.getGuiRenderer().render(guiGraphic);

		}

	}
}
