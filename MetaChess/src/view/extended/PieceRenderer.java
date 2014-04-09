package view.extended;

import graphic.Graphic;
import graphic.PieceGraphic;
import view.basic.RectangleRenderer;

public class PieceRenderer implements Renderer {

	@Override
	public void render(Graphic graphic) {
		PieceGraphic pieceGraphic = (PieceGraphic) graphic;
		float x = graphic.getX();
		float y = graphic.getY();
		float w_8 = (pieceGraphic.getTile().getWidth()) / 8;
		float w_16 = (pieceGraphic.getTile().getWidth()) / 16;
		int main = graphic.getColor();
		int invert = (main + 1) % 2;
		// core
		RectangleRenderer.drawRectangle(x + 3 * w_8 + w_16, y + 3 * w_8, w_8,
				w_8, invert);
	}

}
