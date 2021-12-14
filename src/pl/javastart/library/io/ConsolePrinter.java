package pl.javastart.library.io;

import pl.javastart.library.model.Book;
import pl.javastart.library.model.Magazine;
import pl.javastart.library.model.Publication;

public class ConsolePrinter {

    public void printbooks(Publication[] publications) {
        int countBooks =0;
        for (Publication publication: publications) {
            if (publication instanceof Book) {
                printLine(publication.toString());
                countBooks++;
            }
        }
        if (countBooks==0){
            printLine("Brak ksiązek w bibliotece");
        }
    }

    public void printMagazine(Publication[] publications) {
        int countMagazine =0;
        for (Publication publication: publications) {
            if (publication instanceof Magazine) {
                printLine(publication.toString());
                countMagazine++;
            }
        }
        if (countMagazine==0){
            printLine("Brak magazynów w bibliotece");
        }
    }

    public void printLine(String text){
        System.out.println(text);
    }
}
