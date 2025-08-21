package com.usuariosplazoleta.microservicio_usuarios.domain.model;

public class RestaurantEmployee {
    private Long id;
    private Long restaurantId;
    private Long employeeId;

    public RestaurantEmployee(Long idRestaurant, Long idEmployee) {
        this.restaurantId = idRestaurant;
        this.employeeId = idEmployee;
    }

    public Long getId() {return id;}

    public Long getRestaurantId() {
        return restaurantId;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setRestaurantId(Long restaurantId) {
        this.restaurantId = restaurantId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }
}
