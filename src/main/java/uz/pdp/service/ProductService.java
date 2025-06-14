package uz.pdp.service;

import uz.pdp.baseAbstractions.BaseService;
import uz.pdp.model.Category;
import uz.pdp.model.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ProductService implements BaseService<Product> {
    private ArrayList<Product> products = new ArrayList<>();

    public List<Product> showAllProducts(UUID sellerId) {
        ArrayList<Product> sellerProducts = new ArrayList<>();
        for (Product p : products) {
            if (p != null && p.getSellerId().equals(sellerId)) {
                sellerProducts.add(p);
            }
        }
        return sellerProducts;
    }

    public List<Product> getByCategory(Category category) {
        List<Product> result = new ArrayList<>();
        for(Product p : products) {
            if(p != null && p.getCategory().equals(category) && p.isActive()) {
                result.add(p);
            }
        }
        return result;
    }

    public List<Product> searchByName(String name) {
        List<Product> result = new ArrayList<>();
        for (Product p : products) {
            if (p != null && p.isActive() && p.getName().toLowerCase().contains(name.toLowerCase())) {
                result.add(p);
            }
        }
        return result;
    }

    private boolean isDefined( UUID sellerId, String productName) {
        for (Product product : products) {
            if (product.getSellerId().equals(sellerId) && product.getName().equalsIgnoreCase(productName)) {
                return true;
            }
        }
        return false;
    }


    @Override
    public boolean add(Product product) {
        if (!isDefined(product.getSellerId(), product.getName())) {
            products.add(product);
            return true;
        }
        return false;
    }

    @Override
    public void update(UUID id, Product product) {
        Product old = getById(id);
        if (old != null) {
            old.setCategory(product.getCategory());
            old.setName(product.getName());
            old.setPrice(product.getPrice());
            old.setActive(product.isActive());
            old.updateTimestamp();
        }
    }

    @Override
    public void delete(UUID id) {
        Product deletingProduct = getById(id);
        if (deletingProduct != null) {
            deletingProduct.setActive(false);
        }
    }

    @Override
    public List<Product> showAll() {
        return products;
    }

    @Override
    public Product getById(UUID id) {
        for (Product p : products) {
            if (p != null && p.getId().equals(id)) {
                return p;
            }
        }
        return null;
    }

}
