package com.usuariosplazoleta.microservicio_usuarios.application.mapper;

import com.usuariosplazoleta.microservicio_usuarios.application.dto.request.UserClientRequestDto;
import com.usuariosplazoleta.microservicio_usuarios.application.dto.request.UserEmployeeRequestDto;
import com.usuariosplazoleta.microservicio_usuarios.application.dto.request.UserRequestDto;
import com.usuariosplazoleta.microservicio_usuarios.domain.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IUserRequestMapper {

    @Mapping(target = "birthDate", expression = "java(parseDate(userRequestDto.getBirthDate()))")
    User toUser(UserRequestDto userRequestDto);

    User employeeToUser(UserEmployeeRequestDto userEmployeeRequestDto);

    User clientToUser(UserClientRequestDto userClientRequestDto);

    default LocalDate parseDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return LocalDate.parse(date, formatter);
    }

}
