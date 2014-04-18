package userinterface.specific;

import meta.MetaMapping;
import model.ExtendedBoardModel;
import model.ExtendedPlayerModel;
import userinterface.generic.GUI1Tile;
import userinterface.generic.GUITile;
import action.MetaAction;

public class GTMetaActionCooldownTurnsActive extends GUI1Tile {
	// dynimacally change the grid of square based on the MetaActions activity,
	// cooldown
	// always display the icon
	private MetaAction metaAction;
	private int oldCooldown;
	private int oldBoardActiveTurns;
	private int oldTurnsActive;

	public GTMetaActionCooldownTurnsActive(int color, GUITile container, int i,
			int j, MetaAction metaAction) {
		super(color, container, i, j);
		this.metaAction = metaAction;
	}

	@Override
	public GUITile[][] getElements() {

		ExtendedPlayerModel player = MetaMapping.getBoardModel().getPlayer();
		int cooldown = player.getCooldown(metaAction);
		int turnsActive = player.getTurnsOfActivity(metaAction);

		// if something change, adapt the grid

		if (cooldown != oldCooldown) {
			oldCooldown = cooldown;
			updateCooldownRows();
		}
		if (turnsActive != oldTurnsActive) {
			oldTurnsActive = turnsActive;
			updateTurnsActiveRows();
		}
		
		return elements;

	}

	private void clearCooldownRows() {

		for (int i = 0; i < 8; i++) {
			setColorInGrid(elements, i, 6, color);
		}
		for (int i = 0; i < 8; i++) {
			setColorInGrid(elements, i, 7, color);
		}
	}


	

	private void clearTurnsActiveRows() {
		for (int i = 0; i < 8; i++) {
			setColorInGrid(elements, i, 0, color);
		}
		for (int i = 0; i < 8; i++) {
			setColorInGrid(elements, i, 1, color);
		}

	}

	private void updateCooldownRows() {
		int oppositeColor = (color + 1) % 2;
		clearCooldownRows();

		int row6 = oldCooldown - 8;
		int row7 = oldCooldown;
		// maximum shown cooldown is 16
		if (row6 > 8) {
			row6 = 8;
		}
		if (row7 > 0) {
			for (int i = 0; i < row7; i++) {
				setColorInGrid(elements, i, 7, oppositeColor);
			}
		}
		if (row6 > 0) {
			for (int i = 0; i < row7; i++) {
				setColorInGrid(elements, i, 6, oppositeColor);
			}
		}

	}

	private void updateTurnsActiveRows() {
		int oppositeColor = (color + 1) % 2;
		clearTurnsActiveRows();

		int row1 = oldTurnsActive - 8;
		int row0 = oldTurnsActive;
		// maximum shown cooldown is 16
		if (row1 > 8) {
			row1 = 8;
		}

		if (row1 > 0) {
			for (int i = 0; i < row1; i++) {
				setColorInGrid(elements, i, 1, oppositeColor);
			}
		}
		if (row0 > 0) {
			for (int i = 0; i < row0; i++) {
				setColorInGrid(elements, i, 0, oppositeColor);
			}
		}
	}
}
