import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.stage.Stage;



/**
 * Projekt: Rom und Karthago
 * Author : Julian Heeger, Markus Poell, Christian Bruene, Joern Kabuth
 * Date : 26.04.14
 * Year : 2014
 */
public class Oberflaeche extends Application {

    @Override
    public void start(Stage primaryStage) {

        // Graph initialisieren
        Graph graph = new Graph();
        try {
            graph.read();
        } catch (Exception ex)
        {
            System.out.println("Allgemeine Warnlampe");
        }


        //Button größe

        double HEIGHT = 25;
        double WIDTH = 40;

        // Optionen des JavaFX setzen

        primaryStage.setTitle("Graph");
        Pane root = new Pane();

        // Linien hinzufügen

        for (Kante i: graph.l_kante){
            Path temp_path = new Path();

            MoveTo moveTo = new MoveTo();
            moveTo.setX(i.punkt1.position.x+(WIDTH/2));
            moveTo.setY(i.punkt1.position.y+(HEIGHT/2));

            LineTo lineTo = new LineTo();
            lineTo.setX(i.punkt2.position.x+(WIDTH/2));
            lineTo.setY(i.punkt2.position.y+(HEIGHT/2));

            temp_path.getElements().add(moveTo);
            temp_path.getElements().add(lineTo);
            temp_path.setStrokeWidth(2);
            temp_path.setStroke(Color.BLACK);
            root.getChildren().add(temp_path);
        }

        // Buttons hinzufügen

        for (Knoten i : graph.l_knoten){
            Button temp = new Button();
            temp.setText(String.valueOf(i.id)+" "+i.seite.toString());
            temp.setLayoutX(i.position.x);
            temp.setLayoutY(i.position.y);
            root.getChildren().add(temp);
        }


        // Abschließend Form zeigen

        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(root, 600, 600));
        primaryStage.show();
    }
}
