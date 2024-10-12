package com.springboot.blog.serviceinterfase;

import com.springboot.blog.dto.LoginDto;
import com.springboot.blog.dto.RegisterDto;

public interface LoginService {

    String login(LoginDto loginDto);

    String register(RegisterDto registerDto);

}















































