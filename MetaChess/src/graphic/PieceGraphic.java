package graphic;

import meta.MetaMapping.PieceRendererType;

//drawn relative to tile size the player is situated in



public class PieceGraphic extends Graphic {

	private TileGraphic tile;
	private PieceRendererType piece;
	public PieceGraphic(int color,
			TileGraphic tile, PieceRendererType piece) {
		super( 0, 0, 0, 0, 0, color);
		setTile(tile);
		this.piece=piece;

	}

	public PieceRendererType getPiece() {
		return piece;
	}

	public void setPiece(PieceRendererType piece) {
		this.piece = piece;
	}

	public void setTile(TileGraphic tile) {
		// remove yourself from previous tile
		if (this.tile != null)
			this.tile.setPiece(null);
		this.tile = tile;
		if (tile != null && tile.getPiece() != this) {
			tile.setPiece(this);
		}
	}

	public TileGraphic getTile() {
		return tile;
	}
	
	@Override
	public void setXY(float x, float y) {
		// do nothing
		// can't change x,y position; use tiles
	}

	@Override
	public float getY() {
		return tile.getY();
	}

	@Override
	public float getX() {
		return tile.getX();
	}

}
