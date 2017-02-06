package com.habitrpg.android.habitica.interactors;

import com.habitrpg.android.habitica.data.TaskRepository;
import com.habitrpg.android.habitica.executors.PostExecutionThread;
import com.habitrpg.android.habitica.executors.ThreadExecutor;
import com.habitrpg.android.habitica.helpers.SoundManager;
import com.magicmicky.habitrpgwrapper.lib.models.TaskDirectionData;
import com.magicmicky.habitrpgwrapper.lib.models.tasks.Task;

import javax.inject.Inject;

import rx.Observable;

public class DailyCheckUseCase extends UseCase<DailyCheckUseCase.RequestValues, TaskDirectionData> {

    private TaskRepository taskRepository;
    private SoundManager soundManager;

    @Inject
    public DailyCheckUseCase(TaskRepository taskRepository, SoundManager soundManager,
                             ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.taskRepository = taskRepository;
        this.soundManager = soundManager;
    }

    @Override
    protected Observable<TaskDirectionData> buildUseCaseObservable(RequestValues requestValues) {
        return taskRepository.taskChecked(requestValues.task, requestValues.Up).doOnNext(res -> {

            soundManager.loadAndPlayAudio(SoundManager.SoundDaily);
        });
    }

    public static final class RequestValues implements UseCase.RequestValues {

        protected boolean Up = false;

        protected final Task task;

        public RequestValues(Task task, boolean up) {
            this.task = task;
            this.Up = up;
        }
    }
}