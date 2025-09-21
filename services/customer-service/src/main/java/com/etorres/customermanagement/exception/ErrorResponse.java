package com.etorres.customermanagement.exception;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * Una estructura para enviar detalles de errores en las respuestas HTTP.
 * @param timestamp la fecha y hora del error
 * @param status el código de estado HTTP
 * @param error la descripción del error
 * @param message un mensaje detallado del error
 * @param details detalles adicionales sobre el error (opcional)
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ErrorResponse(
        LocalDateTime timestamp,
        int status,
        String error,
        String message,
        Map<String, String> details
) {
}
