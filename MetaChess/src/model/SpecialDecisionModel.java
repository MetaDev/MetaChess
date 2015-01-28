/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import model.paramobjects.ParamObject;

/**
 *
 * @author Harald
 */
public class SpecialDecisionModel extends DecisionModel {

    private ParamObject specialObject;

    public SpecialDecisionModel(boolean cooldown, ParamObject specialObject, int key, String name) {
        //priority not yet set
        super(cooldown, false, 0, key, name);
        if (cooldown) {
            priority = 2;
        } else {
            priority = 1;
        }

        this.specialObject = specialObject;
    }

    public ParamObject getSpecialObject() {
        return specialObject;
    }

}
