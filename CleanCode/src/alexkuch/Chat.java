package alexkuch;

import javax.json.*;
import java.util.UUID;
import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Chat implements UserInterface {
    public LinkedList<Message> chat;
    public LinkedList<String> logs;

    public Chat() {
        this.chat = new LinkedList<>();
        this.logs = new LinkedList<>();
    }

    private class Message {
        private UUID id;
        private String author;
        private long timestamp;
        private String message;

        public Message() {

        }

        public Message(String author, String message) {
            id = UUID.randomUUID();
            this.author = author;
            this.message = message;
            timestamp = new Date().getTime();
        }

        public Message parseFromJson(JsonObject jsonObject) {
            author = jsonObject.getString("author");
            message = jsonObject.getString("message");
            timestamp = jsonObject.getJsonNumber("timestamp").longValue();
            id = UUID.fromString(jsonObject.getString("id"));
            return this;
        }


        public JsonObject getJson() {
            return Json.createObjectBuilder()
                    .add("id", id.toString())
                    .add("author", author)
                    .add("timestamp", timestamp)
                    .add("message", message).build();
        }

        @Override
        public String toString() {
            return "{" +
                    "id=" + id +
                    ", author='" + author + '\'' +
                    ", timestamp=" + timestamp +
                    ", message='" + message + '\'' +
                    '}';
        }
    }

    @Override
    public void readFromFile(String filename) throws FileNotFoundException {
        String content = new Scanner(new File(filename)).useDelimiter("\\Z").next();
        JsonReader reader = Json.createReader(new StringReader(content));
        JsonArray items = reader.readArray();
        reader.close();
        for (JsonValue item : items) {
            chat.add(new Message().parseFromJson((JsonObject) item));
        }
    }


    public void writeToFile(String filename) {
        try {
            PrintStream outStream = new PrintStream(filename);
            JsonArrayBuilder builder = Json.createArrayBuilder();
            for (Message item : chat) {
                builder = builder.add(item.getJson());
            }
            outStream.println(builder.build() + "\n");
            outStream.close();
        } catch (IOException e) {
            System.out.println("error");
        }
    }

    public void writeToFile(String filename, String string) {
        try {
            PrintStream outStream = new PrintStream(filename);
            JsonArrayBuilder builder = Json.createArrayBuilder();
            for(Message item : chat){
                builder = builder.add(item.getJson());
            }
            outStream.println(builder.build() + "\n");
            logs.add(string);
            for (String log : logs) {
                outStream.println(log + "\n");
            }
            outStream.close();
        }

        catch (IOException e){
            System.out.println("error");
        }


        /*try {
            FileWriter file = new FileWriter(new File(filename));
            file.write("[");
            int i;
            for(i = 0; i < chat.size()-1; ++i){
                file.write(chat.get(i).getJson().toString() + ",");
            }
            file.write(chat.get(i).getJson().toString());
            file.write("]");
            file.close();
        }
        catch(IOException e){
            System.out.println("Error");
        }
        */
    }

    @Override
    public void addMessage() {
        try {
            BufferedReader sc = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("How many messages do you want to add?");
            String count = sc.readLine();
            int b = Integer.valueOf(count);
            int a = chat.size();
            for(int i = 0; i < b; ++i) {
                System.out.println("Write your massage");
                String newmessage = sc.readLine();
                System.out.println("Who is the author of this message?");
                String newauthor = sc.readLine();
                Message newMess = new Message(newauthor, newmessage);
                chat.add(newMess);
            }
            int j = chat.size();
            writeToFile("input.txt");
            writeToFile("input.txt", j - a + " messages were added");

        }
        catch (IOException e){
            writeToFile("input.txt","Error");
            System.out.println("Error");
        }
    }
    public boolean isUUID(String string) {
        try {
            UUID.fromString(string);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    @Override
    public void deleteMessage() {
        try {
            BufferedReader sc = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("How many messages do you want to delete?");
            String count = sc.readLine();
            int b = Integer.valueOf(count);
            int a = chat.size();
            for (int i = 0; i < b; ++i) {
                System.out.println("Please write an ID you want to delete:");
                String str = sc.readLine();
                if(isUUID(str)) {
                    UUID ID = UUID.fromString(str);
                    for (int j = 0; j < chat.size(); ++j) {
                        if (chat.get(j).id.equals(ID)) {
                            chat.remove(j);
                            break;
                        }
                    }
                    writeToFile("input.txt");
                }
                else{
                    System.out.println("Incorrect ID, please, try again call the command and print correct ID:");
                    writeToFile("input.txt", "Incorrect string" + "\n");
                    break;
                }
            }
            int k = chat.size();
            writeToFile("input.txt", a - k + " messages were deleted");
        }
        catch(IOException e){
            System.out.println("Error");
            writeToFile("input.txt","Error");
        }
    }


    @Override
    public void watchHistory(){
        writeToFile("input.txt", "Watch of the History in chronological order");
        for (Message aChat : chat) {
            System.out.println(aChat.toString());
        }
    }
    @Override
    public void searchByAuthor(){
        try {
            BufferedReader sc = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Print an author by whom you want to search");
            String str = sc.readLine();
            int count = 0;
            for (Message aChat : chat) {
                if (aChat.author.equals(str)) {
                    System.out.println(aChat);
                    count++;
                }
            }
            writeToFile("input.txt", count + " searching messages");
            if(count == 0){
                System.out.println("This author haven't got messages");
            }
        }
        catch (Exception e){
            System.out.println("Error");
        }
    }
    @Override
     public void searchByWord(){
        try {
            BufferedReader sc = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Print a word by which you want to search");
            String str = sc.readLine();
            int count = 0;
            for (Message aChat : chat) {
                if (aChat.toString().contains(str)) {
                    System.out.println(aChat);
                    count++;
                }
            }
            writeToFile("input.txt", count + " searching messages");
            if(count == 0){
                System.out.println("Haven't got messages with this word");
            }
        }
        catch (Exception e){
            System.out.println("Error");
        }

    }

    @Override
    public void searchByLecs(){
        try {
            BufferedReader sc = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Print an regex by whom you want to search");
            String str = sc.readLine();
            Pattern pt = Pattern.compile(str);
            int count = 0;
            for (Message aChat : chat) {
                Matcher mt = pt.matcher(aChat.toString());
                if (mt.find()) {
                    System.out.println(aChat);
                    count++;
                }
            }
            writeToFile("input.txt", count + " searching messages");
            if(count == 0){
                System.out.println("Not found messages with such regex");
            }
        }
        catch (Exception e){
            System.out.println("Error");
        }

    }
    @Override
    public void viewHistory(){
        try {
            BufferedReader sc = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Print a period of time: " + "\n" + "From: ");
            String str = sc.readLine();
            System.out.println("To: ");
            String str2 = sc.readLine();
            long a = Long.valueOf(str);
            long b = Long.valueOf(str2);
            int count = 0;
            for (Message aChat : chat) {
                if (aChat.timestamp >= a & aChat.timestamp <= b) {
                    System.out.println(aChat);
                    count++;
                }
            }
            writeToFile("input.txt", count + " searching messages");
            if(count == 0){
                System.out.println("Not found messages till this period");
            }
        }
        catch (Exception e){
            System.out.println("Error");
        }
    }
    public void help(){
        System.out.println("Please write one of this command:\n !help\n addmessage\n deletemessage\n watchhistory\n" +
                " searchbyauthor(print an author you want to find)\n searchbyword(print a phrase you want to find)\n" +
                " searchbylecs(print a regular expression you want to find)\n viewhistory(print a period of time)\n");
    }

}

