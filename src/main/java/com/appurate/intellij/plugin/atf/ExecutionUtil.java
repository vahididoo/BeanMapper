package com.appurate.intellij.plugin.atf;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.util.Computable;
import com.intellij.openapi.util.ThrowableComputable;
import com.intellij.psi.PsiMethod;

import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by vmansoori on 12/26/2015.
 */
public class ExecutionUtil {

    private ExecutionUtil() {
    }

    public static <T extends Object> T execute(Runnable runnable) throws Exception {

        Callable<Object> callable = Executors.callable(runnable);

        return ExecutionUtil.execute(callable);

    }

    public static <T extends Object> T execute(Callable<Object> callable) throws Exception {

        Future<Object> future = Executors.newCachedThreadPool().submit(callable);

        return (T) future.get();
    }

    public static <T extends Object> T execute(ThrowableComputable computable) throws Throwable {
        return (T) ApplicationManager.getApplication().runWriteAction(computable);
    }

    public static <T extends Object> T execute(Computable computable) {
        return (T) ApplicationManager.getApplication().runWriteAction(computable);
    }

    public static void executeRead(Computable computable) {
        ApplicationManager.getApplication().runReadAction(computable);
    }
}
