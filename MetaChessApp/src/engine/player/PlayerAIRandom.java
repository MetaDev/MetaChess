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
public class PlayerAIRandom extends Player {

    private Random rn = new Random();

    public PlayerAIRandom( ExtendedPieceModel controlledModel) {
        super((int)controlledModel.getColor(), controlledModel, "random", "pawn");
        decreaseLivesOnKill = true;

    }
    int nrOfSpecialTurns;

    //WIP
    @Override
    public void turnChange() {
        super.turnChange();
        //make special decisions and hold them for between 1 and 8 turns
        //get random rangeand extended special
        range = rn.nextInt(8) + 1;
        extendedSpecial = rn.nextInt(8) > 0; //one chance in 8 the extended is used
        //if no cooldown do special, set counter from 0 to 7
        if (cooldown == 0) {
            //turn on special
            handleSpecial(true);
            nrOfSpecialTurns = rn.nextInt(8);
        } 
        //nex turn change decrease counter of special is on
        else if (nrOfSpecialTurns > 0) {
            nrOfSpecialTurns--;
        }
        //the turn the nrofspecialturns is 0, turn of special
        else {
            //turn of special
            handleSpecial(false);
        }
        //make random move
        for (Directions.Direction direction : controlledModel.getAllowedMovement()) {
            if (rn.nextInt(controlledModel.getAllowedMovement().size()) == 0) {
                handleMovement(direction);

            }
        }

    }
}
