package view.extended;

import meta.MetaMapping;
import meta.MetaMapping.PieceRendererType;
import model.ExtendedBoardModel;
import model.MetaModel;
import userinterface.TileGraphic;
import view.openglImpl.RectangleRenderer;

public class BoardRenderer {

	public void render(ExtendedBoardModel model) {
		recursiveRender(model.getRootTile());
	}

	// render a FloorGrpahic recursively
	private void recursiveRender(TileGraphic tile) {

		// if no children anymore draw square
		if (tile.getChildren() == null) {
			RectangleRenderer.drawRectangle(tile.getX(), tile.getY(),
					tile.getWidth(), tile.getWidth(), tile.getColor());
			// there's a piece located on the tile
			if (MetaModel.getModelOnPosition(tile) != null) {
				PieceRendererType piece = (MetaModel.getModelOnPosition(tile))
						.getRenderType();
				if (piece != null)
					MetaMapping.getPieceRenderer(piece).render(
							MetaModel.getModelOnPosition(tile));
			}
			// there's an active metaaction on the tile
			if (tile.getActiveMetaAction() != null) {
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
