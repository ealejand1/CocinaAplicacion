package com.example.cocina.API.valoraciones;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder


public class ValoracionDTO {
	private Long id;
	private int puntuacion;
	private String comentario;
	private Long usuario_id;
	private Long receta_id;
	
}
