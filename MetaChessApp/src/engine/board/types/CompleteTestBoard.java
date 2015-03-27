/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package engine.board.types;

import engine.piece.ExtendedBishopModel;
import engine.piece.ExtendedKingModel;
import engine.piece.ExtendedKnightModel;
import engine.piece.ExtendedPawnModel;
import engine.piece.ExtendedPieceModel;
import engine.piece.ExtendedQueenModel;
import engine.piece.ExtendedRookModel;
import engine.player.Player;
import engine.player.PlayerDummy;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Harald
 */
public class CompleteTestBoard extends TestBoard {

    public CompleteTestBoard(boolean randomFraction, String playerName, String playerIcon) {
        super(randomFraction,  ExtendedPieceModel.PieceType.pawn, playerName, playerIcon, 1);
    }

   
    private void initAllPiecesForSide(List<Player> list, int side) {
        // create all pieces
        for (int i = 0; i < 8; i++) {
            list.add(new PlayerDummy(new ExtendedPawnModel(this, side)));
        }
        list.add(new PlayerDummy(new ExtendedRookModel(this, side)));
        list.add(new PlayerDummy(new ExtendedRookModel(this, side)));

        list.add(new PlayerDummy(new ExtendedKnightModel(this, side)));
        list.add(new PlayerDummy(new ExtendedKnightModel(this, side)));

        list.add(new PlayerDummy(new ExtendedBishopModel(this, side)));
        list.add(new PlayerDummy(new ExtendedBishopModel(this, side)));

        list.add(new PlayerDummy(new ExtendedQueenModel(this, side)));

        list.add(new PlayerDummy(new ExtendedKingModel(this, side)));
    }

    @Override
    protected List<Player> initPlayers() {
        ArrayList<Player> list = new ArrayList<>();
        //init all pieces for both sides
        initAllPiecesForSide(list, 1);
        initAllPiecesForSide(list, 0);
        return list;
    }
}
