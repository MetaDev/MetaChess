package userinterface.accesobject;

import engine.board.ExtendedBoardModel;
import meta.MetaConfig;
import engine.piece.ExtendedPieceModel;

public class POSwitch extends PlayerStatsAccessObject{

    

    @Override
    public int getParam(ExtendedBoardModel board) {
       return  getPlayer(board).getControlledModel().getType().ordinal();
    }

    
}
