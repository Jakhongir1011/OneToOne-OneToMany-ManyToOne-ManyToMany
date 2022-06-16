package com.example.appjparelationships.payload;

// ma'lumot tashish uchun xizmat qiladigan class

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class UniversityDto {
    private String name;
    private String city;
    private String district;
    private String street;
}