package com.appurate.intellij.plugin.beanMapper.editor.typesystem;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by vmansoori on 12/31/2015.
 */
public class GTXTypeManager {

    private static GTXTypeManager _instance = new GTXTypeManager();
    private  Map<String,GTXTypeAdapter> adaptorMap;


    private GTXTypeManager() {
        adaptorMap = Collections.synchronizedMap(new HashMap<String, GTXTypeAdapter>());
    }

    public static GTXTypeManager getInstance() {
        return _instance;
    }

    public void init(){
        // TODO: 12/31/2015 Initialize adapterMap by reading the extensions or from plugin configuration

    }



}
