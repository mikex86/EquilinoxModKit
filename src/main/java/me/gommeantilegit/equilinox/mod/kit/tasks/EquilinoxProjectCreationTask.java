package me.gommeantilegit.equilinox.mod.kit.tasks;

import com.sun.istack.internal.NotNull;
import me.gommeantilegit.equilinox.mod.kit.task.execution.TaskExecutionResultPair;
import me.gommeantilegit.equilinox.mod.kit.task.tasktypes.TaskGroup;
import me.gommeantilegit.equilinox.mod.kit.tasks.checking.ScanningTask;
import me.gommeantilegit.equilinox.mod.kit.tasks.cleaning.CleanUpTask;
import me.gommeantilegit.equilinox.mod.kit.tasks.decompilation.cfr.CFRClassCopyTask;
import me.gommeantilegit.equilinox.mod.kit.tasks.decompilation.cfr.CFRPatchTask;
import me.gommeantilegit.equilinox.mod.kit.tasks.decompilation.deletion.LibraryFilesDeletionTask;
import me.gommeantilegit.equilinox.mod.kit.tasks.decompilation.fernflower.FernflowerDecompilationTask;
import me.gommeantilegit.equilinox.mod.kit.tasks.decompilation.fernflower.FernflowerFailedClassesDeletionTask;
import me.gommeantilegit.equilinox.mod.kit.tasks.patching.PatchTask;
import me.gommeantilegit.equilinox.mod.kit.tasks.project.SetupNativesTask;
import me.gommeantilegit.equilinox.mod.kit.tasks.project.MoveResourcesTask;
import me.gommeantilegit.equilinox.mod.kit.tasks.project.ProjectSetupTask;
import me.gommeantilegit.equilinox.mod.kit.tasks.unzipping.DecompileJarUnzippingTask;

import java.io.File;
import java.util.ArrayList;

public class EquilinoxProjectCreationTask extends TaskGroup<Void> {

    @NotNull
    private final ScanningTask scanningTask = new ScanningTask();

    @NotNull
    private final FernflowerDecompilationTask decompilationTask = new FernflowerDecompilationTask();

    @NotNull
    private final ProjectSetupTask projectSetupTask = new ProjectSetupTask(ProjectSetupTask.Type.KOTLIN_DSL);

    @NotNull
    private final DecompileJarUnzippingTask unzippingTask = new DecompileJarUnzippingTask();

    @NotNull
    private final MoveResourcesTask moveResourcesTask = new MoveResourcesTask();

    @NotNull
    private final CFRPatchTask cfrPatchTask = new CFRPatchTask();

    @NotNull
    private final TaskExecutionResultPair<ArrayList<File>> cfrPatchTaskResultPair = new TaskExecutionResultPair<>(cfrPatchTask);

    @NotNull
    private final LibraryFilesDeletionTask libraryFilesDeletionTask = new LibraryFilesDeletionTask();

    @NotNull
    private final FernflowerFailedClassesDeletionTask failedClassesDeletionTask = new FernflowerFailedClassesDeletionTask(cfrPatchTaskResultPair);

    @NotNull
    private final CFRClassCopyTask copyTask = new CFRClassCopyTask(cfrPatchTaskResultPair);

    @NotNull
    private final SetupNativesTask setupNativesTask = new SetupNativesTask();

    @NotNull
    private final PatchTask patchTask = new PatchTask();

    @NotNull
    private final CleanUpTask cleanUpTask = new CleanUpTask();

    public EquilinoxProjectCreationTask() {
        super("EquilinoxProjectCreationTask", false, new TaskExecutionResultPair[12]);
        this.subTasks[0] = new TaskExecutionResultPair<>(scanningTask);
        this.subTasks[1] = new TaskExecutionResultPair<>(decompilationTask);
        this.subTasks[2] = new TaskExecutionResultPair<>(projectSetupTask);
        this.subTasks[3] = new TaskExecutionResultPair<>(unzippingTask);
        this.subTasks[4] = new TaskExecutionResultPair<>(moveResourcesTask);
        this.subTasks[5] = new TaskExecutionResultPair<>(libraryFilesDeletionTask);
        this.subTasks[6] = cfrPatchTaskResultPair;
        this.subTasks[7] = new TaskExecutionResultPair<>(failedClassesDeletionTask);
        this.subTasks[8] = new TaskExecutionResultPair<>(copyTask);
        this.subTasks[9] = new TaskExecutionResultPair<>(setupNativesTask);
        this.subTasks[10] = new TaskExecutionResultPair<>(patchTask);
        this.subTasks[11] = new TaskExecutionResultPair<>(cleanUpTask);
    }


}
