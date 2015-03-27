package engine.piece;

import engine.Directions;
import engine.Directions.Direction;
import engine.board.BoardLogic;
import engine.board.ExtendedBoardModel;

import engine.board.ExtendedTileModel;
import exceptions.WrongCollectionLengthException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import meta.MetaConfig;

public class ExtendedKingModel extends ExtendedPieceModel {
	// when position changes regret active ranged decision

    // list of pawns around the king, can be changed accordgin to axis
    private ExtendedPawnModel[] pawnWall = new ExtendedPawnModel[8];
    // position of the head of pawns around the king, they coil around the king
    // clockwise, with index 0 left bottom, indexing is counterclockwise
    //use axis
    private int wallSize = 0;

    public int getWallSize() {
        return wallSize;
    }

    private void addPawnToWallWithKingMovementDirection(ExtendedPawnModel pawn, Direction kingDirection) {
        //the position of the pawn in the wall is the direction the king makes
        pawnWall[Directions.getTurnFromDirection(kingDirection)] = pawn;
        wallSize++;
        pawn.setBound(this);
    }

    public void removePawnFromWall(ExtendedPawnModel pawn) {
        if (!isPieceInWall(pawn)) {
            return;
        }
        pawnWall[indexInWall(pawn)] = null;
        wallSize--;
    }

    public int indexInWall(ExtendedPawnModel pawn) {
        for (int i = 0; i < 8; i++) {
            if (pawnWall[i] != null && pawnWall[i].equals(pawn)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public void setSpecial(boolean yes, int range, boolean extendedSpecial) {
        if (yes) {
            //shift formation of pawn wall
            this.axis = range;
            if (extendedSpecial) {
                //group pawns in wall
            }
        } else {
            //reset position
            this.axis = 0;

        }
        setPawnWallHeadPos();
    }

    //shift pawn formation, a pawn is killed if collision occurs between pawn and a piece of same side
    public void setPawnWallHeadPos() {

        ExtendedTileModel[] newPawnPos = findNewWallPositions(getTilePosition(), new ExtendedTileModel[8]);

        if (newPawnPos != null) {
            //check new positions for collision conflicts, tile occupied by same color
            List<ExtendedPieceModel> toTakePieces = checkPawnWallPositionForConflict(newPawnPos, new ArrayList<ExtendedPieceModel>(), true);

            if (toTakePieces == null) {
                return;
            }

            //if no conflict found in position and pieces on the new positions
            movePawnWallToPosition(newPawnPos);
            takeAllPieces(toTakePieces);
        }

    }

    private void takeAllPieces(List<ExtendedPieceModel> toTakePieces) {
        for (ExtendedPieceModel piece : toTakePieces) {
            takePiece(piece);
        }
    }
    //returns a list of pieces to take for new pawn wall positions if new positions allowed, else return null
    //also check for conflicts with pieces of same side, if conflict both the confilcting pawn in wall and the piece are taken

    private List<ExtendedPieceModel> checkPawnWallPositionForConflict(ExtendedTileModel[] newPawnPos, List<ExtendedPieceModel> toTakePieces, boolean shift) {
        //check for collision conflict
        ExtendedPieceModel toTakePiece;
        for (int i = 0; i < 8; i++) {
            //if newPawnPos != null
            if (newPawnPos[i] != null) {
                toTakePiece = board.getTilePiece(newPawnPos[i]);
                //no conflict with king owning the pawn wall
                if (toTakePiece != null) {
                    //if from opposite side , take it
                    if (toTakePiece.getColor() != getColor()) {
                        toTakePieces.add(toTakePiece);
                    } //if on same side but not in wall and not the king 
                    else if (toTakePiece != this && !isPieceInWall(toTakePiece)) {
                        //and it's a shift, take piece
                        if (shift) {
                            toTakePieces.add(toTakePiece);
                        } 
                        //if no shift movement not allowed
                        else {
                            return null;
                        }
                    } 
                }

            }
        }
        return toTakePieces;
    }

    //find position for current formation of pawnwall, only check collisions with board borders, not pieces
    private ExtendedTileModel[] findNewWallPositions(ExtendedTileModel kingPosition, ExtendedTileModel[] pawnTiles) {
        if (pawnTiles.length != 8) {
            try {
                throw new WrongCollectionLengthException(8);
            } catch (WrongCollectionLengthException ex) {
                Logger.getLogger(ExtendedKingModel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        int indexInWall;
        //check if every pawn can be moved to new tile in wall
        for (int i = 0; i < 8; i++) {
            //calculate index based on axis
            indexInWall = (i + axis) % 8;
            //if pawn in wall
            if (pawnWall[i] != null) {
                //find new position based on shifted index in wall
                Direction pawnDirection = getDirectionFromWallIndex(indexInWall);
                //hoover doesn't work on single tile movements
                pawnTiles[i] = board.findTileNeighBour(kingPosition, pawnDirection.getX(), pawnDirection.getY(), false, kingPosition.getAbsFraction());
                if (pawnTiles[i] == null) {
                    return null;
                }
            }

        }
        return pawnTiles;
    }

    //index of the wall is index of a direction in the Directions cyclic list
    private Direction getDirectionFromWallIndex(int index) {
        return Directions.getDirectionFromTurn(index);
    }

    private void movePawnWallToPosition(ExtendedTileModel[] pawnPositions) {
        for (int i = 0; i < 8; i++) {
            //if pawn in wall
            if (pawnWall[i] != null) {
                pawnWall[i].setTilePosition(pawnPositions[i]);
            }
        }
    }

    //this method will align all pawns in the wall
    private void groupPawnsInWall() {

    }

    public ExtendedKingModel(ExtendedBoardModel board,int side) {
        super(PieceType.king,board, side, 32);
        Directions.getOrthoDirections(allowedMovement);
        Directions.getDiagDirections(allowedMovement);

    }

    public boolean isPieceInWall(ExtendedPieceModel piece) {
        return piece.getType() == PieceType.pawn && piece.getColor() == getColor() && ((ExtendedPawnModel) piece).isBound();
    }

    @Override
    public boolean handleMovement(Direction direction, int range, boolean extendedSpecial) {
        //find the new position of the pawnwall
        ExtendedTileModel newKingPos = board.findTileNeighBour(getTilePosition(), direction.getX(), direction.getY(), false, getTilePosition().getAbsFraction());
        if (newKingPos == null) {
            return false;
        }
        List<ExtendedPieceModel> toTakePieces = new ArrayList<>();
        ExtendedPieceModel pieceOnnewTile = board
                .getTilePiece(newKingPos);
        //conflict if piece on new tile is from other color and not a pawn from the wall
        //if piece on new tile is a pawn from this side add to wall if formation not shifted
        if (pieceOnnewTile != null) {
            if (pieceOnnewTile.getColor() == getColor() && !isPieceInWall(pieceOnnewTile)
                    && pieceOnnewTile.getType() == PieceType.pawn && axis == 0) {
                addPawnToWallWithKingMovementDirection((ExtendedPawnModel) pieceOnnewTile, direction);
                return false;
            } else if (pieceOnnewTile.getColor() != getColor()) {
                //add piece to pieces to take
                toTakePieces.add(pieceOnnewTile);
            }
        }

        ExtendedTileModel[] newPawnPos = findNewWallPositions(newKingPos, new ExtendedTileModel[8]);
        if (newPawnPos == null) {
            return false;
        }
        //check for collision conflict
        toTakePieces = checkPawnWallPositionForConflict(newPawnPos, toTakePieces, false);
        if (toTakePieces == null) {
            return false;
        }
        //if the code until here is reached the movement can be made
        //put the pawn wall on it's new position, put king on it's new position and handle the pieces taken in the process
        movePawnWallToPosition(newPawnPos);
        setTilePosition(newKingPos);

        takeAllPieces(toTakePieces);
        return false;
    }

}
