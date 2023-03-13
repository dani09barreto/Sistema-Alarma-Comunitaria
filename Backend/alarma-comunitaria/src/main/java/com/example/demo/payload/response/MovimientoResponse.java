package com.example.demo.payload.response;

public class MovimientoResponse {

    private Long id;
    private Long sensorId;
    private String fecha;
    private String hora;
    private String movimiento;

    public MovimientoResponse(Long id, Long sensorId, String fecha, String hora, String movimiento) {
        this.id = id;
        this.sensorId = sensorId;
        this.fecha = fecha;
        this.hora = hora;
        this.movimiento = movimiento;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSensorId() {
        return sensorId;
    }

    public void setSensorId(Long sensorId) {
        this.sensorId = sensorId;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getMovimiento() {
        return movimiento;
    }

    public void setMovimiento(String movimiento) {
        this.movimiento = movimiento;
    }
}
