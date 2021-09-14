# MiniGameInJava

IDE - Eclipse IDE

SE 15

+ 1.0.0
    + created Project
   
+ 1.0.1
    + completed Basic Communication
    + created Class
        + HostMan
        + ClientMan
        + User
        + ENVIRONMENTS
        + SystemManager
        + Main
    + created Event
        + ConnectionInputEvent
        + HostCloseServEvent
        + HostOpenServEvent
        + UserConnectionEndEvent
        + UserEnterHostEvent

+ 1.0.2
    + created GUI package        
    + created ChatSendActionListener
    + chat with GUI
    + created Class
        + ReadyPanel
        + ReadyFrame
    + fixed bug
        + writer.write -> writer.println
   
+ 1.0.3
    + improved Man Interface
        + removed sendNick method
        + created chat method
    + improved SystemManage        
    + created Class for interaction
        + DefaultHostInputListener
        + DefaultUserInputListener  

+ 1.0.4
    + removed package
        + remove Host
        + remove Client
        + remove User
        + remove Kind
        + remove Events
    + created DBConnection
        + Mysql jdbc
        + maked basic query
    + created package
        + telecommunication 
        + events
        + Promise
    + improved ENVIRONMENT
        + read Env.txt File

+ 1.1.0
    + create package
        + me.byungjin.database
        + me.byungjin.minigame.gui
            + item
            + item.banner
            + item.menu
            + page
            + panel
            + actionlistener
    + create GUI Class
        + BannerPanel
        + ControlPanel
        + InnerPanel
        + MenuPanel
        + MasterWindow
        + ClientBanner
        + ConnectionLogBanner
        + DBLogBanner
        + ServerLogBanner
        + ServerStatusBanner
        + TotalLogBanner
        + LogMenu
        + MonitorMenu
        + SettingMenu
    + create DB Class
        + LogSchema
        + UserSchema
    + read Assets
        + Font
        + Icons
    + success DB Connection
        + login
        + log

+ 1.1.1
    + created Default Omok package
        + Board
        + Omok
        + Stone
        + StoneType
    + created Omok Gui
        + Omok Window
        + Omok Panel
    + improved Environment
        + change Static value to Enum
    + changed DB Source value type
        + small int to varchar(15)
   
+ 1.1.2
    + renamed packages

+ 1.2.0
    + improved Omok GUI
        + added Stone Icon Opacity when player not put
        + boardMouseListener
    + improved ControlPanel
        + added ClientPanel
        + added DialogPanel
    + craete Class
        + MiniDiaglog
        + ChatWindow
   
+ 1.2.1
    + improved Package Structure
        + created ResoureLoader Class
    + improved Network Class
        + methods
