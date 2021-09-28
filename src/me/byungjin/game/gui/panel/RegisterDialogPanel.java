package me.byungjin.game.gui.panel;

import me.byungjin.game.gui.MiniDialogPanel;
import me.byungjin.network.Agent;
import me.byungjin.network.PROMISE;
import me.byungjin.network.event.DataComeInEvent;
import resource.ResourceLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.StringTokenizer;

public class RegisterDialogPanel extends MiniDialogPanel {
    private Agent agent;
    private JTextField idField;
    private JTextField pwField;
    private JTextField rPwField;
    JLabel messageLine;

    public RegisterDialogPanel(Agent agent){
        /**
         * Agent Setting
         */
        this.agent = agent;

        agent.addOtherComeInEvent(new DataComeInEvent() {
            @Override
            public void dispatch(Object source, String data) {
                StringTokenizer tokens = new StringTokenizer(data);
                switch(PROMISE.valueOf(tokens.nextToken()))
                {
                    case REGISTER_SUC:
                        registerSuc();
                        break;
                    case REGISTER_FAIL:
                        registerFail(data.substring(data.indexOf(tokens.nextToken())));
                        break;
                    default:
                        break;
                }
            }
        });

        /**
         * Panel Setting
         */
        setBorder(BorderFactory.createEmptyBorder(30,50,30,50));
        setLayout(new GridLayout(5,1, 0, 10));


        idField = new JTextField(20);
        idField.setFont(ResourceLoader.DEFAULT_FONT);
        pwField = new JTextField(20);
        pwField.setFont(ResourceLoader.DEFAULT_FONT);
        rPwField = new JTextField(20);
        rPwField.setFont(ResourceLoader.DEFAULT_FONT);

        add(idField);
        add(pwField);
        add(rPwField);

        JButton registerBtn = new JButton("회원가입");
        registerBtn.setFont(ResourceLoader.DEFAULT_FONT);
        registerBtn.setFocusPainted(false);
        registerBtn.setContentAreaFilled(false);
        registerBtn.setBorderPainted(false);
        registerBtn.setOpaque(true);
        registerBtn.setBackground(Color.WHITE);
        registerBtn.setForeground(Color.GRAY);
        registerBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                register();
            }
        });

        add(registerBtn);

        messageLine = new JLabel("");
        messageLine.setFont(ResourceLoader.DEFAULT_FONT);
        messageLine.setForeground(Color.RED);

        add(messageLine);
    }

    public void register(){
    	if(checkAgent()) return;
    	
        String id = idField.getText().trim();
        String pw = pwField.getText().trim();

        if(id.length() < 10 || id.length() > 15 || pw.length() < 10 || pw.length() > 15) {
            message("올바른 길이가 아닙니다.");
            return;
        }

        if(!pw.equals(rPwField.getText().trim())){
            message("재입력 비밀번호가 올바르지 않습니다.");
            return;
        }
        message("");

        agent.send(PROMISE.REGISTER, id + " " + pw);
    }

    public void registerSuc(){
        message("성공");
    }

    public void registerFail(String reason){
        message(reason);
    }

    public void message(String msg){
        messageLine.setText(msg);
    }
    
    public boolean checkAgent() {
    	if(agent == null || !agent.isRunning()) {
    		message("서버와 연결이 올바르지 않습니다.");
    		return true;
    	}    		
    	return false;
    }	
}
