package com.usuariosplazoleta.microservicio_usuarios.infrastructure.output.feign.client;

import com.usuariosplazoleta.microservicio_usuarios.infrastructure.configuration.security.FeignGlobalConfig;
import com.usuariosplazoleta.microservicio_usuarios.infrastructure.output.feign.dto.RestaurantEmployeeFeignRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "microservicio-plazoleta", url = "${restaurants.service.url}", configuration = FeignGlobalConfig.class)
public interface IRestaurantFeignClient {

    @PostMapping("/saveRestaurantEmployee")
    void createRestaurantEmployee(@RequestBody RestaurantEmployeeFeignRequest request);
}
