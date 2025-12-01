package com.projectLudoteca.ludoteca.common.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@EntityListeners(AuditingEntityListener.class)
public class EducationalInstitution implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "uuid", updatable = false, nullable = false)
    private UUID id;

    private String institutionName;

    @Column(nullable = false, columnDefinition = "boolean default false")
    private Boolean isUtfpr = false;

    @OneToMany(mappedBy = "educationalInstitution", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<User> users = new ArrayList<>();

    public EducationalInstitution() {}

    public  EducationalInstitution(String institutionName) {
        this.institutionName = institutionName;
    }
}
