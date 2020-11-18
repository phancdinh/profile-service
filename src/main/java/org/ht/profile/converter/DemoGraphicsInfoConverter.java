package org.ht.profile.converter;

import org.ht.profile.dto.response.DemoGraphicsInfoResponse;
import org.ht.profile.dto.request.DemoGraphicsInfoCreateRequest;
import org.ht.profile.model.DemoGraphicsInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class DemoGraphicsInfoConverter {
    public DemoGraphicsInfo convert(DemoGraphicsInfoCreateRequest request) {
        DemoGraphicsInfo demoGraphicsInfo = new DemoGraphicsInfo();
        BeanUtils.copyProperties(request, demoGraphicsInfo);
        return demoGraphicsInfo;
    }

    public DemoGraphicsInfoResponse convert(DemoGraphicsInfo demoGraphicsInfo) {
        DemoGraphicsInfoResponse demoGraphicsInfoResponse = new DemoGraphicsInfoResponse();
        BeanUtils.copyProperties(demoGraphicsInfo, demoGraphicsInfoResponse);
        return demoGraphicsInfoResponse;
    }
}
