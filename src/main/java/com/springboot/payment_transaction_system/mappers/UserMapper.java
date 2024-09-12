package com.springboot.payment_transaction_system.mappers;

import com.springboot.payment_transaction_system.dtos.UserRequestDto;
import com.springboot.payment_transaction_system.dtos.UserResponseDto;
import com.springboot.payment_transaction_system.models.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    private final ModelMapper modelMapper;

    @Autowired
    public UserMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public UserResponseDto toUserResponseDto(User user){
        return modelMapper.map(user,UserResponseDto.class);
    }

    public User toUser(UserRequestDto userRequestDto) {
        return modelMapper.map(userRequestDto,User.class);
    }

}
