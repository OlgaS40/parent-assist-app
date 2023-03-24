package com.parentapp.product;

import com.parentapp.plan.Plan;
import com.parentapp.plan.PlanRepository;
import com.parentapp.util.NotFoundException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final PlanRepository planRepository;

    public ProductService(ProductRepository productRepository, PlanRepository planRepository) {
        this.productRepository = productRepository;
        this.planRepository = planRepository;
    }


    public List<ProductDTO> findAll() {
        final List<Product> products = productRepository.findAll(Sort.by("id"));
        return products.stream()
                .map((product) -> mapToDTO(product, new ProductDTO()))
                .collect(Collectors.toList());
    }

    public ProductDTO get(final String id) {
        return productRepository.findById(id)
                .map(product -> mapToDTO(product, new ProductDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public String create(final ProductDTO productDTO) {
        final Product product = new Product();
        mapToEntity(productDTO, product);
        product.setId(productDTO.getId());
        return productRepository.save(product).getId();
    }

    public void update(final String id, final ProductDTO productDTO) {
        final Product product = productRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(productDTO, product);
        productRepository.save(product);
    }

    public void delete(final String id) {
        productRepository.deleteById(id);
    }

    private ProductDTO mapToDTO(final Product product,
                                  final ProductDTO productDTO) {
        productDTO.setId(product.getId());
        productDTO.setName(product.getName());
        productDTO.setDescription(product.getDescription());
        productDTO.setCreateDate(product.getCreateDate());
        productDTO.setPlanIdList(product.getProductPlans().stream()
                .map(Plan::getId)
                .collect(Collectors.toSet()));
        return productDTO;
    }

    private Product mapToEntity(final ProductDTO productDTO,
                                  final Product product) {
        product.setId(productDTO.getId());
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setCreateDate(productDTO.getCreateDate());
        product.setProductPlans(productDTO.getPlanIdList().stream()
                .map(id -> planRepository.findById(id).orElseThrow(NotFoundException::new))
                .collect(Collectors.toSet()));
        return product;
    }

    public boolean idExists(final String id) {
        return planRepository.existsByIdIgnoreCase(id);
    }
}
