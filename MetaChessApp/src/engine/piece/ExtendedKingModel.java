package engine.piece;

import engine.Directions;
import engine.Directions.Direction;
import java.util.Map;

import engine.board.ExtendedTileModel;

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

    public void addPawnToWallWithBoardIndex(ExtendedPawnModel pawn, int boardIndex) {
        pawnWall[getWallIndexOfPawnFromBoard(boardIndex)] = pawn;
        wallSize++;
        pawn.setBound(true);
    }

    public void removePawnFromWall(ExtendedPawnModel pawn, int index) {
        pawnWall[index] = null;
        wallSize--;
        pawn.setBound(false);
    }

    public void removePawnFromWall(ExtendedPawnModel pawn) {
        pawnWall[indexInWall(pawn)] = null;
        wallSize--;
        pawn.setBound(false);
    }

    public int getIndexOfPawnInWallOnBoard(ExtendedPawnModel pawn) {
        int indexInWall = indexInWall(pawn);
        // pawn not in wall
        if (indexInWall == -1) {
            return -1;
        }
        return (axis + indexInWall) % 8;
    }

    public ExtendedPawnModel getPawnFromBoardIndex(int boardIndex) {
        return pawnWall[getWallIndexOfPawnFromBoard(boardIndex)];
    }

    public ExtendedPawnModel getPawnFromWallIndex(int wallIndex) {
        return pawnWall[wallIndex];
    }

    public int getBoardIndexOfPawnInWall(int wallIndex) {
        return (axis + wallIndex) % 8;
    }

    public int getWallIndexOfPawnFromBoard(int boardIndex) {
        return (boardIndex - axis + 8) % 8;
    }

    private int indexInWall(ExtendedPawnModel pawn) {
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
            setPawnWallHeadPos(range);
            if (extendedSpecial) {
                //regroup pawn wall
            }
        }

    }

    public void setPawnWallHeadPos(int turnRange) {
        int newPawnHeadPos = (this.axis + turnRange) % 8;
//        Map<ExtendedPawnModel, ExtendedTileModel> newPositions = PieceMovementLogic
//                .handlePawnAndKingTurnCollision(this, newPawnHeadPos);
//        if (newPositions != null) {
//            this.axis = newPawnHeadPos;
//            // move all pawns
//            for (ExtendedPawnModel pawn : newPositions.keySet()) {
//                pawn.setTilePosition(newPositions.get(pawn));
//            }
//        }
    }

    public ExtendedKingModel(int side) {
        super(PieceType.KING, side, 32);
        Directions.getOrthoDirections(allowedMovement);
        Directions.getDiagDirections(allowedMovement);
    }

    @Override
    public boolean handleMovement(Direction direction, int range, boolean extendedSpecial) {
        //PieceMovementLogic.kingMovement(direction, this);
        return false;
    }

}
