package com.excilys.arnaud.vue;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Optional;
import java.util.Scanner;

import com.excilys.arnaud.model.Company;
import com.excilys.arnaud.model.Computer;
import com.excilys.arnaud.model.Page;
import com.excilys.arnaud.persistance.DAOException;
import com.excilys.arnaud.service.CompanyService;
import com.excilys.arnaud.service.ComputerService;
import com.excilys.arnaud.service.ServiceException;


public class CLI {
	private static final String SEPARATOR = "============================================";

	public static void main(String[] args) {
		String inputString = "";
		Scanner sc = new Scanner(System.in);
		


		
		System.out.println("CLI Computer-database");
		
		
		while(inputString.compareTo("q")!=0){
			System.out.println("\n"+SEPARATOR);
			System.out.println("Press 1 for add a computer");
			System.out.println("Press 2 for del a computer");
			System.out.println("Press 3 for update computer");
			System.out.println("Press 4 for show companies");
			System.out.println("Press 5 for show computers");
			System.out.println("Press 6 for show a computer");
			System.out.println("Press q for quit");
			System.out.println(SEPARATOR);
			inputString=sc.nextLine();
			switch(inputString){
			case "1" :
				addComputer(sc);
				break;
			case "2" :
				delComputer(sc);
				break;
			case "3" :
				updateComputer(sc);
				break;
			case "4" :
				showCompanies(sc);
				break;
			case "5" :
				showComputers(sc);
				break;
			case "6" :
				showComputer(sc);
				break;
			}
		}


	}

	
	private static void addComputer(Scanner sc){
		String name,tmp;
		LocalDateTime introduced = null, discontinued = null;
		int numComp,resultId;
		Company company = null;
		CompanyService companyDAO = CompanyService.COMPANYSERVICE;
		ComputerService computerService = ComputerService.COMPUTERSERVICE; 
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		
		
		try{
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
		
		

			resultId=computerService.add(new Computer(name, company, introduced, discontinued));
			System.out.println("New computer save, id : "+resultId);
		} catch (ServiceException e) {
			System.out.println(e.getMessage());
		} catch (DAOException e){
			System.out.println("Error when accessing to the base");
			e.printStackTrace();
		}  catch(DateTimeParseException e){
			System.out.println("Wrong Date Format "+e);
		} catch(NumberFormatException e){
			System.out.println("Wrong Company id Format");
		}

		
	}
	
	private static void showComputer(Scanner sc){
		int num;
        ComputerService compService=ComputerService.COMPUTERSERVICE;
        Optional<Computer> comp;
        
		System.out.print("\nID of the computer : ");
		
		
		try{
			num=Integer.parseInt(sc.nextLine());
			comp=compService.findById(num);
			if(comp.isPresent())
				System.out.println(comp.get());
			else
				System.out.println("Wrong number");
		} catch (DAOException e){
			System.out.println("Error when accessing to the base");
			e.printStackTrace();
		} catch (NumberFormatException e){
			System.out.println("Wrong Id");
		}
		
	}
	
	
	private static void delComputer(Scanner sc){
		int id;
        ComputerService compService=ComputerService.COMPUTERSERVICE;

        
		System.out.print("\nNumber of the computer : ");
		
		
		
		try{
			id=Integer.parseInt(sc.nextLine());
			compService.del(id);
			System.out.println("Computer "+id+" corectly deleted");
		} catch (DAOException e){
			System.out.println("Error when accessing to the base");
			e.printStackTrace();
		} catch (NumberFormatException e){
			System.out.println("Wrong Id");
		}
		
	}
	
	private static void showCompanies(Scanner sc){
		CompanyService companyService=CompanyService.COMPANYSERVICE;
		
		try{
			Page<Company> companyPage = new Page<Company>(companyService.getCompanies());
			System.out.println("List of companies :");
			showPage(sc, companyPage);
		} catch (DAOException e){
			System.out.println("Error when accessing to the base");
			e.printStackTrace();
		}	
		
	}
	
	
	private static void showComputers(Scanner sc){		
		try{
			ComputerService computerService=ComputerService.COMPUTERSERVICE;
			Page<Computer> computerPage= new Page<Computer>(computerService.getComputers());
			System.out.println("List of computers :");
			showPage(sc, computerPage);
		} catch (DAOException e){
			System.out.println("Error when accessing to the base");
			e.printStackTrace();
		}	
	}
	
	private static void showPage(Scanner sc, Page<?> page){
		boolean loop=true;
		String query ="";



		System.out.println(page.getPage());
		System.out.println("\t\t\t\tPage "+page.getCurrentPage()+"/"+page.getNbPage());
			
		while(loop){
			System.out.println(SEPARATOR);
			System.out.println("Quit : q  |  Prev : p  |  Next : n  |  First : f  |  Last : l  |  Go to page :");
			query = sc.nextLine();
			switch(query){
			case "q" :
				loop=false;
				break;
			case "p" :
				System.out.println(page.getPrevPage());
				System.out.println("\t\t\t\tPage "+page.getCurrentPage());
				break;
			case "n" :
				System.out.println(page.getNextPage());
				System.out.println("\t\t\t\tPage "+page.getCurrentPage());
				break;
			case "f" :
				System.out.println(page.getPageN(1));
				System.out.println("\t\t\t\tPage "+page.getCurrentPage());
				break;
			case "l" :
				System.out.println(page.getPageN(page.getNbPage()));
				System.out.println("\t\t\t\tPage "+page.getCurrentPage());
				break;
			default :
				try{
					int pageNumber = Integer.parseInt(query);
					System.out.println(page.getPageN(pageNumber));
					System.out.println("\t\t\t\tPage "+page.getCurrentPage());
				} catch(NumberFormatException e){
					System.out.println("Wrong page number");
				}
			}						
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
						computer.get().setDiscontinued(discontinued);
						update=true;
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
