package logic;

import java.util.List;
import java.util.Map;

import meta.MetaConfig;
import meta.MetaUtil;
import model.ExtendedPieceModel;
import model.ExtendedTileModel;

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

    // reverse method from above, give position array from tile
    public static MetaPosition getTilePost(ExtendedTileModel tile) {
        if (tile.getLevel() > 0) {
            int[] I = new int[tile.getLevel()];
            int[] J = new int[tile.getLevel()];
            while (tile.getParent() != null) {
                // add position at the tail
                I[tile.getLevel() - 1] = tile.getI();
                J[tile.getLevel() - 1] = tile.getJ();
                tile = tile.getParent();
            }
            return new MetaPosition(I, J);
        }
        return null;
    }

    public static ExtendedTileModel getTileFromAbsPosition(double x, double y, int maxFraction) {
        ExtendedTileModel it = MetaConfig.getBoardModel().getRootTile();
        int i;
        int j;
        double childSize;
        while (x >0 && y > 0 && it.getChildren() != null && it.getAbsFraction() * it.getChildFraction() <= maxFraction) {
            childSize = it.getAbsSize() / it.getChildFraction();
            i = (int) Math.floor(x / childSize);
            j = (int) Math.floor(y / childSize);
            it = it.getChildren()[i][j];
            x -= i*childSize;
            y -= j*childSize;
        }
        return it;
    }

    /*
     * implementation; go to neighbour tile do remainingmovement -1 if
     * remainingmovement>0 do recursion parameters: floating, never go to lower
     * fraction, float on it ignoreoccupation, allowed to jump over pieces
     * penetraLowerFraction, continue movement when entering a lower fractioned
     * tile result: null if movement not allowed else desired tile save alle
     * encountered pieces on movement in a list
     */
    public static ExtendedTileModel getTileNeighbour(ExtendedTileModel tile,
            int horDir, int vertDir, boolean hoover, boolean ignoreOccupation,
            boolean penetrateLowerFraction, List<ExtendedTileModel> tilePath) {
        ExtendedTileModel it = tile;
        int startFraction = it.getAbsFraction();
        // root tile
        if (it.getParent() == null) {
            return null;
        }

        int verMov = Integer.signum(vertDir);
        int horMov = Integer.signum(horDir);
        int remainingHorMov = horDir - horMov;

        int remainingVerMov = vertDir - verMov;

        // on the border of parent tile
        // iterate up until parent which contains neighbour is found
        if (it.getI() + horMov > it.getParent().getChildFraction() - 1
                || it.getI() + horMov < 0
                || it.getJ() + verMov > it.getParent().getChildFraction() - 1
                || it.getJ() + verMov < 0) {
            //estimate neighbour from abs position
            it = getTileFromAbsPosition(it.getAbsX() + it.getAbsSize() / 2 + horMov * it.getAbsSize(), it.getAbsY() + it.getAbsSize() / 2 + verMov * it.getAbsSize(), startFraction);
        } else {
            it = it.getParent().getChildren()[it.getI() + horMov][it.getJ() + verMov];
        }
        //boolean to save whether movement is continue
        boolean cont = false;
        // if the new found tile contains children
        if (it.getChildren() != null) {
            if (hoover) {
                //if  there's movement remaining,
                if (Math.abs(remainingHorMov) + Math.abs(remainingVerMov) != 0) {
                    // and hoover is true, don't enter smaller tile 
                    //continue movement
                    return getTileNeighbour(it, remainingHorMov, remainingVerMov,
                            hoover, ignoreOccupation, penetrateLowerFraction,
                            tilePath);
                }
            } //if you can penetrate a lower fraction 
            else if (penetrateLowerFraction) {
                //if  there's movement remaining,
                if (Math.abs(remainingHorMov) + Math.abs(remainingVerMov) != 0) {
                    cont = true;
                }
            }
            //if hoover is true and there's no movement remaining 
            //or you can't pentrate a lower fraction or you do but there's no movement left
            //enter lowest possible fraction
            it = enterLowerFractionOfTile(it, horMov, verMov);
        }
        //it won't change in this method anymore, add to path
        if (tilePath != null) {
            tilePath.add(it);
        }
        //continue if there the tile on the path is not occupied
        if (cont && (ignoreOccupation || !it.isOccupied())) {
            return getTileNeighbour(it, remainingHorMov, remainingVerMov,
                    hoover, ignoreOccupation, penetrateLowerFraction,
                    tilePath);
        }
        // allowed
        if (!it.isOccupied()) {
            return it;
        } else {
            return null;
        }

    }

    // method for finding direct neighbour no need for floating and penetration
    // and occupation
    // options
    public static ExtendedTileModel getTileNeighbour(ExtendedTileModel tile,
            int horDir, int vertDir) {
        return getTileNeighbour(tile, horDir, vertDir, false, true, false, null);
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
        float x = viewer.getTilePosition().getAbsX()
                - viewer.getTilePosition().getAbsSize()
                * viewer.getNrOfViewTiles();
        float y = viewer.getTilePosition().getAbsY()
                - viewer.getTilePosition().getAbsSize()
                * viewer.getNrOfViewTiles();
        float s = viewer.getTilePosition().getAbsSize()
                * (2 * viewer.getNrOfViewTiles() + 1);

        if (subject.getTilePosition().getAbsX() >= x
                && subject.getTilePosition().getAbsX() <= x + s) {
            if (subject.getTilePosition().getAbsY() >= y
                    && subject.getTilePosition().getAbsY() <= y + s) {
                return true;
            }
        }
        return false;
    }

    // return a tile with an abs fraction smaller then the one given
    public static ExtendedTileModel getRandomTile(int maxAbsFraction,
            boolean canBeOccupied) {
        ExtendedTileModel tileIt;
        do {
            // start from root tile always
            tileIt = MetaConfig.getBoardModel().getRootTile();
            // now choose random tile
            int randCol;
            int randRow;
            // pick a random max level of fraction, different from root (0)
            int randAbsFraction = MetaUtil.randInt(8, maxAbsFraction);

            while (tileIt.getChildren() != null
                    && tileIt.getAbsFraction() <= randAbsFraction) {

                // pick random child on current tile
                randCol = MetaUtil.randInt(0, tileIt.getChildren().length - 1);
                randRow = MetaUtil.randInt(0, tileIt.getChildren().length - 1);
                tileIt = tileIt.getChildren()[randCol][randRow];
            }
        } // if the tile can't be occupied restart the search if the found random
        // tile is occupied
        while (!canBeOccupied
                && MetaConfig.getBoardModel().getModelOnPosition(tileIt) != null);

        return tileIt;

    }

    public static void printAllPiecePositions() {
        for (Map.Entry<ExtendedPieceModel, ExtendedTileModel> pair : MetaConfig
                .getBoardModel().getEntityModels().entrySet()) {

        }
    }

}
