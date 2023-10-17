package mongo;

import javax.enterprise.context.ApplicationScoped;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;
import com.mongodb.client.model.Sorts;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.model.Updates;

import org.bson.Document;
import org.bson.conversions.Bson;
import static com.mongodb.client.model.Filters.eq;

import java.util.ArrayList;

import com.tea.rest.Jar;
import com.tea.rest.User;

/**
 * The UserDb class contains methods to retrieve, update, and delete data from
 * the "Users" collection in the "posdb" MongoDB database.
 */

@ApplicationScoped
public class UserCollection {


    //Uses CDI to inject a MongoDB database.
    MongoProducer mongoProducer = new MongoProducer();
    MongoClient mongoClient = mongoProducer.createMongo();
    MongoDatabase db = mongoProducer.createDB(mongoClient);

    /**
     * Method to return User object from the database via token
     * @param token     String user token
     * @return          User json String
     */
    public String getUserDocument(String token) {



            if(db != null){
                MongoCollection<Document> collection = db.getCollection("Users");
                Bson projectionFields = Projections.fields (
                        Projections.include("token_id","email","notify","my_Jars"),
                        Projections.excludeId());

                Document doc = collection.find(eq("token_id",token))
                        .projection(projectionFields).first();

                        //System.out.println(doc);

                if(doc != null){
                   // System.out.println(doc);
                    String a = doc.toJson();
                   // System.out.println("A" + a);
                    return  a;

                    }else{
                    return null;}

            }else{
                return null;
            }


    }
    
    /**
     * Method to return list of emails to notify.
     * @return
     */
    public ArrayList<String> getUserEmailList() {
        MongoCollection<Document> collection = db.getCollection("Users");
        ArrayList<String> emailArr = new ArrayList<>();

        Bson projectionFields = Projections.fields(
            Projections.include("email"),
            Projections.excludeId()
        );
        BasicDBObject query = new BasicDBObject();
        query.append("notify", true);

        MongoCursor<Document> cursor = collection.find(query)
                    .projection(projectionFields)
                    .sort(Sorts.descending("title")).iterator();
            try {
                while(cursor.hasNext()) {
                    emailArr.add(cursor.next().toJson());
                }
            } finally {
                cursor.close();
            }

        return emailArr;
    }

    /**
     * Method to update the myJars field of a User Document. *MUST BE CALLED WHEN A NEW JAR IS CREATED*
     * @param tokenID       The user's Google Authentication token
     * @param jar        The ArrayList of jars a user is apart of
     */

    public void addJar(String tokenID, Jar jar) {

        JarCollection jarClass = new JarCollection();
        
        Document newJar = jarClass.addJar(jar);
 
        MongoCollection<Document> userCollection = db.getCollection("Users");
        
        
        Bson filter = Filters.eq("token_id",tokenID);
        Bson setUpdate = Updates.push("my_Jars", newJar);

        userCollection.updateOne(filter, setUpdate);
    }

    /**
     * Method to update the tokenID field of a User Document.
     * @param oldToken     the user's current Google Authentication token
     * @param newToken     the user's updated Google Authentication token
     */
    public void updateTokenID(String oldToken, String newToken) {

    MongoCollection<Document> collection = db.getCollection("Users");
    Document query = new Document().append("token_id", oldToken);

    Bson updates = Updates.combine (Updates.set("token_id", newToken));

    UpdateOptions options = new UpdateOptions().upsert(true);

    collection.updateOne(query, updates, options);

    }

    /**
     * Method to update the email field of a User Document.
     * @param tokenId   the user's Google Authentication token
     * @param email     the user's String email
     */
    public void updateEmail(String tokenId, String email) {
        
    MongoCollection<Document> collection = db.getCollection("Users");
    Document query = new Document().append("token_id", tokenId);

    Bson updates = Updates.combine (Updates.set("email", email));

    UpdateOptions options = new UpdateOptions().upsert(true);

    collection.updateOne(query, updates, options);
        

    }

    /**
     * Method to update the notify field of a User Document.
     * @param n     the user's notification status (true/false boolean)
     */
    public void updateNotify(String tokenId, boolean n) {
     
    MongoCollection<Document> collection = db.getCollection("Users");
    Document query = new Document().append("token_id", tokenId);

    Bson updates = Updates.combine (Updates.set("notify", n));

    UpdateOptions options = new UpdateOptions().upsert(true);

    collection.updateOne(query, updates, options);

    }

    /**
     * Method to delete a user from the database.
     * @param tokenId      Token of user to delete
     */
    public void deleteUser(String tokenId) {
        MongoCollection<Document> users = db.getCollection("Users");
        Document query = new Document("token_id", tokenId);

        users.deleteOne(query);

    }

    /**
     * Method to add a user to the database
     * @param user      User object to be added
     */
    public void addUser(User user) {
        MongoCollection<Document> users = db.getCollection("Users");
        Document newUser = new Document();
        newUser.put("token_id", user.getToken_id());
//        newUser.put("my_Jars", user.getMy_Jars());
        newUser.put("email",user.getEmail());
        newUser.put("notify",user.getNotify());
        users.insertOne(newUser);
    }

    
}
