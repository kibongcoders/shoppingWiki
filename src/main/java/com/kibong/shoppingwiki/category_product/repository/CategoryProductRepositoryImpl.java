package com.kibong.shoppingwiki.category_product.repository;

import com.kibong.shoppingwiki.domain.CategoryProduct;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;

import java.util.Optional;

import static com.kibong.shoppingwiki.domain.QCategory.category;
import static com.kibong.shoppingwiki.domain.QCategoryProduct.categoryProduct;
import static com.kibong.shoppingwiki.domain.QProduct.product;

public class CategoryProductRepositoryImpl implements CategoryProductCustom {

    private final JPAQueryFactory queryFactory;

    public CategoryProductRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Optional<CategoryProduct> getCategoryProductByName(String product_name, String categoryName) {

        return Optional.ofNullable(queryFactory.select(categoryProduct)
                .from(categoryProduct)
                .leftJoin(categoryProduct.category, category).fetchJoin()
                .leftJoin(categoryProduct.product, product).fetchJoin()
                .where(category.categoryName.eq(categoryName), product.productName.eq(product_name))
                .fetchOne());
    }
}
