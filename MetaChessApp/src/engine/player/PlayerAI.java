/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package engine.player;

import engine.Directions;
import engine.piece.ExtendedPieceModel;
import java.util.Random;

/**
 *
 * @author Harald
 */
public class PlayerAI extends Player {

    private Random rn = new Random();

    public PlayerAI(int side, ExtendedPieceModel controlledModel, String name, int[][] core) {
        super(side, controlledModel, name, core);
        decreaseLivesOnKill = true;

    }

    @Override
    public void decide() {
        //try to move randomly
        for (Directions.Direction direction : controlledModel.getAllowedMovement()) {
            if (rn.nextInt(controlledModel.getAllowedMovement().size() - 1) == 0) {
                handleMovement(direction);

            }
        }
    }
}
