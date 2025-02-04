package com.xebia.functional.xef.java.auto.jdk21;

import com.xebia.functional.xef.java.auto.AIScope;
import com.xebia.functional.xef.java.auto.ExecutionContext;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;

public class Colors {

    public List<String> colors;

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        try (AIScope scope = new AIScope(new ExecutionContext(Executors.newVirtualThreadPerTaskExecutor()))) {
            scope.prompt("a selection of 10 beautiful colors that go well together", Colors.class)
                  .thenAccept(colors -> System.out.println("Colors:\n" + colors.colors))
                  .get();
        }
    }
}
