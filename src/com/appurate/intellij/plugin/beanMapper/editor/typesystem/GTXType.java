package com.appurate.intellij.plugin.beanMapper.editor.typesystem;

/**
 * Created by vmansoori on 12/30/2015.
 */
public interface GTXType {
    GTXType[] getChildren();
    GTXType getParent();
    GTXTypeCategory getType();
    String getName();
    String getDisplayName();
    String getPath();
}
