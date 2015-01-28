package userinterface.specific;

import meta.MetaConfig;
import model.DecisionModel;
import model.ExtendedPieceModel;
import model.SpecialDecisionModel;
import model.paramobjects.POCooldown;
import model.paramobjects.PORange1;

import userinterface.generic.GUI1Tile;
import userinterface.generic.GUITile;

public class GTPlayerPiece extends GUITile {

    // show cooldown of all decisions of the players piece
    public GTPlayerPiece(int color, GUITile container, int i, int j) {
        super(container.getColumns(), container.getRows(), container
                .getColumns(), container.getRows(), color, container, i, j);
        refresh();
    }

    @Override
    public void refresh() {
        //reset();
        //show piece type
        // decision of piece
        ExtendedPieceModel playerPiece = MetaConfig.getBoardModel().getPlayer()
                .getControlledModel();
        int row = 0;
        addElement(new GTParamObject(color, this, 1, 3 , new POCooldown()));
         addElement(new GTParamObject(color, this, 1, 4 , new PORange1()));
        for (DecisionModel decision : playerPiece.getAllowedDecision()) {
            if (decision.isCooldown()) {
                System.out.println(decision.getName());
                addElement(new GTIcon(color, this, 0, row, decision.getName()));
                addElement(new GTParamObject(color, this, 1, row, ((SpecialDecisionModel) decision).getSpecialObject()));
                row++;
            }

        }
    }

}
