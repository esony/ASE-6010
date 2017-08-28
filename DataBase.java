/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AppPackage;
import fi.tut.rd.ain.schemas.WSMeasurement;
import java.util.*;

/**
 *
 * @author Miglu
 */
public class DataBase {
    private LinkedList list = new LinkedList();
    private final int MAX = 1000;
    
    public void updateList(WSMeasurement[] array){

        if (!list.isEmpty() && !array[0].equals(list.getFirst())){
            list.addFirst(array[0]);
            System.out.println("Added: " + array[0].getTimestamp());            
            if (list.size() >= MAX){
                list.removeLast();
            }
        
        } else if (list.isEmpty()){ 
            int i = 0;
            while (i < array.length){
                list.add(array[i]);
                ++i;
            }
            System.out.println("Added: " + i);
        }
    }
    
    public Object getCurrent(){
        if (list.isEmpty()){
            return new WSMeasurement();
        }
        return list.get(0);
    }
    public LinkedList getList(){
        return list;
    }
    
}
