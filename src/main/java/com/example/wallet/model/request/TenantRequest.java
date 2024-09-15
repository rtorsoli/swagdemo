package com.example.wallet.model.request;

import com.example.wallet.model.Request;
import com.example.wallet.validator.Updated;
import com.example.wallet.validator.Registration;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Builder
@Accessors(fluent = false, chain = true)
public class TenantRequest implements Request {
    
    @NotNull(message = "Id is mandatory", groups = Updated.class)
    private Long id;
    
    @NotNull(message = "Firstname is mandatory", groups = { Registration.class, Updated.class })
    @NotBlank(message = "Firstname is mandatory", groups = { Registration.class, Updated.class })
    @Size(max=255, groups = { Registration.class, Updated.class })
    private String firstname;
    
    @NotNull(message = "Lastname is mandatory", groups = { Registration.class, Updated.class })
    @NotBlank(message = "Lastname is mandatory", groups = { Registration.class, Updated.class })
    @Size(max=255, groups = { Registration.class, Updated.class })
    private String lastname;

    @NotNull(message = "Nickname is mandatory", groups = { Registration.class, Updated.class })
    @NotBlank(message = "Nickname is mandatory", groups = { Registration.class, Updated.class })
    @Size(max=255, groups = { Registration.class, Updated.class })
    private String nickname;

    @NotNull(message = "Password is mandatory", groups = Registration.class)
    @NotBlank(message = "Password is mandatory", groups = Registration.class)
    @Size(max=255, groups = Registration.class)
    private String password;

    @NotNull(message = "Email is mandatory", groups = { Registration.class, Updated.class })
    @NotBlank(message = "Email is mandatory", groups = { Registration.class, Updated.class })
    @Size(max=255, groups = { Registration.class, Updated.class })
    @Email(regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$",
    flags = Pattern.Flag.CASE_INSENSITIVE, message = "Email is not valid", groups = { Registration.class, Updated.class })
    private String email;

    
}
