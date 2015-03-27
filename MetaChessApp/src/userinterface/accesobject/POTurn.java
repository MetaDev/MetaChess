package userinterface.accesobject;

import engine.board.ExtendedBoardModel;
import meta.MetaConfig;
import engine.piece.ExtendedPieceModel;

public class POTurn extends PlayerStatsAccessObject {


    @Override
    public int getParam(ExtendedBoardModel board) {
        return booleanToInt(getPlayer(board).hasTurn());
    }


}
