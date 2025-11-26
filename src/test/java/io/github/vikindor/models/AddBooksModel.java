package io.github.vikindor.models;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class AddBooksModel {
    String userId;
    List<IsbnModel> collectionOfIsbns;
}
