package com.shaarky.hms.entity;

import com.shaarky.hms.entity.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(
        name = "departments",
        indexes = {
                @Index(name = "idx_department_name", columnList = "name", unique = true),
                @Index(name = "idx_department_deleted", columnList = "is_deleted")
        }
)
public class Department extends BaseEntity {

    @Column(name = "name", nullable = false, unique = true, length = 100)
    private String name;

    @Column(name = "description", length = 500)
    private String description;
}