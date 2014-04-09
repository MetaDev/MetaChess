package userinterface;


public class GUIElementTurn extends GUIElement{

	public GUIElementTurn(int height, int width, int color) {
		super(height, width,color);
		addRectangle(new GUIRectangleTurn());
	}

}
