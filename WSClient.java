/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/*
        Alustetaan mainissa:

        DataBase dataBase = new DataBase();
        Thread t = new Thread(new WSClient(dataBase));
        t.start();

*/
package AppPackage;

import fi.tut.rd.ain.WeatherService;
import fi.tut.rd.ain.WeatherServiceService;
import fi.tut.rd.ain.WeatherServiceServiceLocator;
import fi.tut.rd.ain.schemas.Request;
import fi.tut.rd.ain.schemas.Response;
import fi.tut.rd.ain.schemas.WSMeasurement;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author Miglu
 */
public class WSClient extends Thread {
    private final int MAX = 10;
    DataBase dataBase;
    
    
    public WSClient(DataBase x){
        this.dataBase = x;
    }
    
    public void run(){
        
        int counter = 0;
        int n = 100;
        Date lastConnection = Calendar.getInstance().getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.YYYY HH:mm:ss");
        WSMeasurement[] measurement;
        WeatherServiceService wss = new WeatherServiceServiceLocator();
        WeatherService ws;
        
        while (true){
            try {
                
                System.out.println("Connecting " + sdf.format(Calendar.getInstance().getTime()));
                //Stub instance of the web service
                ws = wss.getWeatherService();
                
                //Create request and response
                Request request = new Request();
                request.setAmountOfmeasurements(n);
                Response response = new Response();
                
                //Make the request
                response = ws.readWeather(request);
                
                //Save the response to measurements
                measurement = response.getMeasurements();
                dataBase.updateList(measurement);
                
                TimeUnit.SECONDS.sleep(60);
                lastConnection = Calendar.getInstance().getTime();
                n = 1;
                counter = 0;
                
            } catch (Exception e){
                //Reconnect 10 times if not succesful
                if (++counter >= MAX){                    
                    System.out.println("Cannot connect to service");
                    e.printStackTrace();
                    break;
                }                
                System.out.println("Connection lost " + sdf.format(lastConnection) + " retry " + counter);
            }
        }
    }    
}
