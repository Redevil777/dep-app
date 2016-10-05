package com.app.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Created by andrei on 15.09.16.
 */
@MappedSuperclass
public abstract class AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private boolean enabled;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    @Column(name = "create_at")
    private LocalDateTime createAt;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    @Column(name = "update_at")
    private LocalDateTime updateAt;
    @Column(name = "create_by")
    private long createBy;
    @Column(name = "update_by")
    private long updateBy;

    public AbstractEntity() {
    }

    public AbstractEntity(long id){
        this.id = id;
    }

    public AbstractEntity(long id, boolean enabled, LocalDateTime createAt, LocalDateTime updateAt, long createBy, long updateBy) {
        this.id = id;
        this.enabled = enabled;
        this.createAt = createAt;
        this.updateAt = updateAt;
        this.createBy = createBy;
        this.updateBy = updateBy;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    public LocalDateTime getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(LocalDateTime updateAt) {
        this.updateAt = updateAt;
    }

    public long getCreateBy() {
        return createBy;
    }

    public void setCreateBy(long createBy) {
        this.createBy = createBy;
    }

    public long getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(long updateBy) {
        this.updateBy = updateBy;
    }
}
