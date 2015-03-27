package userinterface.specific;

import userinterface.accesobject.POLivesLost;
import userinterface.accesobject.POLivesTaken;
import userinterface.accesobject.PORemainingTeamLives;
import userinterface.generic.GUITile;

public class GUISideStatus extends GUITile {

    public GUISideStatus(float color,
            GUITile container, int i, int j) {
        super(container.getColumns(), container.getRows(), container.getColumns(), container.getRows(), color, container, i, j);

        addElement(new GTInt64(color, this, 1, getRows() - 1, new PORemainingTeamLives()));
        addElement(new GTIcon(color, this, 0, getRows() - 1, "livesside"));
        addElement(new GTInt64(color, this, 1, getRows() - 2, new POLivesTaken()));
        addElement(new GTIcon(color, this, 0, getRows() - 2, "livestaken"));
        addElement(new GTInt64(color, this, 1, getRows() - 3, new POLivesLost()));
        addElement(new GTIcon(color, this, 0, getRows() - 3, "liveslost"));
    }
}
