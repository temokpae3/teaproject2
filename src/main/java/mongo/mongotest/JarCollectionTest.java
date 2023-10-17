package mongo.mongotest;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;
import com.mongodb.client.model.Updates;

import org.bson.Document;
import org.bson.conversions.Bson;
import static com.mongodb.client.model.Filters.eq;

import java.util.ArrayList;
import java.util.Scanner;

import com.tea.rest.Jar;
import com.tea.rest.Note;
import com.tea.rest.User;

public class JarCollectionTest {
    static MongoClient mongoClient = new MongoClient();
    static MongoDatabase db = mongoClient.getDatabase("dbTest");

//public static void main(String[] args) {
//    Scanner scanner = new Scanner(System.in);
//    int choice = scanner.nextInt();
//    User u = new User();
//    u.setToken_Id("1234");
//    Jar j = new Jar(u, "family");
//    j.setJarId(j.hashCode());
//    Note n = new Note("mongodb sucks", 1, "kgray3@oswego.edu");
//    int jarID = 268084911;
//
//
//    while(choice > 0) {
//        if(choice == 1) {
//            //addJar(j);
//        } else if(choice == 2) {
//            deleteJar("6789",jarID);
//        } else if(choice == 3) {
//            updateJarName("6789",jarID, "positivity family");
//        } else if(choice == 4) {
//            updateNumberOfNotes("6789",jarID, 12);
//        } else if (choice == 5) {
//            updateClosedStatus("6789", jarID, false);
//        } else if(choice == 6) {
//            updateJarAdmin("6789",jarID,"joe");
//        } else if(choice == 7) {
//            updateCollaborators("6789",jarID,"bastian","add");
//        } else if(choice == 8) {
//            updateCollaborators("6789",jarID,"bastian","remove");
//        } else if(choice == 9) {
//            updateOpeningTime("6789",jarID,"1:00");
//        }
//        choice = scanner.nextInt();
//    }
//
//    }

    /**
     * Method to update the jar name of a Jar document.
     * @param token_id      Google Authentication token_id
     * @param jar_id        unique jar hash code
     * @param name          String name of Jar
     */
    public static void updateJarName(String token_id,int jar_id, String name) {

        MongoCollection<Document> collection = db.getCollection("Users");
        
        Bson filter = Filters.and(Filters.eq("token_id", token_id), Filters.eq("myJars.jar_id", jar_id));
        Bson setUpdate = Updates.set("myJars.$.name", name);
        collection.updateOne(filter,setUpdate);
    }

    /**
     * Method to update number of notes in Jar Document in db.
     * @param token_id      Google Authentication token_id
     * @param jar_id        unique jar hash code
     * @param numberOfNotes     integer number of jar notes
     */
    public static void updateNumberOfNotes(String token_id, int jar_id, int numberOfNotes) {

        MongoCollection<Document> collection = db.getCollection("Users");
        
        Bson filter = Filters.and(Filters.eq("token_id", token_id), Filters.eq("myJars.jar_id", jar_id));
        Bson setUpdate = Updates.set("myJars.$.numberOfNotes", numberOfNotes);
        collection.updateOne(filter,setUpdate);
    }


    /**
     * Method to update the closed status in Jar Document in db.
     * @param token_id      Google Authentication token_id
     * @param jar_id        unique jar hash code
     * @param closed        jar status boolean
     */
    public static void updateClosedStatus(String token_id, int jar_id, boolean closed) {
        MongoCollection<Document> collection = db.getCollection("Users");
        
        Bson filter = Filters.and(Filters.eq("token_id", token_id), Filters.eq("myJars.jar_id", jar_id));
        Bson setUpdate = Updates.set("myJars.$.closed", closed);
        collection.updateOne(filter,setUpdate);
    }


    /**
     * Method to update jarAdmin field in Jar document in db.
     * @param token_id      Google Authentication token_id
     * @param jar_id        unique jar hash code id
     * @param jarAdmin      jar admin string
     */
    public static void updateJarAdmin(String token_id, int jar_id, String jarAdmin) {
        MongoCollection<Document> collection = db.getCollection("Users");
        
        Bson filter = Filters.and(Filters.eq("token_id", token_id), Filters.eq("myJars.jar_id", jar_id));
        Bson setUpdate = Updates.set("myJars.$.jarAdmin", jarAdmin);
        collection.updateOne(filter,setUpdate);
    }

    /**
     * Method to update the ArrayList of collaborators in a Jar object.
     * @param token_id      Google Authentication token_id
     * @param jar_id        unique jar hash code
     * @param collaborator      String email of the user
     * @param choice            either "add" or "remove"
     */
    public static void updateCollaborators(String token_id, int jar_id, String collaborator, String choice) {

        MongoCollection<Document> userCollection = db.getCollection("Users");

        if(choice.equalsIgnoreCase("add")) {
            Bson filter = Filters.and(Filters.eq("token_id", token_id), Filters.eq("myJars.jar_id", jar_id));
            Bson setUpdate = Updates.push("myJars.$.collaborators", collaborator);
        
            userCollection.updateOne(filter, setUpdate);
        } else {
            Bson filter = Filters.and(Filters.eq("token_id", token_id), Filters.eq("myJars.jar_id", jar_id));
            Bson delete = Updates.pull("myJars.$.collaborators", collaborator);
        
            userCollection.updateOne(filter, delete);
        }
     
    }


    /**
     * Method to update the opening time of a jar.
     * @param token_id      Google Authentication token_id
     * @param jar_id        unique jar hash code
     * @param time          String opening time
     */
    public static void updateOpeningTime(String token_id, int jar_id, String time) {
        MongoCollection<Document> collection = db.getCollection("Users");
        
        Bson filter = Filters.and(Filters.eq("token_id", token_id), Filters.eq("myJars.jar_id", jar_id));
        Bson setUpdate = Updates.set("myJars.$.time", time);
        collection.updateOne(filter,setUpdate);
    }




    /**
     * Method to add a Jar document to the "Jars" collection.
     * @param jar       Jar object to be added.
     */
    public Document addJar(Jar jar) {
        Document newJar = new Document();

        newJar.put("jar_id",jar.hashCode());
        newJar.put("name", jar.getName());
        newJar.put("numberOfNotes",jar.getNumberOfNotes());
        newJar.put("closed",jar.isClosed());
        newJar.put("jarAdmin",jar.getJarAdmin());
        newJar.put("collaborators",jar.getUsers());
        newJar.put("openingTime",jar.getOpeningTime());
        

        return newJar;
    }


    /**
     * Method to delete a jar from the database
     * @param token_id        jar hash code id
     */
    public static void deleteJar(String token_id, int jar_id) {
        MongoCollection<Document> userCollection = db.getCollection("Users");

        Bson filter = Filters.eq("token_id", token_id);
        Bson delete = Updates.pull("myJars", new Document("jar_id", jar_id));
        
        userCollection.updateOne(filter, delete);
    }
    
}
