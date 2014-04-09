package view.extended;

import model.ExtendedPieceModel;
import view.openglImpl.RectangleRenderer;

public class RookRenderer extends PieceRenderer {

	@Override
	public void render(ExtendedPieceModel model) {

		float x = model.getX();
		float y = model.getY();
		float w_8 = (model.getWidth()) / 8;
		float w_16 = (model.getWidth()) / 16;
		int main = model.getColor();
		// body
		RectangleRenderer.drawRectangle(x + 2 * w_8, y + w_8, 4 * w_8, 4 * w_8,
				main);
		// ornament
		RectangleRenderer.drawRectangle(x + 1 * w_8 , y + 6 * w_8-w_16, w_8,
				w_8, main);
		RectangleRenderer.drawRectangle(x + 3 * w_8 -w_16, y + 6 * w_8-w_16, w_8,
				w_8, main);
		RectangleRenderer.drawRectangle(x + 4 * w_8 +w_16, y + 6 * w_8-w_16, w_8,
				w_8, main);
		RectangleRenderer.drawRectangle(x + 6 * w_8 , y + 6 * w_8-w_16 ,w_8,
				w_8, main);
		// core
		super.render(model);
	}
}
