package com.crayons_2_0.controller;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.crayons_2_0.model.graph.Graph;

public class GraphExpImp {
    
    public static void save(Graph data) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("test.bin"))) {
            out.writeObject(data);

          System.out.println("Serialization succeeded");
          System.out.println();
        } catch (Exception e) {
          System.out.println("Serialization failed");
          System.out.println();
        }
      }
     
     public static Graph load() {
    	 Graph x = null;

    	    try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("test.bin"))) {
    	      x = (Graph) in.readObject();

    	      System.out.println("Deserialization succeeded");
    	      System.out.println();
    	    } catch (Exception e) {
    	      System.out.println("Deserialization failed");
    	      System.out.println();
    	    }
			return x;
     }
}
