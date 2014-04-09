/*
 * This model contains model info as well as logic.
 * Because of the recursive nature of the floor it would be inefficient to
 * have to create 2 instances for every tile.
 */
package graphic;

import model.MetaModel;


public class TileGraphic extends Graphic {
	private TileGraphic[][] children;
	private TileGraphic parent;
	private int level;
	private int childFraction =1;
	private String ActiveMetaAction;
	public String getActiveMetaAction() {
		return ActiveMetaAction;
	}

	public void setActiveMetaAction(String activeMetaAction) {
		ActiveMetaAction = activeMetaAction;
	}

	// relative position in parent
	private int i;
	private int j;
	private PieceGraphic piece;

	public PieceGraphic getPiece() {
		return piece;
	}

	public void setPiece(PieceGraphic piece) {

		this.piece = piece;

	}

	public boolean isOccupied() {
		if (piece != null)
			return true;
		if (children==null)
			return false;
		for (TileGraphic[] childrow : children) {
			for (TileGraphic child : childrow) {
				if (child.isOccupied()) {
					return true;
				}
			}
		}
		return false;
	}

	public TileGraphic(float x, float y, int color, float width, int i, int j,
			int level, TileGraphic parent) {
		super(x, y, 0, width, width, color);
		this.level = level;
		this.parent = parent;
		this.i = i;
		this.j = j;
	}
	
	
	
	public int getI() {
		return i;
	}

	public int getJ() {
		return j;
	}


	public void undivide() {
		children = null;
	}

	public int absoluteFraction() {
		if (parent != null) {
			return parent.getChildFraction() * parent.absoluteFraction();

		} else {
			return 1;
		}

	}
	// don't allow empty (null) children
	public void divide(int fraction) {
		TileGraphic board = ((TileGraphic)MetaModel.getBoardModel().getGraphic());
		int absoluteFraction = absoluteFraction() * fraction;
		System.out.println(1 / absoluteFraction);
		childFraction = fraction;
		children = new TileGraphic[fraction][fraction];
		for (int i = 0; i < fraction; i++) {
			for (int j = 0; j < fraction; j++) {
				if (fraction % 2 == 1) {
					children[i][j] = new TileGraphic(x
							+ (float) (1 / absoluteFraction) * i
							* board.getWidth(), y
							+ (float) (1 / absoluteFraction) * j
							* board.getWidth(),
							((i + j) % 2 + color) % 2,
							(float) (1 / absoluteFraction)
									* board.getWidth(), i, j,
							level + 1, this);
				} else {
					children[i][j] = new TileGraphic(x
							+ ((float) 1 / absoluteFraction) * i
							* board.getWidth(), y
							+ ((float) 1 / absoluteFraction) * j
							* board.getWidth(), (i + j) % 2,
							((float) 1 / absoluteFraction)
									* board.getWidth(), i, j,
							level + 1, this);
				}
			}
		}
	}

	public int getChildFraction() {
		return childFraction;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public void removeChild(int i, int j) {
		children[i][j] = null;
	}

	public TileGraphic[][] getChildren() {
		return children;
	}

	public TileGraphic getParent() {
		return parent;
	}
}
