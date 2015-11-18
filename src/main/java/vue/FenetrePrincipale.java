package vue;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class FenetrePrincipale extends Application{

	private RootLayout vueControleur;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/RootLayout.fxml"));
		Parent root = fxmlLoader.load();
		vueControleur = (RootLayout)fxmlLoader.getController();
        
		primaryStage.setTitle("OptimodLyon");
        primaryStage.setScene(new Scene(root, 1000, 668));
        primaryStage.setMinWidth(1000);
        primaryStage.setMinHeight(668);
        
        primaryStage.widthProperty().addListener(vueControleur.ecouteurDeRedimensionnement);
        primaryStage.heightProperty().addListener(vueControleur.ecouteurDeRedimensionnement);
        
        primaryStage.show();
	}
}
