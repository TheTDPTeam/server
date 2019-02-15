package com.tdpteam.repo.entity.user;

import com.tdpteam.repo.entity.BClass;
import com.tdpteam.repo.entity.Batch;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Table(name = "student")
@NoArgsConstructor
public class Student {

    public Student(Account account) {
        this.account = account;
    }

    @Id
    private Long id;

    @OneToOne
    @JoinColumn(name = "account_id")
    @MapsId
    @NonNull
    private Account account;

    @ManyToMany(mappedBy = "students")
    private Set<BClass> bClasses = new HashSet<>();

    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "batch_id", nullable = true)
    private Batch batch;
}
