package view.renderer;

import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glTranslatef;
import userinterface.generic.GUI1Tile;
import userinterface.generic.GUITile;
import view.zgpu.RectangleRenderer;

public class GUIRenderer {

    public void render(GUITile rootTile) {
        recursiveRender(rootTile);
    }

    private void recursiveRender(GUITile tile) {

        glPushMatrix();
        // move GUI to correct position of Tile
        // if tile is not a container draw it's grid
        if (tile.getColumns() == 1 && tile.getRows() == 1) {
            //System.out.println(tile.getWidth());
            glTranslatef(tile.getX(), tile.getY(), 0);
            GridRenderer.render(((GUI1Tile) tile).getGrid(),
                    tile.getHeight() / 8);
        } else if (tile.getElements() != null) {
            // draw container
            RectangleRenderer.drawRectangle(tile.getX(), tile.getY(), tile.getWidth(),
                    tile.getHeight(), tile.getColor());
            // iterate children
            GUITile child;
            for (int i = 0; i < tile.getColumns(); i++) {
                for (int j = 0; j < tile.getRows(); j++) {
                    child = tile.getElements()[i][j];
                    // if the tile contains a tile, draw it
                    if (child != null) {
                        recursiveRender(child);
                    }
                }
            }
        }

        glPopMatrix();
    }

}
