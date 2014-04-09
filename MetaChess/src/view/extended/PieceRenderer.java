package view.extended;

import model.ExtendedPieceModel;
import view.openglImpl.RectangleRenderer;

public class PieceRenderer {

	public void render(ExtendedPieceModel model) {
		float x = model.getX();
		float y = model.getY();
		float w_8 = (model.getWidth()) / 8;
		float w_16 = (model.getWidth()) / 16;
		int main = model.getColor();
		int invert = (main + 1) % 2;
		// core
		RectangleRenderer.drawRectangle(x + 3 * w_8 + w_16, y + 3 * w_8, w_8,
				w_8, invert);
	}

}
