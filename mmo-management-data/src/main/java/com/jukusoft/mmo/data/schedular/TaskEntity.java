package com.jukusoft.mmo.data.schedular;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
@Table(name = "schedular_tasks", indexes = {
        //@Index(columnList = "email", name = "email_idx"),
}, uniqueConstraints = {
        //@UniqueConstraint(columnNames = {"user_id", "title"}, name = "userid_title_uqn")
})
public class TaskEntity {

    @Id
    @Size(min = 2, max = 200, message = "class name has to be between 2 and 255 chars")
    @Column(name = "class_name", nullable = false, updatable = false)
    @JsonIgnore
    private String className;

    @Size(min = 2, max = 90, message = "title has to be between 2 and 90 chars")
    @Column(name = "title", nullable = false, updatable = true)
    @NotEmpty(message = "title is required")
    private String title;

    @Size(min = 2, max = 255, message = "description has to be between 2 and 255 chars")
    @Column(name = "description", nullable = false, updatable = true)
    @NotEmpty(message = "description is required")
    private String description;

    @Column(name = "intervalInSeconds", nullable = false, updatable = true)
    private long intervalInSeconds;

    /**
     * last execution timestamp in ms (see also {@code System.currentTimeMillis()}
     */
    @Column(name = "last_execution", nullable = false, updatable = true)
    private long lastExecution = 0l;

    @Column(name = "success", nullable = false, updatable = true)
    private boolean lastExecutionSuccessful;

    @Column(name = "execution_counter", nullable = false, updatable = true)
    private int executionCounter = 0;

    @Column(name = "activated", nullable = false, updatable = true)
    private boolean activated = true;

    public TaskEntity(@Size(min = 2, max = 200, message = "class name has to be between 2 and 255 chars") String className, @Size(min = 2, max = 90, message = "title has to be between 2 and 90 chars") @NotEmpty(message = "title is required") String title, @Size(min = 2, max = 255, message = "description has to be between 2 and 255 chars") @NotEmpty(message = "description is required") String description, long intervalInSeconds) {
        this.className = className;
        this.title = title;
        this.description = description;
        this.intervalInSeconds = intervalInSeconds;
    }

    public TaskEntity() {
        //
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getIntervalInSeconds() {
        return intervalInSeconds;
    }

    public void setIntervalInSeconds(long intervalInSeconds) {
        this.intervalInSeconds = intervalInSeconds;
    }

    public long getLastExecution() {
        return lastExecution;
    }

    public void setLastExecution(long lastExecution) {
        this.lastExecution = lastExecution;
    }

    public boolean isLastExecutionSuccessful() {
        return lastExecutionSuccessful;
    }

    public void setLastExecutionSuccessful(boolean lastExecutionSuccessful) {
        this.lastExecutionSuccessful = lastExecutionSuccessful;
    }

    public int getExecutionCounter() {
        return executionCounter;
    }

    public void setExecutionCounter(int executionCounter) {
        this.executionCounter = executionCounter;
    }

    public boolean isActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }

}
