package me.byungjin.game.gui.panel;

import me.byungjin.db.UserSchema;
import me.byungjin.game.gui.ClientWindow;
import me.byungjin.game.gui.ConnectErrorDialog;
import me.byungjin.game.gui.item.PlaceHolderTextField;
import me.byungjin.manager.ENVIRONMENT;
import me.byungjin.manager.NetworkManager;
import me.byungjin.manager.SystemManager;
import me.byungjin.network.Agent;
import me.byungjin.network.PROMISE;
import me.byungjin.network.event.DataComeInEvent;
import resource.ResourceLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.StringTokenizer;

public class LoginPanel extends ChildPanel implements Runnable, MouseListener {
    private Agent agentToServer;

    private PlaceHolderTextField idField;
    private PlaceHolderTextField pwField;
    private JLabel messageLine;
    private JPanel content;
    private STATUS status = STATUS.READY;
    private UserSchema me;
    
    private Image bk;
    
    enum STATUS { UP, DOWN, RUNNING, READY};
        
    final int UP_POS = 300;
    final int DOWN_POS = 510;

    public LoginPanel(){    	
    	setBackground(new Color(225,239,250));
    	setLayout(null);
        setSize(400, 570);        
        addMouseListener(this);            
        /**
         * Panel Setting
         */
        bk = ResourceLoader.readBackground("dice_77887.png");
        
        content = new JPanel();
        content.setBackground(Color.WHITE);
        content.setBounds(50, DOWN_POS, 300, 300);
        content.setLayout(new GridLayout(6, 1, 0, 10));
        content.setBorder(BorderFactory.createEmptyBorder(25,40,50,40));
        
        JLabel j_title = new JLabel("Online Mini Game");
        j_title.setOpaque(true);
        j_title.setBackground(Color.black);
        j_title.setForeground(Color.white);
        j_title.setHorizontalAlignment(SwingConstants.CENTER);
        j_title.setFont(ResourceLoader.H_FONT.deriveFont(20f));        
        content.add(j_title);
        
        idField = new PlaceHolderTextField(20, "Enter your Id...");        
        pwField = new PlaceHolderTextField(20, "Enter your Password...");

        content.add(idField);
        content.add(pwField);                
        
        JPanel controlPanel = new JPanel();
        controlPanel.setBackground(Color.white);
        controlPanel.setLayout(new GridLayout(1, 2, 5, 5));
        content.add(controlPanel);
        
        JButton loginBtn = new JButton("Login");
        JButton registerBtn = new JButton("Sign up");
        
        loginBtn.setContentAreaFilled(false);
        loginBtn.setBorderPainted(false);
        loginBtn.setFocusPainted(false);
        loginBtn.setOpaque(true);
        loginBtn.setFont(ResourceLoader.DEFAULT_FONT);
        loginBtn.setBackground(new Color(0xafbdc7));        

        loginBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	loginBtn.setBackground(new Color(0xafbdc7)); 
            	registerBtn.setBackground(Color.white);
                login();
            }
        });
        controlPanel.add(loginBtn);

        
        registerBtn.setContentAreaFilled(false);
        registerBtn.setBorderPainted(false);
        registerBtn.setFocusPainted(false);
        registerBtn.setOpaque(true);
        registerBtn.setFont(ResourceLoader.DEFAULT_FONT);
        registerBtn.setBackground(Color.white);
        registerBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				loginBtn.setBackground(Color.white); 
            	registerBtn.setBackground(new Color(0xafbdc7));
            	register();
			}
		});
        
        controlPanel.add(registerBtn);
                
        messageLine = new JLabel("");
        messageLine.setFont(ResourceLoader.DEFAULT_FONT);
        messageLine.setForeground(Color.RED);
        content.add(messageLine);
        
        content.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
			}
			
			@Override
			public void mousePressed(MouseEvent e) {				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				playAni(STATUS.UP);
			}
		});

        
        add(content);            
        playAni(STATUS.UP);            
    }
    
    public void register() {
    	if(checkAgent()) return;
    	
        String id = idField.getInnerText().trim();
        String pw = pwField.getInnerText().trim();		

        if(id.length() < 10 || id.length() > 15 || pw.length() < 10 || pw.length() > 15) {
            message("올바르지 않는 길이(10~15)");
            return;
        };
        message("");
        me = new UserSchema(id, pw);
    	agentToServer.send(PROMISE.REGISTER, id + " " + pw);
    }
    
    @Override
    protected void paintComponent(Graphics g) {
    	super.paintComponent(g);
    	
    	g.drawImage(bk, 75, 0, 250,250, this);
    }

    public void login(){    	    	
    	if(checkAgent()) return;
    	
        String id = idField.getInnerText().trim();
        String pw = pwField.getInnerText().trim();		

        if(id.length() < 10 || id.length() > 15 || pw.length() < 10 || pw.length() > 15) {
            message("올바르지 않는 길이(10~15)");
            return;
        };
        message("");
        me = new UserSchema(id, pw);
        agentToServer.send(PROMISE.LOGIN, id + " " + pw);
    }
    
    public void initAgent() {
    	try {
			this.agentToServer = NetworkManager.getClientToServer();
		} catch (Exception e) { 
			SystemManager.catchException(ENVIRONMENT.CLIENT, e);
			new ConnectErrorDialog();
			checkAgent();
		}
    	
    	if(this.agentToServer == null) return;
    	this.agentToServer.start();    	
    	System.out.println(this.agentToServer.isRunning());
    	
    	this.agentToServer.addOtherComeInEvent(new DataComeInEvent() {
            @Override
            public void dispatch(Object source, String data) {
                StringTokenizer tokens = new StringTokenizer(data);
                switch(PROMISE.valueOf(tokens.nextToken())){
                    case LOGIN_SUC:
                        loginSuc();
                        break;
                    case LOGIN_FAIL:
                        loginFail();
                        break;
                    case REGISTER_SUC:
                    	registerSuc();
                    	break;
                    case REGISTER_FAIL:
                    	registerFail();
                    	break;
                    default:
                        break;
                }
            }
        });    	
    	((ClientWindow)SwingUtilities.getWindowAncestor(this)).setAgentToServer(agentToServer);
    	checkAgent();
    }
    
    public void registerSuc() {
    	message("회원 가입 성공");
    }
    public void registerFail() {
    	message("회원 가입 실패");
    }
    
    public void loginSuc(){
    	setUser(me);
    	SystemManager.message(ENVIRONMENT.CLIENT, "Login Success, Open Client Window");
    	switchPanel(CHILDPANEL.LANDING);        
    }
    public void loginFail(){
        message("로그인 실패");
    }
    public void message(String msg){
        messageLine.setText(msg);
    }
    public boolean checkAgent() {
    	if(agentToServer == null || !agentToServer.isRunning()) {
    		message("서버와 연결이 올바르지 않습니다.");
    		return true;
    	}    		
    	return false;
    }
    
    public void playAni(STATUS s) {
    	if(status == STATUS.RUNNING) return;
    	    
    	status = s;
    	
    	Thread t = new Thread(this);
    	t.start();
    }

	@Override
	public void run() {
		int curY;
		if(status == STATUS.UP) {
			status = STATUS.RUNNING;
			while((curY = content.getY()) > UP_POS) {
				content.setLocation(50, curY - 4);
				try {
					Thread.sleep(5);
				} catch (InterruptedException e) { 
					e.printStackTrace();
				}
			}
			
		}else {
			status = STATUS.RUNNING;
			while((curY = content.getY()) < DOWN_POS) {
				content.setLocation(50, curY + 4);
				try {
					Thread.sleep(5);
				} catch (InterruptedException e) { 
					e.printStackTrace();
				}
			}			
		}
		status = STATUS.READY;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		playAni(STATUS.DOWN);
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {		
	}

	@Override
	public void init() {
		initAgent(); 
	}
}
