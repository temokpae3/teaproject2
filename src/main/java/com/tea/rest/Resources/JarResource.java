package com.tea.rest.Resources;

import com.google.gson.Gson;
import com.tea.rest.Jar;
import com.tea.rest.Note;
import com.tea.rest.Notification;
import com.tea.rest.User;
import mongo.JarCollection;
import mongo.NoteCollection;
import mongo.UserCollection;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.regex.Pattern;

/**
 * This class sends GET and POST REQUEST for the Jar Class
 */
@Path("Jar")
public class JarResource {
    UserCollection userCollection = new UserCollection();
    JarCollection jarCollection = new JarCollection();
    NoteCollection noteCollection = new NoteCollection();

    @Path("{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getJar(@PathParam("id") String id){
        if(userCollection.getUserDocument(id) != null){
            String userString = userCollection.getUserDocument(id);
            Gson gson = new Gson();
            User user = gson.fromJson(userString, User.class);

            if(!user.getMy_Jars().isEmpty()) {
                ArrayList<Jar> myJars = user.getMy_Jars();
                return Response.status(Response.Status.FOUND).entity(myJars).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).entity(user).build();
            }
        }
            return Response.status(Response.Status.NOT_FOUND).entity("User not Found").build();
    }

    /**
     * Works under the assumption that in the jarJson the Jar_id is a valid id for the user at ID
     * @param id token_id
     * @param jarJson a json to update a jar
     */
    @Path("{id}/add")
    @PUT
    public void updateJar(@PathParam("id") String id, String jarJson ){
        Gson gson = new Gson();
        Jar jarUpdate = gson.fromJson(jarJson, Jar.class);
        String userString = userCollection.getUserDocument(id);
        User user = gson.fromJson(userString, User.class);
        ArrayList<String> temp = new ArrayList<>(jarUpdate.getCollaborators());
        ArrayList<String> temp2 = new ArrayList<>(jarUpdate.getCollaborators());
            for (Jar jar : user.getMy_Jars()) {
                if (jar.getJar_id() == jarUpdate.getJar_id()) {
                    temp.removeAll(jar.getCollaborators());
                    for (String colab2 : temp) {
                        System.out.println("Colab2: " + colab2);
                        jarCollection.updateCollaborators(id, jarUpdate.getJar_id(), colab2, "add");
                        Notification notification = new Notification(colab2, "Jar Update", "The jar: " + jarUpdate.getName() + " has been updated." + "The opening time is " + jarUpdate.getOpeningTime());
                    }
                    temp2.removeAll(temp);
                    for (String colab3 : temp2) {
                        Notification notification = new Notification(colab3, "Jar Update", "The jar: " + jarUpdate.getName() + " has been updated." + "The opening time is " + jarUpdate.getOpeningTime());
                    }

                }
            }
        for(String collaborator : jarUpdate.getCollaborators()){
//            To add if to check if they are already there
            String email = "^[a-zA-Z0-9_+&*-]+(?:\\."+ "[a-zA-Z0-9_+&*-]+)*@" + "(?:[a-zA-Z0-9-]+\\.)+[a-z" + "A-Z]{2,7}$";
            Pattern pattern = Pattern.compile(email);
            if(pattern.matcher(collaborator).matches()){
                String curentcolabString = userCollection.getUserDocument(collaborator);
                Gson gson2 = new Gson();
                User curentColabuser = gson2.fromJson(curentcolabString, User.class);
                if(curentColabuser != null){
                    jarCollection.updateJarName(curentColabuser.getToken_id(), jarUpdate.getJar_id(),jarUpdate.getName());
                    jarCollection.updateJarAdmin(curentColabuser.getToken_id(),jarUpdate.getJar_id(),jarUpdate.getJarAdmin());
                    jarCollection.updateNumberOfNotes(curentColabuser.getToken_id(),jarUpdate.getJar_id(),jarUpdate.getNumberOfNotes());
                    jarCollection.updateClosedStatus(curentColabuser.getToken_id(),jarUpdate.getJar_id(),jarUpdate.isClosed());
                    jarCollection.updateOpeningTime(curentColabuser.getToken_id(),jarUpdate.getJar_id(),jarUpdate.getOpeningTime());
                }else{
                    Notification notification = new Notification(collaborator,"Welcome To The Positivity Jar",
                            "Hello, welcome to the mindfulness WebApp made by Team I, We Look forward to you using this app to form a deeper connection with friends and family."+
                                    "Come to our website at " + "localhost:9080");
                }

            }
        }
    }
    /**
     * Works under the assumption that in the jarJson the Jar_id is a valid id for the user at ID
     * @param id token_id
     * @param jarJson a json to update a jar
     */
    @Path("{id}/remove")
    @PUT
    public void removeCollaborators(@PathParam("id") String id, String jarJson ){
        Gson gson = new Gson();
        Jar jarUpdate = gson.fromJson(jarJson, Jar.class);
        for(String collaborator : jarUpdate.getCollaborators()){
            String curentcolabString = userCollection.getUserDocument(collaborator);
            Gson gson2 = new Gson();
            User curentColabuser = gson2.fromJson(curentcolabString, User.class);
            jarCollection.updateCollaborators(curentColabuser.getToken_id(),jarUpdate.getJar_id(),id,"remove");
            jarCollection.updateJarName(curentColabuser.getToken_id(), jarUpdate.getJar_id(),jarUpdate.getName());
            jarCollection.updateJarAdmin(curentColabuser.getToken_id(),jarUpdate.getJar_id(),jarUpdate.getJarAdmin());
            jarCollection.updateNumberOfNotes(curentColabuser.getToken_id(),jarUpdate.getJar_id(),jarUpdate.getNumberOfNotes());
            jarCollection.updateClosedStatus(curentColabuser.getToken_id(),jarUpdate.getJar_id(),jarUpdate.isClosed());
            jarCollection.updateOpeningTime(curentColabuser.getToken_id(),jarUpdate.getJar_id(),jarUpdate.getOpeningTime());
        }
        jarCollection.deleteJar(id,jarUpdate.getJar_id());
    }

    /**
     * This method adds a jar to a user all users initially have no jars this is to populate those jars
     * This method does not change an existing jar in a user it just adds a new jar to said user
     * @param id the token_id of user from url
     * @param jarJson the jarJson that you will add to user
     */
    @Path("{id}")
    @POST
    public void PostJar(@PathParam("id") String id, String jarJson ){
        Gson gson = new Gson();
        Jar jar = gson.fromJson(jarJson, Jar.class);
        System.out.println(jar);
        userCollection.addJar(id,jar);
    }

    ////    Add way to remeve jar
    @Path("{token_id}/remove/{jar_id}")
    @PUT
    public void removeJar(@PathParam("token_id") String token_id, @PathParam("jar_id") int jar_id){
        String userString = userCollection.getUserDocument(token_id);
        Gson gson = new Gson();
        User user = gson.fromJson(userString, User.class);
        for(Jar jar :user.getMy_Jars()){
            if(jar.getJar_id() == jar_id){
                if(jar.getCollaborators().size() > 1){
                    for(String collaboratorString : jar.getCollaborators()){
                        for(String noteJson : noteCollection.getNotes(jar_id,collaboratorString)){
                            Note note = gson.fromJson(noteJson, Note.class);
                            noteCollection.deleteNote(note.get_NoteId());
                        }
                        jarCollection.deleteJar(collaboratorString, jar_id);
                    }
                }else{
                    for(String noteJson : noteCollection.getNotes(jar_id,token_id)){
                        Note note = gson.fromJson(noteJson, Note.class);
                        noteCollection.deleteNote(note.get_NoteId());
                    }
                    jarCollection.deleteJar(token_id,jar_id);
                }
            }
        }


    }


    @Path("notify")
    @GET
    public String NotifyEmailList(){
        ArrayList<String> emailList = userCollection.getUserEmailList();

            for(String email : emailList){
                email = email.replace("email","");
                email = email.replace("{","");
                email = email.replace("}","");
                email = email.replace(":","");
                email = email.replace("\"","");
            Notification notification = new Notification(email, "Welcome To The Positivity Jar","Hello, welcome to the mindfulness WebApp made by Team I, We Look forward to you using this app to form a deeper connection with friends and family.");
//                System.out.println(emailList.get(2));
        } return "Message successfully sent ";

    }



}
