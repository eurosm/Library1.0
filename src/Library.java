import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Library {

    static Scanner sc = new Scanner(System.in);

    //Opening txt file with book list
    private static final String FILE_PATH = "db";
    private static File dbFile;
    private static List<Book> books = new ArrayList<>();

    static String userInput;
    private static int bookIndex;


    public static void main(String[] args) throws Exception {

        if (!FileUtils.isFileExists(FILE_PATH)) {
            dbFile = FileUtils.createFile(FILE_PATH);
            dbFile.createNewFile();
        } else {
            dbFile = FileUtils.openFile(FILE_PATH);
        }
        System.out.println("Program biblioteczny wersja 1.0");
        //System.out.println("Aby wczytać spis książek wciśnij 'L', książkę dodać 'D', książkę usunąć 'U', zapisać baze danych 'S'");
        userInterface();
    }

    public static void userInterface() throws IOException {
        loadBooks();
        System.out.println("Wczytać spis 'L', dodać 'D', wyszukać 'F', usunąć 'U', zapisać dane 'S'");

        userInput = sc.nextLine();
        if (userInput.equals("L")) {
            booksList();
            userInterface();
        } else if (userInput.equals("D")) {
            newBook();
            saveBooks();
        } else if (userInput.equals("F")) {
            findBook();
        } else if (userInput.equals("S")) {
            saveBooks();
        } else if (userInput.equals("U")) {
            if (!books.isEmpty()) {
                deleteBook();
            } else {
                System.out.println("Z pustego i salomon nie naleje. Nie ma książek w bazie danych");
                userInterface();
            }

        } else {
            System.out.println("Niepoprawne polecenie. Sproboj ponownie: ");
            userInterface();
        }
    }

    private static void booksList() {
        if (books.isEmpty()) {
            System.out.println("Nie ma książek w bazie danych.");
        } else {
            System.out.println("Książki w bazie danych" + books);
        }
    }

    private static void newBook() {
        Book bookOne = new Book();
        bookOne.addTitle();
        bookOne.setIndex(books.size() + 1);
        books.add(bookOne);
    }

    private static void loadBooks() throws IOException {
        List<String> bookLines = Files.readAllLines(dbFile.toPath());
        books = bookLines.stream()
                .map(Book::new)
                .collect(Collectors.toList());
    }

    public static void saveBooks() throws IOException {

        try (FileWriter writer = new FileWriter("db");
             BufferedWriter bufferedWriter = new BufferedWriter(writer)) {
            int size = books.size();
            for (int i = 0; i < size; i++) {
                String str = books.get(i).toString();
                writer.write(str);
                if (i < size - 1) ;
                writer.write("\n");
            }


        } catch (IOException e) {
            System.err.format("IOException: %s%n", e);
        }
        userInterface();
    }

    public static void deleteBook() throws IOException {
        System.out.println("Wprowadź numer indexu książki która chcesz usunąć");
        System.out.println("Należy wprowadzić wartoś z zakresu 1 a " + books.size() + ".");

        try {
            bookIndex = Integer.parseInt(sc.nextLine());
            if (bookIndex <= 0) {
                System.out.println("Nie można wprowadzić wartości ujemnych ani zera.");
                deleteBook();
            } else
                bookIndex = bookIndex - 1;

            if (bookIndex < books.size()) {// check, list size must be greater than index
                books.remove(bookIndex);
                reevaluateIndex();

                if (books.isEmpty()) {
                    System.out.println("Brak książek w bazie.");
                } else {
                    System.out.println("Lista książek po zmianach: " + books);
                }
                saveBooks();

            } else {
                System.out.println("Index spoza zakresu");
                deleteBook();
            }

        } catch (NumberFormatException |
                IOException e) {
            System.out.println("Serio? Index musi być liczbą...");
            try {
                deleteBook();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }

    }

    private static void reevaluateIndex() {
        for (int i = bookIndex + 1; i <= books.size(); i++) {
            books.get(bookIndex).reSetIndex(bookIndex);
            bookIndex++;
        }

    }

    public static void findBook() throws IOException {
        System.out.println("Wprowadź szukaną frazę");
        String searchedPhrase = sc.nextLine();
        ArrayList<Integer> findsIndexes = new ArrayList<>();

        for (int i = 0; i < books.size(); ) {
//if ((books.get(i).toString()).contains(searchedPhrase)) {
            if ((books.get(i).returnTitle(0)).contains(searchedPhrase)) {
                String s = books.get(i).returnTitle(0);
                findsIndexes.add(i+1);
                System.out.println(s);
                //System.out.println("Znaleziono");
                i++;

            } else {
                i++;
                //System.out.println("Nie znaleziono.");
            }
        }
        if (0 < (findsIndexes.size())) {
            System.out.println("Znaleziono w pozycjach z indeksem: " + findsIndexes.toString());
            userInterface();
        } else {
            System.out.println("Nie znaleziono :( ");
            userInterface();
        }
    }
}
