/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.paramobjects;

import model.ExtendedPieceModel;

/**
 *
 * @author Harald
 */
public class POCooldown extends ParamObject {

    @Override
    public int getParam(ExtendedPieceModel model) {
       return model.getCooldown();
    }

    @Override
    public void setParam(ExtendedPieceModel model) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
