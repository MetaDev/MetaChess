package view.renderer;

import engine.board.ExtendedBoardModel;
import engine.board.ExtendedTileModel;
import engine.piece.ExtendedPieceModel;
import engine.player.Player;
import java.util.Map;

public class BoardRenderer {
    
    public void render(ExtendedBoardModel board) {
        // draw board
        recursiveTileRender(board, board.getRootTile());
        //draw pieces on top
        pieceRenderer(board);
        playerRender(board);
        
    }

    // render a FloorGrpahic recursively
    private void recursiveTileRender(ExtendedBoardModel board, ExtendedTileModel tile) {
        // if no children anymore draw square
        if (tile.getChildren() == null) {
            if (ExtendedBoardModel.isOverMacFraction(tile.getAbsFraction())) {
                RectangleRenderer.drawRectangle(tile.getAbsX(), tile.getAbsY(), tile.getRelSize(),
                        tile.getRelSize(), ((float) tile.getColor() + 0.5f) / 2.0f);
            } else {
                RectangleRenderer.drawRectangle(tile.getAbsX(), tile.getAbsY(), tile.getRelSize(),
                        tile.getRelSize(), tile.getColor());
            }
            
        } // recursive render
        else {
            for (int i = 0; i < tile.getChildFraction(); i++) {
                for (int j = 0; j < tile.getChildFraction(); j++) {
                    if (tile.getChildren()[i][j] != null) {
                        recursiveTileRender(board, tile.getChildren()[i][j]);
                    }
                    
                }
            }
        }
        
    }
    
    private void pieceRenderer(ExtendedBoardModel board) {
        //iterate all pieces on board
        for (Map.Entry<ExtendedPieceModel, ExtendedTileModel> entry : board.getPieceToTile().entrySet()) {
            if (entry.getValue() != null) {
                PieceRenderer.render(entry.getKey());
            }
        }
        
    }

    private void playerRender(ExtendedBoardModel board) {
        //iterate all players on board
        for (Player player : board.getPlayersOnBoard()) {
            PlayerRenderer.render(player);
        }
        
    }
    
}
