/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package engine.board.types;

import engine.piece.ExtendedPieceModel;
import engine.player.Player;
import java.util.List;

/**
 *
 * @author Harald
 */
public class QueenTestBoard extends TestBoard{

    public QueenTestBoard(boolean randomFraction, ExtendedPieceModel.PieceType playerType, String playerName, String playerIcon, int playerSide) {
        super(randomFraction, playerType, playerName, playerIcon, playerSide);
    }

    @Override
    protected List<Player> initPlayers() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
