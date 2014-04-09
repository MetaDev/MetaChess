package model;

import graphic.Graphic;
import logic.Logic;
import meta.MetaMapping.ControllerType;
import view.extended.Renderer;
import control.Controller;

public class ExtendedModel {
	
		protected Graphic graphic;
		protected ControllerType controllerType;



		public Graphic getGraphic() {
			return graphic;
		}


		public ExtendedModel(Graphic graphic,ControllerType controllerType) {
			this.graphic = graphic;
			this.controllerType = controllerType;
		}


		public ControllerType getControllerType() {
			return controllerType;
		}


		public void setControllerType(ControllerType controllerType) {
			this.controllerType = controllerType;
		}


		public void setGraphic(Graphic graphic) {
			this.graphic = graphic;
		}
}
