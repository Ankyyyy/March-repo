package sample;

import insidefx.undecorator.UndecoratorScene;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Ankita on 9/2/2017.
 */
public class Symbols implements Initializable,EventHandler {

    @FXML
    public GridPane griding;

    public ListView<String>Symbols[]=new ListView[56];
   static public Stage symWindow;
   /**This defines the special symbols to be added to the question when the user types a new question. **/
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        for(int i=0;i<56;i++)
        {Symbols[i]=new ListView<String>();
            Symbols[i].setOnMouseReleased(this);

        }
        Symbols[0].getItems().add("\u0240");
        Symbols[1].getItems().add("\u00B1");Symbols[2].getItems().add("\u00B5");
        Symbols[3].getItems().add("\u00D7");Symbols[4].getItems().add("\u00D8");
        Symbols[5].getItems().add("\u00F7");Symbols[6].getItems().add("\u0190");
        Symbols[7].getItems().add("\u019F");Symbols[8].getItems().add("\u01A9");
        Symbols[9].getItems().add("\u01B1");Symbols[10].getItems().add("\u01B5");
        Symbols[11].getItems().add("\u0245");Symbols[12].getItems().add("\u0275");
        Symbols[13].getItems().add("\u0278");Symbols[14].getItems().add("\u0283");
        Symbols[15].getItems().add("\u028A");Symbols[16].getItems().add("\u0298");

        Symbols[17].getItems().add("\u03A0");Symbols[18].getItems().add("\u03A3");
        Symbols[19].getItems().add("\u03A9");Symbols[20].getItems().add("\u03B1");
        Symbols[21].getItems().add("\u03B2");Symbols[22].getItems().add("\u03B3");
        Symbols[23].getItems().add("\u03B4");Symbols[24].getItems().add("\u03B5");
        Symbols[25].getItems().add("\u03B6");Symbols[26].getItems().add("\u03B7");
        Symbols[27].getItems().add("\u03B8");Symbols[28].getItems().add("\u03BA");
        Symbols[29].getItems().add("\u03BB");Symbols[30].getItems().add("\u03BC");
        Symbols[31].getItems().add("\u03BD");Symbols[32].getItems().add("\u03C0");
        Symbols[33].getItems().add("\u03C1");Symbols[34].getItems().add("\u03C3");
        Symbols[35].getItems().add("\u03C4");Symbols[36].getItems().add("\u03C5");
        Symbols[37].getItems().add("\u03C8");Symbols[38].getItems().add("\u03C9");
        Symbols[39].getItems().add("\u0424");Symbols[40].getItems().add("\u043F");
        Symbols[41].getItems().add("\u0510");Symbols[42].getItems().add("\u00BC");
        Symbols[43].getItems().add("\u00BD");Symbols[44].getItems().add("\u00BE");
        Symbols[45].getItems().add("\u02FF");Symbols[46].getItems().add("\u00BB");
        Symbols[47].getItems().add("\u00AB");Symbols[48].getItems().add("\u00B6");
        Symbols[49].getItems().add("\u0277");Symbols[50].getItems().add("\u0283");
        Symbols[51].getItems().add("\u029E");Symbols[52].getItems().add("\u0394");
        Symbols[53].getItems().add("\u0180");Symbols[54].getItems().add("\u01B2");
        Symbols[55].getItems().add("\u03FD");


        for(int i=0,j=0,k=0;i<56;i++) {

            GridPane.setConstraints(Symbols[i], k, j);
            k++;
            if(k==7){k=0;j++;}
        }
        for(int i=0;i<56;i++)
            griding.getChildren().addAll(Symbols[i]);

    }
    public void selectSymbol()
    {   int i;
        for( i=0;i<56;i++)
        {
            if(Symbols[i].getSelectionModel().getSelectedItem()!=null)
            {    symWindow.close();
                Uni.Append= Symbols[i].getSelectionModel().getSelectedItem();
                return;
            }
        }
        System.out.println("select the symbol");


    }
    public void cancel()
    {
        symWindow.close();
    }
    @Override
    public void handle(Event event) {

        for(int i=0;i<56;i++)
        {
            if(!Symbols[i].equals((ListView<String>)event.getSource()))
                Symbols[i].getSelectionModel().clearSelection();
        }
    }


    public void create()throws Exception
    {
        Stage st=new Stage();
        symWindow=st;
        st.setTitle("Select Symbol");

        Pane p= FXMLLoader.load(getClass().getResource("Symbols.fxml"));
        UndecoratorScene undecoratorScene=new UndecoratorScene(st,p);
        st.setWidth(550);
        st.setHeight(450);
        st.setX(Main.window.getX() + Main.window.getWidth()/2 - st.getWidth()/2);
        st.setY(Main.window.getY() + Main.window.getHeight()/2 - st.getHeight()/2);
        st.setResizable(false);
        st.setScene(undecoratorScene);
        st.initModality(Modality.APPLICATION_MODAL);

        st.showAndWait();
    }
}
