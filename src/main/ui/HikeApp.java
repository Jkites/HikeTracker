package ui;

import model.Hike;
import model.HikeList;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

// Hike tracker application
public class HikeApp extends JFrame {
    public static final int WIDTH = 1000;
    public static final int HEIGHT = 700;
    private static final String JSON_STORE = "./data/hikeList.json";
    private final HikeListener hikeListener = new HikeListener(this);
    private JTabbedPane tabPane;
    private HikeList hikeList;
    private Scanner sc;

    // EFFECTS: runs the hike application
    public HikeApp() {
        super("Hike Tracker");
        runApp();
    }

    // MODIFIES: this
    // EFFECTS: processes initial user input in console
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
        setLayout(new BorderLayout());
        setMinimumSize(new Dimension(WIDTH, HEIGHT));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        addTabs();
        setVisible(true);
        sc = new Scanner(System.in);
    }

    // MODIFIES: this
    // EFFECTS: initializes the tabs and each page
    private void addTabs() {
        JPanel hikePanel = createHikePanel();
        JPanel addPanel = createAddPanel();
        JPanel savePanel = createSavePanel();
        JPanel removePanel = createRemovePanel();
        tabPane = new JTabbedPane();
        tabPane.addTab("Hikes", null, hikePanel, "View hikes");
        tabPane.addTab("Add", null, addPanel, "Add a new hike");
        tabPane.addTab("Save/Load", null, savePanel, "Save or load data");
        tabPane.addTab("Remove", null, removePanel, "Remove a hike");
        add(tabPane);
    }

    // EFFECTS: creates hike tab
    private JPanel createHikePanel() {
        JPanel hikeLeftPanel = new JPanel();
        JScrollPane scrollPane = new JScrollPane(populateHikes());
        hikeLeftPanel.add(scrollPane);
        hikeLeftPanel.setLayout(new BoxLayout(hikeLeftPanel, BoxLayout.PAGE_AXIS));
        JPanel buttonPanel = getButtonPanel();
        JPanel hikePanel = new JPanel();
        hikePanel.setLayout(new BoxLayout(hikePanel, BoxLayout.LINE_AXIS));
        hikePanel.add(hikeLeftPanel);
        hikePanel.add(buttonPanel);
        return hikePanel;
    }

    // EFFECTS: creates button panel for hike tab
    private JPanel getButtonPanel() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.PAGE_AXIS));
        JButton sortLengthButton = new JButton("Sort by Length"); //still need to implement listener
        JButton sortNameButton = new JButton("Sort by Name");
        JButton sortRatingButton = new JButton("Sort by Rating");
        sortLengthButton.setActionCommand("SLength");
        sortNameButton.setActionCommand("SName");
        sortRatingButton.setActionCommand("SRating");
        sortLengthButton.addActionListener(hikeListener);
        sortNameButton.addActionListener(hikeListener);
        sortRatingButton.addActionListener(hikeListener);
        buttonPanel.add(sortLengthButton, BorderLayout.NORTH);
        buttonPanel.add(sortNameButton, BorderLayout.CENTER);
        buttonPanel.add(sortRatingButton, BorderLayout.SOUTH);
        return buttonPanel;
    }

    // EFFECTS: creates hike list text area representation
    private JTextArea populateHikes() {
        JTextArea hikeText = new JTextArea();
        hikeText.setEditable(false);
        hikeText.setText(viewHike());
        return hikeText;
    }

    // MODIFIES: HikeListener
    // EFFECTS: creates tab for adding new hikes
    private JPanel createAddPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        String[] labelStrings = {"Name: ", "Length (any number): ", "Rating (1-10): "};
        JTextField[] fields = new JTextField[labelStrings.length];
        JTextField nameField = hikeListener.getNameField();
        fields[0] = nameField;
        JTextField lengthField = hikeListener.getLengthField();
        JTextField ratingField = hikeListener.getRatingField();
        fields[1] = lengthField;
        fields[2] = ratingField;
        JLabel[] labels = associateLabels(fields, labelStrings);
        JButton saveButton = new JButton("Add");
        saveButton.setActionCommand("Add");
        saveButton.addActionListener(hikeListener);
        for (int i = 0; i < labels.length; i++) {
            panel.add(labels[i]);
            panel.add(fields[i]);
        }
        JLabel label = hikeListener.getImageLabel();
        label.setVisible(false);
        panel.add(saveButton);
        panel.add(label);
        return panel;
    }

    // EFFECTS: associates fields and labels and adds listeners
    private JLabel[] associateLabels(JTextField[] fields, String[] labelStrings) {
        JLabel[] labels = new JLabel[labelStrings.length];
        for (int i = 0; i < labelStrings.length; i++) {
            labels[i] = new JLabel(labelStrings[i], JLabel.TRAILING);
            labels[i].setLabelFor(fields[i]);
            fields[i].addActionListener(hikeListener);
        }
        return labels;
    }

    // EFFECTS: returns a tab for saving
    private JPanel createSavePanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        JButton saveToFileButton = new JButton("Save to file");
        JButton loadButton = new JButton("Load from file");
        saveToFileButton.setActionCommand("Save");
        loadButton.setActionCommand("Load");
        saveToFileButton.addActionListener(hikeListener);
        loadButton.addActionListener(hikeListener);
        panel.add(saveToFileButton);
        panel.add(loadButton);
        return panel;
    }

    // EFFECTS: returns a tab for removing hikes
    private JPanel createRemovePanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        JLabel label = new JLabel("Enter index of hike to remove: ");
        JTextField field = hikeListener.getIndexField();
        field.addActionListener(hikeListener);
        JButton removeButton = new JButton("Remove");
        removeButton.setActionCommand("Remove");
        removeButton.addActionListener(hikeListener);
        panel.add(label);
        panel.add(field);
        panel.add(removeButton);
        return panel;
    }

    // MODIFIES: this
    // EFFECTS: refreshes hike panel for user to see changes
    public void updateHikeDisplay() {
        int index = tabPane.indexOfTab("Hikes");
        tabPane.setComponentAt(index, createHikePanel());
    }

    // EFFECTS: opens a message dialog for the user
    public void sendMessage(String message) {
        JOptionPane.showMessageDialog(null, message, "User message", JOptionPane.INFORMATION_MESSAGE);
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
            // removeHike();
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
    public void addHike() throws InputMismatchException {
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
    // EFFECTS: adds a hike of specified name, length, and rating to hike list
    //          otherwise, sends message of error
    public void addHike(String name, String stringLength, String stringRating) {
        try {
            double length = Double.parseDouble(stringLength);
            int rating = Integer.parseInt(stringRating);
            Hike newHike = new Hike(name, length, rating);
            System.out.println("Adding " + newHike + " to list!");
            sendMessage("Adding " + newHike + " to list!");
            hikeList.addHike(newHike);
            hikeListener.getImageLabel().setVisible(true);
        } catch (NumberFormatException e) {
            sendMessage("Invalid Input");
        }
    }

    // MODIFIES: this
    // EFFECTS: prompt user for index to remove, then
    //          removes hike at specified index
    public void removeHike(String index) {
        try {
            int i = Integer.parseInt(index);
            try {
                hikeList.removeHike(i);
                sendMessage("Removed hike at index " + i);
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Invalid index");
                sendMessage("Invalid index");
            }
        } catch (NumberFormatException e) {
            sendMessage("Index not readable");
        }
        System.out.println("Enter index of hike to remove");
        System.out.println("Here is current list:");
        System.out.println(hikeList);
    }

    // EFFECTS: returns current hike list to user
    public String viewHike() {
        return "Here's your list of hikes! \n" + hikeList;
    }

    // MODIFIES: this
    // EFFECTS: sorts hike by length then displays sorted current hike list
    public void sortHikeByLength() {
        hikeList.sortByLength();
        System.out.println("Here's the updated list! \n" + hikeList);
    }

    // MODIFIES: this
    // EFFECTS: sorts hikes by name then displays sorted current hike list
    public void sortHikeByName() {
        hikeList.sortByName();
        System.out.println("Here's the updated list! \n" + hikeList);
    }

    // MODIFIES: this
    // EFFECTS: sorts hikes by rating then displays sorted current hike list
    public void sortHikeByRating() {
        hikeList.sortByRating();
        System.out.println("Here's the updated list! \n" + hikeList);
    }

    // EFFECTS: saves the hike list to file
    public void saveHikeList() {
        try {
            JsonWriter jsonWriter = new JsonWriter(JSON_STORE);
            jsonWriter.open();
            jsonWriter.write(hikeList);
            jsonWriter.close();
            System.out.println("Saved your hikes to " + JSON_STORE);
            sendMessage("Saved your hikes to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
            sendMessage("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads hike list from file
    public void loadHikeList() {
        try {
            JsonReader jsonReader = new JsonReader(JSON_STORE);
            hikeList = jsonReader.read();
            System.out.println("Loaded your hikes from " + JSON_STORE);
            sendMessage("Loaded your hikes from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
            sendMessage("Unable to read from file: " + JSON_STORE);
        }
    }

}
