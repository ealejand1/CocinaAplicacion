package com.example.cocina.API.receta_ingrediente;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class RecetaIngredienteDTO {

	private Long id;
	private float cantidad;
	private String unidades;
	private Long receta_id;
	private Long ingrediente_id;
}
