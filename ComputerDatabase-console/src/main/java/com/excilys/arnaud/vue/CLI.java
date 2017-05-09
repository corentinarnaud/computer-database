package com.excilys.arnaud.vue;

import com.excilys.arnaud.mapper.MapperException;
import com.excilys.arnaud.dto.CompanyDto;
import com.excilys.arnaud.dto.ComputerDto;
import com.excilys.arnaud.dto.ComputerPage;
import com.excilys.arnaud.dto.Page;
import com.excilys.arnaud.service.CompanyService;
import com.excilys.arnaud.service.ComputerService;
import com.excilys.arnaud.service.ServiceException;
import java.util.Optional;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;

public class CLI {
  private static final String SEPARATOR = "============================================";
  
  @Autowired
  private static CompanyService companyService;
  @Autowired
  private static ComputerService computerService;

  public static void main(String[] args) {
    String inputString = "";
    Scanner sc = new Scanner(System.in);

    System.out.println("CLI Computer-database");

    while (inputString.compareTo("q") != 0) {
      System.out.println("\n" + SEPARATOR);
      System.out.println("Press 1 for add a computer");
      System.out.println("Press 2 for del a computer");
      System.out.println("Press 3 for update computer");
      System.out.println("Press 4 for show companies");
      System.out.println("Press 5 for show computers");
      System.out.println("Press 6 for show a computer");
      System.out.println("Press q for quit");
      System.out.println(SEPARATOR);
      inputString = sc.nextLine();
      switch (inputString) {
        case "1":
          addComputer(sc);
          break;
        case "2":
          delComputer(sc);
          break;
        case "3":
          updateComputer(sc);
          break;
        case "4":
          showCompanies(sc);
          break;
        case "5":
          showComputers(sc);
          break;
        case "6":
          showComputer(sc);
          break;
        default :
          break;
      }
    }

  }

  private static void addComputer(Scanner sc) {
    String name;
    String tmp;
    String introduced = "";
    String discontinued = "";
    long numComp;
    long resultId;
    CompanyDto company = null;


    try {
      System.out.print("\nName : ");
      name = sc.nextLine();

      System.out.print("Numero of the company (refer to companie list) : ");
      tmp = sc.nextLine();
      if (tmp.compareTo("") != 0) {
        numComp = Integer.parseInt(tmp);
        if (numComp < 0) {
          throw new NumberFormatException();
        }
        Optional<CompanyDto> opt = companyService.findById(numComp);
        company = opt.isPresent() ? opt.get() : null;
      }

      System.out.print("Date of introducing at format dd/mm/yyyy : ");
      introduced = sc.nextLine();
      if (tmp.compareTo("") != 0) {
        System.out.print("Date of discontinuing at format dd/mm/yyyy :");
        discontinued = sc.nextLine();
      }


      resultId = computerService
          .add(new ComputerDto(name, company, introduced, discontinued));
      System.out.println("New computer save, id : " + resultId);
    } catch (ServiceException e) {
      System.out.println(e.getMessage());
    } catch (MapperException e) {
      System.out.println(e.getMessage());
    } catch (NumberFormatException e) {
      System.out.println("Wrong Company id Format");
    }

  }

  private static void showComputer(Scanner sc) {
    int num;

    Optional<ComputerDto> comp;

    System.out.print("\nID of the computer : ");

    try {
      num = Integer.parseInt(sc.nextLine());
      comp = computerService.findById(num);
      if (comp.isPresent()) {
        System.out.println(comp.get());
      } else {
        System.out.println("Wrong number");
      }
    } catch (NumberFormatException e) {
      System.out.println("Wrong Id");
    }

  }

  private static void delComputer(Scanner sc) {
    int id;


    System.out.print("\nNumber of the computer : ");

    try {
      id = Integer.parseInt(sc.nextLine());
      computerService.delComputer(id);
      System.out.println("Computer " + id + " corectly deleted");
    } catch (NumberFormatException e) {
      System.out.println("Wrong Id");
    }

  }

  private static void showCompanies(Scanner sc) {
    Page<CompanyDto> companyPage = companyService.getCompanyPage(0, 10);
    System.out.println("List of companies :");

    
    boolean loop = true;
    String query = "";


    System.out.println(companyPage.getPage());
    System.out.println(
        "\t\t\t\tPage " + companyPage.getCurrentPage() + "/" + companyPage.getNbPage());

    while (loop) {
      System.out.println(SEPARATOR);
      System.out.println(
          "Quit : q  |  Prev : p  |  Next : n  |  First : f  |  Last : l  |  Go to page :");
      query = sc.nextLine();
      switch (query) {
        case "q":
          loop = false;
          break;
        case "p":
          companyPage = companyService.getCompanyPage(companyPage.getCurrentPage() - 1, 10);
          System.out.println(companyPage);
          System.out.println("\t\t\t\tPage " + companyPage.getCurrentPage());
          break;
        case "n":
          companyPage = companyService.getCompanyPage(companyPage.getCurrentPage() + 1,10);
          System.out.println(companyPage);
          System.out.println("\t\t\t\tPage " + companyPage.getCurrentPage());
          break;
        case "f":
          companyPage = companyService.getCompanyPage(0, 10);
          System.out.println(companyPage);
          System.out.println("\t\t\t\tPage " + companyPage.getCurrentPage());
          break;
        case "l":
          companyPage = companyService.getCompanyPage(companyPage.getNbPage(), 10);
          System.out.println(companyPage);
          System.out.println("\t\t\t\tPage " + companyPage.getCurrentPage());
          break;
        default:
          try {
            int pageNumber = Integer.parseInt(query);
            companyPage = companyService.getCompanyPage(pageNumber, 10);
            System.out.println(companyPage);
            System.out.println("\t\t\t\tPage " + companyPage.getCurrentPage());
          } catch (NumberFormatException e) {
            System.out.println("Wrong page number");
          }
      }
    }

  }

  private static void showComputers(Scanner sc) {
    ComputerPage computerPage = computerService.getComputerPage(0, "", 0, 10);
    System.out.println("List of computers :");


    
    boolean loop = true;
    String query = "";
    String pattern = "";

    System.out.println(computerPage.getPage());
    System.out.println(
        "\t\t\t\tPage " + computerPage.getCurrentPage() + "/" + computerPage.getNbPage());

    while (loop) {
      System.out.println(SEPARATOR);
      System.out.println(
          "Quit : q  |  Prev : p  |  Next : n  |  First : f  |  Last : l  |  Go to page :");
      query = sc.nextLine();
      switch (query) {
        case "q":
          loop = false;
          break;
        case "p":
          computerPage = computerService.getComputerPage(computerPage.getCurrentPage() - 1, pattern, 0, 10);
          System.out.println(computerPage);
          System.out.println("\t\t\t\tPage " + computerPage.getCurrentPage());
          break;
        case "n":
          computerPage = computerService.getComputerPage(computerPage.getCurrentPage() + 1, pattern, 0, 10);
          System.out.println(computerPage);
          System.out.println("\t\t\t\tPage " + computerPage.getCurrentPage());
          break;
        case "f":
          computerPage = computerService.getComputerPage(0, pattern, 0, 10);
          System.out.println(computerPage);
          System.out.println("\t\t\t\tPage " + computerPage.getCurrentPage());
          break;
        case "l":
          computerPage = computerService.getComputerPage(computerPage.getNbPage(), pattern, 0, 10);
          System.out.println(computerPage);
          System.out.println("\t\t\t\tPage " + computerPage.getCurrentPage());
          break;
        default:
          try {
            int pageNumber = Integer.parseInt(query);
            computerPage = computerService.getComputerPage(pageNumber, pattern, 0, 10);
            System.out.println(computerPage);
            System.out.println("\t\t\t\tPage " + computerPage.getCurrentPage());
          } catch (NumberFormatException e) {
            System.out.println("Wrong page number");
          }
      }
    }
  }

  private static void updateComputer(Scanner sc) {
    int id;
    int companyId;
    String string;
    boolean loop = true;
    boolean update = false;
    Optional<ComputerDto> computer;

    System.out.print("\nID of computer : ");
    try {
      id = Integer.parseInt(sc.nextLine());
    } catch (NumberFormatException e) {
      System.out.println("The id must be an unsignied integer");
      return;
    }

    try {
      computer = computerService.findById(id);
      if (computer.isPresent()) {
        System.out.println(computer.get());
      } else {
        System.out.println("Wrong id");
        return;
      }

      while (loop) {
        System.out.println("Which field ?");
        System.out.println("1\tName");
        System.out.println("2\tCompany");
        System.out.println("3\tDate of introducing");
        System.out.println("4\tDate of discontinuing");
        System.out.println("z\tUndo");
        System.out.println("v\tValid");
        string = sc.nextLine();
        switch (string) {
          case "1":
            System.out.print("New name : ");
            computer.get().setName(sc.nextLine());
            update = true;
            break;
          case "2":
            try {
              System.out.print("New company (id): ");
              companyId = Integer.parseInt(sc.nextLine());
              Optional<CompanyDto> company = companyService.findById(companyId);
              if (company.isPresent()) {
                computer.get().setCompany(company.get());
                update = true;
              } else
                System.out.println("Wrong id");
            } catch (NumberFormatException e) {
              System.out.println("ID must be an unsignied integer");
            }
  
            break;
          case "3":
            System.out.print("Date of introducing (dd/mm/yyyy) :");
            computer.get().setIntroduced(sc.nextLine());
            update = true;
            break;
          case "4":
            System.out.print("Date of discontinuing (dd/mm/yyyy) :");
            computer.get().setDiscontinued(sc.nextLine());
            update = true;
            break;
          case "z":
            loop = false;
            break;
          case "v":
            loop = false;
            break;
          default :
            break;
        }
      }
      if (update) {
        if (computerService.update(computer.get())) {
          System.out.println("Computer " + id + " corectly updated");
        } else {
          System.out.println("Computer " + id + " not updated");
        }
      }

    } catch (ServiceException e) {
      System.out.println("Computer " + id + " not updated");
      System.out.println(e.getMessage());
    } 
  }
}
