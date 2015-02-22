/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package userinterface.accesobject;

import meta.MetaConfig;

/**
 *
 * @author Harald
 */
public class PORemainingTeamLives extends PlayerStatsAccessObject{

    @Override
    public int getParam() {
        return MetaConfig.getBoardModel().getSideLives(getPlayer().getSide());
    }
    
}
