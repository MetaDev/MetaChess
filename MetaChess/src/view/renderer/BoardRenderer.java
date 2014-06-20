package view.renderer;

import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glTranslatef;
import meta.MetaConfig;
import meta.MetaConfig.PieceType;
import model.ExtendedBoardModel;
import model.ExtendedPieceModel;
import model.ExtendedTileModel;
import userinterface.generic.IconLoader;
import view.zgpu.RectangleRenderer;

public class BoardRenderer {

	public void render(ExtendedBoardModel model) {
		recursiveRender(model.getRootTile());
	}

	// render a FloorGrpahic recursively
	private void recursiveRender(ExtendedTileModel tile) {
		ExtendedBoardModel board = MetaConfig.getBoardModel();
		// if no children anymore draw square
		glPushMatrix();
		if (tile.getChildren() == null) {
			glPushMatrix();
			glTranslatef(tile.getRelX(), tile.getRelY(), 0);
			RectangleRenderer.drawRectangle(0, 0,
					tile.getRelSize(), tile.getRelSize(), tile.getColor());
			// there's a piece located on the tile
			if (board.getModelOnPosition(tile) != null) {
				ExtendedPieceModel piece = board.getModelOnPosition(tile);
				if (piece != null){
					PieceRenderer.render(piece);
				}
					
			}
			
			glPopMatrix();
			
		}
		// recursive render
		else {
			glTranslatef(tile.getRelX(), tile.getRelY(), 0);
			for (int i = 0; i < tile.getChildFraction(); i++) {
				for (int j = 0; j < tile.getChildFraction(); j++) {
					if (tile.getChildren()[i][j] != null) {
						
						recursiveRender(tile.getChildren()[i][j]);
					}

				}
			}
		}
		glPopMatrix();
	}

}
