package projekt.Frontend.GBlocks;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import projekt.Frontend.Concrete_block;
import projekt.Frontend.GBlock;
import projekt.Frontend.GPort;

/**
 * Created by Meh on 2. 5. 2018.
 */
public class GBlock_plus_jedna extends GBlock implements Concrete_block {

    GPort port1, port2;
    Label op;

    public GBlock_plus_jedna(final Node node) {
        super(node);
        op = new Label("+1");
        port1 = new GPort("odpor");
        port2 = new GPort("odpor");
        this.getChildren().addAll(op, port1, port2);
        this.setAlignment(port1, Pos.CENTER_RIGHT);
        this.setAlignment(port2, Pos.CENTER_LEFT);
        this.setAlignment(op, Pos.TOP_CENTER);


        /*Following part is same for all blocks*/
    }
}
