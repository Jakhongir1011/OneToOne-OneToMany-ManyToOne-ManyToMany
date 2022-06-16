package com.example.appjparelationships.payload;

import com.example.appjparelationships.entity.Faculty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupDto {
    private String  name;
    private Integer facultyId;
}
