package userinterface.specific;

import meta.MetaConfig;
import model.ExtendedPieceModel;
import model.paramobjects.ParamObjectsAcces;
import userinterface.generic.GUI1Tile;
import userinterface.generic.GUITile;

public class GUIPanelDecision extends GUITile {
	// show cooldown of all decisions of the players piece
	public GUIPanelDecision(int color, GUITile container, int i, int j) {
		super(container.getColumns(), container.getRows(), container
				.getColumns(), container.getRows(), color, container, i, j);
		refresh();
	}

	@Override
	public void refresh() {
		clearTile();
		int k = 0;
		// decision of piece
		ExtendedPieceModel playerPiece = MetaConfig.getBoardModel().getPlayer()
				.getControlledModel();
		for (String decision : playerPiece.getCooldownOfDecisions().keySet()) {
			if (MetaConfig.getSpecialsSet().keySet().contains(decision)) {
				addElement(new GTIcon((color + 1) % 2, this, 0, k, decision));
				addElement(new GTNumber((color + 1) % 2, this, 1, k, MetaConfig
						.getSpecialsSet().get(decision)));
				addElement(new GTMetaActionCooldownTurnsActive((color + 1) % 2,
						this, 2, k, decision));
				k++;
			}

		}

		// range on new row
		addElement(new GTIcon((color + 1) % 2, this, 0, k, "MOVRANGE"));
		addElement(new GTNumber((color + 1) % 2, this, 1, k,
				ParamObjectsAcces.getPOMovement()));
		k++;
		//show type
		addElement(new GUI1Tile((color + 1) % 2, this, 0, k,
				MetaConfig.getIcon(playerPiece.getType().name())));
	}

}
