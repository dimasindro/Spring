package by.belotskiy.keepintouch.controller.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterResponse {

    private String message;

    private String errorMessage;
}
