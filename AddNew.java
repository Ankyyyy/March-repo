package sample;

import insidefx.undecorator.UndecoratorScene;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.*;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.layout.*;
/**
 * Created by Champ on 9/9/2015.
 */
public class AddNew implements Initializable{
    @FXML
   public Label xyz;
    public TextField input;
    public Button add;
    static String type;
    static Stage stg;
    public void create(String arg)throws Exception
    {
        Pane str;
        type=arg;
        stg=new Stage();
        str= FXMLLoader.load(getClass().getResource("addNew.fxml"));
        UndecoratorScene undecoratorScene=new UndecoratorScene(stg,str);

        stg.setWidth(400);
        stg.setHeight(200);
        stg.setResizable(false);
        stg.setX(Main.window.getX() + Main.window.getWidth() / 2 - stg.getWidth() / 2);
        stg.setY(Main.window.getY() + Main.window.getHeight() / 2 - stg.getHeight() / 2);
        stg.setScene(undecoratorScene);
        stg.initModality(Modality.APPLICATION_MODAL);
        stg.showAndWait();

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println(type);
        xyz.setText(type);

    }
    public void addItBaby()
    {
           if(input.getText()!=null&&input.getText().trim().length()>0)
           {Uni.tempo=input.getText();
           stg.close();}
    }

}
