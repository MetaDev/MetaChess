package editor;

import java.util.ArrayList;
import java.util.List;

import engine.board.BoardLogic;
import meta.MetaConfig;
import engine.piece.ExtendedBishopModel;
import engine.piece.ExtendedKingModel;
import engine.piece.ExtendedKnightModel;
import engine.piece.ExtendedPawnModel;
import engine.piece.ExtendedPieceModel;
import engine.piece.ExtendedPieceModel.PieceType;
import engine.piece.ExtendedQueenModel;
import engine.piece.ExtendedRookModel;
import engine.player.PlayerDummy;
import engine.player.PlayerInput;

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
            piece.setTilePosition(BoardLogic.getRandomTile(false));
            MetaConfig.getBoardModel().getPlayersOnBoard().add(new PlayerDummy(piece.getColor(), piece, "test", "pawn"));
        }
    }

    public static void init() {
        //    debug();
        List<ExtendedPieceModel> list1 = initPieces(1);
        List<ExtendedPieceModel> list0 = initPieces(0);
        initPiecePosition(list1);
        initPiecePosition(list0);

        // assign pieces randomly to player(s)
      
        //the input players piece
        ExtendedPieceModel myPiece = MetaConfig
                .getBoardModel().getPieceByTypeAndSide(PieceType.king, 1);
        MetaConfig.getBoardModel().removePlayerByPiece(myPiece);
        PlayerInput player = new PlayerInput(1, myPiece,
                "HARALD",
                "harald");

        MetaConfig.getBoardModel().setInputPlayer(player);
        MetaConfig.getBoardModel().getPlayersOnBoard().add(player);
    }

    private static void debug() {
        ExtendedPieceModel rook = new ExtendedKingModel(1);

        List<ExtendedPieceModel> list = new ArrayList<>();
        // create all pieces
        for (int i = 0; i < 8; i++) {
            list.add(new ExtendedPawnModel(1));
        }
        initPiecePosition(list);
        PlayerInput player = new PlayerInput(1, rook,
                "HARALD", "harald");
        rook.setTilePosition(BoardLogic.getRandomTile(false));
        MetaConfig.getBoardModel().setInputPlayer(player);
        MetaConfig.getBoardModel().getPlayersOnBoard().add(player);

    }
}
