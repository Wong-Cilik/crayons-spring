package com.crayons_2_0.controller;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.crayons_2_0.model.SaveUnit;

public class ExportImport {
    
    public static void save(SaveUnit data) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("test.bin"))) {
            out.writeObject(data);

          System.out.println("Serialization succeeded");
          System.out.println();
        } catch (Exception e) {
          System.out.println("Serialization failed");
          System.out.println();
        }
      }
     
     public static SaveUnit load() {
    	    SaveUnit x = new SaveUnit();

    	    try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("test.bin"))) {
    	      x = (SaveUnit) in.readObject();

    	      System.out.println("Deserialization succeeded");
    	      System.out.println();
    	    } catch (Exception e) {
    	      System.out.println("Deserialization failed");
    	      System.out.println();
    	    }
			return x;
     }
}
