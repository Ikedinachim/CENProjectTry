/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cenprojecttry;

import java.util.HashMap;
import java.util.Map;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import javax.swing.Icon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

/**
 *
 * @author USER
 */
public class CENProjectTry {
    static HashMap<Double, String> redisData = new HashMap<Double, String>();
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Jedis jedis = new Jedis("localhost");
        Info statistics = new Info();
       // HashMap<Double, String> redisData = new HashMap<Double, String>();
        
         if (jedis.zcard("statistics") == null || jedis.zcard("statistics") == 0) {
            jedis.zadd("statistics", (Map) Info.map);
        }
        
         for(Tuple t: jedis.zrangeByScoreWithScores("statistics", 0, 100)){
        System.out.println(t.getScore());
        redisData.put(t.getScore(),t.getElement());
        }
         
        ArrayList<String> states = new ArrayList<String>();
         for (Map.Entry m : redisData.entrySet()) {
             states.add((String)m.getValue());
         }
         
         String[] statesArray = new String[states.size()];
         states.toArray(statesArray);

        

        JComboBox<String> stateList = new JComboBox<>(statesArray);
        stateList.addItemListener(new Handler());
       // stateList.addItemListener(null);

// add to the parent container (e.g. a JFrame):
        JFrame jframe = new JFrame();
        JLabel item1 = new JLabel("Availability of water per state in Nigeria");
        item1.setToolTipText("By Ikedinachim Collins");
        jframe.add(item1);
        
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.setLayout(new FlowLayout());
        jframe.setSize(275,180);
        jframe.setVisible(true);
        
        jframe.add(stateList);
        
        

// get the selected item:
       // String selectedBook = (String) stateList.getSelectedItem();
       

        // check whether the server is running or not
        System.out.println("Server is running: " + jedis.ping());
        //getting the percentage for each state
       
//        System.out.println((jedis.zrangeByScoreWithScores("names", 0, 1000).forEach(Tuple r:)}));
        // storing the data into redis database
       
        System.out.println(jedis.zrange("statistics", 0, 100));
        
        for (Map.Entry m : Info.map.entrySet()) {
            System.out.println(m.getKey() + " " + m.getValue());

            //jedis.zadd("statistics", M)
        }
    }
    private static class Handler implements ItemListener{
//
//        @Override
//        public void actionPerformed(ActionEvent e) {
//           JOptionPane.showMessageDialog(null, String.format("%s", e.getActionCommand()));
//        }

        @Override
        public void itemStateChanged(ItemEvent e) {
             for (Map.Entry m : redisData.entrySet()) {
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
