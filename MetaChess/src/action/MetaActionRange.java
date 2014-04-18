package action;

import java.util.Set;

import model.ExtendedPieceModel;
import model.ExtendedTileModel;

public interface MetaActionRange {
	//returns all tiles affected by metaAction
public Set<ExtendedTileModel> getRange(ExtendedPieceModel model, MetaAction action);
}
