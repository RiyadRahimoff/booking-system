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

    BusinessDetailsResponse getBusinessById(Long id);

    List<BusinessResponseAdmin> getAllPendingBusiness();

    BusinessResponseAdmin approvePendingBusiness(Long businessId);

}
