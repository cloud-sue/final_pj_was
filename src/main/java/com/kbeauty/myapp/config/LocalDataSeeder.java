package com.kbeauty.myapp.config;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.kbeauty.myapp.entity.Product;
import com.kbeauty.myapp.entity.ProductDetail;
import com.kbeauty.myapp.repository.ProductDetailRepository;
import com.kbeauty.myapp.repository.ProductRepository;

@Configuration
@Profile("local")
public class LocalDataSeeder {

    @Bean
    CommandLineRunner seedProducts(ProductRepository productRepository, ProductDetailRepository detailRepository) {
        return args -> {
            if (productRepository.count() > 0) {
                return;
            }

            List<Product> products = productRepository.saveAll(List.of(
                    product("d'Alba", "화이트 트러플 퍼스트 스프레이 세럼 100ml", "광채 보습을 위한 미스트 세럼", 35000, 24,
                            "https://images.unsplash.com/photo-1620916566398-39f1143ab7be?auto=format&fit=crop&q=85&w=900", true),
                    product("Anua", "어성초 77 수딩 토너 250ml", "민감 피부를 위한 진정 토너", 28000, 18,
                            "https://images.unsplash.com/photo-1612817288484-6f916006741a?auto=format&fit=crop&q=85&w=900", true),
                    product("Beauty of Joseon", "맑은쌀 선크림 SPF50+ PA++++", "매일 바르기 좋은 촉촉한 선케어", 22000, 10,
                            "https://images.unsplash.com/photo-1556228578-8c89e6adf883?auto=format&fit=crop&q=85&w=900", true),
                    product("Torriden", "다이브인 저분자 히알루론산 세럼 50ml", "수분 레이어링을 위한 저자극 세럼", 26000, 15,
                            "https://images.unsplash.com/photo-1608248543803-ba4f8c70ae0b?auto=format&fit=crop&q=85&w=900", true),
                    product("Round Lab", "1025 독도 토너 200ml", "산뜻하게 닦아내는 데일리 토너", 24000, 12,
                            "https://images.unsplash.com/photo-1598440947619-2c35fc9aa908?auto=format&fit=crop&q=85&w=900", true),
                    product("Laneige", "워터뱅크 블루 히알루로닉 크림", "건조한 피부를 채우는 수분 크림", 42000, 20,
                            "https://images.unsplash.com/photo-1571781926291-c477ebfd024b?auto=format&fit=crop&q=85&w=900", true),
                    product("Dr.G", "레드 블레미쉬 클리어 수딩 크림", "민감 피부를 위한 산뜻한 진정 크림", 36000, 22,
                            "https://images.unsplash.com/photo-1601049541289-9b1b7bbbfe19?auto=format&fit=crop&q=85&w=900", true),
                    product("Innisfree", "그린티 씨드 히알루론 세럼", "녹차 수분 에너지 세럼", 33000, 16,
                            "https://images.unsplash.com/photo-1617897903246-719242758050?auto=format&fit=crop&q=85&w=900", true),
                    product("COSRX", "원스텝 오리지널 클리어 패드", "매끈한 피부결을 위한 토너 패드", 19000, 8,
                            "https://images.unsplash.com/photo-1556228453-efd6c1ff04f6?auto=format&fit=crop&q=85&w=900", false),
                    product("Mediheal", "티트리 에센셜 마스크 10매", "편안한 진정을 위한 데일리 마스크", 18000, 25,
                            "https://images.unsplash.com/photo-1573461160327-b450ce3d8e7f?auto=format&fit=crop&q=85&w=900", false),
                    product("Sulwhasoo", "윤조에센스 6세대 60ml", "첫 단계 피부 균형 에센스", 105000, 10,
                            "https://images.unsplash.com/photo-1596755389378-c31d21fd1273?auto=format&fit=crop&q=85&w=900", true),
                    product("Etude", "순정 약산성 5.5 폼 클렌저", "편안한 약산성 데일리 클렌저", 13000, 14,
                            "https://images.unsplash.com/photo-1580870069867-74c57ee1bb07?auto=format&fit=crop&q=85&w=900", false),
                    product("HERA", "블랙 쿠션 SPF34 PA++", "얇고 밀착되는 세미매트 쿠션", 72000, 18,
                            "https://images.unsplash.com/photo-1522335789203-aabd1fc54bc9?auto=format&fit=crop&q=85&w=900", true),
                    product("Rom&nd", "쥬시 래스팅 틴트 베스트 컬러", "맑고 생기 있는 데일리 립 틴트", 13000, 7,
                            "https://images.unsplash.com/photo-1586495777744-4413f21062fa?auto=format&fit=crop&q=85&w=900", false),
                    product("Peripera", "잉크 무드 글로이 틴트", "촉촉한 광택의 무드 립 컬러", 12000, 10,
                            "https://images.unsplash.com/photo-1512496015851-a90fb38ba796?auto=format&fit=crop&q=85&w=900", false),
                    product("Abib", "어성초 스팟 패드 카밍 터치", "간편하게 붙이는 진정 케어 패드", 24000, 19,
                            "https://images.unsplash.com/photo-1608248597279-f99d160bfcbc?auto=format&fit=crop&q=85&w=900", false)));

            detailRepository.saveAll(products.stream()
                    .map(product -> detail(product.getProductId(), 1, product.getMainImageUrl()))
                    .toList());
        };
    }

    private Product product(String brandName, String productName, String subTitle, int originalPrice,
            int discountRate, String mainImageUrl, boolean isGlobalBest) {
        Product product = new Product();
        product.setBrandName(brandName);
        product.setProductName(productName);
        product.setSubTitle(subTitle);
        product.setOriginalPrice(BigDecimal.valueOf(originalPrice));
        product.setDiscountRate(discountRate);
        product.setMainImageUrl(mainImageUrl);
        product.setIsGlobalBest(isGlobalBest);
        return product;
    }

    private ProductDetail detail(Long productId, int sortOrder, String imageUrl) {
        ProductDetail detail = new ProductDetail();
        detail.setProductId(productId);
        detail.setSortOrder(sortOrder);
        detail.setImageUrl(imageUrl);
        return detail;
    }
}
