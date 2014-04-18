package editor;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import meta.MetaMapping;
import meta.MetaMapping.ActionType;
import meta.MetaMapping.ControllerType;
import model.ExtendedPieceModel;

import org.lwjgl.input.Keyboard;

import action.MAAKeyDown;
import action.MARStraightDirectionX;
import action.MetaAction;
import action.MetaActionActivity;
import action.MetaActionRange;

//initial mapping of the controls, this can of course be edited with an editor
public class MetaAcionKeyAndPieceMappingEditor extends Editor {
	// initialise all basic MetaActions and map to keys
	public static void init() {
		// init range
		// range 1

		MetaActionActivity keyInput1 = new MAAKeyDown(Keyboard.KEY_NUMPAD1);
		initMetaAction(new ActionType[] { ActionType.RANGEPLUS1 },
				new ActionType[] { ActionType.RANGEMIN1 },

				keyInput1, null, 0, false, "RANGE1",0,0,0);

		// // range 2
		MetaActionActivity keyInput2 = new MAAKeyDown(Keyboard.KEY_NUMPAD2);
		initMetaAction(new ActionType[] { ActionType.RANGEPLUS2 },
				new ActionType[] { ActionType.RANGEMIN2 },

				keyInput2, null, 0, false, "RANGE2",0,0,0);

		// // range 4
		MetaActionActivity keyInput4 = new MAAKeyDown(Keyboard.KEY_NUMPAD4);
		initMetaAction(new ActionType[] { ActionType.RANGEPLUS4 },
				new ActionType[] { ActionType.RANGEMIN4 },

				keyInput4, null, 0, false, "RANGE4",0,0,0);

		// init orthog movement
		MetaActionActivity keyInputZ = new MAAKeyDown(Keyboard.KEY_Z);
		initMetaAction(new ActionType[] { ActionType.UP }, null, keyInputZ,
				null, 0, true, "UP",0,0,0);

		MetaActionActivity keyInputS = new MAAKeyDown(Keyboard.KEY_S);
		initMetaAction(new ActionType[] { ActionType.DOWN }, null, keyInputS,
				null, 0, true, "DOWN",0,0,0);

		MetaActionActivity keyInputQ = new MAAKeyDown(Keyboard.KEY_Q);
		initMetaAction(new ActionType[] { ActionType.LEFT }, null, keyInputQ,
				null, 0, true, "LEFT",0,0,0);

		MetaActionActivity keyInputD = new MAAKeyDown(Keyboard.KEY_D);
		initMetaAction(new ActionType[] { ActionType.RIGHT }, null, keyInputD,
				null, 0, true, "RIGHT",0,0,0);

		//
		// // init orthog movement
		// action = initMetaAction(new ActionType[] { ActionType.DOWNLEFT },
		// null,
		//
		// noCoolDown, null, 0, true, "DOWNLEFT");
		// MetaMapping.bindMetaActionToInput(Keyboard.KEY_W + "press", action);
		// MetaMapping.bindMetaActionToInput(Keyboard.KEY_W + "hold", action);
		//
		// action = initMetaAction(new ActionType[] { ActionType.UPLEFT }, null,
		//
		// noCoolDown, null, 0, true, "UPLEFT");
		// MetaMapping.bindMetaActionToInput(Keyboard.KEY_A + "press", action);
		// MetaMapping.bindMetaActionToInput(Keyboard.KEY_A + "hold", action);
		//
		// action = initMetaAction(new ActionType[] { ActionType.UPRIGHT },
		// null,
		//
		// noCoolDown, null, 0, true, "UPRIGHT");
		// MetaMapping.bindMetaActionToInput(Keyboard.KEY_E + "press", action);
		// MetaMapping.bindMetaActionToInput(Keyboard.KEY_E + "hold", action);
		//
		// action = initMetaAction(new ActionType[] { ActionType.DOWNRIGHT },
		// null,
		//
		// noCoolDown, null, 0, true, "DOWNRIGHT");
		// MetaMapping.bindMetaActionToInput(Keyboard.KEY_C + "press", action);
		// MetaMapping.bindMetaActionToInput(Keyboard.KEY_C + "hold", action);

		// decision tile view up, active for half the cooldown, not locking
		MetaActionRange mainRange = new MARStraightDirectionX();
		initMetaAction(new ActionType[] { ActionType.TILEVIEWUP },
				new ActionType[] { ActionType.TILEVIEWDOWN }, new MAAKeyDown(
						Keyboard.KEY_P), mainRange, 8, true, "TILEVIEW",2,1,0);
		// // decision to be able to pentrate lower tile fraction, active while
		// // pressed
		// MetaMapping.bindMetaActionToInput(
		// Keyboard.KEY_I + "press",
		// initMetaAction(new ActionType[] { ActionType.PENETRATELFTILE },
		// new ActionType[] { ActionType.NPENETRATELFTILE },
		// keyRealease, null, 0, false, "PENTRATELFTILE"));
		// // decision to double maxrange, active while on same parent tile as
		// when
		// // decided

		// initMetaAction(new ActionType[] { ActionType.PENETRATELFTILE },
		// new ActionType[] { ActionType.NPENETRATELFTILE },
		// new MAAHalfCooldownInput(Keyboard.KEY_B), parentTile, 0, false,
		// "PENETRATELFTILE");

		// // decsion to invert vert direction, active on 3 tiles in chosen
		// direction
		// //first switch range from movement to decision
		// //give a tilegraphic an extra list of metaactions
		// //on act of metaaction you add self to list of tiles in range
		// //on revert of metaaction you remove self from list of range
		// //on release of key the range is switched back
		// //TODO
		// MetaMapping.bindMetaActionToInput(
		// Keyboard.KEY_NUMPAD9 + "press",
		// initMetaAction(new ActionType[] { ActionType.PENETRATELFTILE },
		// new ActionType[] { ActionType.NPENETRATELFTILE },
		// noCoolDown, null, 0, false, "PENTRATELFTILE"));
		initPieceMapping();
	}

	// extended initialiser
	private static MetaAction initMetaAction(ActionType[] acttypes,
			ActionType[] reverttypes, MetaActionActivity activity,
			MetaActionRange range, int cooldown, boolean locking, String name, int turnsActive, int weight, int balance) {

		List<Method> acts = new ArrayList<>();
		List<Method> revertActs = new ArrayList<>();
		try {
			// add acts
			for (ActionType type : acttypes) {
				acts.add(Class.forName("logic.ActionLogic").getMethod(
						type.name(), new Class[] { ExtendedPieceModel.class }));
			}
			// add revert acts
			if (reverttypes != null)
				for (ActionType type : reverttypes) {
					revertActs.add(Class.forName("logic.ActionLogic")
							.getMethod(type.name(),
									new Class[] { ExtendedPieceModel.class }));
				}

		} catch (NoSuchMethodException | SecurityException
				| ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		MetaAction temp = new MetaAction(name, weight, balance, turnsActive,
				acts, revertActs,
				activity, range, locking);

		return temp;
	}

	private static void initPieceMapping() {
		ControllerType[] types;
		// variable range
		types = new ControllerType[] { ControllerType.INPUTROOK,
				ControllerType.INPUTBISCHOP, ControllerType.INPUTQUEEN };
		for (ControllerType controlType : types) {
			MetaMapping.getPieceMetaActions(controlType).add("RANGE1");
			MetaMapping.getPieceMetaActions(controlType).add("RANGE2");
			MetaMapping.getPieceMetaActions(controlType).add("RANGE4");

		}
		// orthogonal movement
		types = new ControllerType[] { ControllerType.INPUTROOK,
				ControllerType.INPUTQUEEN };
		for (ControllerType controlType : types) {
			MetaMapping.getPieceMetaActions(controlType).add("DOWN");
			MetaMapping.getPieceMetaActions(controlType).add("UP");
			MetaMapping.getPieceMetaActions(controlType).add("RIGHT");
			MetaMapping.getPieceMetaActions(controlType).add("LEFT");

		}

		// diagonal movement
		types = new ControllerType[] { ControllerType.INPUTBISCHOP,
				ControllerType.INPUTQUEEN };
		for (ControllerType controlType : types) {
			MetaMapping.getPieceMetaActions(controlType).add("UPLEFT");
			MetaMapping.getPieceMetaActions(controlType).add("UPRIGHT");
			MetaMapping.getPieceMetaActions(controlType).add("DOWNLEFT");
			MetaMapping.getPieceMetaActions(controlType).add("DOWNRIGHT");

		}

		// tile view change
		MetaMapping.getPieceMetaActions(ControllerType.INPUTROOK).add(
				"TILEVIEW");
		MetaMapping.getPieceMetaActions(ControllerType.INPUTROOK).add(
				"UPRANGERANGED");

	}
}
