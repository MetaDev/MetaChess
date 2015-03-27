/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package engine.board.types;

import engine.MetaClock;
import engine.board.ExtendedBoardModel;
import engine.piece.ExtendedPawnModel;
import engine.piece.ExtendedPieceModel;
import engine.player.Player;
import engine.player.PlayerAIRandom;
import engine.player.PlayerInput;
import java.util.List;
import meta.MetaUtil;

/**
 *
 * @author Harald
 */
public abstract class TestBoard extends ExtendedBoardModel {

    public TestBoard(boolean randomFraction, ExtendedPieceModel.PieceType playerType, String playerName, String playerIcon, int playerSide) {
        super();
        int fractionUnderMaxFraction = 32;
        int maxAbsFraction = MetaClock.getMaxFraction()
                + fractionUnderMaxFraction;
        int iterations = 100;

        // the more iterations the more fractioned tiles
        if (randomFraction) {
            int randFraction;
            int randAbsFraction;
            for (int i = 0; i < iterations; i++) {

                // pick random fraction
                randFraction = (int) Math.pow(2, MetaUtil.randInt(1, 3));
                // pick a random max level of depth
                randAbsFraction = MetaUtil.randInt(8, maxAbsFraction);

                // now choose random tile
                getRandomTile(true).divide(randFraction);

            }
        }
        //initialise pieces
        initPlayerPosition(initPlayers());
        initPlayer(playerType, playerName, playerIcon, playerSide);
    }

    private void initPlayer(ExtendedPieceModel.PieceType playerType, String playerName, String playerIcon, int playerSide) {
        //the input players piece
        ExtendedPieceModel myPiece = getPieceByTypeAndSide(playerType, playerSide);
        removePlayerByPiece(myPiece);
        PlayerInput player = new PlayerInput(playerSide, myPiece,
                playerName,
                playerIcon);

        setInputPlayer(player);
        getPlayersOnBoard().add(player);
    }

    private void initPlayerPosition(List<Player> list) {
        for (Player player : list) {
            player.getControlledModel().setTilePosition(getRandomTile(false));
            getPlayersOnBoard().add(player);
        }
    }

    //implemented by differen board types

    protected abstract List<Player> initPlayers();

    protected void initTestPawns(List<Player> list) {
        // create exercice pawns
        for (int i = 0; i < 8; i++) {
            list.add(new PlayerAIRandom( new ExtendedPawnModel(this, 0)));
        }

    }
}
