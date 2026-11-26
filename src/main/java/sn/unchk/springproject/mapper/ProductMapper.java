package sn.unchk.springproject.mapper;

import sn.unchk.springproject.dto.ProductDto;
import sn.unchk.springproject.entity.Product;

public class ProductMapper {

    public static ProductDto maoToProductDto(Product product){
        return new ProductDto(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getStock()
        );
    }


    public static Product mapToProduct(ProductDto productDto){
        return new Product(
                productDto.getId(),
                productDto.getName(),
                productDto.getPrice(),
                productDto.getStock()
        );
    }
}
