package com.bookflow.business.service.abstraction;

import com.bookflow.business.dto.request.CreateBusinessRequest;
import com.bookflow.business.dto.response.BusinessDetailsResponse;
import com.bookflow.business.dto.response.BusinessListResponse;
import com.bookflow.business.dto.response.BusinessResponseAdmin;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;
import java.util.List;

public interface BusinessService {
    BusinessResponseAdmin createBusiness(CreateBusinessRequest BusinessRequest, Long ownerID);

    List<BusinessResponseAdmin> getAllBusinessAdmin();

    Page<BusinessListResponse> getAllActiveBusiness(Pageable pageable);

    BusinessResponseAdmin getBusinessByIdAdmin(Long id);

    BusinessDetailsResponse getMyBusiness(Long ownerId);

    Page<BusinessResponseAdmin> getAllPendingBusiness(Pageable pageable);

    BusinessDetailsResponse getBusinessById(Long id);

    BusinessResponseAdmin approvePendingBusiness(Long businessId);

    void deleteBusiness(Long id);

}
