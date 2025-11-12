package com.company.jmixpmdata.view.task;

import com.company.jmixpmdata.entity.Task;
import com.company.jmixpmdata.repository.TaskRepository;
import com.company.jmixpmdata.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.core.repository.JmixDataRepositoryContext;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;

import java.util.Collection;
import java.util.List;

@Route(value = "tasks-repo", layout = MainView.class)
@ViewController(id = "Task_repo.list")
@ViewDescriptor(path = "task-list-view-repo.xml")
@LookupComponent("tasksDataGrid")
@DialogMode(width = "64em")
public class TaskListViewRepo extends StandardListView<Task> {

    @Autowired
    private TaskRepository repository;

    @Install(to = "tasksDl", target = Target.DATA_LOADER, subject = "loadFromRepositoryDelegate")
    private List<Task> loadDelegate(Pageable pageable, JmixDataRepositoryContext context) {
        return repository.findAll(pageable, context).getContent();
    }

    @Install(to = "tasksDataGrid.removeAction", subject = "delegate")
    private void tasksDataGridRemoveDelegate(final Collection<Task> collection) {
        repository.deleteAll(collection);
    }

    @Install(to = "pagination", subject = "totalCountByRepositoryDelegate")
    private Long paginationTotalCountByRepositoryDelegate(final JmixDataRepositoryContext context) {
        return repository.count(context);
    }
}