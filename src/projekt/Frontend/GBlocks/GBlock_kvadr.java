package projekt.Frontend.GBlocks;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import projekt.Frontend.GBlock;
import projekt.Frontend.GPort;
import projekt.Frontend.Main;
import projekt.backend.Block;
import projekt.backend.Schema;
import projekt.backend.bloky.Block_soucet;
import projekt.backend.Port.hodnoty;

import java.io.Serializable;

/**
 * Created by Meh on 2. 5. 2018.
 */
public class GBlock_kvadr extends GBlock{

    GPort port_out1, port_out2, port_out3, port_in1;
    Label op;
    Block block;


    public GBlock_kvadr(double transx, double transy){
        super();
        Main.id += 1;
        if(Main.plan1.add_block(Main.id.toString(), "kvadr", hodnoty.Metr)) {
            block = Main.plan1.find_block(Main.id.toString());
            op = new Label("kvadr");
            port_out1 = new GPort("Metr", block.return_end_ports()[0]);
            port_in1 = new GPort("Metr", block.return_start_ports()[0]);
            port_out2 = new GPort("Metr^2", block.return_end_ports()[1]);
            port_out3 = new GPort("Metr^3", block.return_end_ports()[2]);
            this.getChildren().addAll(op, port_out1, port_in1, port_out2, port_out3);
            GBlock_kvadr.setAlignment(port_out1, Pos.CENTER_RIGHT);
            GBlock_kvadr.setAlignment(port_in1, Pos.CENTER_LEFT);
            GBlock_kvadr.setAlignment(port_out2, Pos.BOTTOM_RIGHT);
            GBlock_kvadr.setAlignment(port_out3, Pos.TOP_RIGHT);
            GBlock_kvadr.setAlignment(op, Pos.TOP_CENTER);
        }
        this.setTranslateX(transx);
        this.setTranslateY(transy);
    }
    @Override
    public Block see_matchblock(){
        return block;
    }
}
