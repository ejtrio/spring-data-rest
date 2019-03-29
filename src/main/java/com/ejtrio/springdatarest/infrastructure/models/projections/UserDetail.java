package com.ejtrio.springdatarest.infrastructure.models.projections;

import com.ejtrio.springdatarest.infrastructure.models.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

@Projection(name = "userDetail", types = { User.class })
public interface UserDetail {

    @Value("#{target.firstName} #{target.lastName}")
    String getFullName();

    String getEmail();
}
