package com.ecxilys.vue;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Optional;
import java.util.Scanner;

import com.ecxilys.model.Company;
import com.ecxilys.model.CompanyList;
import com.ecxilys.model.Computer;
import com.ecxilys.model.Page;
import com.ecxilys.persistance.DAOException;
import com.ecxilys.persistance.DataBaseConnection;
import com.ecxilys.service.CompanyService;
import com.ecxilys.service.ComputerService;
import com.ecxilys.service.ServiceException;


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
						System.out.println("Wrong Company id Format");
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
		LocalDateTime introduced = null, discontinued = null;
		int numComp,resultId;
		Company company = null;
		CompanyService companyDAO = CompanyService.COMPANYSERVICE;
		ComputerService computerService = ComputerService.COMPUTERSERVICE; 
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		
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
			introduced = LocalDateTime.from(LocalDate.parse(tmp,formatter).atStartOfDay());
			
			System.out.print("Date of discontinuing at format dd/mm/yyyy :");
			tmp= sc.nextLine();
			if(tmp.compareTo("")!=0){
				discontinued = LocalDateTime.from(LocalDate.parse(tmp,formatter).atStartOfDay());
			}
			
			
		}
		try {
			resultId=computerService.add(new Computer(name, company, introduced, discontinued));
			System.out.println("New computer save, id : "+resultId);
		} catch (ServiceException e) {
			System.out.println(e.getMessage());
		} catch (DAOException e){
			System.out.println("Error when accessing to the base");
			e.printStackTrace();
		}

		
	}
	
	private static void showComputer(Scanner sc){
		int num;
        ComputerService compService=ComputerService.COMPUTERSERVICE;
        Optional<Computer> comp;
        
		System.out.print("\nID of the computer : ");
		
		num=Integer.parseInt(sc.nextLine());
		try{
			comp=compService.findById(num);
			if(comp.isPresent())
				System.out.println(comp.get());
			else
				System.out.println("Wrong number");
		} catch (DAOException e){
			System.out.println("Error when accessing to the base");
			e.printStackTrace();
		}
		
	}
	
	
	private static void delComputer(Scanner sc){
		int num;
        ComputerService compService=ComputerService.COMPUTERSERVICE;

        
		System.out.print("\nNumber of the computer : ");
		
		num=Integer.parseInt(sc.nextLine());
		
		try{
			compService.del(num);
		} catch (DAOException e){
			System.out.println("Error when accessing to the base");
			e.printStackTrace();
		}
		
	}
	
	private static void showCompanies(){
		CompanyService companyDAO=CompanyService.COMPANYSERVICE;
		
		try{
			CompanyList companyList = companyDAO.getCompanies();
			System.out.println("List of companies :");
			System.out.println(companyList);
		} catch (DAOException e){
			System.out.println("Error when accessing to the base");
			e.printStackTrace();
		}	
		
	}
	
	private static void showComputers(Scanner sc){
		boolean loop=true;
		String query ="";
		ComputerService computerService=ComputerService.COMPUTERSERVICE;
		
		try{
			Page<Computer> computerPage = new Page<Computer>(computerService.getComputers());
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
		} catch (DAOException e){
			System.out.println("Error when accessing to the base");
			e.printStackTrace();
		}	
		
	}
	
	private static void updateComputer(Scanner sc){
		int id, companyId;
		String string;
		boolean loop=true, update=false;
        ComputerService computerService=ComputerService.COMPUTERSERVICE;
        CompanyService companyDAO=CompanyService.COMPANYSERVICE;
        Optional<Computer> computer;
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        
		System.out.print("\nID of computer : ");
		try{
			id=Integer.parseInt(sc.nextLine());
		} catch(NumberFormatException e){
			System.out.println("The id must be an unsignied integer");
			return;
		}	
		
		
		
		try{
			computer=computerService.findById(id);
			if(computer.isPresent())
				System.out.println(computer.get());
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
					computer.get().setName(sc.nextLine());
					update=true;
					break;
				case "2":
					try{
						System.out.print("New company (id): ");
						companyId=Integer.parseInt(sc.nextLine());
						Optional<Company> company=companyDAO.findById(companyId);
						if(company.isPresent()){
							computer.get().setCompany(company.get());
							update=true;
						}
						else
							System.out.println("Wrong id");
					}catch(NumberFormatException e){
						System.out.println("ID must be an unsignied integer");
					}
					
					break;
				case "3" :
						try{
							System.out.print("Date of introducing (dd/mm/yyyy) :");					
							computer.get().setIntroduced(LocalDateTime.from(LocalDate.parse(sc.nextLine(),formatter).atStartOfDay()));
							update=true;
						}catch (DateTimeParseException e){
							System.out.println("Wrong format");
						}
					break;
				case "4" :
					try{
						System.out.print("Date of discontinuing (dd/mm/yyyy) :");
						LocalDateTime discontinued=LocalDateTime.from(LocalDate.parse(sc.nextLine(),formatter).atStartOfDay());
						if(discontinued.isAfter(computer.get().getIntroduced())){
							computer.get().setDiscontinued(discontinued);
							update=true;
						}
						else
							System.out.println("Date of discontinuing must be afer date of introducing");
					}catch (DateTimeParseException e){
						System.out.println("Wrong format");
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
				if(computerService.update(computer.get()))
					System.out.println("Computer "+id+" corectly updated");
				else
					System.out.println("Computer "+id+" not updated");
			}
		
		
		} catch (ServiceException e) {
			System.out.println("Computer "+id+" not updated");
			System.out.println(e.getMessage());
		} catch (DAOException e){
			System.out.println("Error when accessing to the base");
			e.printStackTrace();
		}
	}
}
