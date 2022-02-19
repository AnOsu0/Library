package pl.javastart.library.model;

import pl.javastart.library.Exceptions.PublicationAlreadyExistException;
import pl.javastart.library.Exceptions.UserAlreadyExistException;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Library implements Serializable {

    Map<String, Publication> publications = new HashMap<>();
    Map<String, LibraryUser> users = new HashMap<>();

    public Map<String, Publication> getPublications() {
        return publications;
    }

    public Map<String, LibraryUser> getUsers() {
        return users;
    }

    public void addPublication(Publication publication) {
        if (publications.containsKey(publication.getTitle())) {
            throw new PublicationAlreadyExistException("Publikacja o takim tytule już isnieje " +
                   publication.getTitle() );
        }
        publications.put(publication.getTitle(), publication);
    }

    public void addUser(LibraryUser user){
        if (users.containsKey(user.getPesel())){
            throw new UserAlreadyExistException("Czytelnik o takim  numerze pesel już istnieje: "
            + user.getPesel());
        }
        users.put(user.getPesel(),user);
    }

    public boolean removePublication(Publication pub) {
      if (publications.containsValue(pub)){
          publications.remove(pub.getTitle());
          return true;
      }
        return false;
    }

}
