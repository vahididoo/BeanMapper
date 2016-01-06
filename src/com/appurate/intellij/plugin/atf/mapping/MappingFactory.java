package com.appurate.intellij.plugin.atf.mapping;


import com.intellij.openapi.vfs.VirtualFile;

/**
 * Created by vmansoori on 1/5/2016.
 */
public interface MappingFactory {

    public MappingClass createMapping(VirtualFile parent, String name);

    public MappingMethod createMappingMethod(Object... params);
}
