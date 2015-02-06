/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package userinterface.accesobject;

import engine.piece.ExtendedPieceModel;

/**
 *
 * @author Harald
 */
public class POExtendeSpecial extends PlayerStatsAccessObject {

    @Override
    public int getParam() {
        return booleanToInt(getPlayer().isExtendeSpecial());

    }

}
