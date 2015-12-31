package com.appurate.intellij.plugin.common.util;

import com.intellij.ide.DataManager;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.actionSystem.DataKeys;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;

/**
 * Created by vmansoori on 12/31/2015.
 */
public class CommonUtil {

    // FIXME: 12/31/2015 This might not be the right way of getting the active project
    public static Project getActiveProject() {
        ProjectManager manager = ProjectManager.getInstance();
        return manager.getDefaultProject();
    }
}
