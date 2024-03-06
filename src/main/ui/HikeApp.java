package ui;

import model.Hike;
import model.HikeList;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

// Hike tracker application
public class HikeApp {
    private static final String JSON_STORE = "./data/hikeList.json";
    private HikeList hikeList;
    private Scanner sc;

    // EFFECTS: runs the hike application
    public HikeApp() {
        runApp();
    }

    // MODIFIES: this
    // EFFECTS: processes initial user input
    private void runApp() {
        boolean keepGoing = true;
        String input;

        init();

        while (keepGoing) {
            displayMenu();
            input = sc.nextLine();
            input = input.toLowerCase();

            if (input.equals("q")) {
                keepGoing = false;
            } else {
                try {
                    processCommand(input);
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input");
                    sc.nextLine();
                }
            }
        }
        System.out.println("Exiting.");
    }

    // MODIFIES: this
    // EFFECTS: initializes list of hikes
    private void init() {
        hikeList = new HikeList();
        sc = new Scanner(System.in);
    }

    // EFFECTS: displays menu of option to user
    private void displayMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\t1 - Add hike");
        System.out.println("\t2 - Remove hike");
        System.out.println("\t3 - View hikes");
        System.out.println("\t4 - Sort hikes by length");
        System.out.println("\t5 - Sort hikes by name");
        System.out.println("\t6 - Sort hikes by rating");
        System.out.println("\t7 - Load hikes from file");
        System.out.println("\t8 - Save hikes to file");
        System.out.println("\tq -> quit");
    }

    // EFFECTS: processes user command
    private void processCommand(String input) {
        if (input.equals("1")) {
            addHike();
        } else if (input.equals("2")) {
            removeHike();
        } else if (input.equals("3")) {
            viewHike();
        } else if (input.equals("4")) {
            sortHikeByLength();
        } else if (input.equals("5")) {
            sortHikeByName();
        } else if (input.equals("6")) {
            sortHikeByRating();
        }  else if (input.equals("7")) {
            saveHikeList();
        } else if (input.equals("8")) {
            loadHikeList();
        } else {
            System.out.println("Invalid input");
        }
    }

    // MODIFIES: this
    // EFFECTS: prompts user for name, length, and rating of hike then
    //          adds a hike of specified name, length, and rating to hike list
    private void addHike() throws InputMismatchException {
        String name;
        int rating;
        double length;
        System.out.println("Enter the name of the hike");
        name = sc.nextLine();
        System.out.println("Enter the length of the hike");
        length = sc.nextDouble();
        System.out.println("Enter the rating of the hike from 1-10");
        rating = sc.nextInt();
        sc.nextLine();
        Hike newHike = new Hike(name, length, rating);
        System.out.println("Adding " + newHike + " to list!");
        hikeList.addHike(newHike);
    }

    // MODIFIES: this
    // EFFECTS: prompt user for index to remove, then
    //          removes hike at specified index
    private void removeHike() {
        int index;
        System.out.println("Enter index of hike to remove");
        System.out.println("Here is current list:");
        System.out.println(hikeList);
        index = sc.nextInt();
        sc.nextLine();
        try {
            hikeList.removeHike(index);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Invalid index");
        }
    }

    // EFFECTS: displays current hike list to user
    private void viewHike() {
        System.out.println("Here's your list of hikes! \n" + hikeList);
    }

    // MODIFIES: this
    // EFFECTS: sorts hike by length then displays sorted current hike list
    private void sortHikeByLength() {
        hikeList.sortByLength();
        System.out.println("Here's the updated list! \n" + hikeList);
    }

    // MODIFIES: this
    // EFFECTS: sorts hikes by name then displays sorted current hike list
    private void sortHikeByName() {
        hikeList.sortByName();
        System.out.println("Here's the updated list! \n" + hikeList);
    }

    // MODIFIES: this
    // EFFECTS: sorts hikes by rating then displays sorted current hike list
    private void sortHikeByRating() {
        hikeList.sortByRating();
        System.out.println("Here's the updated list! \n" + hikeList);
    }

    // EFFECTS: saves the workroom to file
    private void saveHikeList() {
        try {
            JsonWriter jsonWriter = new JsonWriter(JSON_STORE);
            jsonWriter.open();
            jsonWriter.write(hikeList);
            jsonWriter.close();
            System.out.println("Saved your hikes to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads workroom from file
    private void loadHikeList() {
        try {
            JsonReader jsonReader = new JsonReader(JSON_STORE);
            hikeList = jsonReader.read();
            System.out.println("Loaded your hikes from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }
}
