# ERP Project

## Google Drive for assets
https://drive.google.com/drive/u/0/folders/0B_ITHrAhCStAOERzUFRvOFY1Uk0

## How to setup development
[![IMAGE ALT TEXT HERE](https://img.youtube.com/vi/frsvx1e-UjQ/0.jpg)](https://www.youtube.com/watch?v=frsvx1e-UjQ)
- Clone from Github
- https://github.com/luhonghai/erp-project.git
- Open as Maven project
- Create new blank database
- Run org.jallinone.commons.client.ClientApplication on module erp-project from main method.
- Change port if needed
- Setup database connection
- Wait for database schema generating.
- Login with admin account
- Build application by command: mvn clean install
- Output jar at [root-project]/erp-project/target/**.one-jar.jar
- Simply open application by double click (Base on OS)
- Or command: java -jar path/to/jar [optional port]
