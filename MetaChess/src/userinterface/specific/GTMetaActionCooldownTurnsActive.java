package userinterface.specific;

import decision.Decision;
import meta.MetaMapping;
import model.ExtendedPlayerModel;
import userinterface.generic.GUI1Tile;
import userinterface.generic.GUITile;

public class GTMetaActionCooldownTurnsActive extends GUI1Tile {
	//display cooldown and active turns
	//there are 5 rows for the cooldown (max 40)
	//there are 2 rows for turns active (max 16)
	private Decision metaAction;
	private int oldCooldown;
	private int oldTurnsActive;

	public GTMetaActionCooldownTurnsActive(int color, GUITile container, int i,
			int j, Decision metaAction) {
		super(color, container, i, j);
		this.metaAction = metaAction;
		updateCooldownRows();
		updateTurnsActiveRows();
	}

	@Override
	public int[][] getGrid() {

		ExtendedPlayerModel player = MetaMapping.getBoardModel().getPlayer();
		int cooldown = Math.min(player.getCooldown(metaAction),40);
		int turnsActive = Math.min(player.getTurnsOfActivity(metaAction),16);

		// if something change, adapt the grid

		if (cooldown != oldCooldown) {
			oldCooldown = cooldown;
			updateCooldownRows();
		}
		if (turnsActive != oldTurnsActive) {
			oldTurnsActive = turnsActive;
			updateTurnsActiveRows();
		}
		
		return grid;

	}

	private void clearCooldownRows() {
		for (int i = 0; i < 8; i++) {
			grid[3][i]=color;
		}
		for (int i = 0; i < 8; i++) {
			grid[4][i]=color;
		}
		for (int i = 0; i < 8; i++) {
			grid[5][i]=color;
		}
		for (int i = 0; i < 8; i++) {
			grid[6][i]=color;
		}
		for (int i = 0; i < 8; i++) {
			grid[7][i]=color;
		}
	}


	

	private void clearTurnsActiveRows() {
		for (int i = 0; i < 8; i++) {
			grid[0][i]=color;
		}
		for (int i = 0; i < 8; i++) {
			grid[1][i]=color;
		}

	}

	private void updateCooldownRows() {
		int oppositeColor = (color + 1) % 2;
		clearCooldownRows();
		int row4 = Math.min(oldCooldown - 32,8);
		int row3 = Math.min(oldCooldown - 24,8);
		int row2 = Math.min(oldCooldown - 16,8);
		int row1 = Math.min(oldCooldown - 8,8);
		int row0 = Math.min(oldCooldown,8);
		if (row4 > 0) {
			for (int i = 0; i < row4; i++) {
				grid[i][3]=oppositeColor;
			}
		}
		if (row3 > 0) {
			for (int i = 0; i < row3; i++) {
				grid[i][4]=oppositeColor;
			}
		}
		if (row2 > 0) {
			for (int i = 0; i < row2; i++) {
				grid[i][5]=oppositeColor;
			}
		}
		if (row1 > 0) {
			for (int i = 0; i < row1; i++) {
				grid[i][6]=oppositeColor;
			}
		}
		if (row0 > 0) {
			for (int i = 0; i < row0; i++) {
				grid[i][7]=oppositeColor;
			}
		}

	}

	private void updateTurnsActiveRows() {
		int oppositeColor = (color + 1) % 2;
		clearTurnsActiveRows();

		int row1 = Math.min(oldTurnsActive - 8,8);
		int row0 =  Math.min(oldTurnsActive,8);
		
		if (row1 > 0) {
			for (int i = 0; i < row1; i++) {
				grid[i][1]=oppositeColor;
			}
		}
		if (row0 > 0) {
			for (int i = 0; i < row0; i++) {
				grid[i][0]=oppositeColor;
			}
		}
	}
}
