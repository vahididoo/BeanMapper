package com.appurate.intellij.plugin.atf.typesystem;

import com.appurate.intellij.plugin.atf.typesystem.ATFType;

/**
 * Created by vmansoori on 1/5/2016.
 */
public interface ATFMethod extends ATFType {

    ATFParameter[] getParameters();
    ATFType getReturnType();

}
