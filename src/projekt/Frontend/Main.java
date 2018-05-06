package projekt.Frontend;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Duration;
import projekt.Frontend.GBlocks.*;
import projekt.backend.Block;
import projekt.backend.Port;
import projekt.backend.Schema;
import projekt.backend.Wire;

import java.io.*;
import java.util.*;



public class Main extends Application{

    public static ArrayList<GPort> portList;
    public static ArrayList<GWire> wireList;
    public static ArrayList<GBlock> blockList;
    public static AnchorPane root;
    public static Integer id;
    public static Schema plan1;
        @Override
        public void start(Stage primaryStage) {

            primaryStage.setTitle("IJA-Projekt");

            root = new AnchorPane();


            create_buttons(primaryStage);
            primaryStage.setScene(new Scene(root, 1600, 900));
            primaryStage.show();

            String name = "";
            TextInputDialog dialog = new TextInputDialog("Schema1");
            dialog.setTitle("Nazev schematu");
            dialog.setHeaderText("Nazev schematu");
            dialog.setContentText("Nazev schematu:");
            Optional<String> result = dialog.showAndWait();
            if (result.isPresent()) {
                name = result.get();
            }
            Label scheme_name = new Label(name);
            scheme_name.setFont(new Font("Serif", 32));
            root.getChildren().add(scheme_name);
            root.setTopAnchor(scheme_name, 10.0);
            root.setRightAnchor(scheme_name, 10.0);

            plan1 = new Schema(name);
            id = new Integer (0);
            blockList = new ArrayList<>();
            portList = new ArrayList<>();
            wireList = new ArrayList<>();
        }

    /**
     *
     * @param stage hlavni stage aplikace
     * @throws IOException pri chybe pri otevirani souboru vyhodi exception
     */
        public void save(Stage stage) throws IOException {
            try
            {
                FileChooser chooser = new FileChooser();
                chooser.setInitialDirectory(new File("."));
                File fil = chooser.showSaveDialog(stage);
                BufferedWriter file = new BufferedWriter(new FileWriter(fil, false));
                String output = "";

                for(GBlock block : blockList){
                    if(block instanceof GBlock_start){
                        output += "start ";
                        GBlock_start tmp = (GBlock_start) block;
                        output += Double.toString(tmp.start_hodnota) + " " + tmp.unit.toString() + " ";
                    }
                    else if(block instanceof GBlock_soucet){
                        output += "soucet ";
                    }
                    else if(block instanceof GBlock_rozdil){
                        output += "rozdil ";
                    }
                    else if(block instanceof GBlock_koule){
                        output += "koule ";
                    }
                    else if(block instanceof GBlock_kvadr){
                        output += "kvadr ";
                    }
                    else if(block instanceof GBlock_obdelnik){
                        output += "obdelnik ";
                    }
                    else if(block instanceof GBlock_podil){
                        output += "podil ";
                    }
                    else if(block instanceof GBlock_soucin){
                        output += "soucin ";
                    }
                    output += Double.toString(block.getTranslateX())+" "+Double.toString(block.getTranslateY()) + " " +
                           block.see_matchblock().see_name() + " " + Integer.toString(block.see_matchblock().see_poradi()) + "\n";

                }
                for (GWire wire : wireList){
                    output += "wire ";
                    output += wire.start.see_matchblock().see_name() + " ";
                    output += wire.end.see_matchblock().see_name() + " ";
                    output += wire.start_port.matching_port.see_nazev() + " ";
                    output += wire.end_port.matching_port.see_nazev() + " ";
                    output += Integer.toString(wire.start_port.port_id) + " ";
                    output += Integer.toString(wire.end_port.port_id) + "\n";
                }
                file.write(output);
                file.flush();
                file.close();
                System.out.println("Saved");

            }

            catch(IOException ex)
            {
                System.out.println("IOException is caught");
            }

        }

    /**
     * Vytvori nove schema (ve skutecnosti jen odstrani vsechny prvky a nastavi promenne do puvodniho stavu)
     */
    public void new_scheme(){

            for (GBlock block : blockList){
                plan1.delete_blok(block.see_matchblock().see_name());
                root.getChildren().remove(block);
            }

            for (GWire wire : wireList){
                root.getChildren().remove(wire);
            }
            blockList.clear();
            portList.clear();
            wireList.clear();
            GPort.port_number = 0;
            Main.id = 0;
        }

        public void load(Stage stage) throws IOException{
            try
            {
                FileChooser chooser = new FileChooser();
                chooser.setInitialDirectory(new File("."));
                File file = chooser.showOpenDialog(stage);
                FileInputStream fstream = new FileInputStream(file);
                BufferedReader br = new BufferedReader(new InputStreamReader(fstream));


                for (GBlock block : blockList){
                    plan1.delete_blok(block.see_matchblock().see_name());
                    root.getChildren().remove(block);
                }

                for (GWire wire : wireList){
                    root.getChildren().remove(wire);
                }
                blockList.clear();
                portList.clear();
                wireList.clear();
                GPort.port_number = 0;
                Main.id = 0;

                String line = null;
                while ((line = br.readLine()) != null) {
                    //System.out.println(line);
                    String[] split = line.split(" ");
                    if (split[0].equals("start")){
                        Port.hodnoty hodny = Port.hodnoty.Metr;
                        for (Port.hodnoty hod : Port.hodnoty.values()){
                            if (split[2].equals(hod.toString())){
                                hodny = hod;
                                break;
                            }
                        }
                        GBlock_start loaded_block = new GBlock_start(Double.parseDouble(split[3]), Double.parseDouble(split[4]),
                                Double.parseDouble(split[1]), hodny, Integer.parseInt(split[5]));
                        loaded_block.see_matchblock().set_poradi(Integer.parseInt(split[6]));
                        root.getChildren().add(loaded_block);
                    }
                    else if(split[0].equals("soucet")){
                        GBlock_soucet loaded_block = new GBlock_soucet(Double.parseDouble(split[1]),
                                Double.parseDouble(split[2]), Integer.parseInt(split[3]));
                        loaded_block.see_matchblock().set_poradi(Integer.parseInt(split[4]));
                        root.getChildren().add(loaded_block);
                    }
                    else if(split[0].equals("rozdil")){
                        GBlock_rozdil loaded_block = new GBlock_rozdil(Double.parseDouble(split[1]),
                                Double.parseDouble(split[2]), Integer.parseInt(split[3]));
                        loaded_block.see_matchblock().set_poradi(Integer.parseInt(split[4]));
                        root.getChildren().add(loaded_block);
                    }
                    else if(split[0].equals("koule")){
                        GBlock_koule loaded_block = new GBlock_koule(Double.parseDouble(split[1]),
                                Double.parseDouble(split[2]), Integer.parseInt(split[3]));
                        loaded_block.see_matchblock().set_poradi(Integer.parseInt(split[4]));
                        root.getChildren().add(loaded_block);
                    }
                    else if(split[0].equals("kvadr")){
                        GBlock_kvadr loaded_block = new GBlock_kvadr(Double.parseDouble(split[1]),
                                Double.parseDouble(split[2]), Integer.parseInt(split[3]));
                        loaded_block.see_matchblock().set_poradi(Integer.parseInt(split[4]));
                        root.getChildren().add(loaded_block);
                    }
                    else if(split[0].equals("obdelnik")){
                        GBlock_obdelnik loaded_block = new GBlock_obdelnik(Double.parseDouble(split[1]),
                                Double.parseDouble(split[2]), Integer.parseInt(split[3]));
                        loaded_block.see_matchblock().set_poradi(Integer.parseInt(split[4]));
                        root.getChildren().add(loaded_block);
                    }
                    else if(split[0].equals("podil")){
                        GBlock_podil loaded_block = new GBlock_podil(Double.parseDouble(split[1]),
                                Double.parseDouble(split[2]), Integer.parseInt(split[3]));
                        loaded_block.see_matchblock().set_poradi(Integer.parseInt(split[4]));
                        root.getChildren().add(loaded_block);
                    }
                    else if(split[0].equals("soucin")){
                        GBlock_soucin loaded_block = new GBlock_soucin(Double.parseDouble(split[1]),
                                Double.parseDouble(split[2]), Integer.parseInt(split[3]));
                        loaded_block.see_matchblock().set_poradi(Integer.parseInt(split[4]));
                        root.getChildren().add(loaded_block);
                    }
                }
                Main.plan1.poradnik_refresh();

                br.close();
                fstream.close();



                FileInputStream fstream2 = new FileInputStream(file);
                BufferedReader br2 = new BufferedReader(new InputStreamReader(fstream2));
                br2 = new BufferedReader(new InputStreamReader(fstream2));
                while ((line = br2.readLine()) != null) {
                    //System.out.println(line);
                    String[] split = line.split(" ");
                    if (split[0].equals("wire")) {

                        System.out.println(line);
                        plan1.add_propoj(split[1], split[3], split[2], split[4]);
                        GPort tmp1 = null;
                        GPort tmp2 = null;
                        //
                        for (GPort port : portList){
                            if(port.matching_port.see_nazev().equals(split[3])) {
                                GBlock block = (GBlock) port.getParent();
                                Block tmp = plan1.find_block(split[1]);
                                if (block.see_matchblock().see_name().equals(tmp.see_name())) {
                                    tmp1 = port;
                                    break;
                                }
                            }
                        }
                        for (GPort port : portList){
                            if(port.matching_port.see_nazev().equals(split[4])) {
                                GBlock block = (GBlock) port.getParent();
                                Block tmp = plan1.find_block(split[2]);
                                if (block.see_matchblock().see_name().equals(tmp.see_name())) {
                                    tmp2 = port;
                                    break;
                                }
                            }
                        }
                        //
                        /*for (GPort gport : Main.portList) {
                            if (gport.port_id == Integer.parseInt(split[5])) {
                                tmp1 = gport;
                            } else if (gport.port_id == Integer.parseInt(split[6])) {
                                tmp2 = gport;
                            }
                        }*/
                        GWire gwire = new GWire(tmp1, tmp2, plan1.find_wire(split[1] + "-" + split[2]));
                        root.getChildren().add(gwire);
                    }
                }



            }

            catch(IOException ex)
            {
                System.out.println("IOException is caught");
            }

        }

    /**
     * Vytvori vsechna potrebna ovladaci tlacitka
     * @param stage hlavni stage aplikace
     */
    public void create_buttons(Stage stage){
        HBox box = new HBox();

        Button btn0 = new Button();
        btn0.setText("Start");
        btn0.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                GBlock_start real_block = new GBlock_start(Main.id);
                root.getChildren().add(real_block);
            }
        });

        Button btn1 = new Button();
        btn1.setText("Soucet");
        btn1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                GBlock_soucet real_block = new GBlock_soucet(0,0, Main.id);
                root.getChildren().add(real_block);

            }
        });

        Button btn2 = new Button();
        btn2.setText("Rozdil");
        btn2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                GBlock_rozdil real_block = new GBlock_rozdil(0,0, Main.id);
                root.getChildren().add(real_block);
            }
        });

        Button btn3 = new Button();
        btn3.setText("Koule");
        btn3.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                GBlock_koule real_block = new GBlock_koule(0,0, Main.id);
                root.getChildren().add(real_block);
            }
        });

        Button btn4 = new Button();
        btn4.setText("Kvadr");
        btn4.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                GBlock_kvadr real_block = new GBlock_kvadr(0,0, Main.id);
                root.getChildren().add(real_block);
            }
        });

        Button btn5 = new Button();
        btn5.setText("Obdelnik");
        btn5.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                GBlock_obdelnik real_block = new GBlock_obdelnik(0,0, Main.id);
                root.getChildren().add(real_block);
            }
        });

        Button btn6 = new Button();
        btn6.setText("Podil");
        btn6.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                GBlock_podil real_block = new GBlock_podil(0,0, Main.id);
                root.getChildren().add(real_block);
            }
        });

        Button btn7 = new Button();
        btn7.setText("Soucin");
        btn7.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                GBlock_soucin real_block = new GBlock_soucin(0,0, Main.id);
                root.getChildren().add(real_block);
            }
        });

        Button btn8 = new Button();
        btn8.setText("Vypocet");
        btn8.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (Main.plan1.have_cykles()) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Chyba");
                    alert.setHeaderText("Cykly");
                    alert.setContentText("Ve schematu byly nalezeny smycky nebo jsou bloky nespravne serazene," +
                            " nelze spustit vypocet");
                    alert.showAndWait();
                }
                else if(Main.plan1.all_connect() != null) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Chyba");
                    alert.setHeaderText("Vstupni porty");
                    alert.setContentText("Ve schematu jsou nepripojene vstupni porty");
                    alert.showAndWait();
                }
                else {
                    boolean check = Main.plan1.full_demo();
                    if(!check){
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Chyba");
                        alert.setHeaderText("NaN");
                        alert.setContentText("Chyba ve vypoctu");
                        alert.showAndWait();
                    }
                    else {
                        String vysledky = "";
                        for (GBlock block : blockList) {
                            Port[] tmp = block.see_matchblock().return_end_ports();
                            for (Port port : tmp) {
                                if (!port.connected()) {
                                    vysledky += "Blok " + block.see_matchblock().see_name() + " : "
                                            + Double.toString(port.see_hodnota()) + "\n";
                                }
                            }
                        }
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Vysledky");
                        alert.setHeaderText("Vysledne hodnoty:");
                        alert.setContentText(vysledky);
                        alert.showAndWait();
                    }
                }
            }
        });

        Button btn9 = new Button();
        btn9.setText("Krok");
        btn9.setOnAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent event) {
                if(Main.plan1.have_cykles()){
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Chyba");
                    alert.setHeaderText("Cykly");
                    alert.setContentText("Ve schematu byl nalezen cyklus nebo jsou bloky nespravne serazene" +
                            " nelze spustit vypocet");
                    alert.showAndWait();
                }
                else if(Main.plan1.all_connect() != null) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Chyba");
                    alert.setHeaderText("Vstupni porty");
                    alert.setContentText("Ve schematu jsou nepripojene vstupni porty");
                    alert.showAndWait();
                }
                else{
                    int krok = Main.plan1.get_krok();
                    int check = Main.plan1.step_demo();
                    if(check == 1){
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Chyba");
                        alert.setHeaderText("NaN");
                        alert.setContentText("Chyba ve vypoctu");
                        alert.showAndWait();
                    }
                    else if (check == 2){
                        Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                        alert2.setTitle("Konec Vypoctu");
                        alert2.setHeaderText("Konec Vypoctu");
                        alert2.setContentText("Vypocet je u konce");
                        alert2.showAndWait();

                        String vysledky = "";
                        for (GBlock block : blockList) {
                            Port[] tmp = block.see_matchblock().return_end_ports();
                            for (Port port : tmp) {
                                if (!port.connected()) {
                                    vysledky += "Blok " + block.see_matchblock().see_name() + " : "
                                            + Double.toString(port.see_hodnota()) + "\n";
                                }
                            }
                        }
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Vysledky");
                        alert.setHeaderText("Vysledne hodnoty:");
                        alert.setContentText(vysledky);
                        alert.showAndWait();
                    }
                    else
                    {
                    for(GBlock block : blockList) {
                        if (block.see_matchblock().see_poradi() == krok) {
                            block.rect.setFill(Paint.valueOf("#add8e6"));

                        } else
                            block.rect.setFill(Paint.valueOf("aaaaaa"));
                    }
                    }
                }
            }
        });

        Button btn10 = new Button();
        btn10.setText("Save");
        btn10.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    save(stage);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });

        Button btn11 = new Button();
        btn11.setText("Load");
        btn11.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    load(stage);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });

        Button btn12 = new Button();
        btn12.setText("New");
        btn12.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                new_scheme();
            }
        });

        Button btn13 = new Button();
        btn13.setText("Reset");
        btn13.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Main.plan1.start_mode();
                for (GBlock block : blockList){
                    block.rect.setFill(Paint.valueOf("#aaaaaa"));
                }
            }
        });
        Button btn14 = new Button();
        btn14.setText("Swap");
        btn14.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String poradi = "Aktualni poradi:\n";
                for (GBlock block : blockList){
                    poradi += "blok:" + block.see_matchblock().see_name() + " - "
                            + "poradi:" + block.see_matchblock().see_poradi() + "\n";
                }

                TextInputDialog dialog = new TextInputDialog();
                dialog.setTitle("Swap");
                dialog.setHeaderText(poradi);
                dialog.setContentText("Vymena - ID prvniho bloku:");
                String ID1 = "";
                Optional<String> result = dialog.showAndWait();
                if (result.isPresent()) {
                    ID1 = result.get();
                }

                TextInputDialog dialog2 = new TextInputDialog();
                dialog2.setTitle("Swap");
                dialog2.setHeaderText(poradi);
                dialog2.setContentText("Vymena - ID druheho bloku:");
                String ID2 = "";
                Optional<String> result2 = dialog2.showAndWait();
                if (result2.isPresent()) {
                    ID2 = result2.get();
                }
                Block tmp1 = Main.plan1.find_block(ID1);
                Block tmp2 = Main.plan1.find_block(ID2);

                if (tmp1 != null && tmp2 != null) {
                    Main.plan1.swap_items(tmp1, tmp2);
                }
                else{
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Chyba");
                    alert.setHeaderText("Blok nenalezen");
                    alert.setContentText("Blok se zadanym ID nenalezen");
                    alert.showAndWait();
                }

            }
        });

        box.getChildren().addAll(btn0,btn1,btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn13, btn10, btn11, btn12, btn14);
        root.getChildren().add(box);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
