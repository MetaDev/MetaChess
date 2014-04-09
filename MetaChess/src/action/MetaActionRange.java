package action;

import graphic.TileGraphic;

import java.util.List;

import model.PieceExtendedModel;

public interface MetaActionRange {
	//returns all tiles affected by metaAction
public List<TileGraphic> getRange(PieceExtendedModel model, MetaAction action);
}
