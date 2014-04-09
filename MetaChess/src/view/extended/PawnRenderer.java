package view.extended;

import model.ExtendedPieceModel;
import view.openglImpl.RectangleRenderer;

public class PawnRenderer extends PieceRenderer{
	
	
	
	@Override
	public void render(ExtendedPieceModel model){
		
		float x = model.getX();
		float y = model.getY();
		float w_8 = (model.getWidth()) / 8;
		int main = model.getColor();
		//body
		RectangleRenderer.drawRectangle( x + 3 * w_8, y + w_8,
				 4 * w_8, 2 * w_8, main);
		//core
		super.render(model);
 }
}
