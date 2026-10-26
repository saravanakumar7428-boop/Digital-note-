
import java.io.*;
import java.util.*;

class Note implements Serializable {
    private static final long serialVersionUID = 1L;

    String title;
    String content;
    String filePath;

    Note(String title, String content, String filePath) {
        this.title = title;
        this.content = content;
        this.filePath = filePath;
    }

    void display() {
        System.out.println("Title: " + title);
        System.out.println("Content: " + content);
        System.out.println("File Path: " + filePath);
        System.out.println("----------------------------");
    }
}

public class NoteSystem {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ArrayList<Note> notes = new ArrayList<>();

        int choice = 0;

        do {
            System.out.println("\n--- Digital Note Organizer ---");
            System.out.println("1. Add Note");
            System.out.println("2. View Notes");
            System.out.println("3. Delete Note");
            System.out.println("4. Save Notes");
            System.out.println("5. Load Notes");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");

            // Handle invalid menu input
            if (!sc.hasNextInt()) {
                System.out.println("Please enter a number from 1 to 6.");
                sc.nextLine();
                continue;
            }

            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {

                case 1:
                    System.out.print("Enter Title: ");
                    String title = sc.nextLine();

                    System.out.print("Enter Content: ");
                    String content = sc.nextLine();

                    System.out.print("Enter File Path: ");
                    String path = sc.nextLine();

                    notes.add(new Note(title, content, path));
                    System.out.println("Note added successfully!");
                    break;

                case 2:
                    if (notes.isEmpty()) {
                        System.out.println("No notes available!");
                    } else {
                        for (Note n : notes) {
                            n.display();
                        }
                    }
                    break;

                case 3:
                    if (notes.isEmpty()) {
                        System.out.println("No notes available to delete!");
                        break;
                    }

                    System.out.print("Enter title to delete: ");
                    String delTitle = sc.nextLine();

                    boolean removed = notes.removeIf(
                        n -> n.title.equalsIgnoreCase(delTitle)
                    );

                    if (removed) {
                        System.out.println("Note deleted successfully!");
                    } else {
                        System.out.println("Note not found!");
                    }
                    break;

                case 4:
                    try (ObjectOutputStream oos =
                             new ObjectOutputStream(
                                 new FileOutputStream("notes.dat"))) {

                        oos.writeObject(notes);
                        System.out.println("Notes saved successfully!");

                    } catch (IOException e) {
                        System.out.println("Error saving notes: "
                                           + e.getMessage());
                    }
                    break;

                case 5:
                    try (ObjectInputStream ois =
                             new ObjectInputStream(
                                 new FileInputStream("notes.dat"))) {

                        Object obj = ois.readObject();

                        @SuppressWarnings("unchecked")
                        ArrayList<Note> loadedNotes =
                            (ArrayList<Note>) obj;

                        notes = loadedNotes;
                        System.out.println("Notes loaded successfully!");

                    } catch (FileNotFoundException e) {
                        System.out.println(
                            "No saved notes found! Save notes first."
                        );
                    } catch (IOException | ClassNotFoundException |
                             ClassCastException e) {
                        System.out.println("Error loading notes: "
                                           + e.getMessage());
                    }
                    break;

                case 6:
                    System.out.println("Exiting application...");
                    break;

                default:
                    System.out.println("Invalid choice! Enter 1 to 6.");
            }

        } while (choice != 6);

        sc.close();
    }
}
