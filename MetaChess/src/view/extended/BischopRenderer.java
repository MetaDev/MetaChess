package view.extended;

import graphic.Graphic;
import graphic.PieceGraphic;
import view.basic.RectangleRenderer;

public class BischopRenderer extends PieceRenderer {

	
	@Override
	public void render(Graphic graphic) {
		PieceGraphic pieceGraphic = (PieceGraphic) graphic;
		float x = graphic.getX();
		float y = graphic.getY();
		float w_8 = (pieceGraphic.getTile().getWidth()) / 8;
		float w_16 = (pieceGraphic.getTile().getWidth()) / 16;
		int main = graphic.getColor();
		
		// body
		RectangleRenderer.drawRectangle( x + 2 * w_8 + w_16, y + w_8,
				 3 * w_8, 4 * w_8, main);
		// ornament
		RectangleRenderer.drawRectangle( x + 2 * w_8 + w_16, y + 5 * w_8
				+ w_16,  3 * w_8, w_16, main);
		RectangleRenderer.drawRectangle( x + 3 * w_8,
				y + 6 * w_8 + w_16,  2 * w_8, w_16,
				main);
		//render core
		super.render(pieceGraphic);

	}
}
