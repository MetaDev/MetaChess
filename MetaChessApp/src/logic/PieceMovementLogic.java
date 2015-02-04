package logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import meta.MetaClock;
import meta.MetaConfig;
import meta.MetaConfig.PieceType;
import model.ExtendedKingModel;
import model.ExtendedPawnModel;
import model.ExtendedPieceModel;
import model.ExtendedTileModel;

//geef builder mee omdat controller het hele object moet kunnen vernietigen
public class PieceMovementLogic {

    // handle logic when 2 pieces are on same tile, return of piece can make
    // move or not
    private static boolean handlePieceCollision(ExtendedPieceModel piece,
            ExtendedPieceModel pieceOnnewTile, boolean sideStep) {
        if (pieceOnnewTile != null) {
            // if a piece is not lethal it can't kill another piece

            if (pieceOnnewTile.getSide() != piece.getSide()) {

                // the model is a dragon knight or it isn't a sidestep
                // and the target tile is occupied
                if ((piece.isDragon()) || !sideStep) {
                    //TODO
                    //check if chance doesn't have to be applied
                    return pieceKill(piece, pieceOnnewTile);

                }
                // either killed piece or stepped above it, move can be made
                if (sideStep) {
                    return true;
                }

            } // the piece on the new tile is from same side
            else {
                return false;
            }
        }
        // if there's no piece on the new tile move can be made
        return true;
    }

    // return whether a piece is killed
    private static boolean pieceKill(ExtendedPieceModel piece,
            ExtendedPieceModel pieceOnnewTile) {
        //if()
        // TODO check if piece is occupied by a player
        MetaConfig.getBoardModel().decreaseSideLives(pieceOnnewTile.getSide(),
                pieceOnnewTile.getLives());
        // put player in other piece and delete piece form play if
        // on main board
        if (pieceOnnewTile.getTilePosition().getAbsFraction() == 8) {
            //todo
            //set player ghost
            //MetaConfig.getBoardModel().deleteModel(pieceOnnewTile);
        } else {
            pieceOnnewTile.setTilePosition(BoardLogic.getRandomTile(
                                    MetaClock.getMaxFraction(), false));
        }
        return true;
        //
        // TODO networking
        // switch on correct player the switch under
        // MetaConfig.getSpecialsSet().get("SWITCH").setParam(1);

    }

    // return new positions of pawns in wall if allowed
    //based on new pawn head position
    // if not return null
    public static Map<ExtendedPawnModel, ExtendedTileModel> handlePawnAndKingTurnCollision(
            ExtendedKingModel king, int newPawnHeadPos) {
        // find new position based on new pawn head position
        // check if new position isn't occupied or outside of board
        Map<ExtendedPawnModel, ExtendedTileModel> newPositions = new HashMap<>();
        for (int i = 0; i < 8; i++) {
            ExtendedPawnModel pawn = king.getPawnFromWallIndex(i);
            if (pawn != null) {
                // new direction
                int[] newDir = MetaConfig.getDirectionWithIndex(i);
                ExtendedTileModel newPawnPosition = BoardLogic
                        .getTileNeighbour(king.getTilePosition(), newDir[0],
                                newDir[1]);

                if (newPawnPosition != null && handlePawnWallAndKingCollision(pawn, MetaConfig
                        .getBoardModel().getPieceByPosition(newPawnPosition),
                        king)) {
                    // add new position
                    newPositions.put(pawn, newPawnPosition);
                } else {
                    return null;
                }
            }
        }
        if (!newPositions.isEmpty()) {
            return newPositions;
        }
        return null;
    }

    // collision for the king and his wall are handled slightly differently, as
    // in that collision between them is allowed, because they all move in the
    // same direction
    private static boolean handlePawnWallAndKingCollision(
            ExtendedPieceModel pawnOrKing, ExtendedPieceModel pieceOnnewTile,
            ExtendedKingModel king) {
        // move is legal if new tile is empty, ocuppied by
        // opponent or this king or a pawn in the wall from our side
        if (pieceOnnewTile == null
                || pieceOnnewTile == king
                || (pieceOnnewTile.getType() == PieceType.PAWN
                && pieceOnnewTile.getSide() == king.getSide() && king
                .getIndexOfPawnInWallOnBoard((ExtendedPawnModel) pieceOnnewTile) != -1)) {
            return true;
        }

        // if lethal and from different side, kill piece
        if (pieceOnnewTile.getSide() != pawnOrKing.getSide()) {
            return pieceKill(pawnOrKing, pieceOnnewTile);
        }
        return false;
    }

    public static boolean movement(int i, int j, ExtendedPieceModel piece,boolean hoover,
            boolean isSideStep) {
        List<ExtendedTileModel> path = null;
        if (piece.isDragon()) {
            path = new ArrayList<>();
        }
        ExtendedTileModel previousTile = piece.getTilePosition();
        ExtendedTileModel newTile = BoardLogic.getTileNeighbour(previousTile,
                i, j, hoover, isSideStep||piece.isIgnoreOccupationOfTile(),
                piece.isPenetrateLowerFraction(), path);
        System.out.println(newTile);
        // movement did not succeed
        if (newTile == null) {
            return false;
        }

        // if there's a piece on the new tile
        ExtendedPieceModel pieceOnnewTile = MetaConfig.getBoardModel()
                .getPieceByPosition(newTile);
        // check if there's a conflict with the state of the tile
        if (handlePieceCollision(piece, pieceOnnewTile, isSideStep)) {
            // check if a dragon didn't hoover over pieces on lower fraction
            // no conflicts possible
            if (piece.isDragon()) {
                for (ExtendedTileModel tile : path) {
                    // if there's a piece on the hoovered tile
                    ExtendedPieceModel pieceOnHooveredTile = MetaConfig
                            .getBoardModel().getPieceByPosition(tile);
                    handlePieceCollision(piece, pieceOnHooveredTile, isSideStep);
                    // now check 1 fraction deeper
                    if (tile.getChildren() != null) {
                        // iterate over all children
                        for (int r = 0; r < tile.getChildren().length; r++) {
                            for (int c = 0; c < tile.getChildren().length; c++) {
                                // if there's a piece on the hoovered tile
                                ExtendedPieceModel pieceOnHooveredChildTile = MetaConfig
                                        .getBoardModel().getPieceByPosition(
                                                tile.getChildren()[r][c]);
                                handlePieceCollision(piece,
                                        pieceOnHooveredChildTile, isSideStep);

                            }
                        }
                    }
                }
            }
            // set new position for model
            piece.setTilePosition(newTile);
            return true;
        }
        // move not made
        return false;

    }

    public static void kingMovement(int[] direction, ExtendedKingModel king) {
        // TODO apply influence to pawns, pawns in wall always are under
        // influence, when moving king, check of first of
        ExtendedTileModel kingPos = king.getTilePosition();
        ExtendedTileModel newKingPos = BoardLogic.getTileNeighbour(kingPos,
                direction[0], direction[1], false, true, false, null);
		// if the stepping model is a king, check if it's pawns can be moved
        // with him

        // king makes a legal move, if king moves on a pawn not in wall
        // add to wall
        if (newKingPos != null) {
            ExtendedPieceModel piecOnKingNewPos = MetaConfig.getBoardModel()
                    .getPieceByPosition(newKingPos);
            // check if any condition of the kings's new tile could end the
            // movement
            if (piecOnKingNewPos != null) {
                // the piece on the king new position is a pawn
                if (piecOnKingNewPos.getType() == PieceType.PAWN
                        && piecOnKingNewPos.getSide() == king.getSide()) {
                    // pawn is not in wall
                    if (king.getIndexOfPawnInWallOnBoard((ExtendedPawnModel) piecOnKingNewPos) == -1) {
                        // add to wall if room on that position
                        king.addPawnToWallWithBoardIndex(
                                (ExtendedPawnModel) piecOnKingNewPos,
                                MetaConfig.getIndexfromDirection(direction));
                        // end movement
                        return;
                    }
                    // king moves in a direction where he has already a pawn in the wall
                } else if (piecOnKingNewPos.getSide() == king.getSide()) {
                    // not a pawn and from same side
                    // end movement
                    return;
                }

            }
            // save the new legal positions of the pawns
            ExtendedTileModel[] newPawnPositions = new ExtendedTileModel[8];
            ExtendedPieceModel[] collisionPieces = new ExtendedPieceModel[8];
            if (king.getWallSize() > 0) {
                newPawnPositions = new ExtendedTileModel[8];

                // check if every pawn around the king can be moved
                for (int i = 0; i < 8; i++) {
                    ExtendedPawnModel pawn = king.getPawnFromBoardIndex(i);
                    // there's a pawn
                    if (pawn != null) {
                        // position of pawn in wall
                        int[] pawnDirInWall
                                = MetaConfig.getDirectionWithIndex(i);
                        // get new pawn position, now we allow that a tile
                        // would be occupied
                        ExtendedTileModel newPawnPos = BoardLogic
                                .getTileNeighbour(newKingPos, pawnDirInWall[0],
                                        pawnDirInWall[1], false, true, false,
                                        null);
                        // new pawn position not allowed
                        if (newPawnPos == null) {
                            return;
                        }
                        ExtendedPieceModel pieceOnnewTile = MetaConfig
                                .getBoardModel().getPieceByPosition(newPawnPos);
                        // move is legal if new tile is empty, occupied by
                        // opponent or a king, pawn in the wall from our side

                        if (pieceOnnewTile == null
                                || pieceOnnewTile.getSide() != king.getSide()
                                || pieceOnnewTile == king
                                || (pieceOnnewTile.getType() == PieceType.PAWN
                                && pieceOnnewTile.getSide() == king
                                .getSide() && king
                                .getIndexOfPawnInWallOnBoard((ExtendedPawnModel) pieceOnnewTile) != -1)) {

                            // save pieceOnNewTile in array too
                            collisionPieces[i] = pieceOnnewTile;
                            // save new legal position
                            newPawnPositions[i] = newPawnPos;
                        } else {
                            // one of the tile moves if illegal, the move is
                            // illegal
                            return;
                        }

                    }
                }

            }

            // if code is reached until here all pawn wall movement is legal
            // make checked ,king and his wall, moves
            // handle it's piece collision and influence
            // king
            handlePawnWallAndKingCollision(king, piecOnKingNewPos, king);
            king.setTilePosition(newKingPos);

            // pawn wall
            // if the king had one
            if (newPawnPositions != null) {
                for (int i = 0; i < 8; i++) {
                    // if there's a new position
                    if (newPawnPositions[i] != null) {
                        handlePawnWallAndKingCollision(king.getPawnFromBoardIndex(i),
                                collisionPieces[i], king);
                        king.getPawnFromBoardIndex(i).setTilePosition(newPawnPositions[i]);

                    }

                }
            }
        }
    }

}
