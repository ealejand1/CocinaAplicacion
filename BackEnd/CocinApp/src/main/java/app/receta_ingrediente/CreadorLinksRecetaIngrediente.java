package app.receta_ingrediente;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import app.ingrediente.ControladorIngredientes;
import app.receta.ControladorRecetas;


@Component
public class CreadorLinksRecetaIngrediente implements RepresentationModelAssembler<RecetaIngrediente, EntityModel<RecetaIngrediente>> {

    @Override
    public EntityModel<RecetaIngrediente> toModel(RecetaIngrediente recetaIngrediente) {
        return EntityModel.of(recetaIngrediente,
                linkTo(methodOn(ControladorRecetasIngredientes.class).obtenerRecetaIngredientePorId(recetaIngrediente.getId())).withSelfRel(),
                linkTo(methodOn(ControladorRecetasIngredientes.class).obtenerRecetasIngredientes()).withRel("recetas-ingredientes"),
                linkTo(methodOn(ControladorRecetas.class).obtenerRecetaPorId(recetaIngrediente.getReceta().getId())).withRel("receta"),
                linkTo(methodOn(ControladorIngredientes.class).obtenerIngredientePorId(recetaIngrediente.getIngrediente().getId())).withRel("ingrediente")
        );
    }
}
