package com.spring.blogapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorGlobalDetail {
    private LocalDate timestamp;
    private String messageEx;
    private String description;
}
