package com.poland.college.entity;

import com.poland.college.entity.enums.Degree;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Entity
@Table(name = "department")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Department extends BaseEntitiy{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotBlank
    @Size(max = 300)
    private String name;

    private Double fee;

    @Column(length = 1000)
    private String info;

    @Column(length = 1000)
    private String comment;

    private LocalDate start;

    private LocalDate end;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private Degree degree;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "college_id", nullable = false)
    private College colleges;
}
