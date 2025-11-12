package com.company.jmixpmdata.app;

import com.company.jmixpmdata.entity.Project;
import com.company.jmixpmdata.entity.ProjectStats;
import com.company.jmixpmdata.entity.Task;
import io.jmix.core.DataManager;
import io.jmix.core.FetchPlan;
import io.jmix.core.FetchPlanRepository;
import io.jmix.core.FetchPlans;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProjectStatsService {
    private static final Logger log = LoggerFactory.getLogger(ProjectStatsService.class);
    private final DataManager dataManager;
    private final FetchPlans fetchPlans;
    private final FetchPlanRepository fetchPlanRepository;

    public ProjectStatsService(DataManager dataManager, FetchPlans fetchPlans, FetchPlanRepository fetchPlanRepository) {
        this.dataManager = dataManager;
        this.fetchPlans = fetchPlans;
        this.fetchPlanRepository = fetchPlanRepository;
    }

    public List<ProjectStats> fetchStats() {

        List<Project> projects = dataManager
                .load(Project.class)
                .all()
                .fetchPlan( "project-with-tasks-fetch-plan")
                .list();

        return projects.stream()
                .map(project -> {
                    ProjectStats stat = dataManager.create(ProjectStats.class);
                    stat.setProjectName(project.getName());

                    List<Task> tasks = project.getTasks();
                    for (Task task :tasks) {
                        log.info("Task assignee name: {}", task.getAssignee().getDisplayName());
                    }
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

    private FetchPlan createPlanWithTasks() {
        return fetchPlans.builder(Project.class)
                .addFetchPlan(FetchPlan.INSTANCE_NAME)
                .add("tasks", fetchPlanBuilder -> {
                    fetchPlanBuilder.add("estimatedEfforts").add("startDate").build();
                })
                .build();
    }
}