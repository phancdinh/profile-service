package org.ht.profile.serviceFacade;

import org.ht.profile.dto.response.DemoGraphicsInfoResponse;
import org.ht.profile.dto.request.DemoGraphicsInfoCreateRequest;
import org.ht.profile.dto.request.DemoGraphicsInfoUpdateRequest;
import org.ht.profile.constants.DemoGraphicsInfoAttribute;

public interface DemoGraphicsInfoServiceFacade {
    DemoGraphicsInfoResponse create(String htId, DemoGraphicsInfoAttribute demoGraphicsInfoAttribute, DemoGraphicsInfoCreateRequest m);

    DemoGraphicsInfoResponse findByHtIdAndAttribute(String htId, DemoGraphicsInfoAttribute demoGraphicsInfoAttribute);

    DemoGraphicsInfoResponse update(String htId, DemoGraphicsInfoAttribute demoGraphicsInfoAttribute, DemoGraphicsInfoUpdateRequest m);

    void delete(String htId, DemoGraphicsInfoAttribute demoGraphicsInfoAttribute);
}
