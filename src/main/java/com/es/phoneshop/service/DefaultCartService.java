package com.es.phoneshop.service;

import com.es.phoneshop.exceptions.NegativeNumberException;
import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.CartItem;
import com.es.phoneshop.exceptions.CartItemNotfoundException;
import com.es.phoneshop.exceptions.OutOfStockException;
import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductDao;
import com.es.phoneshop.model.product.ProductUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.text.NumberFormat;
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
        if (quantity < 0) {
            throw new NegativeNumberException("Negative number");
        }
        Optional<Product> optionalProduct = productDao.getProduct(productId);
        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            Optional<CartItem> optionalCartItem = getCartItem(cart, productId);
            if (optionalCartItem.isPresent()) {
                increaseQuantity(quantity, product, optionalCartItem);
            } else {
                addNewCartItem(cart, product, quantity);
            }
        }
    }

    private void addNewCartItem(Cart cart, Product product, int quantity) {
        checkStock(quantity, product);
        cart.getCartItems().add(new CartItem(product, quantity));
    }

    private void increaseQuantity(int quantity, Product product, Optional<CartItem> optionalCartItem) {
        CartItem cartItem = optionalCartItem.get();
        checkStock(cartItem.getQuantity() + quantity, product);
        cartItem.setQuantity(cartItem.getQuantity() + quantity);
    }

    private Optional<CartItem> getCartItem(Cart cart, Long productId) {
        return cart.getCartItems().stream()
                .filter(cartItem -> cartItem.getProduct().getId().equals(productId))
                .findFirst();
    }

    @Override
    public void update(Cart cart, Long productId, int quantity) {
        if (quantity < 0) {
            throw new NegativeNumberException("Negative number");
        }
        synchronized (cart) {
            CartItem cartItem = cart.getCartItems().stream()
                    .filter(item -> item.getProduct().getId().equals(productId))
                    .findFirst().orElseThrow(CartItemNotfoundException::new);
            checkStock(quantity, cartItem.getProduct());
            cartItem.setQuantity(quantity);
        }
    }

    @Override
    public void delete(Cart cart, long productId) {
        synchronized (cart) {
            cart.getCartItems().removeIf(item -> item.getProduct().getId().equals(productId));
        }
    }

    @Override
    public void calculateTotalPrice(Cart cart) {
        synchronized (cart) {
            double result = cart.getCartItems().stream()
                    .mapToDouble(c -> c.getQuantity() * c.getProduct().getPrice().doubleValue())
                    .sum();
            cart.setTotal(new BigDecimal(result));
        }
    }

    @Override
    public String getTotalPrice(HttpServletRequest request, Cart cart) {
        NumberFormat format = NumberFormat.getCurrencyInstance(request.getLocale());
        format.setCurrency(ProductUtil.CURRENCY);
        return format.format(cart.getTotal());
    }

    private void checkStock(int quantity, Product product) {
        if (product.getStock() < quantity) {
            throw new OutOfStockException("Not enough stock", product, quantity);
        }
    }
}
