  #-----------------------------------
  #USER RIGHTS MANAGEMENT
  #-----------------------------------

CREATE USER 'admin'@'%' IDENTIFIED BY 'toto5';

  GRANT ALL PRIVILEGES ON `computer-database-db-test`.* TO 'admin'@'%' WITH GRANT OPTION;


  FLUSH PRIVILEGES;
