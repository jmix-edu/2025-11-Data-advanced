package com.company.jmixpmdata.security;

import com.company.jmixpmdata.entity.Project;
import com.company.jmixpmdata.entity.Task;
import com.company.jmixpmdata.entity.TimeEntry;
import com.company.jmixpmdata.entity.User;
import io.jmix.security.model.EntityAttributePolicyAction;
import io.jmix.security.model.EntityPolicyAction;
import io.jmix.security.role.annotation.EntityAttributePolicy;
import io.jmix.security.role.annotation.EntityPolicy;
import io.jmix.security.role.annotation.ResourceRole;
import io.jmix.securityflowui.role.annotation.MenuPolicy;
import io.jmix.securityflowui.role.annotation.ViewPolicy;

@ResourceRole(name = "DeveloperRole", code = DeveloperRole.CODE, scope = "UI")
public interface DeveloperRole{
    String CODE = "developer-role";

    @EntityAttributePolicy(entityClass = Project.class, attributes = "tasks", action = EntityAttributePolicyAction.MODIFY)
    @EntityAttributePolicy(entityClass = Project.class, attributes = {"id", "labels", "name", "startDate", "endDate", "manager", "status", "totalEstimatedEfforts", "participants", "roadmap", "deletedBy", "deletedDate"}, action = EntityAttributePolicyAction.VIEW)
    @EntityPolicy(entityClass = Project.class, actions = {EntityPolicyAction.READ, EntityPolicyAction.UPDATE})
    void project();

    @EntityAttributePolicy(entityClass = Task.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @EntityPolicy(entityClass = Task.class, actions = EntityPolicyAction.ALL)
    void task();

    @EntityAttributePolicy(entityClass = TimeEntry.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @EntityPolicy(entityClass = TimeEntry.class, actions = EntityPolicyAction.ALL)
    void timeEntry();

    @EntityAttributePolicy(entityClass = User.class, attributes = {"username", "lastName", "firstName"}, action = EntityAttributePolicyAction.VIEW)
    @EntityPolicy(entityClass = User.class, actions = EntityPolicyAction.READ)
    void user();

    @MenuPolicy(menuIds = {"Project.list", "Task_.list", "TimeEntry.list"})
    @ViewPolicy(viewIds = {"Project.list", "Task_.list", "TimeEntry.list", "User.list", "Project.detail", "Task_.detail", "TimeEntry.detail"})
    void viewsAndMenu();
}