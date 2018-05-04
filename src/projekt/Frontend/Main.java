package projekt.Frontend;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import projekt.Frontend.GBlocks.GBlock_plus_jedna;

import java.util.*;



public class Main extends Application {

    public static ArrayList<GPort> portList;
    public static ArrayList<GWire> wireList;
    public AnchorPane root;
        @Override
        public void start(Stage primaryStage) {
            primaryStage.setTitle("Hello World!");
            Button btn = new Button();
            btn.setText("Say 'Hello World'");
            root = new AnchorPane();
            btn.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    Node node = (Node) event.getSource();
                    GBlock_plus_jedna real_block = new GBlock_plus_jedna(node);
                    root.getChildren().add(real_block);

                }
            });
            portList = new ArrayList<>();
            wireList = new ArrayList<>();
            root.getChildren().add(btn);
            primaryStage.setScene(new Scene(root, 1600, 900));
            primaryStage.show();
        }


    public static void main(String[] args) {
        launch(args);
    }
}
