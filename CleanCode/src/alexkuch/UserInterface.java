package alexkuch;


import javax.json.JsonObject;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;

public interface UserInterface {
    public void readFromFile(String filename)throws FileNotFoundException;
    public void addMessage();
    public void deleteMessage();
    public void watchHistory();
    public void searchByAuthor();
    public void searchByWord();
    public void searchByLecs();
    public void viewHistory();


}
