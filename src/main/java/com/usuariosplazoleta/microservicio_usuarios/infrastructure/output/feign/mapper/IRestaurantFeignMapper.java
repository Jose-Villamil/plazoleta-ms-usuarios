package com.usuariosplazoleta.microservicio_usuarios.infrastructure.output.feign.mapper;

import com.usuariosplazoleta.microservicio_usuarios.domain.model.RestaurantEmployee;
import com.usuariosplazoleta.microservicio_usuarios.infrastructure.output.feign.dto.RestaurantEmployeeFeignRequest;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IRestaurantFeignMapper {
    RestaurantEmployeeFeignRequest toRequest(RestaurantEmployee restaurantEmployee);
}
