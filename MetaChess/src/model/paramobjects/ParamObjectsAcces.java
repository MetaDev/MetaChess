package model.paramobjects;

public class ParamObjectsAcces {
 private static ParamObject poView;
 private static ParamObject poMovement;
 public static ParamObject getPOView(){
	 if(poView==null)
		 poView=new POView();
	 return poView;
 }
 public static ParamObject getPOMovement(){
	 if(poMovement==null)
		 poMovement=new POMovement();
	 return poMovement;
 }
}
