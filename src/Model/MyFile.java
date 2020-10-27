package Model;

import java.io.*;
import java.util.ArrayList;

public class MyFile<T> {

    private String path;
    private int objCount;

    //Constructor
    public MyFile(String path) {
        this.path = path;
    }

    //Reads from file.
    public ArrayList<T> readFrom() throws IOException {

        ArrayList<T> list = new ArrayList<T>();
        try {
            FileInputStream inStream = new FileInputStream(path);
            try {
                ObjectInputStream objectFile = new ObjectInputStream(inStream);

                T obj;
                while ((obj = (T) objectFile.readObject()) != null)
                    list.add((T) obj);
                objectFile.close();
            } catch (EOFException e) {
                System.out.println("file has reached end");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return list;
    }

    //Writes to file
    public void writeTo(T obj) throws FileNotFoundException, IOException {
        try {
            FileOutputStream outStream = new FileOutputStream(path);

            try {
                ObjectOutputStream objectFile = new ObjectOutputStream(outStream);

                objectFile.writeObject(obj);
                objCount++;
                objectFile.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    /*
    Appends to file. Counts the number of stored object via objCount attribute. Writes to file if no objects are found or
    appends to file if objects are found.
    */
    public void appendTo(T obj) throws FileNotFoundException, IOException {


        if (objCount == 0) writeTo(obj);
        else {
            System.out.println(objCount);
            try {
                FileOutputStream outStream = new FileOutputStream(path, true);

                try {
                    AppendObjectOutputStream objectFile = new AppendObjectOutputStream(outStream);
                    objectFile.writeObject(obj);
                    objCount++;
                    objectFile.close();
                    System.out.println("appended");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

}
