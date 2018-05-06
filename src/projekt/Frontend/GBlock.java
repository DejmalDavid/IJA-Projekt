package projekt.Frontend;

import java.awt.*;
import java.io.Serializable;
import java.util.Stack;


import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.control.Label;
import projekt.backend.Block;

/**
 * Created by Meh on 3. 5. 2018.
 */
public abstract class GBlock extends StackPane implements Serializable{

    Rectangle rect;

    public GBlock(){
        super();
        rect = new Rectangle(150,100);
        rect.setFill(Paint.valueOf("#aaaaaa"));
        this.getChildren().add(rect);
        this.setMaxSize(150.0, 100.0);
        this.setMinSize(150.0, 100.0);
        this.setLayoutX(200);
        this.setLayoutY(200);
        this.setOnMousePressed(BlockPressedHandler);
        this.setOnMouseDragged(BlockDraggedHandler);
        this.setOnMouseClicked(BlockClickedHandler);
        Main.blockList.add(this);
    }

    public Block see_matchblock(){
        return null;
    }


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
            for (GWire wire : Main.wireList){
                if (wire.matching_wire.start() == null && wire.matching_wire.end() == null){
                    Main.root.getChildren().remove(wire);
                }
            }
            Main.root.getChildren().remove(tmp);

        }
    };


}