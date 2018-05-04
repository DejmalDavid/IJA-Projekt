package projekt.Frontend;

import javafx.geometry.Bounds;
import javafx.scene.shape.Line;
import projekt.Frontend.Main;

/**
 * Created by Meh on 3. 5. 2018.
 */
public class GWire extends Line {

    //double startx, starty, endx, endy;
    GBlock start, end;
    double startx, starty, endx, endy;
    double stransx, stransy, etransx, etransy;
    public GWire (GPort port_start, GPort port_end){
        super();
        Bounds bounds_start = port_start.localToScene(port_start.getBoundsInLocal());
        Bounds bounds_end = port_end.localToScene(port_start.getBoundsInLocal());
        this.setStartX(bounds_start.getMaxX());
        this.setStartY(bounds_start.getMaxY());
        this.setEndX(bounds_end.getMinX());
        this.setEndY(bounds_end.getMaxY());
        this.end = (GBlock) port_end.getParent();
        this.start = (GBlock) port_start.getParent();

        stransx = start.getTranslateX();
        stransy = start.getTranslateY();
        etransx = end.getTranslateX();
        etransy = end.getTranslateY();

        startx = bounds_start.getMaxX();
        starty = bounds_start.getMaxY();
        endx = bounds_end.getMinX();
        endy = bounds_end.getMaxY();


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
}
