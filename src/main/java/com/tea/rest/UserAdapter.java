package com.tea.rest;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

/**
 * The UserAdapter Class is used with the Gson Class to write Json and Read Json based on the fields
 * provided in this class. So far only token_Id, email, admin, and notify.
 * @author JC
 */
public class UserAdapter extends TypeAdapter<User> {
    @Override
    public void write(JsonWriter jsonWriter, User user) throws IOException {
        jsonWriter.beginObject();
        jsonWriter.name("token_id");
        jsonWriter.value(user.getToken_id());
        jsonWriter.name("email");
        jsonWriter.value(user.getEmail());
        jsonWriter.name("notify");
        jsonWriter.value(user.getNotify());
        jsonWriter.name("admin");
        jsonWriter.value(user.is_admin());
        jsonWriter.endObject();
    }

    @Override
    public User read(JsonReader jsonReader) throws IOException {
        User user = new User();
        jsonReader.beginObject();
        String fieldName = null;
        while (jsonReader.hasNext()){
            JsonToken token = jsonReader.peek();
            if (token.equals(JsonToken.NAME)){
                fieldName = jsonReader.nextName();
            }
            if ("token_id".equals(fieldName)){
                token = jsonReader.peek();
                user.setToken_id(jsonReader.nextString());
            }
            if ("email".equals(fieldName)){
                token = jsonReader.peek();
                user.setEmail(jsonReader.nextString());
            }
            if ("notify".equals(fieldName)){
                token = jsonReader.peek();
                user.setAdmin(jsonReader.nextBoolean());
            }
            if ("admin".equals(fieldName)){
                token = jsonReader.peek();
                user.setAdmin(jsonReader.nextBoolean());
            }
//            if("myJars".equals(fieldName)){
//                while (jsonReader.hasNext()){
//                    user.add_Owned_Jar(new Jar(user, jsonReader.nextString()));
//                }
//            }
        }
        jsonReader.endObject();

        return user;
    }
}
