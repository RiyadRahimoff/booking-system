package com.bookflow.business.service.abstraction;

import com.bookflow.business.dto.request.CreateBusinessRequest;
import com.bookflow.business.dto.response.BusinessResponse;
import com.bookflow.business.entity.BusinessEntity;

import java.util.List;

public interface BusinessService {
  BusinessResponse createBusiness(CreateBusinessRequest BusinessRequest, Long ownerID);
  List<BusinessEntity> getAllBusiness();

}
