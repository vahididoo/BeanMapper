package com.appurate.intellij.plugin.experimental;

import com.sun.codemodel.*;

import java.io.*;

/**
 * Created by vmansoori on 1/14/2016.
 */
public class TestCodeModeller {

    public static void main(String[] args) {

    }

    public void doWork() {
        JCodeModel model = new JCodeModel();
        try {
            JPackage jPackage = model._package("com.appurate.intellij.plugin.experimental");
            JDefinedClass generatedClass = jPackage._class(JMod.PUBLIC, "GeneratedClass", ClassType.CLASS);
            JMethod method = generatedClass.method(JMod.PUBLIC, model.ref("java.math.BigDecimal"), "setName");

            JForLoop jForLoop = new JForLoop();
            JVar init = jForLoop.init(model.INT, "i", JExpr.lit(10));
            jForLoop.test(init.lt(JExpr.lit(30)));
            jForLoop.update(init.assignPlus(JExpr.lit(1)));
            jForLoop.body().add(model.ref(System.class).staticRef("out").invoke("printLn").arg(init));
            method.body().add(jForLoop);
            method.body()._return(JExpr.lit(30f));
            File destDir = new File("/generated/src");
            destDir.mkdirs();
            model.build(destDir);
        } catch (JClassAlreadyExistsException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
