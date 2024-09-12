package com.springboot.payment_transaction_system.services;

import com.springboot.payment_transaction_system.dtos.UserRequestDto;
import com.springboot.payment_transaction_system.dtos.UserResponseDto;
import com.springboot.payment_transaction_system.enums.UserType;
import com.springboot.payment_transaction_system.exceptions.*;
import com.springboot.payment_transaction_system.mappers.UserMapper;
import com.springboot.payment_transaction_system.models.User;
import com.springboot.payment_transaction_system.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class UserServiceImp implements UserService{

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    @Autowired
    public UserServiceImp(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }


    @Override
    public Page<UserResponseDto> getUsers(Pageable pageable) {
        return userRepository.findAll(pageable).map(userMapper::toUserResponseDto);
    }

    @Override
    public UserResponseDto getUserById(Long id) {
        User user = findUserById(id);
        return userMapper.toUserResponseDto(user);
    }

    public User findUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(()->new UserNotFoundException("User with id: "+ id +" not found!"));
    }

    @Override
    @Transactional
    public UserResponseDto addUser(UserRequestDto userRequestDto) {
        User user= userMapper.toUser(userRequestDto);

        validateUniqueData(user);

        return userMapper.toUserResponseDto(userRepository.save(user));
    }

    private void validateUniqueData(User user) {
        if(userRepository.existsByDocument(user.getDocument())){
            throw new UserDocumentExistsException("User with document: "+ user.getDocument()+" already exists!");
        }
        if(userRepository.existsByEmail(user.getEmail())){
            throw new UserEmailExistsException("User with email: "+ user.getEmail()+" already exists!");
        }
    }

    @Override
    @Transactional
    public UserResponseDto updateUser(UserRequestDto userRequestDto,Long id) {
        User user= userMapper.toUser(userRequestDto);
        user.setId(id);

        User recoveredUser= findUserById(id);

        validateUpdateConflict(user,recoveredUser);//Validate no conflict Data

        BeanUtils.copyProperties(user,recoveredUser,"transactionsAsPayer","transactionsAsPayee");
        return userMapper.toUserResponseDto(recoveredUser);
    }

    private void validateUpdateConflict(User user,User recoveredUser) {
        if(userDocumentExistingAndBelongsToAnotherInstance(user.getDocument(),recoveredUser))
            throw new UserDocumentExistsException("User with document: "+ user.getDocument()+" already exists!");
        if(userEmailExistingAndBelongsToAnotherInstance(user.getEmail(),recoveredUser))
            throw new UserEmailExistsException("User with email: "+ user.getEmail()+" already exists!");
    }

    private boolean userDocumentExistingAndBelongsToAnotherInstance(String document,User recoveredUser) {
        return userRepository.existsByDocument(document) && !document.equals(recoveredUser.getDocument());
    }
    private boolean userEmailExistingAndBelongsToAnotherInstance(String email,User recoveredUser) {
        return userRepository.existsByEmail(email) && !email.equals(recoveredUser.getEmail());
    }

    //Note: Not required for the assessment test
    @Override
    @Transactional
    public UserResponseDto removeUser(Long id) {
        User user= findUserById(id);
        userRepository.delete(user);
        return userMapper.toUserResponseDto(user);
    }

    //This method will be called from the TransactionServiceImp class
    //Validate if the user type is allowed to make a transfer (just common user can make transfer)
    public void validateUserTypeAllowedToMakeTransfer(User user){
        if(user.getUserType()!=UserType.COMMON){
            throw new UserTypeNotAllowedToMakeTransferException("User Type not allowed to make Transfer!");
        }
    }

    //This method will be called from the TransactionServiceImp class
    public void validateTransactionAmount(User user,BigDecimal amount){
        if(amount.compareTo(user.getBalance())>0){//if amount is greater than the current balance
            throw new UserBalanceInsufficientException("The User Balance is insufficient!");
        }
    }

}
