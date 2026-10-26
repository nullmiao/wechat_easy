# 仿造微信项目
本project是后端的开发
这是一个仿造微信的项目，旨在学习和实践后端开发技术。项目包含了基本的聊天功能、好友列表、消息通知等模块，使用了现代前端框架和工具进行开发。
##语言：
java
## 技术栈：
- Spring Boot
- MyBatis
- MySQL
- Redis
- WebSocket
- Maven
- netty
## 功能模块：
1. 用户注册与登录
2. 好友管理（添加好友、删除好友、查看好友列表）
3. 聊天功能（单聊、群聊、消息发送与接收）
4. 消息通知（新消息提醒、未读消息统计）
5. 文件传输（图片、语音、视频等文件的发送与接收）
6. 用户资料管理（修改昵称、头像、个性签名等）
## 项目结构：
```
├── src/main/java/com/example/wechat
│   ├── controller
│   ├── service
│   ├── repository
│   ├── model
│   └── config
├── src/main/resources
│   ├── application.properties
│   └── mapper
├── src/test/java/com/example/wechat
└── pom.xml 