package me.byungjin.game.gui;

import me.byungjin.game.gui.panel.ControlClientPanel;
import me.byungjin.game.gui.panel.LoginPanel;
import me.byungjin.manager.ENVIRONMENT;
import me.byungjin.manager.NetworkManager;
import me.byungjin.manager.SystemManager;
import me.byungjin.network.Agent;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class LoginWindow extends JFrame {
    private Agent agent;

    public LoginWindow(){
        setUndecorated(true);
        setResizable(false);
        setLocationRelativeTo(null);
        setTitle("Login");

        Container con = getContentPane();
        con.setLayout(new GridLayout(1,1));

        JPanel container = new JPanel();
        container.setLayout(new BorderLayout());
        container.setBorder(new LineBorder(Color.GRAY));

        container.add(new ControlClientPanel("Login"), BorderLayout.NORTH);

        /**
         * Agent Setting and display
         */
        try{
            agent = NetworkManager.getClientToServer();
            agent.open();
            
            
            while(true) {
            	if(agent.isRunning()) {
                    container.add(new LoginPanel(agent), BorderLayout.CENTER);
                    con.add(container);

                    setSize(getPreferredSize());
                    setVisible(true);
            		break;
            	}
            }
           
//            if(!agent.isRunning()) throw new Exception("Not connect Agent to Server!");
        }catch(Exception e){
            SystemManager.catchException(ENVIRONMENT.GUI, e);
        }
    }
    
    public void close() {
    	if(agent != null)
    		agent.close();
    	dispose();
    }
}
