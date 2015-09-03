package engine.board;

import engine.piece.ExtendedBishopModel;
import engine.piece.ExtendedPieceModel;
import engine.piece.ExtendedPieceModel.PieceType;
import engine.player.Player;
import engine.player.PlayerInput;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import meta.MetaUtil;

public class ExtendedBoardModel {

    //private static int maxWaitTime = 64* 64;
    //decides what the highest fraction allowed of a tile is, this is chosen such that the shortest turn before becoming real time is 4 *64 ms
    public static int maxTileFraction = 64;
    public static int rootSize = 8 * 64;

    //these are lookup tables for fast lookup
    private Map<ExtendedTileModel, ExtendedPieceModel> tileToPiece = new HashMap<>();
    private Map<ExtendedPieceModel, ExtendedTileModel> pieceToTile = new HashMap<>();

    public Map<ExtendedPieceModel, ExtendedTileModel> getPieceToTile() {
        return pieceToTile;
    }

    public ExtendedTileModel getPieceTile(ExtendedPieceModel piece) {
        return pieceToTile.get(piece);
    }

    public ExtendedPieceModel getTilePiece(ExtendedTileModel tile) {
        return tileToPiece.get(tile);
    }

    public void changePiecePosition(ExtendedPieceModel piece, ExtendedTileModel tile) {
        //update lookup table and tiles

        //get previous tile from piece
        ExtendedTileModel previousTile = pieceToTile.get(piece);

        //remove previous tile of piece from map
        tileToPiece.remove(previousTile);

        //put new tile of piece in map
        if (tile != null) {
            tileToPiece.put(tile, piece);
        }
        pieceToTile.put(piece, tile);

    }

    public ExtendedPieceModel getFurthestPiece(ExtendedPieceModel piece, int side) {
        double compareDist;
        double tempDist;
        ExtendedPieceModel closestNeighbour = null;
        compareDist = 0;
        for (Map.Entry<ExtendedPieceModel, ExtendedTileModel> entry : getPieceToTile().entrySet()) {
            ExtendedPieceModel neighbour = entry.getKey();
            if (neighbour != piece && side == neighbour.getColor() && (tempDist = BoardLogic.calculateDistance(entry.getValue(), piece.getTilePosition())) > compareDist) {
                compareDist = tempDist;
                closestNeighbour = neighbour;
            }
        }
        return closestNeighbour;
    }

    public ExtendedPieceModel getClosestPiece(ExtendedPieceModel piece, int side) {
        double compareDist;
        double tempDist;
        ExtendedPieceModel closestNeighbour = null;
        compareDist = Double.MAX_VALUE;
        for (Map.Entry<ExtendedPieceModel, ExtendedTileModel> entry : getPieceToTile().entrySet()) {
            ExtendedPieceModel neighbour = entry.getKey();
            if (neighbour != piece && side == neighbour.getColor() && (tempDist = BoardLogic.calculateDistance(entry.getValue(), piece.getTilePosition())) < compareDist) {
                compareDist = tempDist;
                closestNeighbour = neighbour;
            }
        }
        return closestNeighbour;
    }

    public ExtendedTileModel findTileNeighBour(ExtendedTileModel tile, int horMov, int verMov, boolean hoover, int startFraction) {
        ExtendedTileModel neighbour = tile;
        if (neighbour.getParent() == null || !BoardLogic.isSingleTileMovement(horMov) || !BoardLogic.isSingleTileMovement(verMov)) {
            return null;
        }
        if (tile.getI() + horMov > tile.getParent().getChildFraction() - 1 || tile.getI() + horMov < 0 || tile.getJ() + verMov > tile.getParent().getChildFraction() - 1 || neighbour.getJ() + verMov < 0) {
            neighbour = getTileFromAbsPosition(tile.getAbsCenterX() + horMov * tile.getAbsSize(), tile.getAbsCenterY() + verMov * tile.getAbsSize(), startFraction);
        } else {
            neighbour = neighbour.getParent().getChildren()[neighbour.getI() + horMov][neighbour.getJ() + verMov];
        }
        if (neighbour == null) {
            return neighbour;
        }
        if (neighbour.getChildren() != null && !hoover) {
            neighbour = getClosestLargestFractionTileFromNeighbour(tile, neighbour, verMov, horMov);
        }
        return neighbour;
    }

    // return a tile with an abs fraction smaller then the one given
    public ExtendedTileModel getRandomTile(boolean canBeOccupied) {
        ExtendedTileModel tileIt;
        do {
            tileIt = getRootTile();
            // now choose random tile
            int randCol;
            int randRow;
            while (tileIt.getChildren() != null) {
                randCol = MetaUtil.randInt(0, tileIt.getChildren().length - 1);
                randRow = MetaUtil.randInt(0, tileIt.getChildren().length - 1);
                tileIt = tileIt.getChildren()[randCol][randRow];
            }
        } while (!canBeOccupied && getTilePiece(tileIt) != null);
        return tileIt;
    }

    public ExtendedTileModel getTile(int[] I, int[] J) {
        ExtendedTileModel currTile = getRootTile();
        int i = 0;
        while (currTile.getChildren() != null && i < I.length && i < J.length) {
            currTile = currTile.getChildren()[I[i]][J[i]];
            i++;
        }
        return currTile;
    }

    public ExtendedTileModel getTileFromAbsPosition(double x, double y, int maxFraction) {
        if (x < 0 || y < 0 || x > rootSize || y > rootSize) {
            return null;
        }
        ExtendedTileModel it = getRootTile();
        int i;
        int j;
        double childSize;
        while (it.getChildren() != null && it.getAbsFraction() * it.getChildFraction() <= maxFraction) {
            childSize = it.getAbsSize() / it.getChildFraction();
            i = (int) Math.floor(x / childSize);
            j = (int) Math.floor(y / childSize);
            it = it.getChildren()[i][j];
            x -= i * childSize;
            y -= j * childSize;
            x = Math.max(0, x);
            y = Math.max(0, y);
        }
        return it;
    }

    public ExtendedTileModel getClosestLargestFractionTileFromNeighbour(ExtendedTileModel tile, ExtendedTileModel neighbour, int verMov, int horMov) {
        double minDist;
        ExtendedTileModel closestChild;
        ExtendedTileModel it = BoardLogic.enterLowerFractionOfTile(neighbour, horMov, verMov);
        while (it.getChildren() != null) {
            closestChild = it.getChildren()[1][1];
            minDist = BoardLogic.euclidianDistance(closestChild, tile);
            for (int i = 0; i < 2; i++) {
                for (int j = 0; j < 2; j++) {
                    if (BoardLogic.euclidianDistance(it.getChildren()[i][j], tile) < minDist) {
                        closestChild = it.getChildren()[i][j];
                        minDist = BoardLogic.euclidianDistance(closestChild, tile);
                    }
                }
            }
            it = closestChild;
        }
        return it;
    }
    //if the fraction you enter is smaller (bigger fractioning) than positioning is depended of move direction

    // save a mapping of clientid and ExtendedPieceModel to know where all
    // player are
    private ExtendedTileModel rootTile;
    private Set<Player> playersOnBoard = new HashSet<>();

    private Map<Integer, Integer> sideLives = new ConcurrentHashMap<>();
    private PlayerInput inputPlayer;

    public void pieceTaken(ExtendedPieceModel pieceTaken, ExtendedPieceModel pieceTaker) {
        Player playerTaken = getPlayerByPiece(pieceTaken);
        Player playerTaker = getPlayerByPiece(pieceTaker);
        //decrease team lives
        if (playerTaken != null && playerTaken.isDecreaseLivesOnKill()) {
            decreaseSideLives(playerTaken.getSide(),
                    pieceTaken.getLives());
            //adapt player lives lost and taken
            playerTaken.increaseLivesLost(pieceTaken.getLives());
            //lives not added to your own score if a bischop step on burning tail
            if (!playerTaken.equals(playerTaker)) {
                playerTaker.increaseLivesTaken(pieceTaken.getLives());
            }
        }
        //put piecetaken on a random tile
        pieceTaken.setTilePosition(getRandomTile(false));

    }

    public Set<Player> getPlayersOnBoard() {
        return playersOnBoard;
    }

    public static boolean isOverMacFraction(int absFraction) {
        return absFraction > maxTileFraction;
    }

    public static boolean isOverMacFraction(float tilesSize) {
        return tilesSize < rootSize / maxTileFraction;
    }

    public void getPiecesByTypeAndSide(List<ExtendedPieceModel> pieces, PieceType type, int side) {
        for (Player player : playersOnBoard) {
            ExtendedPieceModel piece = player.getControlledModel();
            if (piece.getType() == type && piece.getColor() == side) {
                pieces.add(piece);
            }
        }
    }

    public ExtendedPieceModel getPieceByTypeAndSide(PieceType type, int side) {
        List<ExtendedPieceModel> pieces = new ArrayList<ExtendedPieceModel>();
        getPiecesByTypeAndSide(pieces, type, side);
        Random rn = new Random();
        //retrun random correct piece
        return pieces.get(rn.nextInt(pieces.size()));
    }

    public PlayerInput getInputPlayer() {
        return inputPlayer;
    }

    public void setInputPlayer(PlayerInput inputPlayer) {
        this.inputPlayer = inputPlayer;
    }

    public ExtendedBoardModel() {
        this.rootTile = new ExtendedTileModel(rootSize, rootSize);
        rootTile.divide(8);
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
