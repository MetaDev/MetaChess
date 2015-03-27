/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.renderer;

import engine.piece.ExtendedPawnModel;
import engine.piece.ExtendedPieceModel;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glTranslatef;

/**
 *
 * @author Harald
 */
public class PieceRenderer {
    public static void render(ExtendedPieceModel piece){
        glPushMatrix();
        glTranslatef(piece.getTilePosition().getAbsX(), piece.getTilePosition().getAbsY(), 0);        
        // draw from grid
        GridRenderer.transparentRender(piece.getName(), piece
                .getTilePosition().getAbsSize() / 8, piece.getColor());       
        glPopMatrix();

    }
}
