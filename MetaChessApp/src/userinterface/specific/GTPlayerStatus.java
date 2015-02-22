package userinterface.specific;

import userinterface.accesobject.POAxis;
import userinterface.accesobject.POCooldown;
import userinterface.accesobject.POExtendeSpecial;
import userinterface.accesobject.POLives;
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
        addElement(new GTIcon(color, this, 0, getRows() - 1, "turn"));
        addElement(new GTInt8(color, this, 1, getRows() - 2, new PORange()));
        addElement(new GTIcon(color, this, 0, getRows() - 2, "range"));
        addElement(new GTBool(color, this, 1, getRows() - 3, new POExtendeSpecial()));
        addElement(new GTIcon(color, this, 0, getRows() - 3, "extended"));
        addElement(new GTInt8(color, this, 1, getRows() - 4, new PORangeOfDecision()));
        addElement(new GTIcon(color, this, 0, getRows() - 4, "decisionrange"));
        addElement(new GTInt64(color, this, 1, getRows() - 5, new POCooldown()));
        addElement(new GTIcon(color, this, 0, getRows() - 5, "decisioncooldown"));
        addElement(new GTType(color, this, 0, getRows()-6));
        addElement(new GTInt64(color, this, 1, getRows()-6, new POLives()));

    }

}
