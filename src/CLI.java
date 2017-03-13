import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import dao.MySQLAccess;
import modele.Companies;
import modele.Computer;
import modele.ComputerList;

public class CLI {

	public static void main(String[] args) {
		String inputString = "";
		Scanner sc = new Scanner(System.in);
		
		MySQLAccess sqla = MySQLAccess.getInstance();
		sqla.loadCompanies();
		sqla.loadComputer();
		
		System.out.println("CLI Computer-database");
		
		
		while(inputString.compareTo("q")!=0){
			System.out.println("\nPress 1 for add a computer");
			System.out.println("Press 2 for show a computer");
			System.out.println("Press q for quit");
			inputString=sc.nextLine();
			if(inputString.compareTo("1")==0)
				try{
					addComputer(sc);
				} catch(ParseException e){
					System.out.println("Wrong Date Format "+e);
				} catch(NumberFormatException e){
					System.out.println("Wrong Companie Format");
				}
						
			else if(inputString.compareTo("2")==0)
				try{
					showComputer(sc);
				} catch(IndexOutOfBoundsException e){
					System.out.println("This computeur doesn't exist");
				}
		}
		sqla.close();

	}

	
	private static void addComputer(Scanner sc) throws ParseException, NumberFormatException{
		String name,tmp;
		Date introduced = null, discontinued = null;
		int numComp;
		ComputerList cl = ComputerList.getInstance();
		Companies comp = Companies.getInstance();
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		
		System.out.print("\nName : ");
		name= sc.nextLine();
		
		System.out.print("Numero of the company (refer to companie list) : ");
		numComp=Integer.parseInt(sc.nextLine())-1;
		if(numComp<0 || numComp>=comp.size())
			throw new NumberFormatException();
		
		
		
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
		cl.add(new Computer(name, comp.getCompanies(numComp), introduced, discontinued));
		
	}
	
	private static void showComputer(Scanner sc){
		int num;
		ComputerList cl = ComputerList.getInstance();
		System.out.print("\nNumber of the computer : ");
		
		num=Integer.parseInt(sc.nextLine())-1;
		if(num>=0 || num<cl.size())
			System.out.println(cl.get(num));
		else
			System.out.println("Wrong number");
		
	}
}
