package mongo.mongotest;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.model.Updates;

import org.bson.Document;
import org.bson.conversions.Bson;
import static com.mongodb.client.model.Filters.eq;


import com.tea.rest.*;
import com.tea.rest.User;

public class UserCollectionTest {
     static MongoClient mongoClient = new MongoClient();
     static MongoDatabase db = mongoClient.getDatabase("dbTest");

public static void main(String[] args) {
//        Scanner scanner = new Scanner(System.in);
//        int choice = scanner.nextInt();
//        User u = new User();
//        u.setToken_Id("6789");
//
//        while(choice > 0) {
//            if(choice == 1) {
//                addUser(u);
//            } else if(choice == 2) {
//                deleteUser("6789");
//            } else if(choice == 3) {
//                System.out.println(getUserDocument("6789"));
//            } else if(choice == 4) {
//                updateEmail("6789","kg3@oswego.edu");
//            } else if (choice == 5) {
//                updateTokenID("6789","2357");
//            } else if(choice == 6) {
//                updateNotify("6789", true);
//            } else if(choice == 7) {
//                Jar j = new Jar(u,"csc 380");
//                addJar("6789",j);
//            }
//            choice = scanner.nextInt();
//        }



    }

    /**
     * Method to return User object from the database via token
     * @param token     String user token
     * @return          User json String
     */
    public static String getUserDocument(String token) {
        MongoCollection<Document> collection = db.getCollection("Users");

        Bson projectionFields = Projections.fields (
            Projections.include("token_id","email","notify","myJars"),
//                Projections.include("token_id","email","notify"),
                Projections.excludeId());
        
        Document doc = collection.find(eq("token_id",token))
        .projection(projectionFields).first();
        String j;
        if(doc != null){
             j = doc.toJson();
        }else{
            j = null;
        }

        
        return j;
    }

    /**
     * Method to update the myJars field of a User Document. *MUST BE CALLED WHEN A NEW JAR IS CREATED*
     * @param tokenID       The user's Google Authentication token
     * @param jar        The ArrayList of jars a user is apart of
     */

    public static void addJar(String tokenID, Jar jar) {

        JarCollectionTest jarClass = new JarCollectionTest();
        
        Document newJar = jarClass.addJar(jar);
 
        MongoCollection<Document> userCollection = db.getCollection("Users");
        
        Bson filter = Filters.eq("token_id",tokenID);
        Bson setUpdate = Updates.push("myJars", newJar);

        userCollection.updateOne(filter, setUpdate);

    }

    /**
     * Method to update the tokenID field of a User Document.
     * @param oldToken     the user's current Google Authentication token
     * @param newToken     the user's updated Google Authentication token
     */
    public static void updateTokenID(String oldToken, String newToken) {

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
    public static void updateEmail(String tokenId, String email) {
        
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
    public static void updateNotify(String tokenId, boolean n) {
     
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
    public static void deleteUser(String tokenId) {
        MongoCollection<Document> users = db.getCollection("Users");
        Document query = new Document("token_id", tokenId);

        users.deleteOne(query);

    }

    /**
     * Method to add a user to the database
     * @param user      User object to be added
     */
    public static void addUser(User user) {
        MongoCollection<Document> users = db.getCollection("Users");
        Document newUser = new Document();
        newUser.put("token_id", user.getToken_id());
       // newUser.put("myJars", user.view_Jars());
        newUser.put("email",user.getEmail());
        newUser.put("notify",user.getNotify());
        users.insertOne(newUser);
    }
     
}
