package sample;

import insidefx.undecorator.UndecoratorScene;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.stage.*;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.*;
/**
 * Created by Ankita.
 */
public class DisplayPaper implements Initializable{

    @FXML    /* FXML is a XML-based user interface markup language created
                by Oracle Corporation for defining the user interface of a JavaFX application.*/
    public TextArea canvas;
    public Button file,collection;
    static ArrayList<Question>paper;
    static Stage stg;
static TestPattern title;
    static int distinction,difference;
    @Override
    /*This is used to make the pattern of the question paper.*/
    public void initialize(URL location, ResourceBundle resources) {
        int i=1,p=1;
        for(Question q:paper)
        {
            if("null".equals(q.qid+""))
                canvas.appendText("\nSection "+(i++)+"\n\n"); /*This provides the number of sections in the paper*/
            else
                canvas.appendText("\nQues "+(p++)+": "+q.ques+"\n"); /*This provides the number of questions in
                                                                             the particular section*/
        }
        canvas.scrollTopProperty();
        Tooltip t=new Tooltip();
        t.setText("EXPORT TO DOC FILE");    /*We can also save it in a doc file */
        file.setTooltip(t);
        Tooltip tt=new Tooltip();
        tt.setText("ADD TO COLLECTION FOR FUTURE REFERENCE"); /* Adding the paper in the collection */
        collection.setTooltip(tt);
    }
    /* Used to create the paper */
    public void create(ArrayList<Question> p,TestPattern name,int diff)throws Exception
    {
      paper=p;
        title=name;

        difference=diff;
        stg=new Stage();
        stg.setTitle(name.name);
        Pane ppe= FXMLLoader.load(getClass().getResource("DisplayingPaper.fxml"));
        UndecoratorScene undecoratorScene=new UndecoratorScene(stg,ppe);
        stg.setWidth(775);
        stg.setHeight(690);
        stg.setX(Main.window.getX() + Main.window.getWidth() / 2 - stg.getWidth() / 2);
        stg.setY(Main.window.getY() + Main.window.getHeight() / 2 - stg.getHeight() / 2);
        stg.setScene(undecoratorScene);
        stg.setResizable(false);
        stg.initModality(Modality.APPLICATION_MODAL);
        Platform.runLater(new Runnable() {
            public void run() {
                Genpaper.p.close();Genpaper.p=null;
            }
        });
        stg.showAndWait();

    }
  public  void saveToFile()
    {   int i=1,naming=0;

        FileChooser ff=new FileChooser();
        ff.setTitle("SAVE");
        ff.getExtensionFilters().add(new FileChooser.ExtensionFilter("DOC files (*.doc)", "*.doc"));
        ff.setInitialDirectory(new File("./"));




        File f = ff.showSaveDialog(stg);
        if(f!=null)
       try
       {
         FileOutputStream fout=new FileOutputStream(f);
           PrintStream p=new PrintStream(fout);

        p.print(canvas.getText());
           p.close();
           fout.close();
           new Message().create("Paper has been created.",DisplayPaper.stg);
       }catch(Exception e)
       {

       }

    }
   public void saveToDataBase()throws Exception
    {
        if( new Papers(title,distinction,difference,paper).insert())
            new Message().create("Paper has been added to the Collection.",DisplayPaper.stg);
        else new Message().create("Paper could not be added to the Collection",DisplayPaper.stg);

    }
}
