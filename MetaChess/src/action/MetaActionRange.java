package action;

import java.util.List;

import userinterface.TileGraphic;
import model.ExtendedPieceModel;

public interface MetaActionRange {
	//returns all tiles affected by metaAction
public List<TileGraphic> getRange(ExtendedPieceModel model, MetaAction action);
}
