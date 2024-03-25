package ui;

import model.Hike;
import model.HikeList;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

// Hike tracker application
public class HikeApp extends JFrame {
    public static final int WIDTH = 1000;
    public static final int HEIGHT = 700;
    private static final String JSON_STORE = "./data/hikeList.json";
    private HikeList hikeList;
    private Scanner sc;

    // EFFECTS: runs the hike application
    public HikeApp() {
        super("Hike Tracker");
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
    // EFFECTS: initializes list of hikes and JFrame window
    private void init() {
        hikeList = new HikeList();
        setLayout(new BorderLayout()); // MOVE to display??/
        setMinimumSize(new Dimension(WIDTH, HEIGHT));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        addButtons();
        setVisible(true);
        sc = new Scanner(System.in);
    }

    private void addButtons() {
        JPanel buttonArea = new JPanel();
        buttonArea.setLayout(new BorderLayout());
        JPanel hikePanel = new JPanel();
        JButton viewButton = new JButton("View Hike");
        JButton addButton = new JButton("Add Hike");
        JButton sortButton = new JButton("Sort Hike");
        viewButton.addActionListener(new ViewHikeClickHandler());
        addButton.addActionListener(new AddHikeClickHandler());
        sortButton.addActionListener(new SortHikeClickHandler());
        hikePanel.add(viewButton);
        JPanel addPanel = new JPanel();
        addPanel.add(addButton);
        JPanel sortPanel = new JPanel();
        sortPanel.add(sortButton);
        buttonArea.add(hikePanel, BorderLayout.WEST);
        buttonArea.add(addPanel, BorderLayout.CENTER);
        buttonArea.add(sortPanel, BorderLayout.EAST);
        add(buttonArea);
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
            loadHikeList();
        } else if (input.equals("8")) {
            saveHikeList();
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

    // EFFECTS: saves the hike list to file
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
    // EFFECTS: loads hike list from file
    private void loadHikeList() {
        try {
            JsonReader jsonReader = new JsonReader(JSON_STORE);
            hikeList = jsonReader.read();
            System.out.println("Loaded your hikes from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    private class ViewHikeClickHandler implements ActionListener {

        // EFFECTS:
        @Override
        public void actionPerformed(ActionEvent e) {
            viewHike(); //TODO: Re implement based off text field
        }
    }

    private class AddHikeClickHandler implements ActionListener {

        // EFFECTS:
        @Override
        public void actionPerformed(ActionEvent e) {
            addHike(); // TODO: Re implement based off text field
        }
    }

    private class SortHikeClickHandler implements ActionListener {

        // EFFECTS:
        @Override
        public void actionPerformed(ActionEvent e) {
            //TODO: new menu for which kind of sorting
        }
    }
}
