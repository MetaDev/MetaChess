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
public class POExtendeSpecial extends ParamObject {

    @Override
    public int getParam(ExtendedPieceModel model) {
        return booleanToInt(model.isExtendeSpecial());

    }

    @Override
    public void setParam(ExtendedPieceModel model) {
        model.setExtendeSpecial();
    }

}
