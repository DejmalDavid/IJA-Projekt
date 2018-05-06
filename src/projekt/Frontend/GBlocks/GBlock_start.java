package projekt.Frontend.GBlocks;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import projekt.Frontend.GBlock;
import projekt.Frontend.GPort;
import projekt.Frontend.Main;
import projekt.backend.Block;
import projekt.backend.Schema;
import projekt.backend.bloky.Block_soucet;
import projekt.backend.Port.hodnoty;

import java.io.Serializable;
import java.util.Optional;

/**
 * Created by Meh on 2. 5. 2018.
 */
public class GBlock_start extends GBlock{

    GPort port_out1;
    Label op, value;
    Block block;
    public double start_hodnota;
    public hodnoty unit;

    public GBlock_start(int id) {
        super(id);
        Main.id += 1;
        double hodnota = 0;


        /*User input*/
        TextInputDialog dialog = new TextInputDialog("10");
        dialog.setTitle("Hodnota startu");
        dialog.setHeaderText("Nastaveni hodnoty");
        dialog.setContentText("Nastavte vystupni hodnotu startovaciho bloku");
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            hodnota = Double.parseDouble(result.get());
        }
        ChoiceDialog<hodnoty> choice = new ChoiceDialog<>(hodnoty.Metr, hodnoty.Metr, hodnoty.Metr_2,
                hodnoty.Metr_3);
        choice.setTitle("Jednotky");
        choice.setHeaderText("Vyberte jednotku:");
        choice.setContentText("Jednotka:");

        hodnoty jednotka = hodnoty.Metr_2;
        Optional<hodnoty> result2 = choice.showAndWait();
        if (result2.isPresent()){
            jednotka = result2.get();
        }


        start_hodnota = hodnota;
        unit = jednotka;
        if(Main.plan1.add_start_block(Integer.toString(id), hodnota, jednotka)) {


            block = Main.plan1.find_block(Integer.toString(id));
            op = new Label("Start");
            value = new Label(String.valueOf(hodnota));
            port_out1 = new GPort(jednotka.toString(), block.return_end_ports()[0]);
            this.getChildren().addAll(op, port_out1, value);
            GBlock_start.setAlignment(port_out1, Pos.CENTER_RIGHT);
            GBlock_start.setAlignment(value, Pos.CENTER_LEFT);
            GBlock_start.setAlignment(op, Pos.TOP_CENTER);
        }


        /*Following part is same for all blocks*/
    }

    public GBlock_start(double transx, double transy, double start_value, hodnoty start_unit, int id){
        super(id);
        Main.id += 1;
        start_hodnota = start_value;
        unit = start_unit;
        if(Main.plan1.add_start_block(Integer.toString(id), start_value, start_unit)) {

            block = Main.plan1.find_block(Integer.toString(id));
            op = new Label("Start");
            value = new Label(String.valueOf(start_value));
            port_out1 = new GPort(start_unit.toString(), block.return_end_ports()[0]);
            this.getChildren().addAll(op, port_out1, value);
            GBlock_start.setAlignment(port_out1, Pos.CENTER_RIGHT);
            GBlock_start.setAlignment(value, Pos.CENTER_LEFT);
            GBlock_start.setAlignment(op, Pos.TOP_CENTER);
            this.setTranslateX(transx);
            this.setTranslateY(transy);
        }
    }
    @Override
    public Block see_matchblock(){
        return block;
    }
}
