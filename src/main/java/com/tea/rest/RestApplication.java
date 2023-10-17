package com.tea.rest;

import com.tea.rest.Resources.JarResource;
import com.tea.rest.Resources.NoteResource;
import com.tea.rest.Resources.UserResources;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/api")
public class RestApplication extends Application {
    private final Set<Object> singletons = new HashSet<>();

    public RestApplication(){
        singletons.add(new UserResources());
        singletons.add(new JarResource());
        singletons.add(new NoteResource());
    }
    public Set<Object> getSingleton(){
        return singletons;
    }

}