/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cenprojecttry;


import static cenprojecttry.CENProjectTry.redisData;
import java.awt.FlowLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.Tuple;
import redis.clients.jedis.exceptions.JedisConnectionException;
import redis.clients.jedis.params.ZAddParams;


public class azureTry 
{
     static HashMap<Double, String> azureData = new HashMap<Double, String>();
    public static void main( String[] args )
    {

        boolean useSsl = true;
        azureStates states = new azureStates();
        String cacheHostname = System.getenv("REDISCACHEHOSTNAME");
        String cachekey = "6yjao6UHOnajmStJQOgPirRN6x04bjBSAt7yd0SwifA=";

        // Connect to the Azure Cache for Redis over the TLS/SSL port using the key.
        JedisShardInfo shardInfo = new JedisShardInfo("CENassignment.redis.cache.windows.net", 6380, useSsl);
        shardInfo.setPassword("6yjao6UHOnajmStJQOgPirRN6x04bjBSAt7yd0SwifA="); /* Use your access key. */
        // Perform cache operations using the cache connection object...
        // Simple PING command
        Jedis jedis = new Jedis(shardInfo.getHost()); 
      try  {jedis.auth(cachekey);
            jedis.connect();
       System.out.println( "\nCache Command  : Ping" );
            System.out.println( "Cache Response : " + jedis.ping());
       if (jedis.zcard("statistics") == null || jedis.zcard("statistics") == 0) {
           
           jedis.zadd("statistics", azureStates.stateMap);
           
        }
              for(Tuple t: jedis.zrangeByScoreWithScores("statistics", 0, 100)){
              System.out.println(t.getScore());
              azureData.put(t.getScore(),t.getElement());
              
              }
              ArrayList<String> _states = new ArrayList<>();
         for (Map.Entry m : azureData.entrySet()) {
             _states.add((String)m.getValue());
         }
         
         String[] statesArray = new String[_states.size()];
         _states.toArray(statesArray);

        

        JComboBox<String> stateList = new JComboBox<>(statesArray);
        stateList.addItemListener(new Handler());
       // stateList.addItemListener(null);

// add to the parent container (e.g. a JFrame):
        JFrame jframe = new JFrame();
        JLabel item1 = new JLabel("Availability of water per state in Nigeria");
        JLabel item2 = new JLabel("Data obtained from Azure Cache for Redis");
        item1.setToolTipText("By Ikedinachim Collins");
        jframe.add(item1);
        jframe.add(item2);
        
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.setLayout(new FlowLayout());
        jframe.setSize(300,180);
        jframe.setVisible(true);
        
        jframe.add(stateList);
        
        

// get the selected item:
       // String selectedBook = (String) stateList.getSelectedItem();
       

        // check whether the server is running or not
        System.out.println("Server is running: " + jedis.ping());
      }catch(JedisConnectionException e){
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(null, "Require Internet Connection!!");
            
            }
            // Perform cache operations using the cache connection object...
            
            // Simple PING command
           
        //getting the percentage for each state
       
//        System.out.println((jedis.zrangeByScoreWithScores("names", 0, 1000).forEach(Tuple r:)}));
        // storing the data into redis database
       
       // System.out.println(jedis.zrange("statistics", 0, 100));
        
//        for (Map.Entry m : Info.map.entrySet()) {
//            System.out.println(m.getKey() + " " + m.getValue());
//
//            //jedis.zadd("statistics", M)
//        }
    }
    private static class Handler implements ItemListener{
//
//        @Override
//        public void actionPerformed(ActionEvent e) {
//           JOptionPane.showMessageDialog(null, String.format("%s", e.getActionCommand()));
//        }

        @Override
        public void itemStateChanged(ItemEvent e) {
             for (Map.Entry m : azureData.entrySet()) {
                 if(e.getItem().toString() == m.getValue()&& e.getStateChange() == 1){
                     
                     JOptionPane.showMessageDialog(null, m.getKey() + "%", "Percentage", 1);
                     System.out.println(m.getKey());
                     break;
                     
                 }
          //  System.out.println(m.getKey() + " " + m.getValue());

            //jedis.zadd("statistics", M)
        }
       //     System.out.println(e.getItem().toString());
        }
        
    }

}
