/*
 * Dependencies: WorldPhysicsModel,  WorldGrpahicsModel
 * Responsibilities: drawing drawable objects, following player, render everything that's inside the world
 * Capabilities:
 */
package view;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.GL_SCISSOR_TEST;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glOrtho;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glScalef;
import static org.lwjgl.opengl.GL11.glScissor;
import static org.lwjgl.opengl.GL11.glTranslatef;
import static org.lwjgl.opengl.GL11.glViewport;

import java.util.Map;
import meta.MetaConfig;
import meta.MetaConfig.GUIPosition;
import engine.board.ExtendedBoardModel;
import userinterface.generic.ExtendedGUI;
import engine.player.Player;
import engine.board.ExtendedTileModel;

import userinterface.generic.GUITile;

public class MetaView {

    private static int height = 8 * 64 * 2;
    private static int width = 8 * 64 * 2;

    public static void setWindowSize(int height, int width) {
        MetaView.height = height * 2;
        MetaView.width = width * 2;
        refresh();
    }

    public static void refresh() {

        float centerBoardX = 0;
        float centerBoardY = 0;
        if (width > height) {
            centerBoardX = (width - height) / 2;
        } else {
            centerBoardY = (height - width) / 2;
        }

        float min = Math.min(height, width);
        //float startSize = Math.max(height,width);
        //float resizeToDisplay = min/startSize ;

        ExtendedBoardModel board = MetaConfig.getBoardModel();
        Player player = board.getInputPlayer();
        ExtendedTileModel PlayerTile = player.getControlledModel().getTilePosition();

        // set the drawing as absolute
        glClear(GL_COLOR_BUFFER_BIT);
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glOrtho(0.0, width, 0, height, -1.0, 1.0);

        glMatrixMode(GL_MODELVIEW);
        glLoadIdentity();

        glLoadIdentity();
        //set view size; equals window
        glViewport(0, 0, (int) width, (int) height);
        
        //move and scale the view to fit the board 
        glPushMatrix();
        
        //move the board to center of the screen
        glTranslatef(centerBoardX, centerBoardY, 0);
        //cut everything outside center space of window
        glScissor((int) centerBoardX, (int) centerBoardY, (int) min, (int) min);
        glEnable(GL_SCISSOR_TEST);

     
        int tiles = player.getControlledModel().getNrOfViewTiles();
        float singleTileSize = min / (2 * tiles + 1);
        float scaleCurrentTileToDesiredTileSize = singleTileSize / PlayerTile.getAbsSize();
       
        //zoom such that the tiles fill exactly the view
        glScalef(scaleCurrentTileToDesiredTileSize, scaleCurrentTileToDesiredTileSize, 1);
        
        //after scaling the coordinates of the board can be used
        //move board such that the player is in the exact middle

        glTranslatef(-PlayerTile.getAbsX() + tiles*PlayerTile.getAbsSize(), -PlayerTile.getAbsY()+tiles*PlayerTile.getAbsSize() , 0);
        
        
        // render board
        MetaConfig.getBoardRenderer().render(board);
        //System.out.println(width + ";" + height + ";" + centerBoardX + ";" +centerBoardY + ";" + tiles);
        glDisable(GL_SCISSOR_TEST);

        glPopMatrix();

        // draw UI
        // ipv de width en height mee te geven beter herschalen
        // different depending if GUI draws left, right, top or bottom
        // an optimisation could be to only resize when the window resizes
        Map<GUIPosition, GUITile> guis = ExtendedGUI.getGuis();
        for (Map.Entry<GUIPosition, GUITile> gui : guis.entrySet()) {
            GUIPosition position = gui.getKey();
            GUITile guiBlock = gui.getValue();
            if (position == GUIPosition.LEFT) {
                //position 0,0
                guiBlock.setWidth(centerBoardX);
                guiBlock.setHeight(height);
            } else if (position == GUIPosition.BOTTOM) {
                //position 0,0
                guiBlock.setWidth(width);
                guiBlock.setHeight(centerBoardY);
            } else if (position == GUIPosition.RIGHT) {
                guiBlock.setWidth(centerBoardX);
                guiBlock.setHeight(height);
                guiBlock.setX(((width - centerBoardX)));
            } else if (position == GUIPosition.TOP) {
                guiBlock.setWidth(width);
                guiBlock.setHeight(centerBoardY);
                guiBlock.setY((height - centerBoardY));
            }
            MetaConfig.getGuiRenderer().render(guiBlock);

        }

    }
}
