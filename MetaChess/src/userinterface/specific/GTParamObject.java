package userinterface.specific;

import meta.MetaConfig;
import model.paramobjects.ParamObject;
import userinterface.generic.GUI1Tile;
import userinterface.generic.GUITile;

public class GTParamObject extends GUI1Tile {

    private ParamObject paramObject;

    public GTParamObject(int color, GUITile container, int i, int j,
            ParamObject paramObject) {
        super(color, container, i, j);
        this.paramObject = paramObject;
       
    }

    private int getParam() {
        return paramObject.getParam(MetaConfig.getBoardModel().getPlayer().getControlledModel());
    }

    @Override
    public int[][] getGrid() {
        updateRows();
        return grid;
    }

    

    private void updateRows() {
        clear();
        int oppositeColor = (color + 1) % 2;

        int parameterValue = getParam();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < Math.min(parameterValue, 8); j++) {
                setColorInGrid(i, j, oppositeColor);
            }
            parameterValue -= 8;
            if (parameterValue < 1) {
                break;
            }
        }

    }

}
