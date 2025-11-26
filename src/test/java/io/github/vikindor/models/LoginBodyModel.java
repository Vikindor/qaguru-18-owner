package io.github.vikindor.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginBodyModel {
    String userName, password;
}
