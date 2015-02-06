/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package userinterface.accesobject;

/**
 *
 * @author Harald
 */
public class POAxis extends PlayerStatsAccessObject {

    @Override
    public int getParam() {
        return getPlayer().getControlledModel().getAxis();
    }

    
}
