package com.bookflow.admin.service.concrete;

import com.bookflow.admin.dto.response.AdminUsersResponse;
import com.bookflow.admin.mapper.AdminMapper;
import com.bookflow.admin.repository.AdminRepository;
import com.bookflow.admin.service.abstraction.AdminService;
import com.bookflow.business.entity.BusinessEntity;
import com.bookflow.business.enums.BusinessStatus;
import com.bookflow.business.repository.BusinessRepository;
import com.bookflow.email.abstraction.EmailProducer;
import com.bookflow.email.entity.EmailBlockMessage;
import com.bookflow.email.entity.EmailUnblockMessage;
import com.bookflow.exception.BusinessNotFoundException;
import com.bookflow.exception.UserCannotBlockException;
import com.bookflow.exception.UserNotFoundException;
import com.bookflow.user.entity.UserEntity;
import com.bookflow.user.enums.StatusEnum;
import com.bookflow.user.enums.UserEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminServiceHandler implements AdminService {

    private final AdminRepository adminRepository;
    private final AdminMapper adminMapper;
    private final EmailProducer emailProducer;
    private final BusinessRepository businessRepository;

    @Override
    public List<UserEntity> getUsers() {
        List<UserEntity> users = adminRepository.findAll();
        if (users.isEmpty()) {
            throw new UserNotFoundException("Users not found");
        }

        return users;
    }

    @Override
    public AdminUsersResponse getUserByID(Long id) {
        UserEntity userEntity = adminRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found !?"));
        return adminMapper.toResponse(userEntity);
    }

    @Override
    public void blockUser(String blockMessage, Long id) {
        UserEntity user = adminRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User doesn't exist!"));
        if (user.getRole() != UserEnum.ADMIN) {
            user.setStatus(StatusEnum.BLOCKED);
        } else {
            throw new UserCannotBlockException("This user role is ADMIN");
        }

        if (user.getRole() == UserEnum.OWNER) {
            BusinessEntity business = businessRepository.findByOwner_Id(id)
                    .orElseThrow(() -> new BusinessNotFoundException("Business not found"));
            business.setStatus(BusinessStatus.INACTIVE);
            businessRepository.save(business);
        }
        emailProducer.sendBlockUserEmailMessage(new EmailBlockMessage(user.getEmail(), blockMessage));
        adminRepository.save(user);
    }

    @Override
    public void unblockUser(Long id) {
        UserEntity user = adminRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found!?"));
        user.setStatus(StatusEnum.ACTIVE);
        if (user.getRole() == UserEnum.OWNER) {
            BusinessEntity business = businessRepository.findByOwner_Id(id)
                    .orElseThrow(() -> new BusinessNotFoundException("Business not found!?"));
            business.setStatus(BusinessStatus.ACTIVE);
            businessRepository.save(business);
        }
        emailProducer.sendUnbLockEmailMessage(new EmailUnblockMessage(user.getEmail()));
        adminRepository.save(user);


    }
}
