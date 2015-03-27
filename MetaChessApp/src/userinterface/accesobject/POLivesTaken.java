/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package userinterface.accesobject;

import engine.board.ExtendedBoardModel;

/**
 *
 * @author Harald
 */
public class POLivesTaken extends PlayerStatsAccessObject{

   

    @Override
    public int getParam(ExtendedBoardModel board) {
        return getPlayer(board).getLivesTaken();
    }
    
}
