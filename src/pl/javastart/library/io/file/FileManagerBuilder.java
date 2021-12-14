package pl.javastart.library.io.file;

import pl.javastart.library.Exceptions.NoSuchFileTypeException;
import pl.javastart.library.io.ConsolePrinter;
import pl.javastart.library.io.DataReader;

public class FileManagerBuilder {
    DataReader reader;
    ConsolePrinter printer;

    public FileManagerBuilder(DataReader reader, ConsolePrinter printer) {
        this.reader = reader;
        this.printer = printer;
    }

    public FileManger build (){
        printer.printLine("Wybierz format danych:");
        FileType fileType = getFileType();
        switch (fileType){
            case SERIAL:
                return new SerializableFIleManager();
            case CSV:
                return new CsvFileManager();
            default:
                throw new NoSuchFileTypeException("Nieobsługiwany format danych");
        }
    }

    private FileType getFileType() {
        boolean typeOk = false;
        FileType result = null;
        do{
            printTypes();
            String type = reader.getString().toUpperCase();
            try{
               result = FileType.valueOf(type);
               typeOk = true;

            } catch (IllegalArgumentException e){
                printer.printLine("Niepoprawny typ danych, wybierz ponownie");
            }
        } while (!typeOk);
        return result;
    }

    private void printTypes() {
        for (FileType type: FileType.values()) {
            printer.printLine(type.name());
        }
    }
}
