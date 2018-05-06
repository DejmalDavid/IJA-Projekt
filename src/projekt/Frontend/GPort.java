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
import projekt.backend.Port;
import projekt.backend.Wire;

import java.io.Serializable;

/**
 * Created by Meh on 3. 5. 2018.
 */

public class GPort extends Label{
    public boolean checked;
    Port matching_port;
    public static int port_number = 0;
    public int port_id;
    public GPort(String type, Port port){
        super(type);
        port_number += 1;
        port_id = port_number;
        matching_port = port;
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
            for (GPort port : Main.portList) {
                if (port.checked && !(port == tmp)) {

                    GBlock tmp1 = (GBlock) port.getParent();
                    GBlock tmp2 = (GBlock) tmp.getParent();
                    if(Main.plan1.add_propoj(tmp1.see_matchblock().see_name(),port.matching_port.see_nazev(),
                            tmp2.see_matchblock().see_name(), tmp.matching_port.see_nazev())) {
                        Wire match_wire = Main.plan1.find_wire(tmp1.see_matchblock().see_name()+"-"+tmp2.see_matchblock().see_name());
                        GWire wire = new GWire(port, tmp, match_wire);
                        AnchorPane st = (AnchorPane) (tmp.getParent().getParent());
                        st.getChildren().add(wire);
                    }
                        port.setUnchecked();
                        propojeno = true;


                    break;
                }

                if (port.checked && tmp.equals(port)){
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
