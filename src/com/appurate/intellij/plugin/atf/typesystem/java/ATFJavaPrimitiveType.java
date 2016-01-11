package com.appurate.intellij.plugin.atf.typesystem.java;

import com.appurate.intellij.plugin.atf.typesystem.ATFType;
import com.appurate.intellij.plugin.atf.typesystem.ATFTypeCategory;
import com.intellij.psi.PsiMember;
import org.jetbrains.annotations.NotNull;

/**
 * Created by vmansoori on 1/7/2016.
 */
public class ATFJavaPrimitiveType implements ATFType{

    public static final ATFJavaPrimitiveType BYTE = new ATFJavaPrimitiveType("byte", "java.lang.Byte");
    public static final ATFJavaPrimitiveType CHAR = new ATFJavaPrimitiveType("char", "java.lang.Character");
    public static final ATFJavaPrimitiveType DOUBLE = new ATFJavaPrimitiveType("double", "java.lang.Double");
    public static final ATFJavaPrimitiveType FLOAT = new ATFJavaPrimitiveType("float", "java.lang.Float");
    public static final ATFJavaPrimitiveType INT = new ATFJavaPrimitiveType("int", "java.lang.Integer");
    public static final ATFJavaPrimitiveType LONG = new ATFJavaPrimitiveType("long", "java.lang.Long");
    public static final ATFJavaPrimitiveType SHORT = new ATFJavaPrimitiveType("short", "java.lang.Short");
    public static final ATFJavaPrimitiveType BOOLEAN = new ATFJavaPrimitiveType("boolean", "java.lang.Boolean");
    public static final ATFJavaPrimitiveType VOID = new ATFJavaPrimitiveType("void", "java.lang.Void");
    public static final ATFJavaPrimitiveType NULL = new ATFJavaPrimitiveType("null", (String) null);
    private final String name;
    private final String boxedName;

    public ATFJavaPrimitiveType(@NotNull String name, @NotNull String boxedName) {
        this.name = name;
        this.boxedName = boxedName;
    }

    @Override
    public ATFType[] getChildren() {
        return new ATFType[0];
    }

    @Override
    public ATFType getParent() {
        return null;
    }

    @Override
    public ATFTypeCategory getType() {
        return null;
    }

    public String getName() {
        return name;
    }

    @Override
    public String getDisplayName() {
        return this.getName();
    }

    @Override
    public String getPath() {
        return null;
    }

    @Override
    public PsiMember getBasedOn() {
        return null;
    }

    public String getBoxedName() {
        return boxedName;
    }

    public Class resolve() {
        try {
            return Class.forName(this.boxedName);
        } catch (ClassNotFoundException e) {
            return null;
        }
    }
}
