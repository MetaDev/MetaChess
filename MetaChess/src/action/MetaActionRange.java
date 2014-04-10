package action;

import java.util.List;

import model.ExtendedPieceModel;
import model.ExtendedTileModel;

public interface MetaActionRange {
	//returns all tiles affected by metaAction
public List<ExtendedTileModel> getRange(ExtendedPieceModel model, MetaAction action);
}
