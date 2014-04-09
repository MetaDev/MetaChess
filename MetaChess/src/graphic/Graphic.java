/*
 * Dependencies:
 * Responsibilities: color, sprite image
 * save model of classes
 * Capabilities:
 */
package graphic;

import org.lwjgl.util.Color;



public abstract class Graphic {
	//used for objects which don't have a physic model
	protected float x;
	protected float y;
	protected float angle;
	protected float height;
	protected float width;
	protected int color;
	
	public Graphic(float x, float y, float angle,float width, float height, int color) {
		this.x = x;
		this.y = y;
		this.height=height;
		this.width=width;
		this.angle = angle;
		this.color = color;

	}

	public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
	}

	public float getWidth() {
		return width;
	}

	public void setWidth(float width) {
		this.width = width;
	}

	public void setX(float x) {
		this.x = x;
	}

	public void setY(float y) {
		this.y = y;
	}

	public float getX() {
		return x;
	}

	

	public float getY() {
		return y;
	}

	public void setXY(float x,float y) {
		this.x = x;
		this.y = y;
	}

	public float getAngle() {
		return angle;
	}

	public void setAngle(float angle) {
		this.angle = angle;
	}

	
	
	public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
	}

}
