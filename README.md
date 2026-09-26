# Sistema de Reservas de Laboratorios

Post-contenido U5 — Arquitectura en Capas y MVC + REST.

## Tecnologías
- Java 17, Spring Boot 3.2, Spring Data JPA, H2 (en memoria), Bean Validation, Thymeleaf.

## Arquitectura
Capas: `model` (entidades JPA) → `repository` (Spring Data JPA) → `service` (reglas de negocio) → `controller` (REST y, en la Parte 2, MVC).

## Decisiones de diseño

### 1. Regla de solapamiento de horarios en la capa Service
La validación de que un laboratorio no puede tener dos reservas en horarios que se crucen se implementó en `ReservaService.crearReserva()`, apoyada en una consulta JPQL (`ReservaRepository.buscarSolapamientos`) que compara `inicio`/`fin` y excluye las reservas `CANCELADA`. Se puso en el Service (no en el Controller ni en el Repository) porque es una regla de negocio, no de acceso a datos ni de transporte HTTP: así queda reutilizable y testeable de forma aislada.

### 2. Manejo de errores centralizado con @RestControllerAdvice
En vez de usar try/catch dentro de cada método del `ReservaController`, se centralizó la traducción de excepciones a códigos HTTP en `GlobalExceptionHandler`: `SolapamientoReservaException` → 409, `NoSuchElementException` → 404, `IllegalArgumentException` / errores de validación → 400. Esto mantiene el controller limpio y evita duplicar el mapeo de errores si se agregan más endpoints.