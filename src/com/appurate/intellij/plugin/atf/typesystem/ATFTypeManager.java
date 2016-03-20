package com.appurate.intellij.plugin.atf.typesystem;

import com.appurate.intellij.plugin.atf.typesystem.psi.java.ATFJavaTypeFactory;
import com.intellij.openapi.project.Project;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by vmansoori on 12/31/2015.
 * // TODO: 1/2/2016 Make this an application component to start at the startup
 */
public class ATFTypeManager {

    private static Map<Project, ATFTypeManager> instances = Collections.synchronizedMap(new HashMap<Project,
            ATFTypeManager>());
    private final Project project;
    private Map<String, ATFTypeFactory> adaptorMap;

    private ATFTypeManager(Project project) {
        this.project = project;
        adaptorMap = new HashMap<String, ATFTypeFactory>();
        init();
    }

    public static ATFTypeManager getInstance(Project project) {
        ATFTypeManager instance;
        synchronized (instances) {
            instance = instances.get(project);
            if (instance == null) {
                instances.put(project, instance = new ATFTypeManager(project));
            }
        }
        return instance;
    }

    public void init() {
        // TODO: 12/31/2015 Initialize adapterMap by reading the extensions or from plugin configuration
        adaptorMap.put("java", new ATFJavaTypeFactory(project));
    }


    public ATFTypeFactory getTypeFactory(String type) {
        return adaptorMap.get(type);
    }


}
