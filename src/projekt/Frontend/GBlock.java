package projekt.Frontend;

import java.awt.*;
import java.util.Stack;


import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.control.Label;

/**
 * Created by Meh on 3. 5. 2018.
 */
public abstract class GBlock extends StackPane {

    Rectangle rect;

    public GBlock(final Node node){
        super();
        rect = new Rectangle(100,100);
        rect.setFill(Paint.valueOf("#aaaaaa"));
        this.getChildren().add(rect);
        this.setMaxSize(100.0, 100.0);
        this.setMinSize(100.0, 100.0);
        this.setLayoutX(200);
        this.setLayoutY(200);
        this.setOnMousePressed(BlockPressedHandler);
        this.setOnMouseDragged(BlockDraggedHandler);
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
            GBlock moved = (GBlock) t.getSource(); //for testing
            for (GWire wire : Main.wireList){
                wire.actualize_pos();
            }

        }
    };

}