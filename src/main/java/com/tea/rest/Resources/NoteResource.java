package com.tea.rest.Resources;

import com.google.gson.Gson;
import com.tea.rest.Note;
import com.tea.rest.User;
import mongo.JarCollection;
import mongo.NoteCollection;
import mongo.UserCollection;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;

@Path("Note")
public class NoteResource {
    UserCollection userCollection = new UserCollection();
    JarCollection jarCollection = new JarCollection();
    NoteCollection noteCollection = new NoteCollection();

    /**
     * This method takes in a noteJson String and a token_id in the url
     * it makes a note object and adds it to the note collection
     * it then gets the user via token_id and updates the note count of selected jar based on note.get_jarId
     *
     * @param token_id the token_id of the user
     * @param noteJson a note in json form {"Note_id":2, "Jar_id":1409646767, "email":"test@wmail.com", "weighted_Rating":5, "time":"5:30", "message":"Hello World2"}
     */
    @POST
    @Path("{token_id}")
    public void postNote(@PathParam("token_id") String token_id, String noteJson) {
        Gson gson = new Gson();
        Note note = gson.fromJson(noteJson, Note.class);
        note.set_NoteId(note.get_Message().hashCode());//Overrides the given note id so that it is unique
        noteCollection.addNote(note);
        String userJson = userCollection.getUserDocument(token_id);
        User user = gson.fromJson(userJson, User.class);
        for (int i = 0; i < user.getMy_Jars().size(); i++) {
            if (user.getMy_Jars().get(i).getJar_id() == note.get_JarId()) {
                for(String x:user.getMy_Jars().get(i).getCollaborators()){
                    jarCollection.updateNumberOfNotes(x, note.get_JarId(), user.getMy_Jars().get(i).getNumberOfNotes() + 1);
                }
            }

        }
    }
//    For PUT REQUEST make sure you account for deleted note is  number of notes

    /**
     * Get notes via note_id
     * @param note_id
     * @return Response with either a json or a not found with the note_id
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("note_id/{note_id}")
    public Response getNote(@PathParam("note_id") String note_id) {
        if (noteCollection.getNoteDocument(Integer.parseInt(note_id)) != null) {
            String note = noteCollection.getNoteDocument(Integer.parseInt(note_id));
            return Response.status(Response.Status.FOUND).entity(note).build();
        }
        return Response.status(Response.Status.NOT_FOUND).entity("Note: " + note_id + " was not found.").build();

    }

    /**Get Notes via Jar id
     * @param jar_id
     * @return Response of either array of notes or empty array
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("jar_id/{jar_id}")
    public Response getNoteJar_id(@PathParam("jar_id") int jar_id) {
        ArrayList<String> notesList = noteCollection.getNotes(jar_id);
        Gson gson = new Gson();
        String noteArrayJson = gson.toJson(notesList);
        return Response.status(Response.Status.FOUND).entity(noteArrayJson).build();
    }
    /**Get Notes via Jar id
     * @param jar_id
     * @return Response of either array of notes or empty array
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("jar_id/{jar_id}/{rating}")
    public Response getNoteJar_id_Rating(@PathParam("jar_id") int jar_id, @PathParam("rating") int rating) {
        ArrayList<String> notesList = noteCollection.getNotes(jar_id,rating);
        Gson gson = new Gson();
        String noteArrayJson = gson.toJson(notesList);
        return Response.status(Response.Status.FOUND).entity(noteArrayJson).build();
    }
    /**
     * Get notes via jar id and author
     * @param jar_id
     * @return Response of either array of notes or empty array
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("jar_id/{jar_id}/{author}")
    public Response getNoteJar_id_Auth(@PathParam("jar_id") int jar_id, @PathParam("author") String author) {
        ArrayList<String> noteList = noteCollection.getNotes(jar_id,author);
        Gson gson = new Gson();
        String noteArrayJson = gson.toJson(noteList);
        return Response.status(Response.Status.FOUND).entity(noteArrayJson).build();
    }
    /**
     * Get notes via jar id, author, rating
     * @param jar_id
     * @return Response of either array of notes or empty array
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("jar_id/{jar_id}/{author}/{rating}")
    public Response getNoteJar_id_Auth_Rating(@PathParam("jar_id") int jar_id, @PathParam("author") String author,@PathParam("rating") int rating) {
        ArrayList<String> noteList = noteCollection.getNotes(jar_id,author, rating);
        Gson gson = new Gson();
        String noteArrayJson = gson.toJson(noteList);
        return Response.status(Response.Status.FOUND).entity(noteArrayJson).build();
    }

    @PUT
    @Path("note_id/{note_id}")
    public Response updateNote(@PathParam("note_id") int note_id, String note_json){
        Gson gson = new Gson();
        Note note = gson.fromJson(note_json,Note.class);
        noteCollection.updateAuthor(note_id, note.getEmail());
        noteCollection.updateRating(note_id, note.getWeighted_Rating());
        noteCollection.updateNoteTime(note_id, note.getTime());
        noteCollection.updateCharNum(note_id, note.getChar_Num());
        noteCollection.updateMessage(note_id,note.getMessage());
        return Response.status(Response.Status.ACCEPTED).build();
    }

    @DELETE
    @Path("note_id/{note_id}/{token_id}")
    public Response removeNote(@PathParam("note_id") int note_id, @PathParam("token_id") String token_id){
        Gson gson = new Gson();
        String userJson = userCollection.getUserDocument(token_id);
        User user = gson.fromJson(userJson, User.class);
        Note note = gson.fromJson(noteCollection.getNoteDocument(note_id), Note.class);
        if(note != null){
            for (int i = 0; i < user.getMy_Jars().size(); i++) {
                System.out.println(user.getMy_Jars().get(i).getJar_id());
                System.out.println(note.get_JarId());
                if (user.getMy_Jars().get(i).getJar_id() == note.get_JarId()) {
                    for(String x:user.getMy_Jars().get(i).getCollaborators()){
                        jarCollection.updateNumberOfNotes(x, note.get_JarId(), user.getMy_Jars().get(i).getNumberOfNotes() - 1);

                    }
                }

            }
            noteCollection.deleteNote(note_id);
            return Response.status(Response.Status.ACCEPTED).entity("Removed note with id: "+note_id).build();
        }return Response.status(Response.Status.ACCEPTED).entity("Note with id: "+note_id +" did not found").build();


    }
}

