package com.iddev.repository;

import com.iddev.entity.Order;
import com.iddev.filters.ClientFilter;
import com.iddev.predicates.QPredicate;
import com.querydsl.jpa.impl.JPAQuery;
import lombok.RequiredArgsConstructor;
import org.hibernate.graph.GraphSemantic;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import java.util.List;

import static com.iddev.entity.QClient.client;
import static com.iddev.entity.QOrder.order;

@RequiredArgsConstructor
public class FilterOrderRepositoryImpl implements FilterOrderRepository{

    private final EntityManager entityManager;

}
