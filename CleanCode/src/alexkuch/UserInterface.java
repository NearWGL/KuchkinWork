package alexkuch;

import java.io.FileNotFoundException;

public interface UserInterface {
    void readFromFile(String filename)throws FileNotFoundException;
    void addMessage();
    void deleteMessage();
    void watchHistory();
    void searchByAuthor();
    void searchByWord();
    void searchByLecs();
    void viewHistory();
}
