package org.fitness.activityservice.feignClients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="auth-service")
public interface AuthServiceFeignClient {

    @GetMapping("/auth/validate/{id}")
    Boolean validateUser(@PathVariable("id") String id);
}
