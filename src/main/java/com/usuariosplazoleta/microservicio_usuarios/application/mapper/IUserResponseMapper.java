package com.usuariosplazoleta.microservicio_usuarios.application.mapper;

import com.usuariosplazoleta.microservicio_usuarios.application.dto.response.UserResponseDto;
import com.usuariosplazoleta.microservicio_usuarios.domain.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IUserResponseMapper {
    @Mapping(source = "user.name", target = "name")
    @Mapping(source = "user.lastName", target = "lastName")
    @Mapping(source = "user.document", target = "document")
    @Mapping(source = "user.phoneNumber", target = "phoneNumber")
    @Mapping(source = "user.birthDate", target = "birthDate")
    @Mapping(source = "user.email", target = "email")
    @Mapping(source = "user.role", target = "role")
    UserResponseDto toResponse(User user);
    List<UserResponseDto> userListToUserResponseList(List<User> userList);
}
