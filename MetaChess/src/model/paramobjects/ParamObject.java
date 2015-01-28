package model.paramobjects;

import model.ExtendedPieceModel;

public abstract class ParamObject {
    public abstract int getParam(ExtendedPieceModel model);

    public abstract void setParam(ExtendedPieceModel model);

    protected int booleanToInt(boolean bool) {
        if (bool) {
            return 1;
        }
        return 0;
    }
}
