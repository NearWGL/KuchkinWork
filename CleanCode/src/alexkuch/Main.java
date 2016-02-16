package alexkuch;

import javax.json.Json;
import javax.json.JsonObject;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        Chat a = new Chat();
        a.readFromFile("input.txt");
        System.out.println("Please write one of this command:\n !help\n addmessage\n deletemessage\n watchhistory\n" +
                " searchbyauthor(print an author you want to find)\n searchbyword(print a phrase you want to find)\n" +
                " searchbylecs(print a regular expression you want to find)\n viewhistory(print a period of time)\n");
        Scanner scanner = new Scanner(System.in);
        boolean b = true;
        while(b) {
            String str= scanner.nextLine();
            switch (str) {
                case "!help":
                    a.help();
                    break;
                case "addmessage":
                    a.addMessage();
                    break;
                case "deletemessage":
                    a.deleteMessage();
                    break;
                case "watchhistory":
                    a.watchHistory();
                    break;
                case "searchbyauthor":
                    a.searchByAuthor();
                    break;
                case "searchbyword":
                    a.searchByWord();
                    break;
                case "searchbylecs":
                    a.searchByLecs();
                    break;
                case "viewhistory":
                    a.viewHistory();
                    break;
                case "exit":
                    b = false;
                    break;
            }
        }
    }
}
