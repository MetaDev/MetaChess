package model;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import meta.MetaConfig.PieceType;

public class ExtendedBoardModel {

    // save a mapping of clientid and ExtendedPieceModel to know where all
    // player are
    private ExtendedTileModel rootTile;
    private Set<PlayerModel> playersOnBoard = new HashSet<>();

    private Map<Integer, Integer> teamLives = new ConcurrentHashMap<>();
    private PlayerInputModel inputPlayer;

    public Set<PlayerModel> getPlayersOnBoard() {
        return playersOnBoard;
    }

    public ExtendedPieceModel getPieceByTypeAndSide(PieceType type, int side) {
        for (PlayerModel player : playersOnBoard) {
            ExtendedPieceModel piece = player.getControlledModel();
            if (piece.getType() == type && piece.getSide() == side) {
                return piece;
            }
        }
        return null;
    }

    public PlayerInputModel getInputPlayer() {
        return inputPlayer;
    }

    public void setInputPlayer(PlayerInputModel inputPlayer) {
        this.inputPlayer = inputPlayer;
    }

    public ExtendedBoardModel(ExtendedTileModel rootTile) {
        this.rootTile = rootTile;
        teamLives.put(0, 32);
        teamLives.put(1, 32);
    }

    public int getSideLives(int team) {
        return teamLives.get(team);
    }

    public void decreaseSideLives(int team, int lives) {
        teamLives.put(team, getSideLives(team) - lives);
    }

    public ExtendedTileModel getRootTile() {
        return rootTile;
    }

    public void setRootTile(ExtendedTileModel rootTile) {
        this.rootTile = rootTile;
    }

    public ExtendedPieceModel getPieceByPosition(ExtendedTileModel pos) {
        for (PlayerModel player : playersOnBoard) {
            ExtendedPieceModel piece = player.getControlledModel();
            if (piece.getTilePosition().equals(pos)) {
                return piece;
            }
        }
        return null;
    }

    public void removePlayer(PlayerModel player) {
        playersOnBoard.remove(player);
    }

    public void removePiece(ExtendedPieceModel toRemovePiece) {
        for (PlayerModel player : playersOnBoard) {
            ExtendedPieceModel piece = player.getControlledModel();
            if (piece.equals(toRemovePiece)) {
                removePlayer(player);
                return;
            }
        }
    }

}
