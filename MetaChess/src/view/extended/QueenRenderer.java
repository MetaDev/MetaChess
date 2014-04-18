package view.extended;

import model.ExtendedPieceModel;
import view.openglImpl.RectangleRenderer;

public class QueenRenderer extends PieceRenderer {
	
	@Override
	public void render(ExtendedPieceModel model) {


		float w_8 = (model.getRelSize()) / 8;
		float w_16 = (model.getRelSize()) / 16;
		int main = model.getColor();
		// body
		RectangleRenderer.drawRectangle( 3 * w_8,  w_8, 2 * w_8, 2 * w_8,
				main);
		RectangleRenderer.drawRectangle(3 * w_8, 4 * w_8, 2 * w_8, w_8,
				main);
		// ornament
		RectangleRenderer.drawRectangle(2 * w_8 + w_16,  5 * w_8 + w_16,
				3 * w_8, w_16, main);
		RectangleRenderer.drawRectangle(3 * w_8,  6 * w_8 + w_16, w_16,
				w_8, main);
		RectangleRenderer.drawRectangle(4 * w_8 + w_16,  6 * w_8 + w_16,
				w_16, w_8, main);

		RectangleRenderer.drawRectangle(3 * w_8 + w_16, 6 * w_8 + w_16,
				w_8, w_16, main);

		RectangleRenderer.drawRectangle(2 * w_8,  4 * w_8 + w_16, w_16,
				w_16, main);
		RectangleRenderer.drawRectangle(5 * w_8 + w_16, 4 * w_8 + w_16,
				w_16, w_16, main);
		// core
		super.render(model);
	}
}
