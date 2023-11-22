# 코디 서비스

## 어플리케이션 실행
```
sh start_app.sh
```

## API
### 상품등록
* POST
* http://localhost:8080/products
* {
  "brandId" : 2,
  "category" : "BAG",
  "price" : 6000
  }

### 상품삭제
* DELETE
* http://localhost:8080/products/5

### 상품 가격 변경
* PATCH
* http://localhost:8080/products/5/price
* {
  "price" : 3000
  }

### 브랜드 삭제
* DELETE
* http://localhost:8080/brands/4

### 카테고리별 최저가 상품 리스트 조회
* GET
* http://localhost:8080/lowest-price-product-by-category

## 추가 설명
CQRS 패턴을 적용하였습니다.
상품 추가, 수정, 삭제와 브랜드 삭제시 이벤트를 발행해 읽기모델인 카테고리별 최저가 상품의 정보를 비동기적으로 동기화합니다.
커맨드 모델과 쿼리 모델의 정합성을 맞추기 위해서 트랜잭션 아웃박스 패턴을 적용하였습니다.
아웃박스 처리는 트랜잭션 이벤트 리스너와 스케줄러에 의해 수행됩니다.

## 구현 범위
과제 설명의 구현1과 구현4를 구현하였으며, 2번과 3번은 일정상 구현하지 못했습니다.
구현 2번과 3번은 구현1번과 마찬가지로 CQRS를 적용하여 구현할 수 있을것으로 판단됩니다.