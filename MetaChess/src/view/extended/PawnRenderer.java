package view.extended;

import graphic.Graphic;
import graphic.PieceGraphic;
import view.basic.RectangleRenderer;

public class PawnRenderer extends PieceRenderer{
	
	
	
	@Override
	public void render(Graphic graphic){
		PieceGraphic pieceGraphic = (PieceGraphic) graphic;
		
		float x = graphic.getX();
		float y = graphic.getY();
		float w_8 = (pieceGraphic.getTile().getWidth()) / 8;
		int main = pieceGraphic.getColor();
		//body
		RectangleRenderer.drawRectangle( x + 3 * w_8, y + w_8,
				 4 * w_8, 2 * w_8, main);
		//core
		super.render(pieceGraphic);
 }
}
