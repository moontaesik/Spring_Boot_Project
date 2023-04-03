package com.reservation.foodTable.repository;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.reservation.foodTable.dto.RestaurantInfo;
import com.reservation.foodTable.entity.QArea;
import com.reservation.foodTable.entity.QCategory;
import com.reservation.foodTable.entity.QMenu;
import com.reservation.foodTable.entity.QReservationInfo;
import com.reservation.foodTable.entity.QRestaurant;
import com.reservation.foodTable.entity.QReview;
import com.reservation.foodTable.entity.Restaurant;
import com.reservation.foodTable.enumClass.PriceRange;

@Repository
public class RestaurantRepositoryCustomImpl implements RestaurantRepositoryCustom {

	@PersistenceContext
	private EntityManager em;

	@Override
	public List<RestaurantInfo> searchRestaurantByKeyword(int filterId, int sortingId, String searchQuery,
			List<Integer> categoryList, List<String> priceList, List<Integer> areaList, List<Integer> guList,
			List<Integer> dongList) {
		System.out
				.println(filterId + sortingId + searchQuery + categoryList + priceList + areaList + guList + dongList);
		return filterBuilder(filterId, sortingId, searchQuery, categoryList, priceList, areaList, guList, dongList);
	}

	@Override
	public Page<RestaurantInfo> searchRestaurantByKeyword(String searchQuer,Pageable pageable) {
		JPAQuery<Restaurant> results = new JPAQueryFactory(em)
				.selectDistinct(QRestaurant.restaurant)
		        .from(QRestaurant.restaurant)
		        .leftJoin(QMenu.menu).on(QMenu.menu.restaurant.eq(QRestaurant.restaurant))
		        .where(QRestaurant.restaurant.name.likeIgnoreCase("%"+searchQuer.trim().replace(" ", "")+"%")
		                .or(QMenu.menu.name.likeIgnoreCase("%"+searchQuer.trim().replace(" ", "")+"%")))
		        .orderBy(QRestaurant.restaurant.id.asc());

		long totalCount = results.fetchCount();
		results.offset(pageable.getOffset()).limit(pageable.getPageSize());
				
				
		
		List<RestaurantInfo> restaurantInfo = results.fetch().stream()
	            .map(RestaurantInfo::new)
	            .collect(Collectors.toList());
	    return new PageImpl<>(restaurantInfo, pageable, totalCount);
	}



	public List<RestaurantInfo> filterBuilder(int filterId, int sortingId, String searchQuery,
			List<Integer> categoryList, List<String> priceList, List<Integer> areaList, List<Integer> guList,
			List<Integer> dongList) {

		System.out.println("쿼리내용:"+searchQuery);
		BooleanBuilder builder = new BooleanBuilder();

		if (categoryList != null && categoryList.isEmpty()) {
			BooleanBuilder categoryNameBuilder = new BooleanBuilder();
			for (Integer category : categoryList) {
				categoryNameBuilder.or(QMenu.menu.category.id.eq(category));
			}
			builder.and(categoryNameBuilder);

		} else if (priceList != null) {
			System.out.println("가격대:");
			BooleanBuilder wonNameBuilder = new BooleanBuilder();
			for (String won : priceList) {
				wonNameBuilder.or(QRestaurant.restaurant.priceRange.eq(PriceRange.valueOf(won)));
			}
			builder.and(wonNameBuilder);

		} else if (areaList != null) {
			System.out.println("시:");
			BooleanBuilder areaNamesBuilder = new BooleanBuilder();
			for (Integer areaName : areaList) {
				areaNamesBuilder.or(QRestaurant.restaurant.si.id.eq(areaName));
			}
			builder.and(areaNamesBuilder);
		} else if (guList != null) {
			System.out.println("구:");
			BooleanBuilder guNamesBuilder = new BooleanBuilder();
			for (Integer guName : guList) {
				guNamesBuilder.or(QRestaurant.restaurant.gun.id.eq(guName));
			}
			builder.and(guNamesBuilder);
		} else if (dongList != null) {
			System.out.println("동:");
			BooleanBuilder dongNamesBuilder = new BooleanBuilder();
			for (Integer dongName : dongList) {
				dongNamesBuilder.or(QRestaurant.restaurant.dong.id.eq(dongName));
			}
			builder.and(dongNamesBuilder);
		}

		// filterId 1번이번 메뉴명/ 2번이면 가게명/ 3번이면 메뉴명+가게명 //디폴트(전쳬)
		switch (filterId) {
		default:
			System.out.println("디폴트:필터");
			builder.or(QMenu.menu.name.like("%" + searchQuery.trim().replace(" ", "") + "%"));
			builder.or(QRestaurant.restaurant.name.like("%" + searchQuery.trim().replace(" ", "") + "%"));
			break;

		case 1:
			System.out.println("메뉴명:필터");
			builder.and(QMenu.menu.name.like("%" + searchQuery.trim().replace(" ", "") + "%"));
			break;

		case 2:
			System.out.println("가게명:필터");
			builder.and(QRestaurant.restaurant.name.like("%" + searchQuery.trim().replace(" ", "") + "%"));
			break;

		case 3:
			System.out.println("전체:필터");
			builder.or(QMenu.menu.name.like("%" + searchQuery.trim().replace(" ", "") + "%"));
			builder.or(QRestaurant.restaurant.name.like("%" + searchQuery.trim().replace(" ", "") + "%"));
			break;

		}
		// 디폴트:정렬없음 /sortingId 1번:별점순 /2번:리뷰순 /3번:예약순
		List<Restaurant> results = null;
		switch (sortingId) {
		default:
			System.out.println("디폴트(없음):정렬");
			results = new JPAQueryFactory(em).selectDistinct(QRestaurant.restaurant).from(QRestaurant.restaurant)
					.join(QRestaurant.restaurant.dong, QArea.area).join(QRestaurant.restaurant.menuList, QMenu.menu)
					.fetchJoin().join(QMenu.menu.category, QCategory.category).fetchJoin().where(builder).fetch();
			break;
		case 1:
			System.out.println("별점순:정렬");	
			results = new JPAQueryFactory(em).selectDistinct(QRestaurant.restaurant).from(QRestaurant.restaurant)
			.join(QRestaurant.restaurant.dong, QArea.area).join(QRestaurant.restaurant.menuList, QMenu.menu)
			.fetchJoin().join(QMenu.menu.category, QCategory.category).fetchJoin().where(builder)
			.orderBy(QRestaurant.restaurant.score.totalScore.desc())
			.fetch();	
			break;
		case 2:
			System.out.println("리뷰순:정렬");
			results = new JPAQueryFactory(em).selectDistinct(QRestaurant.restaurant).from(QRestaurant.restaurant)
					.join(QRestaurant.restaurant.dong, QArea.area).join(QRestaurant.restaurant.menuList, QMenu.menu)
					.fetchJoin().join(QMenu.menu.category, QCategory.category).fetchJoin().where(builder)
					.orderBy(QReview.review.count().desc()).fetch();
			break;
		case 3:
			System.out.println("예약순:정렬");
			results = new JPAQueryFactory(em).selectDistinct(QRestaurant.restaurant).from(QRestaurant.restaurant)
					.join(QRestaurant.restaurant.dong, QArea.area)
					.join(QReservationInfo.reservationInfo.restaurant, QRestaurant.restaurant)
					.join(QRestaurant.restaurant.menuList, QMenu.menu).fetchJoin()
					.join(QMenu.menu.category, QCategory.category).fetchJoin().where(builder)
					.orderBy(QReservationInfo.reservationInfo.count().desc()).fetch();
			break;
		}

		List<RestaurantInfo> restaurantInfo = new ArrayList<>();
		results.forEach(restaurant -> restaurantInfo.add(new RestaurantInfo(restaurant)));

		return restaurantInfo;
	}

}
