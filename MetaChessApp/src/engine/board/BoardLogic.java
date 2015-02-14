package engine.board;

import java.util.List;

import meta.MetaConfig;
import meta.MetaUtil;
import engine.piece.ExtendedPieceModel;
import engine.player.Player;

public class BoardLogic {

    public static ExtendedTileModel getTile(int[] I, int[] J) {
        ExtendedTileModel currTile = (ExtendedTileModel) MetaConfig
                .getBoardModel().getRootTile();
        int i = 0;
        while (currTile.getChildren() != null && i < I.length && i < J.length) {
            currTile = currTile.getChildren()[I[i]][J[i]];
            i++;
        }
        return currTile;
    }

    public static ExtendedTileModel getTileFromAbsPosition(double x, double y, int maxFraction) {
        //check if position is inside the main board
        if (x < 0 || y < 0 || x > MetaConfig.getTileSize() || y > MetaConfig.getTileSize()) {
            return null;
        }
        ExtendedTileModel it = MetaConfig.getBoardModel().getRootTile();
        int i;
        int j;
        double childSize;
        while (x > 0 && y > 0 && it.getChildren() != null && it.getAbsFraction() * it.getChildFraction() <= maxFraction) {
            childSize = it.getAbsSize() / it.getChildFraction();
            i = (int) Math.floor(x / childSize);
            j = (int) Math.floor(y / childSize);

            it = it.getChildren()[i][j];
            x -= i * childSize;
            y -= j * childSize;
        }
        return it;
    }

    private static boolean isCoord(int mov) {
        return mov == 0 || mov == 1 || mov == -1;
    }
    /*
     * implementation; go to neighbour tile do remainingmovement -1 if
     * remainingmovement>0 do recursion parameters: floating, never go to lower
     * fraction, float on it ignoreoccupation, allowed to jump over pieces
     * penetraLowerFraction, continue movement when entering a lower fractioned
     * tile result: null if movement not allowed else desired tile save alle
     * encountered pieces on movement in a list
     */

    public static ExtendedTileModel findTileNeighBour(ExtendedTileModel tile,
            int horMov, int verMov, boolean hoover, int startFraction) {
        ExtendedTileModel it = tile;
        // root tile or wrong move
        if (it.getParent() == null || !isCoord(horMov) || !isCoord(verMov)) {
            return null;
        }
        //hor and vertDir have values -1, or 1

        // on the border of parent tile
        if (it.getI() + horMov > it.getParent().getChildFraction() - 1
                || it.getI() + horMov < 0
                || it.getJ() + verMov > it.getParent().getChildFraction() - 1
                || it.getJ() + verMov < 0) {
            //estimate neighbour from abs position
            it = getTileFromAbsPosition(it.getAbsX() + it.getAbsSize() / 2 + horMov * it.getAbsSize(), it.getAbsY() + it.getAbsSize() / 2 + verMov * it.getAbsSize(), startFraction);
        } else {
            it = it.getParent().getChildren()[it.getI() + horMov][it.getJ() + verMov];
        }
        //at border of board
        if (it == null) {
            //add null to indicate that the path is terminated early
            return it;
        }
        // if the new found tile contains children
        if (it.getChildren() != null) {
            if (!hoover) {
                //if children and not hoover, enterlowest fraction of neighbour
                it = enterLowerFractionOfTile(it, horMov, verMov);
            }

        }
        return it;

    }

//if the fraction you enter is smaller (bigger fractioning) than positioning is depended of move direction
    public static ExtendedTileModel enterLowerFractionOfTile(
            ExtendedTileModel tile, int horMov, int verMov) {
        ExtendedTileModel it = tile;
        int i = 0;
        int j = 0;

        // go to second lowest fraction with random position
        while (it.getChildren() != null) {
            int centerFloor = (it.getChildFraction() - 1) / 2;
            int centerCeiling = it.getChildFraction() / 2;
            int border = it.getChildFraction() - 1;
            //right
            if (horMov == 1 && verMov == 0) {
                i = 0;
                j = centerCeiling;
            } //left
            else if (horMov == -1 && verMov == 0) {
                i = border;
                j = centerFloor;
            } //up
            else if (horMov == 0 && verMov == 1) {
                i = centerFloor;
                j = 0;
            } //down
            else if (horMov == 0 && verMov == -1) {
                i = centerCeiling;
                j = border;
            } //up right
            else if (horMov == 1 && verMov == 1) {
                i = 0;
                j = 0;
            } //down right
            else if (horMov == 1 && verMov == -1) {
                i = 0;
                j = border;
            } //down left
            else if (horMov == -1 && verMov == -1) {
                i = 0;
                j = border;
            } //up left
            else if (horMov == -1 && verMov == 1) {
                i = border;
                j = 0;
            }
            it = it.getChildren()[i][j];
        }

        return it;
    }

    // Euclidian distance of tiles
    public static double calculateDistance(ExtendedTileModel tile1,
            ExtendedTileModel tile2) {
        return Math
                .sqrt(Math.pow((double) tile1.getAbsX() - tile2.getAbsX(), 2)
                        + Math.pow((double) tile1.getAbsY() - tile2.getAbsY(),
                                2));
    }
    

    public static boolean isInrange(ExtendedPieceModel viewer,
            ExtendedPieceModel subject) {
        // calculate viewsquare boundaries
        double x = viewer.getTilePosition().getAbsX()
                - viewer.getTilePosition().getAbsSize()
                * viewer.getNrOfViewTiles();
        double y = viewer.getTilePosition().getAbsY()
                - viewer.getTilePosition().getAbsSize()
                * viewer.getNrOfViewTiles();
        double s = viewer.getTilePosition().getAbsSize()
                * (2 * viewer.getNrOfViewTiles() + 1);
        //the tile of the subject should be completely in the view
        if (subject.getTilePosition().getAbsX() >= x
                && subject.getTilePosition().getAbsX() + subject.getTilePosition().getAbsSize() <= x + s) {
            if (subject.getTilePosition().getAbsY() >= y
                    && subject.getTilePosition().getAbsY() + subject.getTilePosition().getAbsSize() <= y + s) {
                return true;
            }
        }
        return false;
    }

    // return a tile with an abs fraction smaller then the one given
    public static ExtendedTileModel getRandomTile(
            boolean canBeOccupied) {
        ExtendedTileModel tileIt;
        do {
            // start from root tile always
            tileIt = MetaConfig.getBoardModel().getRootTile();
            // now choose random tile
            int randCol;
            int randRow;

            while (tileIt.getChildren() != null) {

                // pick random child on current tile
                randCol = MetaUtil.randInt(0, tileIt.getChildren().length - 1);
                randRow = MetaUtil.randInt(0, tileIt.getChildren().length - 1);
                tileIt = tileIt.getChildren()[randCol][randRow];
            }
        } // if the tile can't be occupied restart the search if the found random
        // tile is occupied
        while (!canBeOccupied
                && MetaConfig.getBoardModel().getPieceByPosition(tileIt) != null);

        return tileIt;

    }

    public static Player getClosestPlayer(Player player) {
        double compareDist;
        double tempDist;
        Player closestNeighbour = null;
        ExtendedPieceModel piece;

        // closest
        compareDist = Double.MAX_VALUE;
        for (Player neighbour : MetaConfig.getBoardModel()
                .getPlayersOnBoard()) {
            piece = neighbour.getControlledModel();
            if (!player.equals(neighbour) && player.getSide() == neighbour.getSide()
                    && (tempDist = BoardLogic.calculateDistance(
                            player.getControlledModel().getTilePosition(),
                            piece.getTilePosition())) < compareDist) {
                compareDist = tempDist;
                closestNeighbour = neighbour;
            }
        }
        return closestNeighbour;
    }

    public static Player getFurthestPlayer(Player player) {
        double compareDist;
        double tempDist;
        Player closestNeighbour = null;
        ExtendedPieceModel piece;

        // furthest
        compareDist = Double.MIN_VALUE;
        for (Player neighbour : MetaConfig.getBoardModel()
                .getPlayersOnBoard()) {
            piece = neighbour.getControlledModel();
            if (!player.equals(neighbour) && player.getSide() == neighbour.getSide()
                    && (tempDist = BoardLogic.calculateDistance(
                            player.getControlledModel().getTilePosition(),
                            piece.getTilePosition())) > compareDist) {
                compareDist = tempDist;
                closestNeighbour = neighbour;
            }
        }
        return closestNeighbour;
    }

}
