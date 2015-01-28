/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Harald
 */
public class MovementDecisionModel extends DecisionModel {

    private int[] direction;

    public MovementDecisionModel( int key, int[] direction, String name) {
        super(false,true, 3,key, name);
        this.direction=direction;
    }

    public int[] getDirection() {
        return direction;
    }
}
