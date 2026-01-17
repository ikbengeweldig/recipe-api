package com.recipe.adapter.out;

import com.recipe.adapter.out.infrastructure.jpa.IngredientJpaEntity;
import com.recipe.domain.core.Ingredient;
import com.recipe.domain.core.IngredientId;
import com.recipe.domain.port.out.IngredientRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static java.util.Optional.ofNullable;

@Repository
@RequiredArgsConstructor
public class IngredientRepositoryJpaAdapter implements IngredientRepository {

    private final EntityManager entityManager;

    @Override
    public Ingredient getOrCreateByName(Ingredient ingredient) {

        IngredientJpaEntity alreadyPersisted = entityManager
                .createQuery("FROM IngredientJpaEntity ing WHERE LOWER(ing.name) = LOWER(:NAME) ", IngredientJpaEntity.class)
                .setParameter("NAME", ingredient.name())
                .getSingleResultOrNull();

        return ofNullable(alreadyPersisted)
                .map(this::toDomain)
                .orElseGet(() -> {
                    IngredientJpaEntity toPersist = IngredientJpaEntity
                            .builder()
                            .name(ingredient.name())
                            .isVegetarian(ingredient.isVegetarian())
                            .build();
                    entityManager.persist(toPersist);
                    return toDomain(toPersist);
                });
    }

    private Ingredient toDomain(IngredientJpaEntity jpaEntity) {

        return new Ingredient(new IngredientId(jpaEntity.getId()), jpaEntity.getName(), jpaEntity.getIsVegetarian());
    }
}
