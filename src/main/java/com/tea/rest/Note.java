package com.tea.rest;

import java.util.Date;

public class Note {
    String message;
    int charNum;
    String time;//Time Posted
    Integer rating;
    String author;
    int note_id;
    int jar_id;
    public Note(String message, Integer weighted_Rating, String email){

        if(message != null && message.length() <= charNum){
            this.message = message;
        }else{
            this.message = null;
        }
        this.rating = weighted_Rating;
        assert message != null;
        note_id = message.hashCode();
        this.author = email;
    }
    public Note(){

    }

    public int get_JarId() {
        return jar_id;
    }

    public void set_JarId(int jar_id) {
        this.jar_id = jar_id;
    }

    public int get_NoteId() {
        return note_id;
    }

    public void set_NoteId(int note_id) {
        this.note_id = note_id;
    }

    public String get_Message(){
        return message;
    }
    public void set_Message(String new_message){
        if(new_message != null && new_message.length() <= charNum)
        message = new_message;
    }

//    public static int getChar_Num() {
//        return char_Num;
//    }


    public void setTime() {
        Date date = new Date();
        this.time = date.toString();
    }

    public String getEmail(){
        return this.author;
    }

    public String getTime() {
        return time;
    }

    public Integer getWeighted_Rating() {
        return rating;
    }

    public void setWeighted_Rating(Integer weighted_Rating) {
        this.rating = weighted_Rating;
    }

    public int getNote_id() {
        return note_id;
    }

    public String getMessage() {
        return message;
    }

    public void setEmail(String email) {
        this.author = email;
    }

    public void setTime(String time) {
        this.time = time;
    }

//    public int getJar_id() {
//        return jar_id;
//    }

    public void setChar_Num(int char_Num) {
        this.charNum = char_Num;
    }
    public int getChar_Num(){
        return charNum;
    }

    @Override
    public String toString() {
        return "Note{" +
                "message='" + message + '\'' +
                ", time='" + time + '\'' +
                ", rating=" + rating +
                ", author='" + author + '\'' +
                ", note_id=" + note_id +
                ", jar_id=" + jar_id +
                '}';
    }
}
