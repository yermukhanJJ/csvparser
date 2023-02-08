package com.git.project.csvparser.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProfileRequest {

    private String itn;

    private String firstname;

    private String lastname;

    private String status;

}
