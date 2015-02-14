package userinterface.specific;

import engine.piece.ExtendedPieceModel;
import meta.MetaConfig;
import userinterface.accesobject.POType;
import userinterface.generic.GUITile;

public class GTType extends GTInt {

    public GTType(int color, GUITile container, int i, int j) {
        super(color, container, i, j, new POType());
        this.transparant = true;
    }

    @Override
    protected void updateRows() {
        grid = ExtendedPieceModel.PieceType.values()[getParam()].name();

    }

}
