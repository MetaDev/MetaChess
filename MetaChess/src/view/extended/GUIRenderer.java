package view.extended;

import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glTranslatef;
import model.ExtendedGUIModel;
import userinterface.GUI;
import userinterface.GUIElement;
import userinterface.GUIRectangle;
import view.openglImpl.RectangleRenderer;

public class GUIRenderer {

	public void render(ExtendedGUIModel model) {
		GUI gui =  model.getGui();
		float x = gui.getX();
		float y = gui.getY();
		float height = gui.getHeight();
		float width  = gui.getWidth();
		GUIElement element;
		glPushMatrix();
		//move GUI to correct position
		glTranslatef(x, y, 0);
		float celHeight =((float) height / (float)gui.getRows());
		float celWidth = ((float)width /(float) gui.getColumns());
		for (int i = 0; i < gui.getRows(); i++) {
			for (int j = 0; j < gui.getColumns(); j++) {
				element = gui.getElements()[i][j];
				if (element != null) {
					glPushMatrix();
					//move GUIElement to correct position
					glTranslatef(j * celWidth, i * celHeight, 0);
					RectangleRenderer.drawRectangle(0, 0,element.getWidth()* celWidth,
							element.getHeight()*celHeight, element.getColor());
					for (GUIRectangle rect : element.getRectangles()) {
						RectangleRenderer.drawRectangle( rect.getRelX()
								* celWidth,  rect.getRelY() * celHeight,rect.getAngle(),
								celWidth * rect.getRelWidth(),
								celHeight * rect.getRelHeight(),
								(element.getColor() + 1) % 2);
					}
					glPopMatrix();
				}
			}
		}
		glPopMatrix();
	}
}
