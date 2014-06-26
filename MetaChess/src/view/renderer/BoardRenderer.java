package view.renderer;

import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glTranslatef;

import java.util.Map;

import meta.MetaConfig;
import model.ExtendedBoardModel;
import model.ExtendedPieceModel;
import model.ExtendedTileModel;
import view.zgpu.RectangleRenderer;

public class BoardRenderer {

	public void render(ExtendedBoardModel model) {
		// draw board
		recursiveTileRender(model.getRootTile());
		// draw pieces on top
		recursivePieceRenderer();

	}

	// render a FloorGrpahic recursively
	private void recursiveTileRender(ExtendedTileModel tile) {
		ExtendedBoardModel board = MetaConfig.getBoardModel();
		// if no children anymore draw square
		glPushMatrix();
		if (tile.getChildren() == null) {
			glPushMatrix();
			glTranslatef(tile.getRelX(), tile.getRelY(), 0);
			RectangleRenderer.drawRectangle(0, 0, tile.getRelSize(),
					tile.getRelSize(), tile.getColor());
			glPopMatrix();
		}
		// recursive render
		else {
			glPushMatrix();
			glTranslatef(tile.getRelX(), tile.getRelY(), 0);
			for (int i = 0; i < tile.getChildFraction(); i++) {
				for (int j = 0; j < tile.getChildFraction(); j++) {
					if (tile.getChildren()[i][j] != null) {
						recursiveTileRender(tile.getChildren()[i][j]);
					}

				}
			}
			glPopMatrix();
		}

		glPopMatrix();
	}

	private void recursivePieceRenderer() {
		for (Map.Entry<ExtendedPieceModel, ExtendedTileModel> entry : MetaConfig
				.getBoardModel().getEntityModels().entrySet()) {
			ExtendedTileModel tile = entry.getValue();
			ExtendedPieceModel piece = entry.getKey();
			glPushMatrix();
			glTranslatef(tile.getAbsX(), tile.getAbsY(), 0);
			// there's a piece located on the tile
			PieceRenderer.render(piece);
			glPopMatrix();
		}
	}

}
