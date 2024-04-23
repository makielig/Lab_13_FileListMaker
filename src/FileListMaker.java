import java.io.*;
import java.util.*;

public class FileListMaker {
    private static boolean needsToBeSaved = false;
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        List<String> itemList = new ArrayList<>();
        boolean quit = false;

        while (!quit) {
            // Display menu options
            System.out.println("\nMenu:");
            System.out.println("A: Add an item to the list");
            System.out.println("D: Delete an item from the list");
            System.out.println("V: View the list");
            System.out.println("O: Open a list file from disk");
            System.out.println("S: Save the current list to disk");
            System.out.println("C: Clear the current list");
            System.out.println("Q: Quit the program");
            System.out.print("Enter your choice: ");

            // Get user input
            String option = scanner.nextLine().toUpperCase();

            switch (option) {
                case "A":
                    addItem(itemList);
                    break;
                case "D":
                    deleteItem(itemList);
                    break;
                case "V":
                    viewList(itemList);
                    break;
                case "O":
                    openListFromFile(itemList);
                    break;
                case "S":
                    saveListToFile(itemList);
                    break;
                case "C":
                    clearList(itemList);
                    break;
                case "Q":
                    quit = true;
                    if (needsToBeSaved) {
                        if (promptToSave()) {
                            saveListToFile(itemList);
                        }
                    }
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
        scanner.close();
    }

    // Method to add an item to the list
    private static void addItem(List<String> itemList) {
        System.out.print("Enter an item to add: ");
        String newItem = scanner.nextLine();
        if (!newItem.isEmpty()) {
            itemList.add(newItem);
            needsToBeSaved = true;
        } else {
            System.out.println("Item cannot be empty.");
        }
    }

    // Method to delete an item from the list
    private static void deleteItem(List<String> itemList) {
        if (itemList.isEmpty()) {
            System.out.println("The list is empty.");
            return;
        }

        viewList(itemList);
        System.out.print("Enter the number of the item to delete: ");
        int index = Integer.parseInt(scanner.nextLine()) - 1;

        if (index >= 0 && index < itemList.size()) {
            itemList.remove(index);
            needsToBeSaved = true;
        } else {
            System.out.println("Invalid item number.");
        }
    }

    // Method to view the list
    private static void viewList(List<String> itemList) {
        if (itemList.isEmpty()) {
            System.out.println("The list is empty.");
        } else {
            System.out.println("Current List:");
            for (int i = 0; i < itemList.size(); i++) {
                System.out.println((i + 1) + ": " + itemList.get(i));
            }
        }
    }

    // Method to open a list file from disk
    private static void openListFromFile(List<String> itemList) {
        System.out.print("Enter the filename to open: ");
        String filename = scanner.nextLine();

        try (Scanner fileScanner = new Scanner(new File(filename))) {
            itemList.clear();
            while (fileScanner.hasNextLine()) {
                itemList.add(fileScanner.nextLine());
            }
            needsToBeSaved = false;
            System.out.println("List loaded successfully.");
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + filename);
        }
    }

    // Method to save the current list to disk
    private static void saveListToFile(List<String> itemList) {
        if (itemList.isEmpty()) {
            System.out.println("The list is empty. Nothing to save.");
            return;
        }

        System.out.print("Enter the filename to save: ");
        String filename = scanner.nextLine();

        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            for (String item : itemList) {
                writer.println(item);
            }
            needsToBeSaved = false;
            System.out.println("List saved successfully.");
        } catch (IOException e) {
            System.out.println("Error occurred while saving the list.");
        }
    }

    // Method to clear the current list
    private static void clearList(List<String> itemList) {
        if (!itemList.isEmpty()) {
            itemList.clear();
            needsToBeSaved = true;
            System.out.println("List cleared successfully.");
        } else {
            System.out.println("The list is already empty.");
        }
    }

    // Method to prompt the user to save unsaved changes
    private static boolean promptToSave() {
        System.out.print("Do you want to save unsaved changes? (Y/N): ");
        String choice = scanner.nextLine().toUpperCase();
        return choice.equals("Y");
    }
}
