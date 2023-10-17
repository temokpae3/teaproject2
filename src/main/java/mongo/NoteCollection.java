package mongo;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

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

import com.tea.rest.Note;

/**
 * The NoteCollection class contains methods to retrieve, update, and delete data from
 * the "Notes" collection in the "posdb" MongoDB database.
 */
@ApplicationScoped
public class NoteCollection {

    MongoProducer mongoProducer = new MongoProducer();
    MongoClient mongoClient = mongoProducer.createMongo();
    MongoDatabase db = mongoProducer.createDB(mongoClient);

    //TO-DO: Test

    /**
     * Method to return Note object from the database via token
     * @param note_id     unique note hash code
     * @return          note json String
     */
    public String getNoteDocument(int note_id) {
        MongoCollection<Document> collection = db.getCollection("Notes");

        Bson projectionFields = Projections.fields (
            Projections.include("message","charNum","time","rating","author","note_id","jar_id"),
            Projections.excludeId());
        
        Document doc = collection.find(eq("note_id",note_id))
        .projection(projectionFields).first();

        if(doc != null){
            return   doc.toJson();
        } return null;
    }

    /**
     * Method to return notes by jar.
     * @param jar_id        unique jar hash code
     * @return              ArrayList of String json objects
     */
    public ArrayList<String> getNotes(int jar_id) {
        MongoCollection<Document> collection = db.getCollection("Notes");
        ArrayList<String> notesArr = new ArrayList<>();

        Bson projectionFields = Projections.fields(
            Projections.include("message","charNum","time","rating","author","note_id"),
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
    public ArrayList<String> getNotes(int jar_id, String author) {
        MongoCollection<Document> collection = db.getCollection("Notes");
        ArrayList<String> notesArr = new ArrayList<>();

        Bson projectionFields = Projections.fields(
            Projections.include("message","charNum","time","rating","author","note_id"),
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
    public ArrayList<String> getNotes(int jar_id, String author, int rating) {
        MongoCollection<Document> collection = db.getCollection("Notes");
        ArrayList<String> notesArr = new ArrayList<>();

        Bson projectionFields = Projections.fields(
            Projections.include("message","charNum","time","rating","author","note_id"),
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
    public ArrayList<String> getNotes(int jar_id, int rating) {
        MongoCollection<Document> collection = db.getCollection("Notes");
        ArrayList<String> notesArr = new ArrayList<>();

        Bson projectionFields = Projections.fields(
            Projections.include("message","charNum","time","rating","author","note_id"),
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
    public void updateMessage(int note_id, String message) {
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
    public void updateCharNum(int note_id, int charNum) {
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
    public void updateNoteTime(int note_id, String time) {
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
    public void updateRating(int note_id, int rating) {
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
    public void updateAuthor(int note_id, String email) {
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
    public void addNote(Note note) {
        MongoCollection<Document> notes = db.getCollection("Notes");
        Document newNote = new Document();

        newNote.put("note_id", note.get_NoteId());
        newNote.put("jar_id", note.get_JarId());
        newNote.put("message", note.get_Message());
        newNote.put("charNum", note.getChar_Num());
        newNote.put("time", note.getTime());
        newNote.put("rating", note.getWeighted_Rating());
        newNote.put("author", note.getEmail());

       notes.insertOne(newNote);
    }

    /**
     * Method to delete a Note from the database.
     * @param id        unique note hash id
     */
    public void deleteNote(int id) {
        MongoCollection<Document> notes = db.getCollection("Notes");
        Document query = new Document("note_id",id);

        notes.deleteOne(query);
    }
}
