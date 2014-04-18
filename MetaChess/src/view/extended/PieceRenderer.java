package view.extended;

import model.ExtendedPieceModel;
import view.openglImpl.RectangleRenderer;

public class PieceRenderer {

	public void render(ExtendedPieceModel model) {

		float w_8 = (model.getRelSize()) / 8;
		float w_16 = (model.getRelSize()) / 16;
		int main = model.getColor();
		int invert = (main + 1) % 2;
		// core
		RectangleRenderer.drawRectangle(3 * w_8 + w_16,  3 * w_8, w_8,
				w_8, invert);
	}

}
