package userinterface;

public class GUIRectangleLives extends GUIRectangle{

	public GUIRectangleLives(int x, int y, int width, int height, float angle) {
		super(10, 10, 80, 80, 0);
	}
	@Override
	public float getRelX() {
		return x/100;
	}

	@Override
	public float getRelY() {
		return y/100;
	}

	
	@Override
	public float getRelWidth() {
		return width/100;
	}

	
	@Override
	public float getRelHeight() {
		return height/100;
	}



}
