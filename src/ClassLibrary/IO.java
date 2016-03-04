/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ClassLibrary;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Serialize and deserialize serializable classes
 * @author Cory Sabol
 */
public class IO<T> implements Serializable {
    
    private T obj;
    private String OSName = System.getProperty("os.name").substring(0, 3);
    private String path;
    
    public IO(String filePath){
        this.path = filePath;
    }
    
    /**
     * 
     * @param obj
     * @param path Path to serialize to including the file name and extension
     *        EX: /tmp/catcher in the rye.con
     */
    public void serialize(T obj) {
        this.obj = obj;
        
        try {
            FileOutputStream fileOut = new FileOutputStream(this.path);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(this.obj);
            fileOut.close();
            out.close();
        } catch (Exception ex) {
            //Logger.getLogger(IO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * 
     * @param path Path to the file including name and extension
     * @return T obj The deserialized object
     */
    public T deserialize(String path) {
        try {
            
            FileInputStream fileIn = new FileInputStream(path);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            this.obj = (T) in.readObject();
            fileIn.close();
            in.close();
            
        } catch (FileNotFoundException ex) {
           Logger.getLogger(IO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(IO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(IO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return this.obj;
    }
    
}