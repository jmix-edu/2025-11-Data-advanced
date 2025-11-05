package com.company.jmixpmdata.app;

import com.company.jmixpmdata.entity.Project;
import com.company.jmixpmdata.entity.ProjectStats;
import com.company.jmixpmdata.entity.Task;
import io.jmix.core.DataManager;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProjectStatsService {
    private final DataManager dataManager;

    public ProjectStatsService(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    public List<ProjectStats> fetchStats() {

        List<Project> projects = dataManager.load(Project.class).all().list();

        return projects.stream()
                .map(project -> {
                    ProjectStats stat = dataManager.create(ProjectStats.class);
                    stat.setProjectName(project.getName());

                    List<Task> tasks = project.getTasks();
                    stat.setTasksCount(tasks.size());

                    Integer estimatedEfforts = tasks.stream()
                            .map(task -> task.getEstimatedEfforts() == null ? 0 : task.getEstimatedEfforts())
                            .reduce(0, Integer::sum);

                    stat.setPlannedEfforts(estimatedEfforts);
                    stat.setActualEfforts(getActualEfforts(project.getId()));
                    return stat;

                })
                .toList();

    }

    private Integer getActualEfforts(UUID id) {

        return dataManager.loadValue("select sum(te.timeSpent) from TimeEntry te " +
                        "where te.task.project.id = :id", Integer.class)
                .parameter("id", id)
                .one();
    }
}