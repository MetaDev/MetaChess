package view.renderer;

import engine.board.ExtendedBoardModel;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glTranslatef;
import userinterface.generic.GUI1Tile;
import userinterface.generic.GUITile;

public class GUIRenderer {

    public void render(ExtendedBoardModel board,GUITile rootTile) {
        recursiveRender(board,rootTile);
    }

    private void recursiveRender(ExtendedBoardModel board,GUITile tile) {
        glPushMatrix();
        // move GUI to correct position of Tile
        // if tile is not a container draw it's grid
        if (tile.getColumns() == 1 && tile.getRows() == 1) {
            glTranslatef(tile.getX(), tile.getY(), 0);
            GridRenderer.render(board,(GUI1Tile) tile);

        } else if (tile.getElements() != null) {
            // draw container
            RectangleRenderer.drawRectangle( tile.getX(), tile.getY(), tile.getWidth(),
                    tile.getHeight(), tile.getColor());
            // iterate children
            GUITile child;
            for (int i = 0; i < tile.getColumns(); i++) {
                for (int j = 0; j < tile.getRows(); j++) {
                    child = tile.getElements()[i][j];
                    // if the tile contains a tile, draw it
                    if (child != null) {
                        recursiveRender(board,child);
                    }
                }
            }
        }

        glPopMatrix();
    }

}
