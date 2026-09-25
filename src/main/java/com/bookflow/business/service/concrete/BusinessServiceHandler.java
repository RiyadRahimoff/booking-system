package com.bookflow.business.service.concrete;

import com.bookflow.business.dto.request.CreateBusinessRequest;
import com.bookflow.business.dto.response.BusinessResponse;
import com.bookflow.business.entity.BusinessEntity;
import com.bookflow.business.enums.BusinessStatus;
import com.bookflow.business.mapper.BusinessMapper;
import com.bookflow.business.repository.BusinessRepository;
import com.bookflow.business.service.abstraction.BusinessService;
import com.bookflow.exception.BusinessAlreadyExistsException;
import com.bookflow.exception.BusinessNotFoundException;
import com.bookflow.exception.UserNotFoundException;
import com.bookflow.user.entity.UserEntity;
import com.bookflow.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static com.bookflow.business.enums.BusinessStatus.ACTIVE;
import static com.bookflow.business.enums.BusinessStatus.PENDING;

@Service
@RequiredArgsConstructor
public class BusinessServiceHandler implements BusinessService {
    private final BusinessRepository businessRepository;
    private final BusinessMapper businessMapper;
    private final UserRepository userRepository;

    @Override
    public BusinessResponse createBusiness(CreateBusinessRequest businessRequest, Long ownerID) {
        if (businessRepository.existsByOwner_Id(ownerID)) {
            throw new BusinessAlreadyExistsException(
                    "Owner already has a business and cannot create a second one"
            );
        }

        UserEntity owner = userRepository.findById(ownerID)
                .orElseThrow(() ->
                        new UserNotFoundException("Owner not found")
                );

        BusinessEntity business = new BusinessEntity();
        business.setName(businessRequest.name());
        business.setEmail(businessRequest.email());
        business.setDescription(businessRequest.description());
        business.setAddress(businessRequest.address());
        business.setCity(businessRequest.city());
        business.setPhone(businessRequest.phone());
        business.setLatitude(businessRequest.latitude());
        business.setLongitude(businessRequest.longitude());
        business.setOwner(owner);
        business.setStatus(PENDING);

        BusinessEntity savedBusiness = businessRepository.save(business);

        return businessMapper.toResponse(savedBusiness);

    }

    @Override
    public List<BusinessResponse> getAllBusiness() {
        return businessRepository.findAll()
                .stream()
                .map(businessMapper::toResponse)
                .toList();
    }

    @Override
    public BusinessResponse getBusinessById(Long id) {
        BusinessEntity business = businessRepository.findById(id)
                .orElseThrow(() -> new BusinessNotFoundException("Business not found!"));
        return businessMapper.toResponse(business);
    }

    @Override
    public List<BusinessResponse> getAllPendingBusiness() {
        return businessRepository.findAllByStatus(PENDING)
                .stream()
                .map(businessMapper::toResponse)
                .toList();
    }

    @Override
    public BusinessResponse approvePendingBusiness(Long businessId) {
        BusinessEntity business = businessRepository.findById(businessId)
                .orElseThrow(() ->
                        new BusinessNotFoundException("Business not found")
                );
        business.setStatus(ACTIVE);
        BusinessEntity savedBusiness = businessRepository.save(business);

        return businessMapper.toResponse(savedBusiness);
    }
}
