package mongo.mongotest;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Projections;
import com.mongodb.client.model.Sorts;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.model.Updates;

import org.bson.Document;
import org.bson.conversions.Bson;

import static com.mongodb.client.model.Filters.eq;

import java.util.ArrayList;
import java.util.Scanner;

import com.tea.rest.Note;
import com.tea.rest.User;
public class NoteCollectionTest {

    static MongoClient mongoClient = new MongoClient();
    static MongoDatabase db = mongoClient.getDatabase("posdb");

public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    int choice = scanner.nextInt();
    User u = new User();
    u.setToken_id("1234");
    Note n = new Note("mongodb sucks", 1, "kgray3@oswego.edu");
    int noteID = 123456;

    while(choice > 0) {
        if(choice == 1) {
            addNote(n);
        } else if(choice == 2) {
            deleteNote(n.get_NoteId());
        } else if(choice == 3) {
           updateMessage(123456, "test");
        } else if(choice == 4) {
            updateCharNum(noteID, 10);
        } else if (choice == 5) {
            updateNoteTime(noteID,"1:00");
        } else if(choice == 6) {
            updateRating(noteID,4);
        } else if(choice == 7) {
            updateAuthor(noteID,"blah@gmail.com");
        } else if(choice == 8) {
            getNoteDocument(noteID);
        } else if(choice == 9) {
            System.out.println("NOTES BY JAR");
            ArrayList<String> s = getNotes(789);
            for(String j : s) {
                System.out.println(j);
            }
            System.out.println();

            System.out.println("NOTES BY JAR AND AUTHOR");
            ArrayList<String> t = getNotes(789,"kgray3@oswego.edu");
            for(String j : t) {
                System.out.println(j);
            }
            System.out.println();

            System.out.println("NOTES BY JAR|AUTHOR|RATING");
            ArrayList<String> r = getNotes(567,"kgray3@oswego.edu",1);
            for(String j : r) {
                System.out.println(j);
            }
            System.out.println();

            System.out.println("NOTES BY JAR|RATING");
            ArrayList<String> f = getNotes(789,5);
            for(String j : f) {
                System.out.println(j);
            }
            System.out.println();

        }
        choice = scanner.nextInt();
    }
        
    }

    /**
     * Method to return Note object from the database via token
     * @param note_id     unique note hash code
     * @return          note json String
     */
    public static String getNoteDocument(int note_id) {
        MongoCollection<Document> collection = db.getCollection("Notes");

        Bson projectionFields = Projections.fields (
            Projections.include("message","charNum","time","rating","author"),
            Projections.excludeId());
        
        Document doc = collection.find(eq("note_id",note_id))
        .projection(projectionFields).first();

        String j = doc.toJson();
        
        return j;
    }

    /**
     * Method to return notes by jar.
     * @param jar_id        unique jar hash code
     * @return              ArrayList of String json objects
     */
    public static ArrayList<String> getNotes(int jar_id) {
        MongoCollection<Document> collection = db.getCollection("Notes");
        ArrayList<String> notesArr = new ArrayList<>();

        Bson projectionFields = Projections.fields(
            Projections.include("message","charNum","time","rating","author"),
            Projections.excludeId()
        );

        MongoCursor<Document> cursor = collection.find(eq("jar_id", jar_id))
                    .projection(projectionFields)
                    .sort(Sorts.descending("title")).iterator();
            try {
                while(cursor.hasNext()) {
                    notesArr.add(cursor.next().toJson());
                }
            } finally {
                cursor.close();
            }

        return notesArr;
    }

    /**
     * Method to return notes by jar and author.
     * @param jar_id        unique jar hash code
     * @param author        String note author
     * @return              ArrayList of String json objects
     */
    public static ArrayList<String> getNotes(int jar_id, String author) {
        MongoCollection<Document> collection = db.getCollection("Notes");
        ArrayList<String> notesArr = new ArrayList<>();

        Bson projectionFields = Projections.fields(
            Projections.include("message","charNum","time","rating","author"),
            Projections.excludeId()
        );
        BasicDBObject query = new BasicDBObject();
        query.append("jar_id", jar_id);
        query.append("author", author);

        MongoCursor<Document> cursor = collection.find(query)
                    .projection(projectionFields)
                    .sort(Sorts.descending("title")).iterator();
            try {
                while(cursor.hasNext()) {
                    notesArr.add(cursor.next().toJson());
                }
            } finally {
                cursor.close();
            }

        return notesArr;
    }

    /**
     * Method to return notes by jar, author, and rating.
     * @param jar_id        unique jar hash code
     * @param author        String note author
     * @param rating        int note rating
     * @return              ArrayList of String json objects
     */
    public static ArrayList<String> getNotes(int jar_id, String author, int rating) {
        MongoCollection<Document> collection = db.getCollection("Notes");
        ArrayList<String> notesArr = new ArrayList<>();

        Bson projectionFields = Projections.fields(
            Projections.include("message","charNum","time","rating","author"),
            Projections.excludeId()
        );

        BasicDBObject query = new BasicDBObject();
        query.append("jar_id", jar_id);
        query.append("author", author);
        query.append("rating", rating);

        MongoCursor<Document> cursor = collection.find(query)
                    .projection(projectionFields)
                    .sort(Sorts.descending("title")).iterator();
            try {
                while(cursor.hasNext()) {
                    notesArr.add(cursor.next().toJson());
                }
            } finally {
                cursor.close();
            }

        return notesArr;
    }

    /**
     * Method to return notes by jar and rating.
     * @param jar_id        unique jar hash code
     * @param rating        int note rating
     * @return              ArrayList of String json objects
     */
    public static ArrayList<String> getNotes(int jar_id, int rating) {
        MongoCollection<Document> collection = db.getCollection("Notes");
        ArrayList<String> notesArr = new ArrayList<>();

        Bson projectionFields = Projections.fields(
            Projections.include("message","charNum","time","rating","author"),
            Projections.excludeId()
        );

        BasicDBObject query = new BasicDBObject();
        query.append("jar_id", jar_id);
        query.append("rating", rating);

        MongoCursor<Document> cursor = collection.find(query)
                    .projection(projectionFields)
                    .sort(Sorts.descending("title")).iterator();
            try {
                while(cursor.hasNext()) {
                    notesArr.add(cursor.next().toJson());
                }
            } finally {
                cursor.close();
            }

        return notesArr;
    }


    /**
     * Method to update the message of a Note in db.
     * @param note_id       unique note hash code
     * @param message       String note message
     */
    public static void updateMessage(int note_id, String message) {
        MongoCollection<Document> collection = db.getCollection("Notes");
        Document query = new Document().append("note_id",note_id);

        Bson updates = Updates.combine(Updates.set("message", message));

        UpdateOptions options = new UpdateOptions().upsert(true);

        collection.updateOne(query, updates, options);
    
    }

    /**
     * Method to update the charNum of a Note in db.
     * @param note_id       unique note hash code
     * @param charNum       int character number of message
     */
    public static void updateCharNum(int note_id, int charNum) {
        MongoCollection<Document> collection = db.getCollection("Notes");
        Document query = new Document().append("note_id",note_id);

        Bson updates = Updates.combine(Updates.set("charNum", charNum));

        UpdateOptions options = new UpdateOptions().upsert(true);

        collection.updateOne(query, updates, options);
    }

    /**
     * Method to update the time a Note was posted in db.
     * @param note_id       unique note hash code
     * @param time          String note time
     */
    public static void updateNoteTime(int note_id, String time) {
        MongoCollection<Document> collection = db.getCollection("Notes");
        Document query = new Document().append("note_id",note_id);

        Bson updates = Updates.combine(Updates.set("time", time));

        UpdateOptions options = new UpdateOptions().upsert(true);

        collection.updateOne(query, updates, options);
    }

    /**
     * Method to update the rating of a Note in db.
     * @param note_id       unique note hash code
     * @param rating        int note rating
     */
    public static void updateRating(int note_id, int rating) {
        MongoCollection<Document> collection = db.getCollection("Notes");
        Document query = new Document().append("note_id",note_id);

        Bson updates = Updates.combine(Updates.set("rating", rating));

        UpdateOptions options = new UpdateOptions().upsert(true);

        collection.updateOne(query, updates, options);
    }

    /**
     * Method to update the author of a Note in db.
     * @param note_id       unique note hash code
     * @param email         String email of note author
     */
    public static void updateAuthor(int note_id, String email) {
        MongoCollection<Document> collection = db.getCollection("Notes");
        Document query = new Document().append("note_id",note_id);

        Bson updates = Updates.combine(Updates.set("author", email));

        UpdateOptions options = new UpdateOptions().upsert(true);

        collection.updateOne(query, updates, options);
    }

    

    /**
     * Method to add a Note to the database.
     * @param note      Note object.
     */
    public static Document addNote(Note note) {
        MongoCollection<Document> notes = db.getCollection("Notes");
        Document newNote = new Document();

        newNote.put("note_id", note.hashCode());
        newNote.put("message", note.get_Message());
        newNote.put("charNum", note.getChar_Num());
        newNote.put("time", note.getTime());
        newNote.put("rating", note.getWeighted_Rating());
        newNote.put("author", note.getEmail());

       notes.insertOne(newNote);

       return newNote;
    }

    /**
     * Method to delete a Note from the database.
     * @param id        unique note hash id
     */
    public static void deleteNote(int id) {
        //TO-DO delete jar from Jar array
        MongoCollection<Document> notes = db.getCollection("Notes");
        Document query = new Document("note_id",id);

        notes.deleteOne(query);
    }
    
    
}

