package userinterface;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.Color;

public abstract class GUIElement {
	//number of blocks it takes in the GUI
	protected int height;
	protected int width;
	protected int color;
	protected List<GUIRectangle> rectangles;
public int getHeight() {
	return height;
}
public void setHeight(int height) {
	this.height = height;
}
public int getWidth() {
	return width;
}
public void setWidth(int width) {
	this.width = width;
}

public int getColor() {
	return color;
}
public void setColor(int color) {
	this.color = color;
}
public void addRectangle(GUIRectangle rect){
	rectangles.add(rect);
}
public void deleteRectangle(GUIRectangle rect){
	rectangles.remove(rect);
}
public List<GUIRectangle> getRectangles() {
	return rectangles;
}
public GUIElement(int height, int width, int color) {
	this.height = height;
	this.width = width;
	this.color = color;
	rectangles = new ArrayList<GUIRectangle>();
}
}
