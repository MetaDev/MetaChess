package userinterface.accesobject;

import engine.board.ExtendedBoardModel;
import meta.MetaConfig;
import engine.piece.ExtendedPieceModel;

public class PORange extends PlayerStatsAccessObject{

  

    @Override
    public int getParam(ExtendedBoardModel board) {
        return getPlayer(board).getRange();
    }

  

	

}
