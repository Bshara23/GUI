package Controllers;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Controllers.Logic.FxmlNames;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.BlendMode;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/** Blend a coke can and a pepsi can to find the difference. */
public class PepsiChallenge extends Application implements Initializable{
    int index = 0;
    int size = BlendMode.values().length;
    private Scene scene;
    @FXML
    private Group gTT;
	@Override
    public void start(Stage stage) {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("gggg.fxml"));
		Parent root = null;
		try {
			root = loader.load();
		} catch (IOException e) {

			e.printStackTrace();
		}
		scene = new Scene(root);
		stage.setScene(scene);
		stage.setTitle("ICM System");
		stage.show();


		stage.setOnCloseRequest(new EventHandler<WindowEvent>() {

			@Override
			public void handle(WindowEvent event) {
				System.exit(1);
			}
		});
		

       
		
        
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		gTT.setBlendMode(BlendMode.DIFFERENCE);

        
        scene.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.A) {
                System.out.println("A key was pressed");
                
                index = (index + 1) % size;
                
                BlendMode bm = BlendMode.values()[index];
				
                gTT.setBlendMode(bm);

                
			}

            
            if (e.getCode() == KeyCode.D) {
                System.out.println("D key was pressed");
                
                index = (index + size - 1) % size;
                
                BlendMode bm = BlendMode.values()[index];

                gTT.setBlendMode(bm);

            }
        });
        		
	}
}