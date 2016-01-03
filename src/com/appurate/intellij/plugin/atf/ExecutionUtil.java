package com.appurate.intellij.plugin.atf;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.util.ThrowableComputable;

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

}
