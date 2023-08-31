package com.iddev.repository;

import com.iddev.entity.Car;
import com.iddev.filters.CarFilter;
import com.iddev.predicates.QPredicate;
import com.querydsl.jpa.impl.JPAQuery;
import lombok.RequiredArgsConstructor;
import org.hibernate.graph.GraphSemantic;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import java.util.List;

import static com.iddev.entity.QCar.car;

@RequiredArgsConstructor
public class FilterCarRepositoryImpl implements FilterCarRepository {

    private final EntityManager entityManager;

    @Override
    public List<Car> findAllByFilter(CarFilter filter, EntityGraph<Car> graph) {
        var predicate = QPredicate.builder()
                .add(filter.getBrand(), car.brand::eq)
                .add(filter.getYear(), car.manufactureYear::eq)
                .add(filter.getCategory(), car.category::eq)
                .add(filter.getIsAvailable(), car.isAvailable::eq)
                .buildAnd();

        return new JPAQuery<Car>(entityManager)
                .select(car)
                .from(car)
                .where(predicate)
                .setHint(GraphSemantic.FETCH.getJpaHintName(), graph)
                .fetch();
    }

}
