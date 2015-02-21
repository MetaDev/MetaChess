/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package editor;

import engine.Directions;
import engine.Directions.Direction;
import engine.piece.ExtendedPieceModel.PieceType;
import java.util.HashMap;
import java.util.Map;
import meta.MetaConfig;
import engine.player.PlayerInput;
import org.lwjgl.glfw.GLFW;

/**
 *
 * @author Harald
 */
public class KeyMapEditor extends Editor {

    //init keys after, init of player
    public static void init() {
        //!!!! watch out for keyboard ghosting when choosing keys

        //all possible action should hav an assigned unique (per piece) non-ghostable key (this is especially important for actions that are able to take place simultanously)
        PlayerInput player = MetaConfig.getBoardModel().getInputPlayer();
        Map<Direction, Integer> mapping = new HashMap<>();
        putOrthogonalMapping(mapping);
        putDiagonalMapping(mapping);
        putKnightMapping(mapping);
        
        //same for each piece, not neccesary
        player.setPiecTypeKeyMapping(PieceType.rook, mapping);
        player.setPiecTypeKeyMapping(PieceType.bischop, mapping);
        player.setPiecTypeKeyMapping(PieceType.king, mapping);
        player.setPiecTypeKeyMapping(PieceType.queen, mapping);
        player.setPiecTypeKeyMapping(PieceType.pawn, mapping);
        player.setPiecTypeKeyMapping(PieceType.knight, mapping);

        player.setRangeKeyMapping(1, GLFW.GLFW_KEY_H);
        player.setRangeKeyMapping(2, GLFW.GLFW_KEY_J);
        player.setRangeKeyMapping(3, GLFW.GLFW_KEY_K);
        player.setRangeKeyMapping(4, GLFW.GLFW_KEY_L);
        player.setSpecialKey(GLFW.GLFW_KEY_SPACE);
        player.setSwitchKey(GLFW.GLFW_KEY_ENTER);
    }

    private static void putDiagonalMapping(Map<Direction, Integer> mapping) {
        mapping.put(Directions.getDirection(1, 1), GLFW.GLFW_KEY_E);
        mapping.put(Directions.getDirection(-1, -1), GLFW.GLFW_KEY_Z);

        mapping.put(Directions.getDirection(1, -1), GLFW.GLFW_KEY_C);
        mapping.put(Directions.getDirection(-1, 1), GLFW.GLFW_KEY_Q);
    }

    private static void putOrthogonalMapping(Map<Direction, Integer> mapping) {
        mapping.put(Directions.getDirection(0, 1), GLFW.GLFW_KEY_W);
        mapping.put(Directions.getDirection(0, -1), GLFW.GLFW_KEY_S);

        mapping.put(Directions.getDirection(1, 0), GLFW.GLFW_KEY_D);
        mapping.put(Directions.getDirection(-1, 0), GLFW.GLFW_KEY_A);
    }

    private static void putKnightMapping(Map<Direction, Integer> mapping) {
        mapping.put(Directions.getDirection(2, 1), GLFW.GLFW_KEY_G);
        mapping.put(Directions.getDirection(2, -1), GLFW.GLFW_KEY_F);
        mapping.put(Directions.getDirection(-2, 1), GLFW.GLFW_KEY_A);
        mapping.put(Directions.getDirection(-2, -1), GLFW.GLFW_KEY_S);

        mapping.put(Directions.getDirection(1, 2), GLFW.GLFW_KEY_R);
        mapping.put(Directions.getDirection(-1, 2), GLFW.GLFW_KEY_W);
        mapping.put(Directions.getDirection(1, -2), GLFW.GLFW_KEY_C);
        mapping.put(Directions.getDirection(-1, -2), GLFW.GLFW_KEY_X);
    }

}
