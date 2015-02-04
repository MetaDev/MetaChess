package editor;

import java.util.ArrayList;
import java.util.List;

import logic.BoardLogic;
import meta.MetaClock;
import meta.MetaConfig;
import meta.MetaConfig.PieceType;
import model.ExtendedBishopModel;
import model.ExtendedKingModel;
import model.ExtendedKnightModel;
import model.ExtendedPawnModel;
import model.ExtendedPieceModel;
import model.ExtendedQueenModel;
import model.ExtendedRookModel;
import model.PlayerEmptyModel;
import model.PlayerInputModel;

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
            piece.setTilePosition(BoardLogic.getRandomTile(MetaClock.getMaxFraction(), false));
            MetaConfig.getBoardModel().getPlayersOnBoard().add(new PlayerEmptyModel(piece.getSide(), piece, "test", MetaConfig.getIcon("PAWN")));
        }
    }

    public static void init() {
        //debug();
        List<ExtendedPieceModel> list1 = initPieces(1);
        List<ExtendedPieceModel> list0 = initPieces(0);
        initPiecePosition(list1);
        initPiecePosition(list0);

        // assign pieces randomly to player(s)
        // PlayerModel player = new PlayerModel(1,
        // list1.get(MetaUtil.randInt(0, 3)), "HARALD",
        // MetaConfig.getIcon("HARALD"));
        PlayerInputModel player = new PlayerInputModel(1, MetaConfig
                .getBoardModel().getPieceByTypeAndSide(PieceType.ROOK, 0),
                "HARALD", MetaConfig.getIcon("HARALD"));
        MetaConfig.getBoardModel().setInputPlayer(player);
        MetaConfig.getBoardModel().getPlayersOnBoard().add(player);
    }

    private static void debug() {
        ExtendedRookModel rook = new ExtendedRookModel(1);
//        PieceLogic
//                .setPosition(rook, BoardLogic.getTile(new int[]{5}, new int[]{5}));
//        ExtendedRookModel rook1 = new ExtendedRookModel(1);
//        PieceLogic
//                .setPosition(rook1, BoardLogic.getTile(new int[]{0}, new int[]{0}));
        PlayerInputModel player = new PlayerInputModel(1, rook,
                "HARALD", MetaConfig.getIcon("HARALD"));
        MetaConfig.getBoardModel().setInputPlayer(player);

    }
}
