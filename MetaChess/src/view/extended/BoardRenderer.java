package view.extended;

import meta.MetaMapping;
import meta.MetaMapping.PieceRendererType;
import model.ExtendedBoardModel;
import model.ExtendedTileModel;
import view.openglImpl.RectangleRenderer;

public class BoardRenderer {

	public void render(ExtendedBoardModel model) {
		
		recursiveRender(model.getRootTile());
	}

	// render a FloorGrpahic recursively
	private void recursiveRender(ExtendedTileModel tile) {
		ExtendedBoardModel board = MetaMapping.getBoardModel();
		// if no children anymore draw square
		if (tile.getChildren() == null) {
			RectangleRenderer.drawRectangle(tile.getX(), tile.getY(),
					tile.getSize(), tile.getSize(), tile.getColor());
			// there's a piece located on the tile
			if (board.getModelOnPosition(tile) != null) {
				PieceRendererType piece = (board.getModelOnPosition(tile))
						.getRenderType();
				if (piece != null)
					MetaMapping.getPieceRenderer(piece).render(
							board.getModelOnPosition(tile));
			}
			// there's an active metaaction on the tile
			if (board.getActiveMetaAction(tile) != null) {
				//
			}
			// render also the piece of a tile and also the metaaction of the
			// tile
		}
		// recursive render
		else {
			for (int i = 0; i < tile.getChildFraction(); i++) {
				for (int j = 0; j < tile.getChildFraction(); j++) {
					if (tile.getChildren()[i][j] != null) {
						recursiveRender(tile.getChildren()[i][j]);
					}

				}
			}
		}
	}

}
