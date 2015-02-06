package view.renderer;

import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glTranslatef;
import engine.piece.ExtendedPieceModel;
import engine.player.Player;
import view.RectangleRenderer;

public class PlayerRenderer {

    public static void render(Player player) {
        ExtendedPieceModel piece = player.getControlledModel();
        float w_8 = (piece.getRelSize()) / 8;
        float w_16 = (piece.getRelSize()) / 16;
        int main = piece.getColor();
        int invert = (main + 1) % 2;
        glPushMatrix();
        glTranslatef(piece.getTilePosition().getAbsX(), piece.getTilePosition().getAbsY(), 0);
        // draw from grid
        GridRenderer.transparentRender(piece.getGrid(), piece
                .getTilePosition().getAbsSize() / 8, piece.getColor());

        // if a player is present
        // draw players core
        glPushMatrix();
        if (piece.equals(player.getControlledModel())
                && player.getCore() != null) {

            glTranslatef(3 * w_8 + w_16, 3 * w_8, 0);
            // draw players personal core
            GridRenderer
                    .render(player.getCore(), w_8 / 8, player.getSide());

        } else {
            RectangleRenderer.drawRectangle(3 * w_8 + w_16, 3 * w_8, w_8,
                    w_8, invert);
        }
        glPopMatrix();
        glPopMatrix();

    }

    public static void render(Player player, float cellSize) {
        ExtendedPieceModel piece = player.getControlledModel();

        float w_8 = cellSize / 8;
        float w_16 = cellSize / 16;
        int main = piece.getColor();
        int invert = (main + 1) % 2;
        // draw from grid
        GridRenderer.transparentRender(
                piece.getGrid(), cellSize / 8,
                piece.getColor());

        glPushMatrix();
        glTranslatef(3 * w_8 + w_16, 3 * w_8, 0);
        // draw players personal core
        GridRenderer.render(player.getCore(), w_8 / 8, player.getSide());
        glPopMatrix();
    }

}
