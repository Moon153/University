package ar1;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
	
	private static Scene scene; 

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(Main.class.getResource("loginView.fxml"));
        scene = new Scene (root, 550, 400);
        primaryStage.setTitle("Sistema di Voto e Scrutinio elettronico");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void showView(String view, Object user, int parameter){
    	try {
			FXMLLoader loader = new FXMLLoader(Main.class.getResource(view + ".fxml"));
			Parent p = loader.load();
			Controller controller = loader.getController();
			if(user != null) {
				if(parameter != 0)
					controller.setParameters(user, parameter);
				else
					controller.setParameters(user);
			}
			controller.init();
			scene.setRoot(p);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
