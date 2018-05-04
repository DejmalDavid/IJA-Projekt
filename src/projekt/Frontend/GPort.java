package projekt.Frontend;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import javafx.event.EventHandler;
import javafx.scene.shape.Line;

/**
 * Created by Meh on 3. 5. 2018.
 */

public class GPort extends Label {
    public boolean checked;
    public GPort(String type){
        super(type);
        checked = false;
        this.setStyle("-fx-background-color: yellow; -fx-padding: 2px;");
        this.setOnMouseClicked(ClickHandler);
        Main.portList.add(this);
    }

    public void setChecked(){
        this.checked = true;
        this.setStyle("-fx-background-color: red; -fx-padding: 2px;");
    }

    public void setUnchecked(){
        this.checked = false;
        this.setStyle("-fx-background-color: yellow; -fx-padding: 2px;");
    }



    EventHandler<MouseEvent> ClickHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            boolean propojeno = false;
            GPort tmp = (GPort) event.getSource();
            for (GPort port : Main.portList){
                if (port.checked && !(port == tmp)){

                    GWire wire = new GWire(port, tmp);
                    AnchorPane st = (AnchorPane)(tmp.getParent().getParent());
                    st.getChildren().add(wire);

                    port.setUnchecked();
                    propojeno = true;
                    break;
                }
                if(port.checked && tmp.equals(port)){
                    tmp.setUnchecked();
                    propojeno = true;
                }
            }
            if(!propojeno){
                tmp.setChecked();
            }

        }
    };
}
