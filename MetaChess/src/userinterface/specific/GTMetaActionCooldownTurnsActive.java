package userinterface.specific;

import logic.DecisionLogic;
import logic.MetaClock;
import meta.MetaConfig;
import model.ExtendedPlayerModel;
import userinterface.generic.GUI1Tile;
import userinterface.generic.GUITile;

public class GTMetaActionCooldownTurnsActive extends GUI1Tile {
	// display cooldown and active turns
	// there are 5 rows for the cooldown (max 40)
	// cooldown is always shown ifo the current tile of the player
	// there are 2 rows for turns active (max 16)
	private String decision;
	private int oldCooldown;
	private int oldTurnsActive;

	public GTMetaActionCooldownTurnsActive(int color, GUITile container, int i,
			int j, String decision) {
		super(color, container, i, j);
		this.decision = decision;
		reset();
		updateCooldownRows();
		updateTurnsActiveRows();
	}

	@Override
	public int[][] getGrid() {
		
		ExtendedPlayerModel player = MetaConfig.getBoardModel().getPlayer();
		if(player.getControlledModel()==null){
			return grid;
		}
		//cooldown is relative and represents number of turns you have to wait on this tile to cool it down
		int cooldown = Math.min(
				player.getControlledModel().getCooldown(decision)
						/ (MetaClock.getMaxFraction()/player.getControlledModel().getTilePosition()
								.getAbsFraction()), 40);
		int turnsActive = Math.min(player.getControlledModel()
				.getTurnsOfActivity(decision), 16);

		// if something change, adapt the grid
		if (cooldown != oldCooldown) {
			System.out.println(cooldown);
			oldCooldown = cooldown;
			updateCooldownRows();
		}
		if (turnsActive != oldTurnsActive) {
			oldTurnsActive = turnsActive;
			updateTurnsActiveRows();
		}

		return grid;

	}

	private void updateCooldownRows() {
		resetRows(new int[] { 3, 4, 5, 6, 7 });
		int oppositeColor = (color + 1) % 2;
		int row4 = Math.min(oldCooldown - 32, 8);
		int row3 = Math.min(oldCooldown - 24, 8);
		int row2 = Math.min(oldCooldown - 16, 8);
		int row1 = Math.min(oldCooldown - 8, 8);
		int row0 = Math.min(oldCooldown, 8);

		if (row4 > 0) {
			for (int i = 0; i < row4; i++) {
				setColorInGrid(i, 3, oppositeColor);
			}
		}
		if (row3 > 0) {
			for (int i = 0; i < row3; i++) {
				setColorInGrid(i, 4, oppositeColor);
			}
		}
		if (row2 > 0) {
			for (int i = 0; i < row2; i++) {
				setColorInGrid(i, 5, oppositeColor);
			}
		}
		if (row1 > 0) {
			for (int i = 0; i < row1; i++) {
				setColorInGrid(i, 6, oppositeColor);
			}
		}
		if (row0 > 0) {

			for (int i = 0; i < row0; i++) {
				setColorInGrid(i, 7, oppositeColor);
			}
		}

	}

	private void updateTurnsActiveRows() {
		resetRows(new int[] { 0, 1 });
		int oppositeColor = (color + 1) % 2;
		int row1 = Math.min(oldTurnsActive - 8, 8);
		int row0 = Math.min(oldTurnsActive, 8);

		if (row1 > 0) {
			for (int i = 0; i < row1; i++) {
				setColorInGrid(i, 1, oppositeColor);
			}
		}
		if (row0 > 0) {
			for (int i = 0; i < row0; i++) {
				setColorInGrid(i, 0, oppositeColor);
			}
		}
	}
}
