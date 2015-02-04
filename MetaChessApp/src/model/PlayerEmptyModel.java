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
public class PlayerEmptyModel extends PlayerModel{

    public PlayerEmptyModel(int side, ExtendedPieceModel controlledModel, String name, int[][] core) {
        super(side, controlledModel, name, core);
        pushable=true;
    }
    //empty player does nothing
}
