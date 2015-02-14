package userinterface.specific;

import userinterface.accesobject.POAxis;
import userinterface.accesobject.POCooldown;
import userinterface.accesobject.POExtendeSpecial;
import userinterface.accesobject.PORange;
import userinterface.accesobject.PORangeOfDecision;
import userinterface.accesobject.POTurn;

import userinterface.generic.GUITile;

public class GTPlayerStatus extends GUITile {

    // show cooldown of all decisions of the players piece
    public GTPlayerStatus(int color, GUITile container, int i, int j) {
        super(container.getColumns(), container.getRows(), container
                .getColumns(), container.getRows(), color, container, i, j);

        build();

    }

    private void build() {
        addElement(new GTBool(color, this, 1, getRows() - 1, new POTurn()));
        addElement(new GTIcon(color, this, 0, getRows() - 1, "YOURTURN"));
        addElement(new GTInt(color, this, 1, getRows() - 2, new PORange()));
        addElement(new GTInt(color, this, 1, getRows() - 3, new POCooldown()));
        addElement(new GTInt(color, this, 1, getRows() - 4, new PORangeOfDecision()));
        addElement(new GTBool(color, this, 1, getRows() - 5, new POExtendeSpecial()));
        addElement(new GTIcon(color, this, 0, getRows() - 6, "TURN"));
        addElement(new GTInt(color, this, 1, getRows() - 6, new POAxis()));

    }

}
