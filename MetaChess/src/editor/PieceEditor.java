package editor;

import logic.BoardLogic;
import logic.MetaClock;
import logic.PieceLogic;
import meta.MetaConfig;
import model.ExtendedBishopModel;
import model.ExtendedKingModel;
import model.ExtendedKnightModel;
import model.ExtendedPawnModel;
import model.ExtendedPlayerModel;
import model.ExtendedQueenModel;
import model.ExtendedRookModel;

public class PieceEditor extends Editor {
	public static void init() {
		initPieces();

	}

	private static void initPieces() {
		boolean debug=true;
		// create all pieces
		ExtendedPawnModel pawn0 = new ExtendedPawnModel(1);
		ExtendedPawnModel pawn1 = new ExtendedPawnModel(1);
		ExtendedPawnModel pawn2 = new ExtendedPawnModel(1);
		ExtendedPawnModel pawn3 = new ExtendedPawnModel(1);
		ExtendedPawnModel pawn4 = new ExtendedPawnModel(1);
		ExtendedPawnModel pawn5 = new ExtendedPawnModel(1);
		ExtendedPawnModel pawn6 = new ExtendedPawnModel(1);
		ExtendedPawnModel pawn7 = new ExtendedPawnModel(1);

		ExtendedRookModel rook0 = new ExtendedRookModel(1);
		ExtendedRookModel rook1 = new ExtendedRookModel(1);

		ExtendedKnightModel knight0 = new ExtendedKnightModel(1);
		ExtendedKnightModel knight1 = new ExtendedKnightModel(1);

		ExtendedBishopModel bischop1 = new ExtendedBishopModel(1);
		ExtendedBishopModel bischop0 = new ExtendedBishopModel(1);

		ExtendedQueenModel queen = new ExtendedQueenModel(1);

		ExtendedKingModel king = new ExtendedKingModel(1);

		// set positions

//		PieceLogic.setPosition(pawn0,
//				BoardLogic.getRandomTile(MetaClock.getMaxFraction(),false));
//
//		PieceLogic.setPosition(pawn1,
//				BoardLogic.getRandomTile(MetaClock.getMaxFraction(),false));
//
//		PieceLogic.setPosition(pawn2,
//				BoardLogic.getRandomTile(MetaClock.getMaxFraction(),false));
//
//		PieceLogic.setPosition(pawn3,
//				BoardLogic.getRandomTile(MetaClock.getMaxFraction(),false));
//
//		PieceLogic.setPosition(pawn4,
//				BoardLogic.getRandomTile(MetaClock.getMaxFraction(),false));
//
//		PieceLogic.setPosition(pawn5,
//				BoardLogic.getRandomTile(MetaClock.getMaxFraction(),false));
//
//		PieceLogic.setPosition(pawn6,
//				BoardLogic.getRandomTile(MetaClock.getMaxFraction(),false));
//
//		PieceLogic.setPosition(pawn7,
//				BoardLogic.getRandomTile(MetaClock.getMaxFraction(),false));
//
//		PieceLogic.setPosition(rook0,
//				BoardLogic.getRandomTile(MetaClock.getMaxFraction(),false));
//
//		PieceLogic.setPosition(rook1,
//				BoardLogic.getRandomTile(MetaClock.getMaxFraction(),false));
//
//		PieceLogic.setPosition(knight0,
//				BoardLogic.getRandomTile(MetaClock.getMaxFraction(),false));
//
//		PieceLogic.setPosition(knight1,
//				BoardLogic.getRandomTile(MetaClock.getMaxFraction(),false));
//
//		PieceLogic.setPosition(bischop0,
//				BoardLogic.getRandomTile(MetaClock.getMaxFraction(),false));
//
//		PieceLogic.setPosition(bischop1,
//				BoardLogic.getRandomTile(MetaClock.getMaxFraction(),false));
//
//		PieceLogic.setPosition(queen,
//				BoardLogic.getRandomTile(MetaClock.getMaxFraction(),false));
//
//		PieceLogic.setPosition(king,
//				BoardLogic.getRandomTile(MetaClock.getMaxFraction(),false));

		if(debug){
			PieceLogic
					.setPosition(king, BoardLogic.getRandomTile(
							MetaClock.getMaxFraction(), false));
			PieceLogic
					.setPosition(pawn0, BoardLogic.getRandomTile(
							MetaClock.getMaxFraction(), false));

			PieceLogic
					.setPosition(pawn1, BoardLogic.getRandomTile(
							MetaClock.getMaxFraction(), false));

			PieceLogic
					.setPosition(pawn2, BoardLogic.getRandomTile(
							MetaClock.getMaxFraction(), false));

			PieceLogic
					.setPosition(pawn3, BoardLogic.getRandomTile(
							MetaClock.getMaxFraction(), false));

			PieceLogic
					.setPosition(pawn4, BoardLogic.getRandomTile(
							MetaClock.getMaxFraction(), false));

			PieceLogic
					.setPosition(pawn5, BoardLogic.getRandomTile(MetaClock.getMaxFraction(),false));
		}
		
		// assign pawns randomly to player(s)
		ExtendedPlayerModel player = new ExtendedPlayerModel(1, king,
				"HARALD", MetaConfig.getIcon("HARALD"));
		MetaConfig.getBoardModel().setPlayer(player);

	}
}
