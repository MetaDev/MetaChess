package view.renderer;

import meta.MetaConfig;
import model.ExtendedPieceModel;
import view.zgpu.RectangleRenderer;

public class PieceRenderer {

	public static void render(ExtendedPieceModel model) {

		float w_8 = (model.getRelSize()) / 8;
		float w_16 = (model.getRelSize()) / 16;
		int main = model.getColor();
		int invert = (main + 1) % 2;
		//draw from grid
		GridRenderer.transparentRender(MetaConfig.getIcon(model.getType().name()), model.getTilePosition().getAbsSize()/8, model.getSide());
		// if a player is present
		// draw core
		if (model.equals(MetaConfig.getBoardModel().getPlayer()
				.getControlledModel()))
			RectangleRenderer.drawRectangle(3 * w_8 + w_16, 3 * w_8, w_8, w_8,
					invert);
	}

}
