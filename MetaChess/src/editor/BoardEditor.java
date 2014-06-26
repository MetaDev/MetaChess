package editor;

import logic.BoardLogic;
import logic.MetaClock;
import meta.MetaConfig;
import meta.MetaUtil;
import model.ExtendedTileModel;

public class BoardEditor extends Editor {

	public static void init() {
		boolean fractionRandom = true;
		// number of fractions allowed under the maxfraction, where the game
		// becomes real-time
		int fractionUnderMaxFraction = 32;
		int maxAbsFraction = MetaClock.getMaxFraction()
				+ fractionUnderMaxFraction;
		int iterations = 100;

		// draw floor tiles
		ExtendedTileModel floor = (ExtendedTileModel) MetaConfig
				.getBoardModel().getRootTile();
		// make manditory 8 tiles
		floor.divide(8);

		// now randomly change the board

		// the more iterations the more fractioned tiles
		if (fractionRandom) {
			int randFraction;
			int randAbsFraction;
			for (int i = 0; i < iterations; i++) {

				// pick random fraction
				randFraction = (int) Math.pow(2, MetaUtil.randInt(1, 3));
				// pick a random max level of depth
				randAbsFraction = MetaUtil.randInt(1, maxAbsFraction);

				// now choose random tile
				BoardLogic.getRandomTile(randAbsFraction).divide(randFraction);

			}
		}

	}

}
