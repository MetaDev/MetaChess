/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package editor;

import engine.Directions;
import engine.Directions.Direction;
import java.util.HashMap;
import java.util.Map;
import meta.MetaConfig;
import model.PlayerInputModel;
import org.lwjgl.glfw.GLFW;

/**
 *
 * @author Harald
 */
public class KeyMapEditor {

    //init keys after, init of player
    public static void init() {
        PlayerInputModel player = MetaConfig.getBoardModel().getInputPlayer();
        Map<Direction, Integer> mapping = new HashMap<>();
        mapping.put(Directions.getDirection(0, 1), GLFW.GLFW_KEY_W);
        mapping.put(Directions.getDirection(0, -1), GLFW.GLFW_KEY_S);

        mapping.put(Directions.getDirection(1, 0), GLFW.GLFW_KEY_D);
        mapping.put(Directions.getDirection(-1, 0), GLFW.GLFW_KEY_A);

        
        player.setPiecTypeKeyMapping(MetaConfig.PieceType.ROOK, mapping);
        
        player.setRangeKeyMapping(1, GLFW.GLFW_KEY_KP_1);
        player.setRangeKeyMapping(2, GLFW.GLFW_KEY_KP_2);
        player.setRangeKeyMapping(3, GLFW.GLFW_KEY_E);
        player.setRangeKeyMapping(4, GLFW.GLFW_KEY_KP_3);
        player.setSpecialKey(GLFW.GLFW_KEY_SPACE);
        
    }

}
