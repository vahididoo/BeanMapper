package com.appurate.intellij.plugin.beanMapper.filetypes;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.fileTypes.UserFileType;
import com.intellij.openapi.options.SettingsEditor;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.util.text.XmlCharsetDetector;
import com.intellij.xml.util.HtmlUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * Created by vmansoori on 12/17/2015.
 */
public class GTXFileType extends UserFileType {


    @Override
    public SettingsEditor getEditor() {
        return null;
    }

    @NotNull
    @Override
    public String getName() {
        return "GTX";
    }

    @NotNull
    @Override
    public String getDescription() {
        return "Gosu Transformation";
    }

    @NotNull
    @Override
    public String getDefaultExtension() {
        return "gtx";
    }

    @Nullable
    @Override
    public Icon getIcon() {
        return AllIcons.FileTypes.Custom;
    }

    @Override
    public boolean isBinary() {
        return false;
    }

    @Override
    public boolean isReadOnly() {
        return false;
    }

    @Nullable
    @Override
    public String getCharset(@NotNull VirtualFile virtualFile, @NotNull byte[] content) {
        String charset = XmlCharsetDetector.extractXmlEncodingFromProlog(content);
        if (charset != null) {
            return charset;
        } else {
            return StandardCharsets.UTF_8.displayName();
        }
    }
}
