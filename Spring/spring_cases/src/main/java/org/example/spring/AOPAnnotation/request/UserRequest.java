package org.example.spring.AOPAnnotation.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;


/**
 * @author liushaoya
 * @since 2025-10-04 15:37
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {
    @NotNull
    private String name;

    @NotNull
    private String age;
}
