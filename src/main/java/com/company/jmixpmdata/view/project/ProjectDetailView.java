package com.company.jmixpmdata.view.project;


import com.company.jmixpmdata.app.ProjectService;
import com.company.jmixpmdata.datatype.ProjectLabels;
import com.company.jmixpmdata.entity.Project;
import com.company.jmixpmdata.entity.Roadmap;
import com.company.jmixpmdata.view.main.MainView;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.router.Route;
import io.jmix.core.DataManager;
import io.jmix.flowui.Notifications;
import io.jmix.flowui.component.textfield.TypedTextField;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.model.DataContext;
import io.jmix.flowui.view.*;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Set;

@Route(value = "projects/:id", layout = MainView.class)
@ViewController(id = "Project.detail")
@ViewDescriptor(path = "project-detail-view.xml")
@EditedEntityContainer("projectDc")
public class ProjectDetailView extends StandardDetailView<Project> {
    @ViewComponent
    private DataContext dataContext;
    @Autowired
    private DataManager dataManager;
    @ViewComponent
    private TypedTextField<ProjectLabels> labelsField;
    @Autowired
    private ProjectService projectService;
    @Autowired
    private Notifications notifications;
    @Autowired
    private Validator validator;

    @Subscribe
    public void onInitEntity(final InitEntityEvent<Project> event) {
        Roadmap roadmap = dataContext.create(Roadmap.class);
        Project project = event.getEntity();
        project.setRoadmap(roadmap);

        labelsField.setReadOnly(false);
        project.setLabels(new ProjectLabels(List.of("bug", "task")));
    }

    @Subscribe(id = "commitWithBeanValidationButton", subject = "clickListener")
    public void onCommitWithBeanValidationButtonClick(final ClickEvent<JmixButton> event) {
        try {

            projectService.save(getEditedEntity());
            close(StandardOutcome.CLOSE);
        } catch (ConstraintViolationException e) {
            StringBuilder sb = new StringBuilder();

            for (ConstraintViolation<?> violation : e.getConstraintViolations()) {
                sb.append(violation.getMessage()).append("\n");
            }

            notifications.create(sb.toString())
                    .withPosition(Notification.Position.TOP_END)
                    .withThemeVariant(NotificationVariant.LUMO_WARNING)
                    .show();
        }

    }

    @Subscribe(id = "performBeanValidationButton", subject = "clickListener")
    public void onPerformBeanValidationButtonClick(final ClickEvent<JmixButton> event) {
        Set<ConstraintViolation<Project>> violations = validator.validate(getEditedEntity());

        showViolations(violations);
    }

    private void showViolations(Set<ConstraintViolation<Project>> violations) {
        StringBuilder sb = new StringBuilder();

        for (ConstraintViolation<?> violation : violations) {
            sb.append(violation.getMessage()).append("\n");
        }

        notifications.create(sb.toString())
                .withPosition(Notification.Position.TOP_START)
                .withThemeVariant(NotificationVariant.LUMO_WARNING)
                .show();
    }


}