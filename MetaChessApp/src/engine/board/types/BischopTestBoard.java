/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package engine.board.types;

import engine.piece.ExtendedBishopModel;
import engine.piece.ExtendedPawnModel;
import engine.piece.ExtendedPieceModel;
import engine.player.Player;
import engine.player.PlayerDummy;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Harald
 */
public class BischopTestBoard extends TestBoard {

    public BischopTestBoard(boolean randomFraction,String playerName, String playerIcon) {
        super(randomFraction, ExtendedPieceModel.PieceType.bischop, playerName, playerIcon, 1);
        //
    }

    @Override
    protected List<Player> initPlayers() {
        ArrayList<Player> list = new ArrayList<>();

        // create exercice pawns
        for (int i = 0; i < 8; i++) {
            list.add(new PlayerDummy(new ExtendedPawnModel(this, 0)));
        }
        
        list.add(new PlayerDummy(new ExtendedBishopModel(this, 1)));
        list.add(new PlayerDummy(new ExtendedBishopModel(this, 1)));
        return list;
    }

}
