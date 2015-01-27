1. 前置条件：装好jdk配好环境，装好mysql(http://dev.mysql.com/downloads/mysql/)，
   装好eclipse，下载好tomcat（http://tomcat.apache.org/download-70.cgi）
2. 将/dependency/jaxws dependency目录下的10个jar拷贝到 tomcat安装路径/lib/ 目录下
3. 打开eclipse，将Hackathon（项目文件夹）导入
4. 在eclipse里配置好server（配置具体过程略，不会可自行搜索一下）
5. 修改项目的db.properties文件，包括url, username, root，（这些是关于你本地mysql的配置）
6. 再向你的mysql导入，已经建好的数据库（/dependency/iExpense.sql），或者你也可以自己建（当然表结构得和我们之前的一样，不然报错）
7. 运行你的server，在浏览器敲入http://localhost:8080/Hackathon/hackService?wsdl，如果能正常打开，就表示webservice正确地部署到服务器上并且运行了。
8. 然后如果你想在不同机器上调用这个webservice，你需要将安卓端配置的地址改为 你的局域网IP地址:8080/Hackathon/hackService?wsdl，
   如果只想在本机上调用，就将配置的地址写为http://localhost:8080/Hackathon/hackService?wsdl即可。

安装mysql http://jingyan.baidu.com/article/597035521d5de28fc00740e6.html
如何导入导出mysql数据库: http://www.cnblogs.com/xiaoluo501395377/archive/2012/12/04/2801127.html
