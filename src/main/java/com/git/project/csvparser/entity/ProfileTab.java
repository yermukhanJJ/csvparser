package com.git.project.csvparser.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfileTab {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "firstname")
    private String firstName;

    @Column(name = "lastname")
    private String lastName;

    @Column(name = "itn")
    private String itn;

    @Column(name = "status")
    private String status;

    public ProfileTab(String firstName, String lastName, String itn, String status) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.itn = itn;
        this.status = status;
    }
}
