package view;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glRotatef;
import static org.lwjgl.opengl.GL11.glTranslatef;
import static org.lwjgl.opengl.GL11.glVertex2f;
import meta.MetaConfig;

public class RectangleRenderer {

    public static void drawRectangle(float x, float y, float width,
            float height, int c) {
        //only draw white square
        drawRectangle(x, y, 0, width, height, c);

    }

    public static void drawRectangle(float x, float y, float angle,
            float width, float height, int c) {
        glPushMatrix();

        //translate to center of rectangle
        glTranslatef(width / 2, height / 2, 0);
        glRotatef(angle, 0, 0, 1);
        //then translate back
        glTranslatef(-width / 2, -height / 2, 0);

        glTranslatef(x, y, 0);
        glBegin(GL_QUADS);
        // Set the color to white.
        glColor3f(c, c, c);
        // glVertex2f function
        glVertex2f(0, height); // Top left corner
        glVertex2f(0, 0); // Bottom left corner
        glVertex2f(width, 0); // Bottom right corner
        glVertex2f(width, height); // Top right corner
        glEnd();
        glPopMatrix();

    }

}
