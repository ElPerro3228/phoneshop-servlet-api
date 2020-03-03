package com.es.phoneshop.service;

import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.CartItem;
import com.es.phoneshop.model.cart.OutOfStockException;
import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductDao;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

public class DefaultCartService implements CartService {

    private static final String CART_ATTRIBUTE = "cart_" + DefaultCartService.class;
    private ProductDao productDao;

    private static DefaultCartService instance;

    private DefaultCartService() {
        productDao = ArrayListProductDao.getInstance();
    }

    public static DefaultCartService getInstance() {
        if (instance == null) {
            instance = new DefaultCartService();
        }
        return instance;
    }

    @Override
    public Cart getCart(HttpServletRequest request) {
        return tryGetCart(request);
    }

    private Cart tryGetCart(HttpServletRequest request) {
        HttpSession session = request.getSession();
        synchronized (session) {
            Cart cart = (Cart) session.getAttribute(CART_ATTRIBUTE);
            if (cart == null) {
                cart = new Cart();
                session.setAttribute(CART_ATTRIBUTE, cart);
            }
            return cart;
        }
    }

    @Override
    public void add(Cart cart, Long productId, int quantity) throws OutOfStockException {
        synchronized (cart) {
            tryAddProduct(cart, productId, quantity);
        }
    }

    private void tryAddProduct(Cart cart, Long productId, int quantity) {
        Optional<Product> optionalProduct = productDao.getProduct(productId);
        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            Optional<CartItem> optionalCartItem = getCartItem(cart, productId);
            if (optionalCartItem.isPresent()) {
                increaseQuantity(quantity, product, optionalCartItem);
            } else {
                addNewCartItem(cart, productId, quantity, product);
            }
        }
    }

    private void addNewCartItem(Cart cart, Long productId, int quantity, Product product) {
        if (product.getStock() < quantity) {
            throw new OutOfStockException(product, quantity);
        }
        cart.getCartItems().add(new CartItem(productId, quantity));
    }

    private void increaseQuantity(int quantity, Product product, Optional<CartItem> optionalCartItem) {
        CartItem cartItem = optionalCartItem.get();
        if (product.getStock() < cartItem.getQuantity() + quantity) {
            throw new OutOfStockException(product, quantity);
        }
        cartItem.setQuantity(cartItem.getQuantity() + quantity);
    }

    private Optional<CartItem> getCartItem(Cart cart, Long productId) {
        return cart.getCartItems().stream()
                .filter(cartItem -> cartItem.getProductId().equals(productId))
                .findFirst();
    }
}
