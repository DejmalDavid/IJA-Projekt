package projekt.Frontend;

import java.awt.*;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Stack;


import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.control.Label;
import projekt.backend.Block;

/**
 * Abstraktni trida pro obecny blok
 * @author Michal Beranek(xberan38)
 */
public abstract class GBlock extends StackPane implements Serializable{

    Rectangle rect;
    Label gblock_id;

    /**
     * Konstruktor
     * @param id id bloku
     */
    public GBlock(int id){
        super();
        rect = new Rectangle(150,100);
        rect.setFill(Paint.valueOf("#aaaaaa"));
        gblock_id = new Label ("ID: " + Integer.toString(id));
        this.getChildren().add(rect);
        this.getChildren().add(gblock_id);
        this.setMaxSize(150.0, 100.0);
        this.setMinSize(150.0, 100.0);
        this.setLayoutX(200);
        this.setLayoutY(200);
        this.setOnMousePressed(BlockPressedHandler);
        this.setOnMouseDragged(BlockDraggedHandler);
        this.setOnMouseClicked(BlockClickedHandler);
        Main.blockList.add(this);
    }

    /**
     * Abstraktni funkce pro vraceni odpovidajiciho bloku ve schematu
     * @return vraci odpovidajici blok
     */
    public abstract Block see_matchblock();


    double orgSceneX, orgSceneY;
    double orgTranslateX, orgTranslateY;

    EventHandler<MouseEvent> BlockPressedHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent t) {
            orgSceneX = t.getSceneX();
            orgSceneY = t.getSceneY();
            orgTranslateX = ((GBlock)(t.getSource())).getTranslateX();
            orgTranslateY = ((GBlock)(t.getSource())).getTranslateY();
        }

    };

    EventHandler<MouseEvent> BlockDraggedHandler = new EventHandler<MouseEvent>() {

        @Override
        public void handle(MouseEvent t) {
            double offsetX = t.getSceneX() - orgSceneX;
            double offsetY = t.getSceneY() - orgSceneY;
            double newTranslateX = orgTranslateX + offsetX;
            double newTranslateY = orgTranslateY + offsetY;

            ((GBlock)(t.getSource())).setTranslateX(newTranslateX);
            ((GBlock)(t.getSource())).setTranslateY(newTranslateY);
            for (GWire wire : Main.wireList){
                wire.actualize_pos();
            }

        }
    };

    EventHandler<MouseEvent> BlockClickedHandler = new EventHandler<MouseEvent>() {

        @Override
        public void handle(MouseEvent t) {
            if (!t.isAltDown()){
                return;
            }
            GBlock tmp = (GBlock) t.getSource();
            Main.plan1.delete_blok(tmp.see_matchblock().see_name());
            for (Iterator<GWire> iterator = Main.wireList.iterator();iterator.hasNext();){
                GWire wire = iterator.next();
                if (wire.matching_wire.start() == null || wire.matching_wire.end() == null){
                    Main.root.getChildren().remove(wire);
                    iterator.remove();
                }
            }

            Main.root.getChildren().remove(tmp);
            Main.blockList.remove(tmp);
            Main.plan1.poradi_refresh();
            /*for (GPort port : Main.portList){
                if (port.getParent() == null){
                    Main.portList.remove(port);
                }
            }*/

        }
    };


}