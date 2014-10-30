package editor;

import java.util.ArrayList;
import java.util.List;

import logic.BoardLogic;
import logic.PieceLogic;
import meta.MetaClock;
import meta.MetaConfig;
import meta.MetaConfig.PieceType;
import meta.MetaUtil;
import model.ExtendedBishopModel;
import model.ExtendedKingModel;
import model.ExtendedKnightModel;
import model.ExtendedPawnModel;
import model.ExtendedPieceModel;
import model.ExtendedPlayerModel;
import model.ExtendedQueenModel;
import model.ExtendedRookModel;

public class PieceEditor extends Editor {

	private static List<ExtendedPieceModel> initPieces(int side) {
		List<ExtendedPieceModel> list = new ArrayList<>();
		// create all pieces
		for (int i = 0; i < 8; i++) {
			list.add(new ExtendedPawnModel(side));
		}

		 list.add(new ExtendedRookModel(side));
		 list.add(new ExtendedRookModel(side));
		
		 list.add(new ExtendedKnightModel(side));
		 list.add(new ExtendedKnightModel(side));
		
		 list.add(new ExtendedBishopModel(side));
		 list.add(new ExtendedBishopModel(side));
		
		 list.add(new ExtendedQueenModel(side));

		list.add(new ExtendedKingModel(side));
		return list;
	}

	private static void initPiecePosition(List<ExtendedPieceModel> list) {
		for (ExtendedPieceModel piece : list) {
			PieceLogic
					.setPosition(piece, BoardLogic.getRandomTile(
							MetaClock.getMaxFraction(), false));
		}
	}

	public static void init() {
		List<ExtendedPieceModel> list1 = initPieces(1);
		List<ExtendedPieceModel> list0 = initPieces(0);
		initPiecePosition(list1);
		initPiecePosition(list0);

		// assign pieces randomly to player(s)
		// ExtendedPlayerModel player = new ExtendedPlayerModel(1,
		// list1.get(MetaUtil.randInt(0, 3)), "HARALD",
		// MetaConfig.getIcon("HARALD"));
		ExtendedPlayerModel player = new ExtendedPlayerModel(1, MetaConfig
				.getBoardModel().getPieceByTypeAndSide(PieceType.KING, 0),
				"HARALD", MetaConfig.getIcon("HARALD"));
		MetaConfig.getBoardModel().setPlayer(player);

	}
}
