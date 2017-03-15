package com.ecxilys.vue;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import java.util.Scanner;

import com.ecxilys.model.Company;
import com.ecxilys.model.CompanyList;
import com.ecxilys.model.Computer;
import com.ecxilys.model.Page;
import com.ecxilys.persistance.ComputerDAO;
import com.ecxilys.persistance.DAOFactory;
import com.ecxilys.persistance.DataBaseConnection;
import com.ecxilys.persistance.CompanyDAO;

public class CLI {
	private static final String SEPARATOR = "============================================";

	public static void main(String[] args) {
		String inputString = "";
		Scanner sc = new Scanner(System.in);
		


		
		System.out.println("CLI Computer-database");
		
		
		while(inputString.compareTo("q")!=0){
			System.out.println("\nPress 1 for add a computer");
			System.out.println("Press 2 for del a computer");
			System.out.println("Press 3 for update computer");
			System.out.println("Press 4 for show companies");
			System.out.println("Press 5 for show computers");
			System.out.println("Press 6 for show a computer");
			System.out.println("Press q for quit");
			inputString=sc.nextLine();
			switch(inputString){
			case "1" :
				try{
						addComputer(sc);
					} catch(ParseException e){
						System.out.println("Wrong Date Format "+e);
					} catch(NumberFormatException e){
						System.out.println("Wrong Companie Format");
					}
				break;
			case "2" :
				delComputer(sc);
				break;
			case "3" :
				updateComputer(sc);
				break;
			case "4" :
				showCompanies();
				break;
			case "5" :
				showComputers(sc);
				break;
			case "6" :
				showComputer(sc);
				break;
			}
		}
		DataBaseConnection.CONNECTION.close();

	}

	
	private static void addComputer(Scanner sc) throws ParseException, NumberFormatException{
		String name,tmp;
		Date introduced = null, discontinued = null;
		int numComp,resultId;
		Company company = null;
		DAOFactory factory=DAOFactory.DAOFACTORY;
		CompanyDAO companyDAO = factory.getCompanyDAO();
		ComputerDAO computerDAO = factory.getComputerDAO(); 
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		
		System.out.print("\nName : ");
		name= sc.nextLine();
		
		System.out.print("Numero of the company (refer to companie list) : ");
		tmp=sc.nextLine();
		if(tmp.compareTo("")!=0){
			numComp=Integer.parseInt(tmp);
			if(numComp<0)
				throw new NumberFormatException();
			Optional<Company> opt = companyDAO.findById(numComp);
			company = opt.isPresent() ? opt.get() : null;
		}
		
		
		
		System.out.print("Date of introducing at format dd/mm/yyyy : ");
		tmp= sc.nextLine();
		if(tmp.compareTo("")!=0){
			introduced = df.parse(tmp);
			
			System.out.print("Date of discontinuing at format dd/mm/yyyy :");
			tmp= sc.nextLine();
			if(tmp.compareTo("")!=0){
				discontinued = df.parse(tmp);
			}
			
			
		}
		resultId=computerDAO.add(new Computer(name, company, introduced, discontinued));
		System.out.println("New computer save, id : "+resultId);
		
	}
	
	private static void showComputer(Scanner sc){
		int num;
        ComputerDAO compDAO=DAOFactory.DAOFACTORY.getComputerDAO();
        Computer comp=null;
        
		System.out.print("\nID of the computer : ");
		
		num=Integer.parseInt(sc.nextLine());
		
		comp=compDAO.findById(num);
		if(comp!=null)
			System.out.println(comp);
		else
			System.out.println("Wrong number");
		
	}
	
	
	private static void delComputer(Scanner sc){
		int num;
        ComputerDAO compDAO=DAOFactory.DAOFACTORY.getComputerDAO();

        
		System.out.print("\nNumber of the computer : ");
		
		num=Integer.parseInt(sc.nextLine());
		
		compDAO.del(num);

		
	}
	
	private static void showCompanies(){
		CompanyDAO companyDAO=DAOFactory.DAOFACTORY.getCompanyDAO();
		CompanyList companyList = companyDAO.getCompanies();
		System.out.println("List of companies :");
		System.out.println(companyList);
		
	}
	
	private static void showComputers(Scanner sc){
		boolean loop=true;
		String query ="";
		ComputerDAO computerDAO=DAOFactory.DAOFACTORY.getComputerDAO();
		Page<Computer> computerPage = new Page<Computer>(computerDAO.getComputers());
		System.out.println("List of computers :");
		System.out.println(computerPage.getPage());
		System.out.println("\t\t\t\tPage "+computerPage.getCurrentPage());
		
		while(loop){
			System.out.println(SEPARATOR);
			System.out.println("Quit : q  |  Prev : p  |  Next : n");
			query = sc.nextLine();
			switch(query){
			case "q" :
				loop=false;
				break;
			case "p" :
				System.out.println(computerPage.getPrevPage());
				System.out.println("\t\t\t\tPage "+computerPage.getCurrentPage());
				break;
			case "n" :
				System.out.println(computerPage.getNextPage());
				System.out.println("\t\t\t\tPage "+computerPage.getCurrentPage());
				break;
			}
			
			
		}

		
	}
	
	private static void updateComputer(Scanner sc){
		int num;
		String string;
		boolean loop=true, update=false;
        ComputerDAO computerDAO=DAOFactory.DAOFACTORY.getComputerDAO();
        CompanyDAO companyDAO=DAOFactory.DAOFACTORY.getCompanyDAO();
        Computer computer=null;
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        
		System.out.print("\nID of computer : ");
		try{
		num=Integer.parseInt(sc.nextLine());
		
		computer=computerDAO.findById(num);
		if(computer!=null)
			System.out.println(computer);
		else{
			System.out.println("Wrong id");
			return;
		}
		
		
		
		while(loop){
			System.out.println("Which field ?");
			System.out.println("1\tName");
			System.out.println("2\tCompany");
			System.out.println("3\tDate of introducing");
			System.out.println("4\tDate of discontinuing");
			System.out.println("z\tUndo");
			System.out.println("v\tValid");
			string=sc.nextLine();
			switch(string){
			case "1":
				System.out.print("New name : ");
				computer.setName(sc.nextLine());
				update=true;
				break;
			case "2":
				try{
					System.out.print("New company (id): ");
					num=Integer.parseInt(sc.nextLine());
					Optional<Company> company=companyDAO.findById(num);
					if(company.isPresent()){
						computer.setCompany(company.get());
						update=true;
					}
					else
						System.out.println("Wrong id");
				}catch(NumberFormatException e){
					System.out.println("L'id doit être un entier positif");
				}
				
				break;
			case "3" :
				try{
					System.out.print("Date of introducing (dd/mm/yyyy) :");					
					computer.setIntroduced(df.parse(sc.nextLine()));
					update=true;
				} catch (ParseException e){
					System.out.println("Wrong date");
				}
				break;
			case "4" :
				try{
					System.out.print("Date of discontinuing (dd/mm/yyyy) :");
					Date discontinued=df.parse(sc.nextLine());
					if(discontinued.after(computer.getIntroduced())){
						computer.setDiscontinued(discontinued);
						update=true;
					}
					else
						System.out.println("Date of discontinuing must be afer date of introducing");
				} catch (ParseException e){
					System.out.println("Wrong date");
				}
				break;
			case "z" :
				loop=false;
				break;
			case "v" :
				loop=false;
				break;
			}
		}
		if(update){
			computerDAO.update(computer);
		}
		
		} catch(NumberFormatException e){
			System.out.println("L'id must be an unsignied integer");
		}
	}
}
