package sample;

import insidefx.undecorator.UndecoratorScene;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
   public ComboBox<String>comboBox1,qno[],qtype[];
    public Button gennies,Back;
    public GridPane gridding;
    public Label label1[],l2[];
    public GridPane gridding2;
    public TextField name;
    public Slider no_of_sec;
    public ListView<String> topics,selTopic[];
    public AnchorPane anchor1,anchor2;
    public ScrollPane scr[];
    public  void backToMain()throws Exception
    {
        Pane root= FXMLLoader.load(getClass().getResource("GenPapers.fxml"));
        UndecoratorScene undecoratorScene=new UndecoratorScene(Main.window,root);
        undecoratorScene.addStylesheet("demoapp/demoapp.css");


        Main.window.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent we) {
                we.consume();   // Do not hide yet
                undecoratorScene.setFadeOutTransition();
            }
        });
        Main.window.setScene(undecoratorScene);
    }

    /** This function would ask the user about what type of questions he wants to add to his question paper **/

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        comboBox1.getItems().addAll(examList());
        qno=new ComboBox[5];
        label1=new Label[5];
        qtype=new ComboBox[5];
        int j=0;
        for(int i=0;i<5;i++)
        {   qno[i]=new ComboBox();
            qno[i].setValue("1");
            label1[i]=new Label();
            qtype[i]=new ComboBox();
            qtype[i].setValue("short");
            qno[i].getItems().addAll("1","3","4","5","6","7","8","9","10");
            label1[i].setText("Section" + (i + 1));
            qtype[i].getItems().addAll("short", "medium", "long","Mixed");
            GridPane.setConstraints(label1[i], j++, i+1);
            GridPane.setConstraints(qno[i],j++,i+1);
            GridPane.setConstraints(qtype[i],j++,i+1);
            j=0;
        }
        gridding.getChildren().addAll(label1[0], qno[0], qtype[0]);
        topics.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);



    }
    public void on_slide()
    {
       int c=(int)no_of_sec.getValue();
        for(int i=0;i<5;i++)
        {
            gridding.getChildren().remove(label1[i]);
            gridding.getChildren().remove(qtype[i]);
            gridding.getChildren().remove(qno[i]);
        }

        for(int i=0;i<c;i++)
        {
            gridding.getChildren().addAll(label1[i],qno[i],qtype[i]);

        }

        for(int i=0;i<5;i++)
        {
            gridding2.getChildren().remove(l2[i]);
            gridding2.getChildren().remove(scr[i]);

        }

        for(int i=0;i<c;i++)
        {
            gridding2.getChildren().addAll(l2[i],scr[i]);

        }


    }
   public void onCombo()
    {
        topics.setDisable(false);
        File f =new File("ExamList/"+comboBox1.getSelectionModel().getSelectedItem());
        topics.getItems().clear();
        topics.setPrefHeight(f.list().length*24+1);

        topics.getItems().addAll(f.list());
        no_of_sec.setDisable(true);
        gridding2.getChildren().clear();
        gridding2.setDisable(true);
        gridding.setDisable(true);


    }

public void doOnTopics()
{
    no_of_sec.setDisable(false);
    gridding2.setDisable(false);
    gridding.setDisable(false);
    gennies.setDisable(false);
    //making the last one
    scr=new ScrollPane[5];
    selTopic=new ListView[5];
    l2=new Label[5];
    for(int i=0;i<5;i++)
    {
        scr[i]=new ScrollPane();
        selTopic[i]=new ListView();
        selTopic[i].getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        l2[i]=new Label("Section"+(i+1));
        selTopic[i].getItems().addAll(topics.getSelectionModel().getSelectedItems());
        scr[i].setContent(selTopic[i]);
        GridPane.setConstraints(scr[i], 1, i);
        GridPane.setConstraints(l2[i],0,i);
    }

    gridding2.getChildren().addAll(l2[0], scr[0]);

}
    private ArrayList<String> examList()
    {
        File f=new File("ExamList");
        if(!f.exists())f.mkdir();

        ArrayList<String> l=new ArrayList();


        String x[]=f.list();
        for(String xx:x)
        {
            l.add(xx);
        }

        return l;


    }
    /** this function would ask the user the pattern of his question paper. **/
    public void createPattern()throws Exception
    {
        if(name.getText().trim().isEmpty()) new Message().create("Enter the name of the Pattern",Main.window);
        else {
            String pt_name=name.getText();
            String A[]=topics.getSelectionModel().getSelectedItems().toArray(new String[]{});
            int n=(int)no_of_sec.getValue();
            String secs[][]=new String[n][2];
            for(int i=0;i<n;i++)
            {
                secs[i][0]=qno[i].getSelectionModel().getSelectedItem();
                secs[i][1]=qtype[i].getSelectionModel().getSelectedItem();
            }
            String choice[][]=new String[n][];
            for(int i=0;i<n;i++)
            {
                choice[i]=selTopic[i].getSelectionModel().getSelectedItems().toArray(new String[]{});
            }

            TestPattern.savePattern(pt_name,A,n,secs,choice,comboBox1.getSelectionModel().getSelectedItem());

        }
    }

}
