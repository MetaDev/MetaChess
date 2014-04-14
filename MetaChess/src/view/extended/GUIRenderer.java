package view.extended;

import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glTranslatef;
import userinterface.generic.GUITile;
import view.openglImpl.RectangleRenderer;

public class GUIRenderer {

	public void render(GUITile rootTile) {
		recursiveRender(rootTile);
	}

	private void recursiveRender(GUITile tile) {

		glPushMatrix();
		// move GUI to correct position of container, if the tile is a container, if not just draw itself
		if(tile.getElements()!=null){
		glTranslatef(tile.getX(), tile.getY(), 0);
		// draw the conatainer itself
		RectangleRenderer.drawRectangle(0, 0, tile.getWidth(),
				tile.getHeight(), tile.getColor());
		}else{
			RectangleRenderer.drawRectangle(tile.getX(), tile.getY(), tile.getWidth(),
					tile.getHeight(), tile.getColor());
			return;
		}
		
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
