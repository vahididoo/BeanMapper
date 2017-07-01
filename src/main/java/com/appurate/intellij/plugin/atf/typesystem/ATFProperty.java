package com.appurate.intellij.plugin.atf.typesystem;

/**
 * Created by vmansoori on 1/6/2016.
 */
public interface ATFProperty extends ATFType {

    ATFReference getReferenceType();

    boolean isWritable();

    ATFMethod getGetter();

    ATFMethod getSetter();

    ATFType getType();
}
