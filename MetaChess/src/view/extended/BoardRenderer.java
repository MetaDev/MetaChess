package view.extended;

import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glTranslatef;
import meta.MetaMapping;
import meta.MetaMapping.PieceRendererType;
import model.ExtendedBoardModel;
import model.ExtendedTileModel;
import userinterface.specific.IconLoader;
import view.openglImpl.RectangleRenderer;

public class BoardRenderer {

	public void render(ExtendedBoardModel model) {
		recursiveRender(model.getRootTile());
	}

	// render a FloorGrpahic recursively
	private void recursiveRender(ExtendedTileModel tile) {
		ExtendedBoardModel board = MetaMapping.getBoardModel();
		// if no children anymore draw square
		glPushMatrix();
		if (tile.getChildren() == null) {
			glPushMatrix();
			glTranslatef(tile.getRelX(), tile.getRelY(), 0);
			RectangleRenderer.drawRectangle(0, 0,
					tile.getRelSize(), tile.getRelSize(), tile.getColor());
			// there's a piece located on the tile
			if (board.getModelOnPosition(tile) != null) {
				PieceRendererType piece = (board.getModelOnPosition(tile))
						.getRenderType();
				if (piece != null)
					MetaMapping.getPieceRenderer(piece).render(
							board.getModelOnPosition(tile));
			}
			// there's an active metaAction on the tile
			if (board.getActiveMetaAction(tile) != null) {
				TileRenderer.render(MetaMapping.getIcon("DECISIONONBOARD"), tile.getRelSize()/8,(tile.getColor()+1)%2);
				
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
