package com.diploma.linguistic_glucose_analyzer.service;

import com.diploma.linguistic_glucose_analyzer.dto.UserSummaryDTO;
import com.diploma.linguistic_glucose_analyzer.model.User;

public interface UserService extends CrudService<User, Long>  {
    UserSummaryDTO getUserSummary(long userId);
    void updateUserData(long userId, UserSummaryDTO summary);
}
