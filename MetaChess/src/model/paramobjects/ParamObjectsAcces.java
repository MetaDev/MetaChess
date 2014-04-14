package model.paramobjects;

public class ParamObjectsAcces {
 private static ParamObject poview;
 public static ParamObject getPOView(){
	 if(poview==null)
		 poview=new POView();
	 return poview;
 }
}
