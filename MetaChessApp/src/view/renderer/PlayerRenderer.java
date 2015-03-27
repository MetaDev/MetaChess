package view.renderer;

import engine.board.ExtendedBoardModel;
import engine.piece.ExtendedPawnModel;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glTranslatef;
import engine.piece.ExtendedPieceModel;
import engine.player.Player;
import meta.MetaConfig;

public class PlayerRenderer {

    public static void render(Player player) {
        ExtendedBoardModel board = player.getControlledModel().getBoard();
        ExtendedPieceModel piece = player.getControlledModel();
        float w_8 = (piece.getRelSize()) / 8;
        float w_16 = (piece.getRelSize()) / 16;
       
        glPushMatrix();
        glTranslatef(piece.getTilePosition().getAbsX(), piece.getTilePosition().getAbsY(), 0);
        //draw in opposite color of tile if a piece is controlled by a player
        if (player.equals(board.getInputPlayer())) {
            //draw special sign to emphasize the controlled piece
            GridRenderer.transparentRender("pieceplayer", piece
                    .getTilePosition().getAbsSize() / 8, (piece.getTilePosition().getColor() + 1) % 2);
        }
        //draw special sign to mark bound pawns
        if (piece.getType() == ExtendedPieceModel.PieceType.pawn && ((ExtendedPawnModel) piece).isBound()) {
            GridRenderer.transparentRender("piecebound", piece
                    .getTilePosition().getAbsSize() / 8, (piece.getTilePosition().getColor() + 1) % 2);
        }

        glTranslatef(3 * w_8 + w_16, 3 * w_8, 0);
        // draw players personal core
        GridRenderer
                .render(player.getCore(), w_8 / 8, player.getSide());

        glPopMatrix();

    }

}
