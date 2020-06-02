package com.jukusoft.mmo.data.entity.log;

import com.jukusoft.mmo.data.entity.AbstractEntity;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
@Table(name = "logs", indexes = {
        //@Index(columnList = "email", name = "email_idx"),
}, uniqueConstraints = {
        //@UniqueConstraint(columnNames = "username", name = "username_uqn")
})
@Cacheable(value = false)//use second level cache
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.NONE)
public class LogEntryEntity extends AbstractEntity {

    //@Size(min = 2, max = 45)
    @Column(name = "title", unique = false, nullable = false, updatable = true)
    @NotEmpty(message = "title is required")
    private String title;

    //@Size(min = 2, max = 45)
    @Column(name = "message", unique = false, nullable = true, updatable = true)
    //@NotEmpty(message = "message is required")
    private String message;

    public LogEntryEntity() {
        //
    }

    public LogEntryEntity(@Size(min = 2, max = 45) @NotEmpty(message = "title is required") String title, @Size(min = 2, max = 45) @NotEmpty(message = "message is required") String message) {
        this.title = title;
        this.message = message;
    }

    public String getTitle() {
        return title;
    }

    public String getMessage() {
        return message;
    }

}
