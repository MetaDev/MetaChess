package userinterface.specific;

import java.util.HashMap;
import java.util.Map;
import meta.MetaConfig;
import model.DecisionModel;
import model.ExtendedPieceModel;
import model.SpecialDecisionModel;
import model.paramobjects.POCooldown;
import model.paramobjects.PORange;

import userinterface.generic.GUITile;

public class GTPlayerPiece extends GUITile {

    // show cooldown of all decisions of the players piece
    public GTPlayerPiece(int color, GUITile container, int i, int j) {
        super(container.getColumns(), container.getRows(), container
                .getColumns(), container.getRows(), color, container, i, j);

        build();

    }

    private void build() {
        addElement(new GTParamObject(color, this, 1, getRows() - 2, new POCooldown()));
        addElement(new GTParamObject(color, this, 0, getRows() - 2, new PORange()));

    }

}
