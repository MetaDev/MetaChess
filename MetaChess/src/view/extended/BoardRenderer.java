package view.extended;

import graphic.Graphic;
import graphic.TileGraphic;
import view.basic.RectangleRenderer;

public class BoardRenderer implements Renderer {


	@Override
	public void render(Graphic graphic) {
		recursiveRender(((TileGraphic) graphic));

	}

	// render a FloorGrpahic recursively
	private void recursiveRender(TileGraphic tile) {

		// if no children anymore draw square
		if (tile.getChildren() == null) {
			RectangleRenderer.drawRectangle(tile.getX(), tile.getY(),
					tile.getWidth(),
					tile.getWidth(), tile.getColor());
			//render also the piece of a tile and also the metaaction of the tile
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
