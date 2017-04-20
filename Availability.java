package sample;

import insidefx.undecorator.UndecoratorScene;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Created by Ankita on 17/01/17
 */
public class Availability implements Initializable {

    @FXML
    public TableView<Question> table;
    public Pane anchor;
    static Stage st;
    static File f;
    static String ques;
    static boolean ismatched=false;

    /** The above program checks the availability of the question that the user is trying to add in the pre defined
     quesList , so that there will be a pop up which tells the user if the question already exists or not. **/


    public ObservableList<Question> quesList()
    {   ismatched=false;
        ObservableList<Question>list= FXCollections.observableArrayList();
        ArrayList<Question> list2=new ArrayList();
        ArrayList<int[]>ranks=new ArrayList();
        ArrayList<String>key=new ArrayList();
        int i;
        if(f.exists())
        {   String files[]=f.list();


               try {
                   for(i=0;i<files.length;i++) {
                       FileInputStream fin = new FileInputStream(new File(f + "/" + files[i]));
                       ObjectInputStream oin = new ObjectInputStream(fin);

                       while (true) {
                           try {
                               Question c = (Question) oin.readObject();
                               list2.add(c);
                           } catch (Exception e) {
                               fin.close();
                               oin.close();
                               break;
                           }
                       }
                   }

               } catch (Exception e) {

               }

            key=Question.findKeyWord(ques);
            int point,l=0;
            for(i=0;i<list2.size();i++)
            {point=0;
            for(int j=0;j<key.size();j++)
            {
                for(int k=0;k<list2.get(i).keyword.size();k++)
                   if(key.get(j).equalsIgnoreCase(list2.get(i).keyword.get(k)))
                   {
                       point++;}
            } if(point==0)continue;
                if((double)point/list2.get(i).keyword.size()*100.0>=50)
                {ismatched=true;ranks.add(new int[]{i,list2.get(i).ques.split(" ").length-point});
                l++;}
            }
            int []temp=new int[2];
        for(i=0;i<ranks.size()-1;i++)
        {
            for(int j=1;j<ranks.size();j++)
            {
                if(ranks.get(i)[1]>ranks.get(j)[1])
                {
                    ranks.get(i)[1]=ranks.get(i)[1]^ranks.get(j)[1];
                    ranks.get(j)[1]=ranks.get(i)[1]^ranks.get(j)[1];
                    ranks.get(i)[1]=ranks.get(i)[1]^ranks.get(j)[1];
                    ranks.get(i)[0]=ranks.get(i)[0]^ranks.get(j)[0];
                    ranks.get(j)[0]=ranks.get(i)[0]^ranks.get(j)[0];
                    ranks.get(i)[0]=ranks.get(i)[0]^ranks.get(j)[0];

                }
            }
        }


        for(i=0;i<ranks.size();i++)
        {

            list.add(list2.get(ranks.get(i)[0]));}

        }
        return list;
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {


        TableColumn<Question,String> ques =new TableColumn<>("Ques");
        ques.setMinWidth(400);
        ques.setCellValueFactory(new PropertyValueFactory<>("ques"));
        ques.setResizable(false);
        TableColumn<Question,String> Type =new TableColumn<>("Type");
        Type.setMinWidth(100);
        Type.setCellValueFactory(new PropertyValueFactory<>("qtype"));
        Type.setResizable(false);
        TableColumn<Question,Integer> file =new TableColumn<>("Id");
        file.setMinWidth(100);
        file.setCellValueFactory(new PropertyValueFactory<>("qid"));
        file.setResizable(false);

        table=new TableView();
        table.setItems(quesList());

        table.getColumns().addAll(file,ques, Type);

        anchor.getChildren().add(table);

        System.out.println("hello");
    }
    static String first,second;
    void create(String file1,String topic,String question)throws Exception
    {   f=new File("ExamList/"+file1);
        ques=question;

        st=new Stage();
        first=file1;
        second=topic;
        quesList();
        if(ismatched)
        {



        Pane p= FXMLLoader.load(getClass().getResource("Availability.fxml"));
        UndecoratorScene undecoratorScene=new UndecoratorScene(st,p);

        st.setWidth(650);
        st.setHeight(670);
        st.setX(Main.window.getX() + Main.window.getWidth() / 2 - st.getWidth() / 2);
        st.setY(Main.window.getY() + Main.window.getHeight() / 2 - st.getHeight() / 2);
        st.setScene(undecoratorScene);
        st.setResizable(false);
        st.initModality(Modality.APPLICATION_MODAL);
        st.showAndWait();
    }else
         addQues();
    }
    public void cancelAddition()
    {
        st.close();
    }
   public  void addQues()
    {
       try{ new Parameter().create(first,second,ques);}
       catch(Exception e)
       {

       }

    }

}
