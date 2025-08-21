package com.usuariosplazoleta.microservicio_usuarios.infrastructure.output.feign.adapter;

import com.usuariosplazoleta.microservicio_usuarios.domain.exception.DomainException;
import com.usuariosplazoleta.microservicio_usuarios.domain.model.RestaurantEmployee;
import com.usuariosplazoleta.microservicio_usuarios.domain.spi.IRestaurantPersistencePort;
import com.usuariosplazoleta.microservicio_usuarios.infrastructure.output.feign.client.IRestaurantFeignClient;
import com.usuariosplazoleta.microservicio_usuarios.infrastructure.output.feign.mapper.IRestaurantFeignMapper;
import feign.FeignException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RestaurantFeignAdapter implements IRestaurantPersistencePort {
    private final IRestaurantFeignClient restaurantFeignClient;
    private final IRestaurantFeignMapper restaurantFeignMapper;

    @Override
    public void saveRestaurantEmployee(RestaurantEmployee restaurantEmployee) {
        try {
            restaurantFeignClient.createRestaurantEmployee(restaurantFeignMapper.toRequest(restaurantEmployee));
        } catch (FeignException e) {
            throw new DomainException(e.getMessage());
        }
    }
}
