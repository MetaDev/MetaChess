package view.extended;

import model.ExtendedPieceModel;
import view.openglImpl.RectangleRenderer;

public class PawnRenderer extends PieceRenderer{
	
	
	
	@Override
	public void render(ExtendedPieceModel model){
		

		float w_8 = (model.getRelSize()) / 8;
		int main = model.getColor();
		//body
		RectangleRenderer.drawRectangle( 3 * w_8,  w_8,
				 4 * w_8, 2 * w_8, main);
		//core
		super.render(model);
 }
}
