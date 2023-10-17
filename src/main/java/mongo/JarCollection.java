package mongo;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;

import org.bson.Document;
import org.bson.conversions.Bson;



import com.tea.rest.Jar;

/**
 * The JarCollection class contains methods to retrieve, update, and delete data from
 * the "Jars" collection in the "posdb" MongoDB database.
 */
@ApplicationScoped
public class JarCollection {

    //Uses CDI to inject a MongoDB database.
    MongoProducer mongoProducer = new MongoProducer();
    MongoClient mongoClient = mongoProducer.createMongo();
    MongoDatabase db = mongoProducer.createDB(mongoClient);


    /**
     * Method to update the jar name of a Jar document.
     * @param token_id      Google Authentication token_id
     * @param jar_id        unique jar hash code
     * @param name          String name of Jar
     */
    public void updateJarName(String token_id,int jar_id, String name) {

        MongoCollection<Document> collection = db.getCollection("Users");
        
        Bson filter = Filters.and(Filters.eq("token_id", token_id), Filters.eq("my_Jars.jar_id", jar_id));
        Bson setUpdate = Updates.set("my_Jars.$.name", name);
        collection.updateOne(filter,setUpdate);
    }

    /**
     * Method to update number of notes in Jar Document in db.
     * @param token_id      Google Authentication token_id
     * @param jar_id        unique jar hash code
     * @param numberOfNotes     integer number of jar notes
     */
    public void updateNumberOfNotes(String token_id, int jar_id, int numberOfNotes) {

        MongoCollection<Document> collection = db.getCollection("Users");
        
        Bson filter = Filters.and(Filters.eq("token_id", token_id), Filters.eq("my_Jars.jar_id", jar_id));
        Bson setUpdate = Updates.set("my_Jars.$.numberOfNotes", numberOfNotes);
        collection.updateOne(filter,setUpdate);
    }



    /**
     * Method to update the closed status in Jar Document in db.
     * @param token_id      Google Authentication token_id
     * @param jar_id        unique jar hash code
     * @param closed        jar status boolean
     */
    public void updateClosedStatus(String token_id, int jar_id, boolean closed) {
        MongoCollection<Document> collection = db.getCollection("Users");
        
        Bson filter = Filters.and(Filters.eq("token_id", token_id), Filters.eq("my_Jars.jar_id", jar_id));
        Bson setUpdate = Updates.set("my_Jars.$.closed", closed);
        collection.updateOne(filter,setUpdate);
    }


    /**
     * Method to update jarAdmin field in Jar document in db.
     * @param token_id      Google Authentication token_id
     * @param jar_id        unique jar hash code id
     * @param jarAdmin      jar admin string
     */
    public void updateJarAdmin(String token_id, int jar_id, String jarAdmin) {
        MongoCollection<Document> collection = db.getCollection("Users");
        
        Bson filter = Filters.and(Filters.eq("token_id", token_id), Filters.eq("my_Jars.jar_id", jar_id));
        Bson setUpdate = Updates.set("my_Jars.$.jarAdmin", jarAdmin);
        collection.updateOne(filter,setUpdate);
    }

    /**
     * Method to update the ArrayList of collaborators in a Jar object.
     * @param token_id      Google Authentication token_id
     * @param jar_id        unique jar hash code
     * @param collaborator      String email of the user
     * @param choice            either "add" or "remove"
     */
    public void updateCollaborators(String token_id, int jar_id, String collaborator, String choice) {
        MongoCollection<Document> userCollection = db.getCollection("Users");

        if(choice.equalsIgnoreCase("add")) {
            Bson filter = Filters.and(Filters.eq("token_id", token_id), Filters.eq("my_Jars.jar_id", jar_id));
            Bson setUpdate = Updates.push("my_Jars.$.collaborators", collaborator);
        
            userCollection.updateOne(filter, setUpdate);
        } else {
            Bson filter = Filters.and(Filters.eq("token_id", token_id), Filters.eq("my_Jars.jar_id", jar_id));
            Bson delete = Updates.pull("my_Jars.$.collaborators", collaborator);
        
            userCollection.updateOne(filter, delete);
        }
     
    }


    /**
     * Method to update the opening time of a jar.
     * @param token_id      Google Authentication token_id
     * @param jar_id        unique jar hash code
     * @param time          String opening time
     */
    public void updateOpeningTime(String token_id, int jar_id, String time) {
        MongoCollection<Document> collection = db.getCollection("Users");
        
        Bson filter = Filters.and(Filters.eq("token_id", token_id), Filters.eq("my_Jars.jar_id", jar_id));
        Bson setUpdate = Updates.set("my_Jars.$.openingTime", time);
        collection.updateOne(filter,setUpdate);
    }




    /**
     * Method to add a Jar document to the "Jars" collection.
     * @param jar       Jar object to be added.
     */
    public Document addJar(Jar jar) {
        Document newJar = new Document();
        if(jar.getJar_id() != 1234){
            newJar.put("jar_id",jar.getJar_id());
        }else{
            newJar.put("jar_id",jar.hashCode());
        }
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
     * @param token_id      Google Authentication token_id
     * @param jar_id        jar hash code id
     */
    public void deleteJar(String token_id, int jar_id) {
        MongoCollection<Document> userCollection = db.getCollection("Users");

        Bson filter = Filters.eq("token_id", token_id);
        Bson delete = Updates.pull("my_Jars", new Document("jar_id", jar_id));
        
        userCollection.updateOne(filter, delete);

    }
}
