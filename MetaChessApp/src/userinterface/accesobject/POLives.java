package userinterface.accesobject;

import engine.board.ExtendedBoardModel;

public class POLives extends PlayerStatsAccessObject{

    @Override
    public int getParam(ExtendedBoardModel board) {
        return getPlayer(board).getControlledModel().getLives();
    }

  


}
