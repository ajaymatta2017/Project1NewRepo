
package eventable.pkgfor.students;

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
public class EventableForStudents extends Application {
    
    @Override
    public void start(Stage stage) throws IOException  {
        
        Parent root = FXMLLoader.load(getClass().getResource("Home.fxml"));
        
        stage.setResizable(false);
        Scene scene = new Scene(root);
        stage.setTitle("Eventable for Students");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
