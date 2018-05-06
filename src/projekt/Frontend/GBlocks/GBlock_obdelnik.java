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
 * Graficke zobrazeni bloku obdelnik
 * @author Michal Beranek(xberan38)
 */
public class GBlock_obdelnik extends GBlock{

    GPort port_out1, port_out2, port_in1, port_in2;
    Label op;
    Block block;

    /**
     * Konstruktor
     * @param transx x-ove posunuti bloku
     * @param transy y-ove posunuti bloku
     * @param id id bloku
     */
    public GBlock_obdelnik(double transx, double transy, int id){
        super(id);
        Main.id += 1;
        if(Main.plan1.add_block(Integer.toString(id), "obdelnik", hodnoty.Metr)) {
            block = Main.plan1.find_block(Integer.toString(id));
            op = new Label("obdelnik");
            port_out1 = new GPort("Metr", block.return_end_ports()[0]);
            port_out2 = new GPort("Metr^2", block.return_end_ports()[1]);
            port_in1 = new GPort("Metr", block.return_start_ports()[0]);
            port_in2 = new GPort("Metr", block.return_start_ports()[1]);
            this.getChildren().addAll(op, port_out1, port_out2, port_in1, port_in2);
            GBlock_obdelnik.setAlignment(port_out1, Pos.CENTER_RIGHT);
            GBlock_obdelnik.setAlignment(port_out2, Pos.BOTTOM_RIGHT);
            GBlock_obdelnik.setAlignment(port_in1, Pos.CENTER_LEFT);
            GBlock_obdelnik.setAlignment(port_in2, Pos.BOTTOM_LEFT);
            GBlock_obdelnik.setAlignment(op, Pos.TOP_CENTER);
        }
        this.setTranslateX(transx);
        this.setTranslateY(transy);
    }
    @Override
    public Block see_matchblock(){
        return block;
    }
}
