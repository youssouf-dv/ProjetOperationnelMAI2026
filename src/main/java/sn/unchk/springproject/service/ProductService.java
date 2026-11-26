package sn.unchk.springproject.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sn.unchk.springproject.dto.ProductDto;
import sn.unchk.springproject.entity.Product;
import sn.unchk.springproject.mapper.ProductMapper;
import sn.unchk.springproject.repository.ProductRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;


    public ProductDto createProduct(ProductDto productDto){
        if (productRepository.existsProductByName(productDto.getName())){
            throw new RuntimeException("Ce Produit existe deja dans notre base.");
        }

        Product product = ProductMapper.mapToProduct(productDto);
        return ProductMapper.maoToProductDto(productRepository.save(product));
    }

    public List<ProductDto> getAllProducts(){
        return  productRepository.findAll().stream().map(ProductMapper::maoToProductDto).toList();
    }

    public ProductDto getProductById(Long id){
        Product product = productRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Product not found with this id "+ id)
        );

        return ProductMapper.maoToProductDto(product);
    }

    public ProductDto updateProduct(Long id, ProductDto productDto){
        Product product = productRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Product not found with this id "+ id)
        );

        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());
        product.setStock(productDto.getStock());

        return ProductMapper.maoToProductDto(productRepository.save(product));
    }

    public void deleteProduct(Long id){
        Product product = productRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Product not found with this id "+ id)
        );

        productRepository.delete(product);
    }
}
