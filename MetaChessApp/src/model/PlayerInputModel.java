/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import engine.Directions;
import engine.Directions.Direction;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import meta.MetaConfig;

/**
 *
 * @author Harald
 */
public class PlayerInputModel extends PlayerModel {

    // save input state
    //1 is press, -1 released, 0 nothing
    private Map<Integer, Integer> keyState = new HashMap<>();
    //contains the key per type for it's movement
    private Map<MetaConfig.PieceType, Map<Direction, Integer>> pieceKeyMovementMappings = new HashMap<>();
    //contains the key for the specialMovement
    private int specialKey;
    //contains the key for the range 
    private Map<Integer, Integer> rangeKeys = new HashMap<>();
    private boolean keyUpdate = false;

    public PlayerInputModel(int side, ExtendedPieceModel controlledModel, String name, int[][] core) {
        super(side, controlledModel, name, core);
    }

    public void setSpecialKey(int specialKey) {
        this.specialKey = specialKey;
        addUsedKeys(specialKey);
    }

    public void setRangeKeyMapping(int range, int key) {
        rangeKeys.put(range, key);
        addUsedKeys(key);
    }

    public void setPiecTypeKeyMapping(MetaConfig.PieceType type, Map<Direction, Integer> mapping) {
        pieceKeyMovementMappings.put(type, mapping);
        addUsedKeys(mapping.values());
    }

    private void addUsedKeys(int key) {
        keyState.put(key, 0);
    }

    private void addUsedKeys(Collection<Integer> keys) {
        for (Integer key : keys) {
            addUsedKeys(key);
        }
    }

    public void setKeyState(int key, int state) {
        if (!keyState.containsKey(key)) {
            return;
        }
        keyState.put(key, state);
    }

    @Override
    public void decide() {

        //decide based on input
        //range keys
        int rangeKey;
        for (Integer rangeValue : rangeKeys.keySet()) {
            rangeKey = rangeKeys.get(rangeValue);
            if (keyPressed(rangeKey)) {
                if (rangeValue != 3) {
                    range += rangeValue;
                } else {
                    extendedSpecial = true;
                }
                resetKey(rangeKey);
            } else if (keyReleased(rangeKey)) {
                if (rangeValue != 3) {
                    range -= rangeValue;
                } else {
                    extendedSpecial = false;
                }
                resetKey(rangeKey);
            }

            //special key is pressed
            if (keyPressed(specialKey)) {
                handleSpecial(true);
            } else if (keyReleased(specialKey)) {
                handleSpecial(false);
            }

        }

        Map<Direction, Integer> pieceKeyMovements = pieceKeyMovementMappings.get(controlledModel.getType());
        //movement can occur when a key is held also
        for (Direction direction : controlledModel.allowedMovement) {
            if (keyPressed(pieceKeyMovements.get(direction))) {
                controlledModel.step(direction, range, extendedSpecial);
                //if over max fraction only execute on press, so the press state is reset after movement
                if (!playerIsOverMaxFraction()) {
                    resetKey(pieceKeyMovements.get(direction));
                }
            }
        }

        super.decide();

        // TODO: here can be saved wether a team makes at least 1 decision every
    }

    private boolean keyPressed(int key) {
        return keyState.get(key) == 1;
    }

    private boolean keyReleased(int key) {
        return keyState.get(key) == -1;
    }

    private void resetKey(int key) {
        keyState.put(key, 0);
    }
}
