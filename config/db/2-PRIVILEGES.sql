  #-----------------------------------
  #USER RIGHTS MANAGEMENT
  #-----------------------------------
  #CREATE USER 'admin'@'localhost' IDENTIFIED BY 'toto5';

  GRANT ALL PRIVILEGES ON `computer-database-db2`.* TO 'admin'@'localhost' WITH GRANT OPTION;


  FLUSH PRIVILEGES;
