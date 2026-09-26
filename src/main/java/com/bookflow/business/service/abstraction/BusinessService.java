package com.bookflow.business.service.abstraction;

import com.bookflow.business.dto.request.CreateBusinessRequest;
import com.bookflow.business.dto.response.BusinessDetailsResponse;
import com.bookflow.business.dto.response.BusinessListResponse;
import com.bookflow.business.dto.response.BusinessResponseAdmin;

import java.util.List;

public interface BusinessService {
    BusinessResponseAdmin createBusiness(CreateBusinessRequest BusinessRequest, Long ownerID);

    List<BusinessResponseAdmin> getAllBusinessAdmin();

    List<BusinessListResponse> getAllActiveBusiness();

    BusinessResponseAdmin getBusinessByIdAdmin(Long id);

    BusinessDetailsResponse getMyBusiness(Long ownerId);

    List<BusinessResponseAdmin> getAllPendingBusiness();

    BusinessDetailsResponse getBusinessById(Long id);

    BusinessResponseAdmin approvePendingBusiness(Long businessId);

    void deleteBusiness(Long id);

}
