public class Program {

	//This class' only purpose is to create the required objects and pass them to the controller to get the program running
	public static void main(String[] args) {
		view.Console ui = new view.Console();
		controller.MainController mc = new controller.MainController();
		model.Registry methods = new model.Registry();
		mc.start(ui, methods);
	}
}