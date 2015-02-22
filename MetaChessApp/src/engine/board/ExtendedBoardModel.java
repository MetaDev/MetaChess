package engine.board;

import engine.piece.ExtendedPieceModel;
import engine.piece.ExtendedPieceModel.PieceType;
import engine.player.Player;
import engine.player.PlayerInput;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import meta.MetaConfig;

public class ExtendedBoardModel {

    // save a mapping of clientid and ExtendedPieceModel to know where all
    // player are
    private ExtendedTileModel rootTile;
    private Set<Player> playersOnBoard = new HashSet<>();

    private Map<Integer, Integer> sideLives = new ConcurrentHashMap<>();
    private PlayerInput inputPlayer;

    public void pieceTaken(ExtendedPieceModel pieceTaken,ExtendedPieceModel pieceTaker){
        Player playerTaken = getPlayerByPiece(pieceTaken);
        Player playerTaker = getPlayerByPiece(pieceTaker);
        //decrease team lives
        if(playerTaken.isDecreaseLivesOnKill()){
                 decreaseSideLives(pieceTaken.getColor(),
                    pieceTaken.getLives());
                 //adapt player lives lost and taken
                 playerTaken.increaseLivesLost(pieceTaken.getLives());
                 playerTaker.increaseLivesTaken( pieceTaken.getLives());
        }
        //put piecetaken on a random tile
        pieceTaken.setTilePosition(BoardLogic.getRandomTile(false));
        
   
    }
    public Set<Player> getPlayersOnBoard() {
        return playersOnBoard;
    }

    public ExtendedPieceModel getPieceByTypeAndSide(PieceType type, int side) {
        for (Player player : playersOnBoard) {
            ExtendedPieceModel piece = player.getControlledModel();
            if (piece.getType() == type && piece.getColor() == side) {
                return piece;
            }
        }
        return null;
    }

    public PlayerInput getInputPlayer() {
        return inputPlayer;
    }

    public void setInputPlayer(PlayerInput inputPlayer) {
        this.inputPlayer = inputPlayer;
    }

    public ExtendedBoardModel(ExtendedTileModel rootTile) {
        this.rootTile = rootTile;
        sideLives.put(0, 32);
        sideLives.put(1, 32);
    }

    public int getSideLives(int team) {
        return sideLives.get(team);
    }

    public void decreaseSideLives(int team, int lives) {
        sideLives.put(team, getSideLives(team) - lives);
    }

    public ExtendedTileModel getRootTile() {
        return rootTile;
    }

    public void setRootTile(ExtendedTileModel rootTile) {
        this.rootTile = rootTile;
    }

    public ExtendedPieceModel getPieceByPosition(ExtendedTileModel pos) {
        for (Player player : playersOnBoard) {
            ExtendedPieceModel piece = player.getControlledModel();
            if (piece.getTilePosition().equals(pos)) {
                return piece;
            }
        }
        return null;
    }

    public void removePlayer(Player player) {
        playersOnBoard.remove(player);
    }

    public Player getPlayerByPiece(ExtendedPieceModel piece) {
        for (Player player : playersOnBoard) {
            ExtendedPieceModel playerPiece = player.getControlledModel();
            if (piece.equals(playerPiece)) {
                return player;
            }
        }
        return null;
    }

    public void removePlayerByPiece(ExtendedPieceModel toRemovePiece) {
        removePlayer(getPlayerByPiece(toRemovePiece));
    }

}
