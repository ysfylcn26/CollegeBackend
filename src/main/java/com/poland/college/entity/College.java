package com.poland.college.entity;

import lombok.*;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "college", uniqueConstraints = {
        @UniqueConstraint(columnNames = "name")
})
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Validated
public class College extends BaseEntitiy{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank
    @Size(max = 300)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "country_id", nullable = false)
    private Country country;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "colleges", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Department> departments = new HashSet<>();
}
