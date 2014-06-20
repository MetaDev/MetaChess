package editor;

import logic.BoardLogic;
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
		int[] I = new int[] { 0 };
		int[] J = new int[] { 1 };
		PieceLogic.setPosition(pawn0, I, J);
		I = new int[] { 1 };
		J = new int[] { 1 };
		PieceLogic.setPosition(pawn1, I, J);
		I = new int[] { 2 };
		J = new int[] { 1 };
		PieceLogic.setPosition(pawn2, I, J);
		I = new int[] { 3 };
		J = new int[] { 1 };
		PieceLogic.setPosition(pawn3, I, J);
		I = new int[] { 4 };
		J = new int[] { 1 };
		PieceLogic.setPosition(pawn4, I, J);
		I = new int[] { 5 };
		J = new int[] { 1 };
		PieceLogic.setPosition(pawn5, I, J);
		I = new int[] { 6 };
		J = new int[] { 1 };
		PieceLogic.setPosition(pawn6, I, J);
		I = new int[] { 7 };
		J = new int[] { 1 };
		PieceLogic.setPosition(pawn7, I, J);
		
		I = new int[] { 0 };
		J = new int[] { 0 };
		PieceLogic.setPosition(rook0, I, J);
		I = new int[] { 7 };
		J = new int[] { 0 };
		PieceLogic.setPosition(rook1, I, J);
		
		I = new int[] { 1 };
		J = new int[] { 0 };
		PieceLogic.setPosition(knight0, I, J);
		I = new int[] { 6 };
		J = new int[] { 0 };
		PieceLogic.setPosition(knight1, I, J);
		
		I = new int[] { 2 };
		J = new int[] { 0 };
		PieceLogic.setPosition(bischop0, I, J);
		I = new int[] { 5 };
		J = new int[] { 0 };
		PieceLogic.setPosition(bischop1, I, J);
		
		I = new int[] { 3 };
		J = new int[] { 0 };
		PieceLogic.setPosition(queen, I, J);
		I = new int[] { 4 };
		J = new int[] { 0 };
		PieceLogic.setPosition(king, I, J);
		
		
		// assign pawns randomly to player(s)
		ExtendedPlayerModel player = new ExtendedPlayerModel(1, pawn1, "test");
		MetaConfig.getBoardModel().setPlayer(player);

	}
}
