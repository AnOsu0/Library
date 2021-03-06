package pl.javastart.library.app;
import pl.javastart.library.Exceptions.DataExportException;
import pl.javastart.library.Exceptions.DataImportException;
import pl.javastart.library.Exceptions.InvalidDataException;
import pl.javastart.library.Exceptions.NoSuchOptionException;
import pl.javastart.library.io.ConsolePrinter;
import pl.javastart.library.io.DataReader;
import pl.javastart.library.io.file.FileManagerBuilder;
import pl.javastart.library.io.file.FileManger;
import pl.javastart.library.model.Library;
import pl.javastart.library.model.Book;
import pl.javastart.library.model.Magazine;
import pl.javastart.library.model.Publication;

import java.util.InputMismatchException;
import java.util.Scanner;

public class LibraryControl {

    private ConsolePrinter printer = new ConsolePrinter();
    private DataReader dataReader = new DataReader(printer);
    private FileManger fileManger;
    private Library library;

    public LibraryControl() {
        fileManger = new FileManagerBuilder(dataReader,printer).build();
        try {
            library = fileManger.importData();
            printer.printLine("Zaimportowano dane z pliku");
        } catch (DataImportException | InvalidDataException e){
            printer.printLine(e.getMessage());
            printer.printLine("zainicjowano nową bazę:");
            library = new Library();
        }
    }


    private Scanner sc = new Scanner(System.in);

    public void controlLoop(){
        Option option;

        do {
            printOptions();
            option = getOption();
            switch (option) {
                case ADD_BOOK -> addBook();
                case ADD_MAGAZINE -> addMagazine();
                case PRINT_BOOK -> printBooks();
                case PRINT_MAGAZINE -> printMagzine();
                case DELETE_BOOK -> deleteBook();
                case DELETE_MAGAZINE -> deleteMagazine();
                case EXIT -> exit();
                default -> printer.printLine("Nie ma takiej opcji, wybierz ponownie opcję");
            }
        } while (option != Option.EXIT);
    }

    private Option getOption() {
        boolean optionOK = false;
        Option option = null;
       while (!optionOK){
           try {
               option = Option.createFromInt(dataReader.getInt());
               optionOK = true;
           } catch (NoSuchOptionException e){
               printer.printLine(e.getMessage());
           } catch (InputMismatchException e){
               printer.printLine("Wprowadzono wartość któa nie jest liczbą, podaj ponownie ");
           }
       }
       return option;
    }

    private void exit() {
        try {
            fileManger.exportData(library);
            printer.printLine("Eksport danych do pliku zakończony powodzeniem");
        } catch (DataExportException e){
            printer.printLine(e.getMessage());
        }
        printer.printLine("Koniec programu, papa");
        dataReader.close();
    }

    private void printBooks() {
        Publication[] publications = library.getPublications();
        printer.printbooks(publications);
    }

    private void addBook() {
        try {
            Book book = dataReader.readAndCreateBook();
            library.addPublication(book);
        } catch (InputMismatchException e){
            printer.printLine("Ne udało ię utworzyć książki, niepoprawne dane.");
        } catch (ArrayIndexOutOfBoundsException e){
            printer.printLine("Osiągnięto limit pojemności, nie można dodać kolejnej książki.");
        }
    }

    private void printMagzine() {
        Publication[] publications = library.getPublications();
        printer.printMagazine(publications);
    }

    private void addMagazine() {
        try {
        Magazine magazine = dataReader.readAndCreateMagazine();
        library.addPublication(magazine);
    } catch (InputMismatchException e){
        printer.printLine("Ne udało ię utworzyć magazynu, niepoprawne dane.");
    } catch (ArrayIndexOutOfBoundsException e){
        printer.printLine("Osiągnięto limit pojemności, nie można dodać kolejnego magazynu.");
    }
    }

    private void deleteMagazine() {
        try {
            Magazine magazine = dataReader.readAndCreateMagazine();
            if (library.removePublication(magazine))
                printer.printLine("Usunięto magazyn.");
            else
                printer.printLine("Brak wskazanego magazynu.");
        } catch (InputMismatchException e) {
            printer.printLine("Nie udało się utworzyć magazynu, niepoprawne dane");
        }
    }

    private void deleteBook() {
        try {
            Book book = dataReader.readAndCreateBook();
            if (library.removePublication(book))
                printer.printLine("Usunięto książkę.");
            else
                printer.printLine("Brak wskazanej książki.");
        } catch (InputMismatchException e) {
            printer.printLine("Nie udało się utworzyć książki, niepoprawne dane");
        }
    }


    private void printOptions() {
        printer.printLine("Wybierz opcję: ");
        for (Option temp: Option.values()) {
            printer.printLine(temp.toString());
        }
    }

    private enum Option {
        EXIT(0, "wyjście z programu"),
        ADD_BOOK(1, "dodanie nowej książki"),
        ADD_MAGAZINE(2, "dodanie nowego magazynu"),
        PRINT_BOOK(3, "wyświetl dostępne ksiązki"),
        PRINT_MAGAZINE(4, "wyświetl dostępne magazyny"),
        DELETE_BOOK(5,"usuń książkę"),
        DELETE_MAGAZINE(6,"usuń magazyn");

        private final int value;
        private final String description;

        Option(int value, String description) {
            this.value = value;
            this.description = description;
        }

        public int getValue() {
            return value;
        }

        public String getDescription() {
            return description;
        }

        @Override
        public String toString() {
            return value + " - " + description;
        }

        static Option createFromInt(int option) throws NoSuchOptionException{
            try {
                return Option.values()[option];
            } catch (ArrayIndexOutOfBoundsException e){
                throw new NoSuchOptionException("Brak opcji o ID " + option);
            }
        }
    }
}
