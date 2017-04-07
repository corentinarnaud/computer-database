  #-----------------------------------
  #USER RIGHTS MANAGEMENT
  #-----------------------------------
  CREATE USER 'admin'@'localhost' IDENTIFIED BY 'toto5';

  GRANT ALL PRIVILEGES ON `computer-database-db`.* TO 'admincdb'@'localhost' WITH GRANT OPTION;


  FLUSH PRIVILEGES;
