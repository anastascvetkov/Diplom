package com.anastas.carsshop.helper;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Getter
@Setter
@NoArgsConstructor
public class ChangePasswordHelper {

    @NotNull(message = " Username is mandatory")
    @NotBlank(message = "Username cannot be empty")
    private String username;

    @NotNull(message = " Old password is mandatory")
    @Length(min = 8, message = "Password must be at least 8 symbols")
    private String oldPassword;
    @NotNull(message = "New password is mandatory")
    @Length(min = 8, message = "Password must be at least 8 symbols")
    private String newPassword;
    @NotNull(message = "Repeated new password is mandatory")
    @Length(min = 8, message = "Repeated password must be at least 8 symbols")
    private String newPasswordAgain;

    public ChangePasswordHelper(String username, String oldPassword, String newPassword, String newPasswordAgain) {
        this.username = username;
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
        this.newPasswordAgain = newPasswordAgain;
    }
}
