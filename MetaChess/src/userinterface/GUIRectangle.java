package userinterface;

public abstract class GUIRectangle {
	//return ints as floats to avoid having to casting every time a percentage is calculated
	// relative position in GUIElement in percentage
	protected float x;
	protected float y;
	protected float width;
	protected float height;
	protected float angle;

	public GUIRectangle(int x, int y, int width, int height, float angle) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.angle = angle;
	}

	public float getRelX() {
		return x/100;
	}

	public void setX(int x) {
		this.x = x;
	}

	public float getRelY() {
		return y/100;
	}

	public void setY(int y) {
		this.y = y;
	}

	public float getRelWidth() {
		return width/100;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public float getRelHeight() {
		return height/100;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public float getAngle() {
		return angle;
	}

	public void setAngle(float angle) {
		this.angle = angle;
	}

}
