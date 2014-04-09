package graphic;

public class GUIElementLives extends GUIElement{
	public GUIElementLives(int height, int width, int color) {
		super(height, width,color);
		addRectangle(new GUIRectangleTurn());
	}
}
