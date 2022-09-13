package PAThree;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.InputMismatchException;

public class Controller {
    @FXML
    private Pane pane;
    @FXML
    private TextField xCoord;
    @FXML
    private TextField yCoord;
    @FXML
    private TextArea atmInfo;
    @FXML
    private TextArea nearestATM;
    @FXML
    private Button submitBtn;
    @FXML
    private Button abtProgBtn;
    @FXML
    private ImageView grid;

    private KDTree kdtree;
    private double gridOriginX;
    private double gridOriginY;
    private double gridScaleX;
    private double gridScaleY;

    private Shape[] ATMShapes;
    private ATM[] atms;
    private Shape currLocation;
    private Shape nearestShape;


    public void initialize(){
        kdtree = new KDTree("ATMLocations2.csv");
        atms = kdtree.getAtmArr();

        nearestATM.setWrapText(true);
        atmInfo.setWrapText(true);

        gridScaleX = grid.getFitWidth() / 10;
        gridScaleY = grid.getFitHeight() / 10;
        gridOriginX = grid.getLayoutX();
        gridOriginY = grid.getLayoutY();

        ATMShapes = new Circle[atms.length];
        for (int i = 0; i < atms.length; i++){
            ATMShapes[i] = new Circle(10);
            ATMShapes[i].setLayoutX(gridOriginX + atms[i].getX()*gridScaleX);
            ATMShapes[i].setLayoutY(gridOriginY + atms[i].getY()*gridScaleY);
            pane.getChildren().add(ATMShapes[i]);
            Circle circle = (Circle)ATMShapes[i];
            String data = atms[i].toString();
            circle.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    circle.setFill(Color.BLUE);
                    atmInfo.setText(data);
                    for (Shape c : ATMShapes){
                        if (!c.equals(circle)) c.setFill(Color.BLACK);
                        if (c.equals(nearestShape) && !c.equals(circle)) c.setFill(Color.RED);
                    }
                }
            });
        }
        currLocation = new Polygon();
    }

    public void submitOnClick(ActionEvent actionEvent) {
        double x = 0; double y = 0;
        try{
            x = Double.parseDouble(xCoord.getText());
            y = Double.parseDouble(yCoord.getText());
//            ATM nearest = kdtree.nearestNeighbour(x, y, kdtree.getRoot() , 0, kdtree.getRoot().getItem());
            ATM nearest = kdtree.nearestNeighbour(x, y, kdtree.getRoot());
            nearestATM.setText(nearest.toString());
            pane.getChildren().remove(currLocation);
            currLocation = new Polygon();
            ((Polygon) currLocation).getPoints().addAll(new Double[]{
                    gridOriginX + x*gridScaleX, gridOriginX + y*gridScaleY-10,
                    gridOriginX + x*gridScaleX+10, gridOriginX + y*gridScaleY+15,
                    gridOriginX + x*gridScaleX-10, gridOriginX + y*gridScaleY+15});
            currLocation.setFill(Color.GREEN);
            pane.getChildren().add(currLocation);
            int nearestIndex = -1;
            for (int i = 0; i < atms.length; i++){
                if (nearest.equals(atms[i])) nearestIndex = i;
                else ATMShapes[i].setFill(Color.BLACK);
            }
            nearestShape = ATMShapes[nearestIndex];
            nearestShape.setFill(Color.RED);
        } catch(InputMismatchException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Input Error");
            alert.setContentText("Input a proper set of coordinates!");
        }
    }

    public void abtProgOnClick(ActionEvent actionEvent) {
        Stage popupwindow = new Stage();
        popupwindow.setTitle("About Programmer");
        try {
            Parent root = FXMLLoader.load(getClass().getResource("aboutProg.fxml"));
            popupwindow.setScene(new Scene(root));
            popupwindow.initModality(Modality.APPLICATION_MODAL);
            popupwindow.initOwner(abtProgBtn.getScene().getWindow());
            popupwindow.showAndWait();
        }catch(Exception e){
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error!");
            alert.setHeaderText("About Programmer cannot be loaded!");
            alert.showAndWait();
        }
    }


}
