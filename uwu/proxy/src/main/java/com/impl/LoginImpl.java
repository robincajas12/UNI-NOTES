package com.impl;

import com.inter.ILogin;

public class LoginImpl implements ILogin {
    @Override
    public boolean checkUsername(String name) {
        System.out.println("checking username : " + name);
        return false;
    }
}
