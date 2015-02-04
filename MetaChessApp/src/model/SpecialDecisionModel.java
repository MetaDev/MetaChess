/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import model.paramobjects.PlayerStatsAccessObject;

/**
 *
 * @author Harald
 */
public class SpecialDecisionModel extends DecisionModel {

    private PlayerStatsAccessObject specialObject;

    public SpecialDecisionModel(boolean cooldown, PlayerStatsAccessObject specialObject, int key, String name) {
        //priority not yet set
        super(cooldown, false, 0, key, name);
        if (cooldown) {
            priority = 2;
        } else {
            priority = 1;
        }

        this.specialObject = specialObject;
    }

    public PlayerStatsAccessObject getSpecialObject() {
        return specialObject;
    }

}
