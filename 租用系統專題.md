# 租用設備專題
# 建立資料庫
CREATE DATABASE rental_db DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

# 安裝 Maven 
<pre>
1. 到 Maven 官方下載頁下載 Binary zip：
https://maven.apache.org/download.cgi
apache-maven-3.9.x-bin.zip

2. 解壓縮到例如：
C:\tools\apache-maven-3.9.16

3. 設定環境變數
新增系統變數：
MAVEN_HOME=C:\tools\apache-maven-3.9.16

然後在 Path 加入：
%MAVEN_HOME%\bin
</pre>

# 啟動後端
cd backend-rental
mvn spring-boot:run

# 啟動前端
cd frontend-rental
npm install
npm run dev
