package projekt.Frontend;

import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Line;
import projekt.Frontend.Main;
import projekt.backend.Wire;

import java.io.Serializable;

/**
 * Created by Meh on 3. 5. 2018.
 */
public class GWire extends Line{

    //double startx, starty, endx, endy;
    GBlock start, end;
    double startx, starty, endx, endy;
    double stransx, stransy, etransx, etransy;
    GPort start_port, end_port;
    Wire matching_wire;
    public Tooltip t;
    public GWire (GPort port_start, GPort port_end, Wire wire_match) {
        super();
        matching_wire = wire_match;

        Bounds bounds_start = port_start.localToScene(port_start.getBoundsInLocal());
        Bounds bounds_end = port_end.localToScene(port_end.getBoundsInLocal());
        this.setStartX(bounds_start.getMaxX());
        this.setStartY(bounds_start.getMaxY());
        this.setEndX(bounds_end.getMinX());
        this.setEndY(bounds_end.getMaxY());
        this.setStrokeWidth(3);
        end = (GBlock) port_end.getParent();
        start = (GBlock) port_start.getParent();
        start_port = port_start;
        end_port = port_end;

        stransx = start.getTranslateX();
        stransy = start.getTranslateY();
        etransx = end.getTranslateX();
        etransy = end.getTranslateY();

        startx = bounds_start.getMaxX();
        starty = bounds_start.getMaxY();
        endx = bounds_end.getMinX();
        endy = bounds_end.getMaxY();

        this.setOnMouseClicked(WireClickedHandler);
        t = new Tooltip(Double.toString(start_port.matching_port.see_hodnota()));
        Tooltip.install(this, t);
        this.setOnMouseEntered(WireEnteredHandler);
        Main.wireList.add(this);
    }

    public void actualize_pos(){
        double start_posx = startx - stransx + start.getTranslateX();
        double start_posy = starty - stransy + start.getTranslateY();
        double end_posx = endx - etransx + end.getTranslateX();
        double end_posy = endy - etransy + end.getTranslateY();
        this.setStartX(start_posx);
        this.setStartY(start_posy);
        this.setEndX(end_posx);
        this.setEndY(end_posy);
    }

    EventHandler<MouseEvent> WireClickedHandler = new EventHandler<MouseEvent>() {

        @Override
        public void handle(MouseEvent t) {
            if (!t.isAltDown()){
                return;
            }
            GWire tmp = (GWire) t.getSource();
            Main.plan1.delete_propoj(tmp.matching_wire.see_name());
            Main.root.getChildren().remove(tmp);
        }
    };

    EventHandler<MouseEvent> WireEnteredHandler = new EventHandler<MouseEvent>() {

        @Override
        public void handle(MouseEvent t) {
            GWire tmp = (GWire) t.getSource();
            tmp.t.setText(Double.toString(tmp.start_port.matching_port.see_hodnota()));
        }
    };


}

