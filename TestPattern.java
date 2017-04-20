package sample;

import javax.swing.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * Created by Ankita on 19/2/2017.
 */
/** The TestPatter defines the pattern of the test. **/
public class TestPattern implements Serializable {

    String pId,name,syl;
    int no_of_sec;
    String secs[][],choice[][],topic[];
    TestPattern(String name,String []A,int n,String secs[][],String choice[][],String syl)
    {   File f=new File("testPattern");
        int i=1;
        try
        {
            FileInputStream fin=new FileInputStream(f);
            ObjectInputStream oin=new ObjectInputStream(fin);
                while(true)
                {
                    try{
                        oin.readObject();
                        i++;
                    }catch(Exception e)
                    {   oin.close();
                        break;
                    }


                }
        }catch(Exception  e)
        {
            System.out.println(e);
        }
        this.pId="$7$"+name+i;
        this.name=name;
        topic=A;
        no_of_sec=n;
        this.secs=secs;
        this.choice=choice;
        this.syl=syl;

    }
    static void savePattern(String name,String []A,int n,String secs[][],String choice[][],String syl)throws Exception
    {
        TestPattern t=new TestPattern(name,A,n,secs,choice,syl);
        if(t.save()) new Message().create("Pattern Created",Main.window);


    }

    boolean save() {
        File f = new File("testPattern");
        if (!f.exists()) {
            try {
                FileOutputStream fout = new FileOutputStream(f);
                ObjectOutputStream out = new ObjectOutputStream(fout);

                out.flush();
                out.close();
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        try {

            FileOutputStream fout = new FileOutputStream("testPattern", true);
            ObjectOutputStream out = new ObjectOutputStream(fout) {
                protected void writeStreamHeader() throws IOException {
                    reset();
                }
            };
            out.writeObject(this);
            out.close();
            return true;
        } catch (Exception e) {
            System.out.println(e);

            return false;
        }


    }
    /** The user can also delete a pattern **/
    boolean delete(File f)
    {TestPattern q;
        try {
            FileInputStream fin = new FileInputStream(f);
            ObjectInputStream obj=new ObjectInputStream(fin);
            FileOutputStream fout = new FileOutputStream(f+"temp");
            ObjectOutputStream obj2=new ObjectOutputStream(fout);
            while(true)
            {
                try{
                    q=(TestPattern)obj.readObject();
                    if(!q.pId.equals(this.pId))
                        obj2.writeObject(q);
                }
                catch(Exception e)
                {       fin.close();
                    obj.close();
                    fout.close();
                    obj2.flush();
                    obj2.close();
                    String name=f.toString();
                    System.out.println(f);
                    Files.delete(Paths.get(f.toString()));
                    new File(name+"temp").renameTo(new File(name));

                    break;
                }

            }
            return true;

        }catch(Exception e)
        {
            System.out.println("file not found"+e);
        }

        return false;

    }
    static ArrayList<TestPattern>read()
    {
        File f=new File("testPattern");
        ArrayList<TestPattern> list=new ArrayList();
        TestPattern t=null;
       try {
           FileInputStream fin = new FileInputStream(f);
           ObjectInputStream oin = new ObjectInputStream(fin);
           while(true)
           {
               try
               {
                   t=(TestPattern)oin.readObject();
                   list.add(t);
               }catch(Exception e)
               {
                   oin.close();
                   break;
               }
           }
           return list;
       }catch(Exception e)
       {
           System.out.println("error in Testpattern.read method");
       }
        return null;
    }


}
