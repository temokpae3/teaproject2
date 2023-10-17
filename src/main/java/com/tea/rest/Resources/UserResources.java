package com.tea.rest.Resources;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tea.rest.Jar;
import com.tea.rest.Notification;
import com.tea.rest.User;
import com.tea.rest.UserAdapter;
import mongo.UserCollection;
import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Objects;

/**
 * This class sends GET and POST REQUEST for the User Class
 */

@Path("User")
@ApplicationScoped
public class UserResources {

    GsonBuilder gsonBuilder = new GsonBuilder();
    UserCollection collection = new UserCollection();

    /**
     * This method takes in a JSOM String at localhost:9080/api/User
     * and converts the json string to a user
     * and adds that user to teh database
     *
     * @param userJson a json string {token_id:"5465",email:"email",admin:true}
     * @return Response
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response postUser(String userJson) {
        Gson gson = new Gson();
        User user = gson.fromJson(userJson, User.class);
        String website ="";
        if (user.getToken_id() != null) {
            if(collection.getUserDocument(user.getToken_id()) == null ){
                if (!collection.getUserEmailList().isEmpty()){
                    for(String email : collection.getUserEmailList()){
                        email = email.replace("email","");
                        email = email.replace("{","");
                        email = email.replace("}","");
                        email = email.replace(":","");
                        email = email.replace("\"","");
                        if(!email.trim().equals( user.getEmail().trim())){
                            collection.addUser(user);
                            Notification notification = new Notification(user.getEmail(),"Welcome To The Positivity Jar",
                "Hello, welcome to the mindfulness WebApp made by Team I, We Look forward to you using this app to form a deeper connection with friends and family."+
                        "Come to our website at " + website);
                            break;
                        }
                }

                }else{
//                    System.out.println(user);
                    collection.addUser(user);
                    //                Notification notification = new Notification(user.getEmail(),"Welcome To The Positivity Jar",
//                "Hello, welcome to the mindfulness WebApp made by Team I, We Look forward to you using this app to form a deeper connection with friends and family."+
//                        "Come to our website at " + website);
                }

                return Response.status(Response.Status.ACCEPTED).entity(gson.toJson(user)).build();
            } else {
                return Response.status(Response.Status.ACCEPTED).entity(gson.toJson(user)).build();
            }

        }
        return Response.status(Response.Status.NO_CONTENT).entity("Token_ID was not accepted").build();

    }

    /**
     * This method takes in a token_id at localhost:9080/api/User/{id}
     * and gets the json string for that token_id
     * and returns that user in json form to that url
     *
     * @param token_ID a token id in the url
     * @return Response
     */
    @Path("{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUser(@PathParam("id") String token_ID) {
        String userString = collection.getUserDocument(token_ID);
//        System.out.println(userString);
        if (userString != null) {
            return Response.status(Response.Status.FOUND).entity(userString).build();
        } else {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
    }

    /**
     * This method takes in a token_id at localhost:9080/api/User/{id}
     * and receives a json string in the body of a request
     * and returns updates the user with that token_id to have the values in the json
     * If there is a Put request with an empty Token_id and an empty email The database entry for the initial id gets deleted
     *
     * @param id a token id in the url
     * @param userJson a json string
     *
     */
    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void updateUser(@PathParam("id") String id , String userJson) {
        gsonBuilder.registerTypeAdapter(User.class, new UserAdapter());
        Gson gson = gsonBuilder.create();
        User userUpdate = gson.fromJson(userJson, User.class);
        if(userUpdate.getToken_id().equals("") && userUpdate.getEmail().equals("")){
            collection.deleteUser(id);
        }else{
            String currentUserString = collection.getUserDocument(id);
//        Makes a duplicate entry in the database keep commented
//        collection.updateTokenID(id, userUpdate.getToken_id());
            collection.updateEmail(id,userUpdate.getEmail());
            collection.updateNotify(id,userUpdate.getNotify());
//            System.out.println(userUpdate);
//            Checks if there json space for myjars was left not blank
            if(userUpdate.getMy_Jars() != null){
                for (Jar j: userUpdate.getMy_Jars()) {
                    collection.addJar(id, j);
                }

            }

        }

    }


}
