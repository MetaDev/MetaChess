package view.renderer;

import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glTranslatef;
import meta.MetaClock;
import meta.MetaConfig;
import meta.MetaConfig.PieceType;
import model.ExtendedPawnModel;
import model.ExtendedPieceModel;
import model.ExtendedPlayerModel;
import view.zgpu.RectangleRenderer;

public class PieceRenderer {

    public static void render(ExtendedPieceModel model) {

        float w_8 = (model.getRelSize()) / 8;
        float w_16 = (model.getRelSize()) / 16;
        int main = model.getColor();
        int invert = (main + 1) % 2;
        glPushMatrix();
        glTranslatef(model.getTilePosition().getAbsX(), model.getTilePosition().getAbsY(), 0);
        ExtendedPlayerModel player = MetaConfig.getBoardModel().getPlayer();
        // draw from grid
        GridRenderer.transparentRender(model.getGrid(), model
                .getTilePosition().getAbsSize() / 8, model.getSide());

			// if a player is present
        // draw players core
        glPushMatrix();
        if (model.equals(player.getControlledModel())
                && player.getCore() != null) {
            // draw if it's the players turn
            if (MetaClock.getTurn(MetaConfig.getBoardModel().getPlayer()
                    .getControlledModel())) {
                GridRenderer.transparentRender(MetaConfig
                        .getIcon("PLAYERTURN"), w_8, (model
                        .getTilePosition().getColor() + 1) % 2);
            }
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

    public static void render(ExtendedPieceModel model, float cellSize) {
        float w_8 = cellSize / 8;
        float w_16 = cellSize / 16;
        int main = model.getColor();
        int invert = (main + 1) % 2;
        // draw from grid
        GridRenderer.transparentRender(
                model.getGrid(), cellSize / 8,
                model.getSide());

        glPushMatrix();
        glTranslatef(3 * w_8 + w_16, 3 * w_8, 0);
        ExtendedPlayerModel player = MetaConfig.getBoardModel().getPlayer();
        if (model.equals(player.getControlledModel())
                && player.getCore() != null) {
            // draw players personal core
            GridRenderer.render(player.getCore(), w_8 / 8, player.getSide());
        } else {
            RectangleRenderer.drawRectangle(0, 0, w_8, w_8, invert);
        }
        glPopMatrix();
    }

}
