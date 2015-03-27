package userinterface.specific;

import engine.board.ExtendedBoardModel;
import engine.piece.ExtendedPieceModel;
import meta.MetaConfig;
import userinterface.accesobject.POType;
import userinterface.generic.GUITile;

public class GTType extends GTInt64 {

    public GTType(float color, GUITile container, int i, int j) {
        super(color, container, i, j, new POType());
        this.transparant = true;
    }

    @Override
    protected void updateRows(ExtendedBoardModel board) {
        grid = ExtendedPieceModel.PieceType.values()[getParam(board)].name();

    }

}
