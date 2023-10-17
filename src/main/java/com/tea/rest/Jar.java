package com.tea.rest;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;


public class Jar implements Serializable {
    private String name;
    private int numberOfNotes;
    private int jar_id;
    private ArrayList<Note> noteArrayList = new ArrayList<>();
    private boolean closed;
    private String jarAdmin;
//    Email
    private ArrayList<String> collaborators = new ArrayList<>();
//    Opening time should
    private String openingTime;


    public void setNumberOfNotes(int numberOfNotes) {
        this.numberOfNotes = numberOfNotes;
    }

    public void setNoteArrayList(ArrayList<Note> noteArrayList) {
        this.noteArrayList = noteArrayList;
    }



    public void setClosed(boolean closed) {
        this.closed = closed;
    }
    public ArrayList<String> getCollaborators() {
        return collaborators;
    }
    public void setCollaborators(ArrayList<String> collaborators) {
        this.collaborators = collaborators;
    }

//

    /**
     * this method checks openingTime where openingTime is formatted 2022-4-10T11:25
     * YYYY-M-D-H-S
     * Where the Hour is in 24 Hour Time
     * collaborators should be a list of emails
     * @throws InterruptedException
     */
//    public void checkOpeningTime() throws InterruptedException {
//        String opt = getOpeningTime();
//        LocalDateTime openingDateTime = LocalDateTime.parse(opt);
////        LocalDateTime openingDateTime = LocalDateTime.of(2022,4,10,16,6);
//        while(true){
//            LocalDateTime currentDateTime = LocalDateTime.now();
//            currentDateTime = currentDateTime.truncatedTo(ChronoUnit.MINUTES);
////            System.out.println(currentDateTime); If anything is hanging with the Timer stuff uncomment and figure out whether the opening Time was sent correctly
////            System.out.println(openingDateTime); If anything is hanging with the Timer stuff uncomment and figure out whether the opening Time was sent correctly
//            if(currentDateTime.equals(openingDateTime)){
//                for(String collaborator : collaborators){
//                    Notification notify = new Notification(collaborator,"Positivity Jar Opening", getName() + " is ready to be opened");
//                }
//                setClosed(false);
//                break;
//            } else{
//                setClosed(true);
//            }
//            Thread.sleep(3000);
//        }
//
//
//    }

    public void openingTimeCloseJar(){
//        There should be a check to make sure the format is correct
        String opt = getOpeningTime();
        if(opt.length() == 10){
           opt = opt.concat("T00:00:00");
        }
        LocalDateTime openingDateTime = LocalDateTime.parse(opt);
        LocalDateTime currentDateTime = LocalDateTime.now();
        currentDateTime = currentDateTime.truncatedTo(ChronoUnit.DAYS);
        if(openingDateTime == currentDateTime){
            setClosed(false);
            for(String colab : collaborators){
                System.out.println(colab);
//                Notification notification = new Notification(colab, "The Jar "+this.getName() +"is now open!",
//                        "Please visit our webapp to view the collected notes of "+ getCollaborators() + " in "+ getName());
            }

        }else if(openingDateTime.isBefore(currentDateTime)){
            setClosed(false);
        }
    }
    public void setJar_id(int jar_id) {
        this.jar_id = jar_id;
    }

    public int getJar_id() {
        return jar_id;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public boolean isClosed() {
        return closed;
    }

    public boolean setStatus(boolean a){
        return closed = a;
    }

    public int getNumberOfNotes() {
        return numberOfNotes;
    }

    public String getJarAdmin() {
        return jarAdmin;
    }

    public void setJarAdmin(String jarAdmin) {
        this.jarAdmin = jarAdmin;
    }

    public void setOpeningTime(String time) {
       this.openingTime = time;
    }
    //    TODO:REWORK TO USE TIMER CLASS
    public String getOpeningTime() {
        return openingTime;
    }


    public void addUser(User user){
        collaborators.add(user.getEmail());
    }

    public void deleteUser(User user){
        collaborators.remove(user.getEmail());
    }

    public ArrayList<String> getUsers() {
        return collaborators;
    }
    public void addNote(String note, String email) {
        if (note != null & note.length() <= 256) {
//            Local notes log
            noteArrayList.add(new Note(note, 0, email));

            numberOfNotes = noteArrayList.size();

        }
    }

    //TODO:Run a test on this to make sure this behaves the way it should
    public void removeNote(String note, String email){
            if (note != null && noteArrayList.contains(new Note(note, 0, email))) {
                noteArrayList.remove(new Note(note, 0,email));
                numberOfNotes = noteArrayList.size();

            }
    }

    public ArrayList<Note> getNoteArrayList() {
        return noteArrayList;
    }

    @Override
    public String toString() {
        return "Jar{" +
                "name='" + name + '\'' +
                ", numberOfNotes=" + numberOfNotes +
                ", jarId=" + jar_id +
                ", noteArrayList=" + noteArrayList +
                ", closed=" + closed +
                ", jarAdmin=" + jarAdmin +
                ", collaborators=" + collaborators +
                ", openingTime=" + openingTime +
                '}';
    }
}
