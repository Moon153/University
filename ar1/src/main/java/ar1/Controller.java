package ar1;

public abstract class Controller {

	public void changeView(String view, Object user){
		Main.showView(view, user, 0);
	}
	
	public void changeView(String view, Object parameter1, int parameter2){
		Main.showView(view, parameter1, parameter2);
	}
	
	public void setParameters(Object user) {
	}

	public void init() {
		
	}

	public void setParameters(Object user, int parameter) {
	}
}
