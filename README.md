# MiniGameInJava

## Summary
---

개발자 - byungjin.dev@gmail.com

|항목|설명|
|---|---|
|Name|실시간 미니게임 프로그램|
|Language|Java(SE 15)|
|GUI|Swing|
|IDE|Eclipse|
|Database|MySql|
|Total Period|21.09.01 ~ 21.11.29|


|기간|내용|
|------|---|
|21.09.01 ~ 21.11.21|프로그램 설계 및 구현 |
|21.11.21 ~ 21.11.28|프로그램 오픈 및 테스트 |
|21.11.29|프로그램 완성 |

기능
- 사용자의 승패 정보를 통한 ‘랭킹(전적)’ 서비스 제공
- 실시간 통신 구축을 통한 1:1 대전 서비스 제공
- 오목 등 간단한 미니게임용 플랫폼
- 채팅 서비스
- 사용자 인증

산출물
|이름|설명|
|--|--|
|Client Program|서비스 수혜자의 서비스 접속 프로그램, 로그인 및 미니게임 등 서비스 수혜|
|Server Program|서비스 제공 프로그램, DB 연결 및 로그인 인증 등 서비스 제공|

## 설계
---

### 스키마
---

Log   

|Column|Data Type|
|---|---|
|Source|Varchar(15)|
|Content|Varchar(255)|
|Warning|Tinyint(1)|
|Time|Datetime|
|Key|Int unsigned|

User   
|Column|Data Type|
|---|---|
|Id|Varchar(20)|
|Pw|Varchar(20)|


User Rank
|Column|Data Type|
|--|--|
|Id|Varchar(20)|
|Kind|Int|
|Victory|Int unsigned|
|Lose|Int unsigned|


### 내부 프로그램 분류
---
|분류|역할|
|--|--|
|SYSTEMMANAGER|	프로그램 시작점 및 다른 자원 관리|
|SERVER|	서버, 클라이언트 연결 관리 및 자원 공유|
|Client|	서버 접속 및 자원 생성
|DBMANAGER|	DataBase 연결 설정 및 SQL 쿼리|
|ENVIRONMENT|	내부 분류 상수(할당 번호) 관리|
|RESOURCELOADER|	Font, Image, |Env| 내부 파일 자원 관리|
|GUI|	GUI 갱신 및 관리|
|ROOMMANAGER|	Host가 생성한 Room 관리|

### 클래스 다이어그램
---

1. Network Class

![Network Class](./preview/network%20class.png)

2. GUI Class

![GUI Class1](./preview/GUI%20Class.png)

![GUI Class2](./preview/GUI%20Class%202.png)

![GUI Class3](./preview/GUI%20Class%203.png)

## 동작

1. Server

![Work Server1](./preview/GUI%20Result1.png)

![Work Server2](./preview/GUI%20Log.png)

2. Client

![Work Client1](./preview/GUI%20Login.png)

![Work Client2](./preview/GUI%20Landing.png)

![Work Client3](./preview/GUI%20Create%20Room.png)

![Work Client4](./preview/GUI%20Room%20Search.png)

![Work Client5](./preview/GUI%20Room%20Enter.png)

![Work Client6](./preview/GUI%20Chat%20and%20Game.png)

![Work Client7](./preview/GUI%20End%20Game.png)

### Commit Log
---

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
   
+ 1.2.2
    + improved Class
        + Agent abstract Class
        + Game abstract Class
        + PROMISE
        + StoneType
        + Omok
    + almost complete Omok Class
   
+ 1.2.3
    + improved Design
        + MiniDialog ControlPanel
        + Client ControlPanel
        + Server ControlPanel
    + created ConnectWarning Panel
    + craeted Abstract Class
        + MiniDialog Panel