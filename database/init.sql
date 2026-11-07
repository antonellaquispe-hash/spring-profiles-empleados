CREATE DATABASE IF NOT EXISTS empleados_dev;
CREATE DATABASE IF NOT EXISTS empleados_qa;
CREATE DATABASE IF NOT EXISTS empleados_test;
CREATE DATABASE IF NOT EXISTS empleados_prod;

CREATE TABLE IF NOT EXISTS empleados_dev.empleado (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombres VARCHAR(100) NOT NULL,
    apellidos VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    sueldo DECIMAL(10,2) NOT NULL
);
CREATE TABLE IF NOT EXISTS empleados_qa.empleado LIKE empleados_dev.empleado;
CREATE TABLE IF NOT EXISTS empleados_test.empleado LIKE empleados_dev.empleado;
CREATE TABLE IF NOT EXISTS empleados_prod.empleado LIKE empleados_dev.empleado;
