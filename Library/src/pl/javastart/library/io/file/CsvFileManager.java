package pl.javastart.library.io.file;

import pl.javastart.library.Exceptions.DataExportException;
import pl.javastart.library.Exceptions.DataImportException;
import pl.javastart.library.Exceptions.InvalidDataException;
import pl.javastart.library.model.Book;
import pl.javastart.library.model.Library;
import pl.javastart.library.model.Magazine;
import pl.javastart.library.model.Publication;

import java.io.*;
import java.util.Scanner;

public class CsvFileManager implements FileManger{
    private static final String FILE_NAME = "library.csv";

    @Override
    public Library importData() {
        Library library = new Library();
        try(
                Scanner fileReader = new Scanner(new File(FILE_NAME))
                ) {
            while (fileReader.hasNextLine()){
                String line = fileReader.nextLine();
                Publication publication = createObjectFromString(line);
                library.addPublication(publication);
            }
        } catch (FileNotFoundException e) {
            throw new DataImportException("Brak pliku " + FILE_NAME);
        }
        return library;
    }

    private Publication createObjectFromString(String line) {
        String[] split = line.split(";");
        String type = split[0];
        if (Book.TYPE.equals(type)){
            return createBook(split);
        } else if (Magazine.TYPE.equals(type)){
            return createMagazine(split);
        }
        throw new InvalidDataException("Nieznany typ publikacji " + type);
    }

    private Magazine createMagazine(String[] split) {
        String title = split[1];
        String publisher = split[2];
        String language = split[6];
        int year = Integer.valueOf(split[3]);
        int month = Integer.valueOf(split[4]);
        int day = Integer.valueOf(split[5]);
        return new Magazine(title,publisher,language,year,month,day);
    }

    private Book createBook(String[] split) {
        String title = split[1];
        String author = split[4];
        String publisher = split[2];
        String isbn = split[6];
        int year = Integer.valueOf(split[3]);
        int pages = Integer.valueOf(split[5]);

        return new Book(title,author,year,pages,publisher,isbn);
    }

    @Override
    public void exportData(Library library) {
        Publication[] publications = library.getPublications();
        try(
                var fw = new FileWriter(FILE_NAME,true);
                var writer = new BufferedWriter(fw);
                ) {
            for (Publication publication : publications) {
                writer.write(publication.toCSV());
                writer.newLine();
            }
        } catch (IOException e) {
            throw new DataExportException("Błąd zapisu danych do pliku " + FILE_NAME);
        }

    }
}
