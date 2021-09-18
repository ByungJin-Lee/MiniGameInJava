package me.byungjin.game.gui.panel;

import me.byungjin.game.gui.ClientWindow;
import me.byungjin.game.gui.MiniDialog;
import me.byungjin.network.Agent;
import me.byungjin.network.PROMISE;
import me.byungjin.network.event.DataComeInEvent;
import resource.ResourceLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.StringTokenizer;

public class LoginPanel extends JPanel {
    private Agent agent;

    private JTextField idField;
    private JTextField pwField;
    private JLabel messageLine;

    public LoginPanel(Agent agent){
        /**
         * Agent Setting
         */
        this.agent = agent;
//        this.agent.addOtherComeInEvent(new DataComeInEvent() {
//            @Override
//            public void dispatch(Object source, String data) {
//                StringTokenizer tokens = new StringTokenizer(data);
//                switch(PROMISE.valueOf(tokens.nextToken())){
//                    case LOGIN_SUC:
//                        loginSuc();
//                        break;
//                    case LOGIN_FAIL:
//                        loginFail();
//                        break;
//                    default:
//                        break;
//                }
//            }
//        });
        /**
         * Panel Setting
         */
        setBorder(BorderFactory.createEmptyBorder(50,70,50,70));
        setLayout(new GridLayout(5, 1, 0, 10));

        idField = new JTextField(20);
        pwField = new JTextField(20);

        add(idField);
        add(pwField);

        JButton loginBtn = new JButton("Login");
        loginBtn.setContentAreaFilled(false);
        loginBtn.setBorderPainted(false);
        loginBtn.setFocusPainted(false);
        loginBtn.setOpaque(true);
        loginBtn.setFont(ResourceLoader.DEFAULT_FONT);
        loginBtn.setBackground(Color.white);
        loginBtn.setForeground(Color.GRAY);

        loginBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                login();
            }
        });
        add(loginBtn);

        messageLine = new JLabel("");
        messageLine.setFont(ResourceLoader.DEFAULT_FONT);
        messageLine.setForeground(Color.RED);
        add(messageLine);

        JButton registerBtn = new JButton("회원가입");
        registerBtn.setContentAreaFilled(false);
        registerBtn.setBorderPainted(false);
        registerBtn.setFocusPainted(false);
        registerBtn.setOpaque(true);
        registerBtn.setFont(ResourceLoader.DEFAULT_FONT);
        registerBtn.setBackground(Color.white);
        registerBtn.setForeground(Color.GRAY);
        registerBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new MiniDialog(new RegisterDialogPanel(agent), "회원가입");
            }
        });
        add(registerBtn);

        setSize(getPreferredSize());
    }

    public void login(){
        String id = idField.getText().trim();
        String pw = pwField.getText().trim();

        if(id.length() < 10 || id.length() > 15 || pw.length() < 10 || pw.length() > 15) {
            message("올바르지 않는 길이입니다.");
            return;
        };
        message("");

        agent.send(PROMISE.LOGIN, id + " " + pw);
    }
    public void loginSuc(){
        new ClientWindow(agent);
        /**
         * Dispose Window And Open Client Window
         */
        SwingUtilities.getWindowAncestor(this).dispose();
    }
    public void loginFail(){
        message("로그인 실패");
    }
    public void message(String msg){
        messageLine.setText(msg);
    }
}
