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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
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
            plan1 = new Schema("jmeno");
            id = new Integer (0);
            primaryStage.setTitle("IJA-Projekt");

            root = new AnchorPane();

            blockList = new ArrayList<>();
            portList = new ArrayList<>();
            wireList = new ArrayList<>();
            create_buttons(primaryStage);
            primaryStage.setScene(new Scene(root, 1600, 900));
            primaryStage.show();
        }

        public void save(Stage stage) throws IOException {
            try
            {
                FileChooser chooser = new FileChooser();
                File fil = chooser.showSaveDialog(stage);
                BufferedWriter file = new BufferedWriter(new FileWriter(fil));
                String output = "";
                // Method for serialization of object
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
                    output += Double.toString(block.getTranslateX())+" "+Double.toString(block.getTranslateY()) + "\n";

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

        public void new_scheme(){
            for (int i = 1; i <= Main.id; i++){
                plan1.delete_blok(Integer.toString(i));
            }

            for (GBlock block : blockList){
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
                File file = chooser.showOpenDialog(stage);
                FileInputStream fstream = new FileInputStream(file);
                BufferedReader br = new BufferedReader(new InputStreamReader(fstream));


                for (int i = 1; i <= Main.id; i++){
                    plan1.delete_blok(Integer.toString(i));
                }

                for (GBlock block : blockList){
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
                                Double.parseDouble(split[1]), hodny);
                        root.getChildren().add(loaded_block);
                    }
                    else if(split[0].equals("soucet")){
                        GBlock_soucet loaded_block = new GBlock_soucet(Double.parseDouble(split[1]),
                                Double.parseDouble(split[2]));
                        root.getChildren().add(loaded_block);
                    }
                    else if(split[0].equals("rozdil")){
                        GBlock_rozdil loaded_block = new GBlock_rozdil(Double.parseDouble(split[1]),
                                Double.parseDouble(split[2]));
                        root.getChildren().add(loaded_block);
                    }
                    else if(split[0].equals("koule")){
                        GBlock_koule loaded_block = new GBlock_koule(Double.parseDouble(split[1]),
                                Double.parseDouble(split[2]));
                        root.getChildren().add(loaded_block);
                    }
                    else if(split[0].equals("kvadr")){
                        GBlock_kvadr loaded_block = new GBlock_kvadr(Double.parseDouble(split[1]),
                                Double.parseDouble(split[2]));
                        root.getChildren().add(loaded_block);
                    }
                    else if(split[0].equals("obdelnik")){
                        GBlock_obdelnik loaded_block = new GBlock_obdelnik(Double.parseDouble(split[1]),
                                Double.parseDouble(split[2]));
                        root.getChildren().add(loaded_block);
                    }
                    else if(split[0].equals("podil")){
                        GBlock_podil loaded_block = new GBlock_podil(Double.parseDouble(split[1]),
                                Double.parseDouble(split[2]));
                        root.getChildren().add(loaded_block);
                    }
                    else if(split[0].equals("soucin")){
                        GBlock_soucin loaded_block = new GBlock_soucin(Double.parseDouble(split[1]),
                                Double.parseDouble(split[2]));
                        root.getChildren().add(loaded_block);
                    }
                }

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
                        for (GPort gport : Main.portList) {
                            if (gport.port_id == Integer.parseInt(split[5])) {
                                tmp1 = gport;
                            } else if (gport.port_id == Integer.parseInt(split[6])) {
                                tmp2 = gport;
                            }
                        }
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

    public void create_buttons(Stage stage){
        HBox box = new HBox();

        Button btn0 = new Button();
        btn0.setText("Start");
        btn0.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                GBlock_start real_block = new GBlock_start();
                root.getChildren().add(real_block);
            }
        });

        Button btn1 = new Button();
        btn1.setText("Soucet");
        btn1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                GBlock_soucet real_block = new GBlock_soucet(0,0);
                root.getChildren().add(real_block);

            }
        });

        Button btn2 = new Button();
        btn2.setText("Rozdil");
        btn2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                GBlock_rozdil real_block = new GBlock_rozdil(0,0);
                root.getChildren().add(real_block);
            }
        });

        Button btn3 = new Button();
        btn3.setText("Koule");
        btn3.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                GBlock_koule real_block = new GBlock_koule(0,0);
                root.getChildren().add(real_block);
            }
        });

        Button btn4 = new Button();
        btn4.setText("Kvadr");
        btn4.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                GBlock_kvadr real_block = new GBlock_kvadr(0,0);
                root.getChildren().add(real_block);
            }
        });

        Button btn5 = new Button();
        btn5.setText("Obdelnik");
        btn5.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                GBlock_obdelnik real_block = new GBlock_obdelnik(0,0);
                root.getChildren().add(real_block);
            }
        });

        Button btn6 = new Button();
        btn6.setText("Podil");
        btn6.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                GBlock_podil real_block = new GBlock_podil(0,0);
                root.getChildren().add(real_block);
            }
        });

        Button btn7 = new Button();
        btn7.setText("Soucin");
        btn7.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                GBlock_soucin real_block = new GBlock_soucin(0,0);
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
                    alert.setContentText("Ve schematu byly nalezeny smycky, nelze spustit vypocet");
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
                    Main.plan1.full_demo();
                }
            }
        });

        Button btn9 = new Button();
        btn9.setText("Krok");
        btn9.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(Main.plan1.have_cykles()){
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Chyba");
                    alert.setHeaderText("Cykly");
                    alert.setContentText("Ve schematu byl nalezen cyklus, nelze spustit vypocet");
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
                    Main.plan1.step_demo();
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

        box.getChildren().addAll(btn0,btn1,btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn10, btn11, btn12);
        root.getChildren().add(box);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
