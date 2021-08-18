import java.util.ArrayList;
import java.util.Scanner;

public class Book extends ArrayList {

    static Scanner in = new Scanner(System.in);

    String title;
    int index;
    String returnTitle;

    public Book() {
    }

    public Book(String bookLine) {
        String[] bookData = bookLine.split(";");
        this.title = bookData[0];
        this.index = Integer.parseInt(bookData[1]);
    }

    public void addTitle() {
        System.out.println("Book Title?");
        title = in.nextLine();

        if (title.isEmpty()) {
            System.out.println("Tytuł nie może pozostać pusty. Wprowadź tytuł.");
            addTitle();
        } else if (title.contains(";")){
            System.out.println("Tytuł nie może zawierać symbolu ';'.");
            addTitle();
        } else {
            System.out.println("Wprowadzono tytuł: " + title);
        }
    }

    public void setIndex(int index) {
        System.out.println("Numer Indexu:" + index);
        this.index = index;

    }
    public void reSetIndex(int index) {
        this.index = index+1;

    }

    public String returnTitle(int index) {
        returnTitle = this.title;
        return returnTitle;

    }

        @Override
        public String toString(){
            return this.title + ";" + this.index;
        }
    }



