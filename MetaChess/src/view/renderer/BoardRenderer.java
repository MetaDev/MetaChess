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
		if (tile.getChildren() == null) {
			RectangleRenderer.drawRectangle(tile.getAbsX(),tile.getAbsY(), tile.getRelSize(),
					tile.getRelSize(), tile.getColor());
		}
		// recursive render
		else {
			for (int i = 0; i < tile.getChildFraction(); i++) {
				for (int j = 0; j < tile.getChildFraction(); j++) {
					if (tile.getChildren()[i][j] != null) {
						recursiveTileRender(tile.getChildren()[i][j]);
					}

				}
			}
		}

	}

	private void recursivePieceRenderer() {
		for (Map.Entry<ExtendedPieceModel, ExtendedTileModel> entry : MetaConfig
				.getBoardModel().getEntityModels().entrySet()) {
			ExtendedPieceModel piece = entry.getKey();
			
			// there's a piece located on the tile
			PieceRenderer.render(piece);
		}
	}

}
