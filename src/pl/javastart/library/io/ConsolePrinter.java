package pl.javastart.library.io;

import pl.javastart.library.model.*;

import java.util.Collection;

public class ConsolePrinter {

    public void printbooks(Collection<Publication> publications) {
        long count = publications.stream()
                .filter(p -> p instanceof Book)
                .map(Publication::toString)
                .peek(this::printLine)
                .count();
        if (count==0){
            printLine("Brak ksiązek w bibliotece");
        }
    }

    public void printMagazine(Collection<Publication>  publications) {
        long count = publications.stream()
                .filter(p -> p instanceof Magazine)
                .map(Publication::toString)
                .peek(this::printLine)
                .count();
        if (count==0){
            printLine("Brak magazynów w bibliotece");
        }
    }

    public void printUsers(Collection<LibraryUser> users) {
       users.stream().map(User::toString).forEach(this::printLine);
    }

    public void printLine(String text){
        System.out.println(text);
    }
}
