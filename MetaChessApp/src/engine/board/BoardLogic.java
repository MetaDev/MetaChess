package engine.board;

import meta.MetaConfig;
import meta.MetaUtil;
import engine.piece.ExtendedPieceModel;
import engine.player.Player;

public class BoardLogic {

    //return wether the movement is a single tile movement
    public static boolean isSingleTileMovement(int mov) {
        return mov == 0 || mov == 1 || mov == -1;
    }
   

    public static Double euclidianDistance(ExtendedTileModel tile1, ExtendedTileModel tile2) {
        //calculate euclididan distance from center of tile
        return Math.sqrt(Math.pow(tile1.getAbsCenterX() - tile2.getAbsCenterX(), 2) + Math.pow(tile1.getAbsCenterY() - tile2.getAbsCenterY(), 2));
    }


    public static ExtendedTileModel enterLowerFractionOfTile(
            ExtendedTileModel tile, int horMov, int verMov) {
        int i = 0;
        int j = 0;

        //right
        if (horMov == 1 && verMov == 0) {
            i = 0;
            j = 1;
        } //left
        else if (horMov == -1 && verMov == 0) {
            i = 1;
            j = 0;
        } //up
        else if (horMov == 0 && verMov == 1) {
            i = 0;
            j = 0;
        } //down
        else if (horMov == 0 && verMov == -1) {
            i = 1;
            j = 1;
        } //up right
        else if (horMov == 1 && verMov == 1) {
            i = 0;
            j = 0;
        } //down right
        else if (horMov == 1 && verMov == -1) {
            i = 0;
            j = 1;
        } //down left
        else if (horMov == -1 && verMov == -1) {
            i = 1;
            j = 1;
        } //up left
        else if (horMov == -1 && verMov == 1) {
            i = 1;
            j = 0;
        }

        return tile.getChildren()[i][j];
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


}
