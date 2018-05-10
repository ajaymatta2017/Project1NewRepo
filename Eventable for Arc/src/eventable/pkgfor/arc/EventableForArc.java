
package eventable.pkgfor.arc;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author ehopk
 */
public class EventableForArc extends Application {
    
    @Override
    public void start(Stage stage) throws IOException  {
        
        Parent root = FXMLLoader.load(getClass().getResource("ARCSocietyHome.fxml"));
        
        stage.setResizable(false);
        Scene scene = new Scene(root);
        stage.setTitle("Eventable for Arc");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}
