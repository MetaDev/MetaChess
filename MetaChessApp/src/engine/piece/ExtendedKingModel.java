package engine.piece;

import engine.Directions;
import engine.Directions.Direction;
import engine.board.BoardLogic;

import engine.board.ExtendedTileModel;
import exceptions.WrongCollectionLengthException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import meta.MetaConfig;

public class ExtendedKingModel extends ExtendedPieceModel {
	// when position changes regret active ranged decision

    // list of pawns around the king
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
            this.axis = range;
            setPawnWallHeadPos(this.axis);
            if (extendedSpecial) {
                //regroup pawn wall
            }
        } else {
            //reset position
            //todo
            //setPawnWallHeadPos(-1 * this.axis);
            this.axis = 0;

        }

    }

    public void setPawnWallHeadPos(int turnRange) {
        ExtendedPawnModel[] newPawnWall = new ExtendedPawnModel[8];
        //construct new pawn wall
        for (int i = 0; i < 8; i++) {
            //if pawn in wall
            if (pawnWall[i] != null) {
                newPawnWall[(i + turnRange + 8) % 8] = pawnWall[i];
            }

        }
        ExtendedTileModel[] newPawnPos = findNewWallPositions(getTilePosition(), newPawnWall, new ExtendedTileModel[8]);

        if (newPawnPos != null) {
            //check new positions for collision conflicts, tile occupied by same color
            List<ExtendedPieceModel> toTakePieces = checkPawnWallPositionForConflict(newPawnPos, new ArrayList<ExtendedPieceModel>());

            if (toTakePieces == null) {
                return;
            }
            //save new pawnWall
            pawnWall = newPawnWall;
            //if so change axis and pawn positions and take pieces from other color upon collision
            axis = (getAxis() + turnRange) % 8;
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

    private List<ExtendedPieceModel> checkPawnWallPositionForConflict(ExtendedTileModel[] newPawnPos, List<ExtendedPieceModel> toTakePieces) {
        //check for collision conflict
        ExtendedPieceModel toTakePiece;
        for (int i = 0; i < 8; i++) {
            //if newPawnPos != null
            if (newPawnPos[i] != null) {
                toTakePiece = MetaConfig.getBoardModel().getPieceByPosition(newPawnPos[i]);
                //no conflict with king owning the pawn wall
                if (toTakePiece != null) {
                    //if on same side but not in wall
                    if (toTakePiece.getColor() == getColor() && toTakePiece != this && !isPieceInWall(toTakePiece)) {
                        return null;
                    } //if from opposite side , take it
                    else if (toTakePiece.getColor() != getColor()) {
                        //there is a piece to take
                        toTakePieces.add(toTakePiece);
                    }

                }
            }
        }
        return toTakePieces;
    }

    private ExtendedTileModel[] findNewWallPositions(ExtendedTileModel kingPosition, ExtendedPawnModel[] pawnWall, ExtendedTileModel[] pawnTiles) {
        if (pawnTiles.length != 8) {
            try {
                throw new WrongCollectionLengthException(8);
            } catch (WrongCollectionLengthException ex) {
                Logger.getLogger(ExtendedKingModel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        //check if every pawn can be moved to new tile in wall
        for (int i = 0; i < 8; i++) {
            //if pawn in wall
            if (pawnWall[i] != null) {
                //find new position
                Direction pawnDirection = getDirectionFromWallIndex(i);
                //hoover doesn't work on single tile movements
                pawnTiles[i] = BoardLogic.findTileNeighBour(kingPosition, pawnDirection.getX(), pawnDirection.getY(), false, kingPosition.getAbsFraction());
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

    public ExtendedKingModel(int side) {
        super(PieceType.KING, side, 32);
        Directions.getOrthoDirections(allowedMovement);
        Directions.getDiagDirections(allowedMovement);
    }

    public boolean isPieceInWall(ExtendedPieceModel piece) {
        return piece.getType() == PieceType.PAWN && piece.getColor() == getColor() && ((ExtendedPawnModel) piece).isBound();
    }

    @Override
    public boolean handleMovement(Direction direction, int range, boolean extendedSpecial) {
        //find the new position of the pawnwall
        ExtendedTileModel newKingPos = BoardLogic.findTileNeighBour(getTilePosition(), direction.getX(), direction.getY(), false, getTilePosition().getAbsFraction());
        if (newKingPos == null) {
            return false;
        }
        List<ExtendedPieceModel> toTakePieces = new ArrayList<>();
        ExtendedPieceModel pieceOnnewTile = MetaConfig.getBoardModel()
                .getPieceByPosition(newKingPos);
        //conflict if piece on new tile is from other color and not a pawn from the wall
        if (pieceOnnewTile != null) {
            if (pieceOnnewTile.getColor() == getColor() && !isPieceInWall(pieceOnnewTile)) {
                //if piece on new tile is a pawn from this side add to wall
                if (pieceOnnewTile.getType() == PieceType.PAWN) {
                    addPawnToWallWithKingMovementDirection((ExtendedPawnModel) pieceOnnewTile, direction);
                }
                return false;
            }
            //add piece to pieces to take
            toTakePieces.add(pieceOnnewTile);
        }

        ExtendedTileModel[] newPawnPos = findNewWallPositions(newKingPos, pawnWall, new ExtendedTileModel[8]);
        if (newPawnPos == null) {
            return false;
        }
        //check for collision conflict
        toTakePieces = checkPawnWallPositionForConflict(newPawnPos, toTakePieces);
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
