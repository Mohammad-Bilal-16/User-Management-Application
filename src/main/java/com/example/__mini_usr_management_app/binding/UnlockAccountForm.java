package com.example.__mini_usr_management_app.binding;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UnlockAccountForm {

    private String email;
    private String tempPwd;
    private String newPwd;
    private String confirmPwd;
}
