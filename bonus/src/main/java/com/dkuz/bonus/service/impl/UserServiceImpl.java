package com.dkuz.bonus.service.impl;

import com.dkuz.bonus.dto.UserDto;
import com.dkuz.bonus.model.Balance;
import com.dkuz.bonus.model.User;
import com.dkuz.bonus.repository.BalanceRepository;
import com.dkuz.bonus.repository.UserRepository;
import com.dkuz.bonus.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BalanceRepository balanceRepository;

    @Override
    @Transactional
    public UserDto createUser(UserDto userDto) {
        User user = new User();
        user.setName(userDto.getName());
        user.setSurname(userDto.getSurname());
        User save = userRepository.save(user);
        Balance balance = new Balance();
        balance.setUser(save);
        balance.setCount(0);
        balanceRepository.save(balance);
        return new UserDto(save.getId(), save.getName(), save.getSurname());
    }
}
