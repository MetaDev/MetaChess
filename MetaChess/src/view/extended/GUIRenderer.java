package view.extended;

import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glTranslatef;
import userinterface.generic.GUI1Tile;
import userinterface.generic.GUITile;
import view.openglImpl.RectangleRenderer;

public class GUIRenderer {

	public void render(GUITile rootTile) {
		recursiveRender(rootTile);
	}

	private void recursiveRender(GUITile tile) {

		glPushMatrix();
		// move GUI to correct position of Tile
		glTranslatef(tile.getX(), tile.getY(), 0);
		// if tile is not a container draw it's grid
		if (tile.getElements() == null) {
			TileRenderer.render(((GUI1Tile) tile).getGrid(),
					tile.getHeight() / 8);
			glPopMatrix();
			return;
		}
		// draw container
		RectangleRenderer.drawRectangle(0, 0, tile.getWidth(),
				tile.getHeight(), tile.getColor());
		// iterate children
		GUITile child;
		for (int i = 0; i < tile.getColumns(); i++) {
			for (int j = 0; j < tile.getRows(); j++) {
				child = tile.getElements()[i][j];
				// if the tile contains a tile, draw it
				if (child != null) {
					recursiveRender(child);
				}
			}
		}
		glPopMatrix();
	}

}
