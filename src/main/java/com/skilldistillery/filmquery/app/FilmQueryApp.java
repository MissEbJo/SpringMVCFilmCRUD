package com.skilldistillery.filmquery.app;

import java.util.List;
import java.util.Scanner;

import com.skilldistillery.film.data.FilmDAO;
import com.skilldistillery.film.data.FilmDaoJDBCImpl;
import com.skilldistillery.film.entities.Film;

public class FilmQueryApp {
  
  FilmDAO db = new FilmDaoJDBCImpl();

  public static void main(String[] args) {
    FilmQueryApp app = new FilmQueryApp();
//    app.test();
    app.launch();
  }

//  private void test() {
//    Film film = db.findFilmById(115);
//    System.out.println(film);
////    Actor actor = db.findActorById(115);
////    System.out.println(actor);
//    List<Actor> actors = db.findActorsByFilmId(127);
//    System.out.println(actors);
//  }

  private void launch() {
    Scanner input = new Scanner(System.in);
    menuBanner();
    
    startUserInterface(input);
    
    input.close();
  }

  private void startUserInterface(Scanner input) {
	  
	  int choice = 0;
	  boolean keepGoing = true;
	  while(keepGoing) {
	  printMenu();
	  choice =input.nextInt();
	  input.nextLine();
	  switch (choice) {
	case 1:
		
		System.out.println("Enter the film number: ");
		int number = input.nextInt();
		Film film = db.findFilmById(number);
		if(film == null) {
			System.out.println("There is not title matching your request! Please try with a different film number!");
		}else {
	    System.out.println(film);
		}
		System.out.println("Do you wish to delete this film? (1)Yes or (2)No ");
		int innerChoice = input.nextInt();
		if(innerChoice == 1) {
			db.deleteFilm(film);
			System.out.println("Film deleted.");
		}else {
			System.out.println("No film was selected for deletion");
		}
		break;
	case 2:
		System.out.println("Enter a word to search for your film: ");
		String theWordToSearchFor = input.next();
		List<Film> film2 = db.findFilmsByKeyword(theWordToSearchFor);
		if(film2.isEmpty()) {
			System.out.println("There is not title matching your request! Please try with a different film number!");
		}else {
	    System.out.println(film2);
		}
		
		break;
	case 3:
		System.out.println("To create a film, enter the following information: ");
		System.out.println();
		Film filmIt = new Film();
		String title = getStringInput(input, "Enter the title: ");
		String description = getStringInput(input, "Enter the description: ");
		filmIt.setTitle(title);
		filmIt.setDescription(description);
		Film createdFilm = db.createFilm(filmIt);
		
		
		
		
		break;
		
	case 4:
		System.out.println("\n\n\tWhat is the film id of the description you would like to change?");
		System.out.print("\n\n\tEnter: ");
		int filmIdToEditDescription = 0;
		filmIdToEditDescription = input.nextInt();
		input.nextLine();
		Film filmToEdit = db.findFilmById(filmIdToEditDescription);
		if (filmToEdit == null) {
			System.out.println("\tCould not find film matching that id.");
		} else {
			System.out.println(filmToEdit);
		}
		System.out.println("\tWhat is the new description you would like to give this film?");
		System.out.print("\n\n\tEnter: ");
		String newDescription = input.nextLine();
		filmToEdit.setDescription(newDescription);
		filmToEdit = db.updateDescriptionOfSpecificFilm(filmToEdit, filmIdToEditDescription);
		System.out.println("\n\tYou have edited the description of this film.");
		
//		continueSearch = menus.searchAgainMenu(input);
		break;
	case 5:
		System.out.println("Thank you for choosing FilmIt! Goodbye!");
		keepGoing = false;
		break;

	default:
		break;
	}
	  
	 
	  }
  }
  private void menuBanner() {
	  System.out.println("\n"
	  		+ " ________ .-./`)   .---.     ,---.    ,---.        .-./`) ,---------.  \n"
	  		+ "|        |\\ .-.')  | ,_|     |    \\  /    |        \\ .-.')\\          \\ \n"
	  		+ "|   .----'/ `-' \\,-./  )     |  ,  \\/  ,  |        / `-' \\ `--.  ,---' \n"
	  		+ "|  _|____  `-'`\"`\\  '_ '`)   |  |\\_   /|  |         `-'`\"`    |   \\    \n"
	  		+ "|_( )_   | .---.  > (_)  )   |  _( )_/ |  |         .---.     :_ _:    \n"
	  		+ "(_ o._)__| |   | (  .  .-'   | (_ o _) |  |         |   |     (_I_)    \n"
	  		+ "|(_,_)     |   |  `-'`-'|___ |  (_,_)  |  |         |   |    (_(=)_)   \n"
	  		+ "|   |      |   |   |        \\|  |      |  |         |   |     (_I_)    \n"
	  		+ "'---'      '---'   `--------`'--'      '--'         '---'     '---'    \n"
	  		+ "                                                                       \n"
	  		+ "");
  }
  
  private void printMenu(){
	  
	  System.out.println();
	  System.out.println("************************************");
	  System.out.println("         Welcome to FilmIt          ");
	  System.out.println("************************************");
	  System.out.println("Please select from the following options(1-3):");
	  System.out.println("1) Search Film By Id");
	  System.out.println("2) Search Film By Keyword");
	  System.out.println("3) Add New Film");
	  System.out.println("4) Update Film");
	  System.out.println("5) Exit");
	  System.out.println();
	  System.out.println("Enter selection: ");
	  
  }
  
  private String getStringInput(Scanner input, String string) {
	  System.out.println(string);
	  String inputString = input.nextLine();
	  return inputString;
  }

}
