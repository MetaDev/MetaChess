package engine.piece;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import engine.MetaClock;
import meta.MetaConfig;
import engine.board.BoardLogic;
import engine.board.ExtendedTileModel;
import engine.piece.ExtendedPieceModel.PieceType;

//geef builder mee omdat controller het hele object moet kunnen vernietigen
public class PieceMovementLogic {

   

//    // return new positions of pawns in wall if allowed
//    //based on new pawn head position
//    // if not return null

//
//    
//    private void handleDragon(ExtendedPieceModel dragon, List<ExtendedTileModel> path) {
//        // check if a dragon didn't hoover over pieces on lower fraction
//        // no conflicts possible
//        for (ExtendedTileModel tile : path) {
//            // if there's a piece on the hoovered tile
//            ExtendedPieceModel pieceOnHooveredTile = MetaConfig
//                    .getBoardModel().getPieceByPosition(tile);
//            handlePieceCollision(piece, pieceOnHooveredTile, isSideStep);
//            // now check 1 fraction deeper
//            if (tile.getChildren() != null) {
//                // iterate over all children
//                for (int r = 0; r < tile.getChildren().length; r++) {
//                    for (int c = 0; c < tile.getChildren().length; c++) {
//                        // if there's a piece on the hoovered tile
//                        ExtendedPieceModel pieceOnHooveredChildTile = MetaConfig
//                                .getBoardModel().getPieceByPosition(
//                                        tile.getChildren()[r][c]);
//                        handlePieceCollision(piece,
//                                pieceOnHooveredChildTile, isSideStep);
//
//                    }
//                }
//            }
//
//        }
//    }
//
//  

}
