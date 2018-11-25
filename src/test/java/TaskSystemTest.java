import com.sun.istack.internal.NotNull;
import me.gommeantilegit.equilinox.mod.kit.task.Task;
import me.gommeantilegit.equilinox.mod.kit.task.execution.TaskExecutionResultPair;
import me.gommeantilegit.equilinox.mod.kit.task.execution.executor.TaskExecutor;
import me.gommeantilegit.equilinox.mod.kit.task.execution.result.TaskResult;
import me.gommeantilegit.equilinox.mod.kit.task.tasktypes.TaskGroup;
import org.junit.Test;

public class TaskSystemTest {

    private final TaskExecutionResultPair<String> task0 = new TaskExecutionResultPair<>(
            new Task<String>("Test-Print-1") {
                @Override
                public String executeTask(TaskExecutor<?> executor) throws Exception {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return "Hello World";
                }
            }
    );
    @NotNull
    private final Task<Void> task = new TaskGroup<>("Test-Task-group", true, new TaskExecutionResultPair[]{
            task0,
            new TaskExecutionResultPair<>(
                    new Task<Void>("Test-Print-2") {
                        @Override
                        public Void executeTask(TaskExecutor<?> executor) throws Exception {
                            TaskResult<String> taskResult = task0.getResult();
                            String value = taskResult.getValue();
                            System.out.println("Result of task0: " + value);
                            return null;
                        }
                    }
            ),
            new TaskExecutionResultPair<>(
                    new Task<Void>("Test-Print-3") {
                        @Override
                        public Void executeTask(TaskExecutor<?> executor) throws Exception {
                            return null;
                        }
                    }
            ),
            new TaskExecutionResultPair<>(
                    new Task<Void>("Test-Print-4") {
                        @Override
                        public Void executeTask(TaskExecutor<?> executor) throws Exception {
                            return null;
                        }
                    }
            )
    });

    @NotNull
    private final TaskExecutor<Void> taskExecutor = new TaskExecutor<>(task, true, false);

    @Test
    public void runTest() {
        this.taskExecutor.runExecutor();
        this.taskExecutor.waitForFinish();
    }

}
