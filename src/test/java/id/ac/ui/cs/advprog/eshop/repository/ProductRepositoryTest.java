package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProductRepositoryTest {

    @InjectMocks
    ProductRepository productRepository;

    @BeforeEach
    void setUp() {
      // No setup required for this test class
    }

    @Test
    void testCreateAndFind() {
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);
        productRepository.create(product);

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
        Product savedProduct = productIterator.next();
        assertEquals(product.getProductId(), savedProduct.getProductId());
        assertEquals(product.getProductName(), savedProduct.getProductName());
        assertEquals(product.getProductQuantity(), savedProduct.getProductQuantity());
    }

    @Test
    void testFindAllIfEmpty() {
        Iterator<Product> productIterator = productRepository.findAll();
        assertFalse(productIterator.hasNext());
    }

    @Test
    void testFindAllIfMoreThanOneProduct() {
        Product product1 = new Product();
        product1.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product1.setProductName("Sampo Cap Bambang");
        product1.setProductQuantity(100);
        productRepository.create(product1);

        Product product2 = new Product();
        product2.setProductId("a0f9de46-90b1-437d-a0bf-d0821dde9096");
        product2.setProductName("Sampo Cap Usep");
        product2.setProductQuantity(50);
        productRepository.create(product2);

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
        Product savedProduct = productIterator.next();
        assertEquals(product1.getProductId(), savedProduct.getProductId());
        savedProduct = productIterator.next();
        assertEquals(product2.getProductId(), savedProduct.getProductId());
        assertFalse(productIterator.hasNext());
    }

    @Test
    void testEdit(){
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);
        productRepository.create(product);

        // edit
        Product editedProduct = new Product();
        editedProduct.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        editedProduct.setProductName("Sampo ABC");
        editedProduct.setProductQuantity(50);

        productRepository.update(editedProduct);

        // positive case
        Product updated = productRepository.findById("eb558e9f-1c39-460e-8860-71af6af63bd6");
        assertNotNull(updated);
        assertEquals("Sampo ABC", updated.getProductName());
        assertEquals(50, updated.getProductQuantity());

        // negative case
        assertNotEquals("Sampo Cap Bambang", updated.getProductName());
        assertNotEquals(100, updated.getProductQuantity());
    }


    @Test
    void testDelete(){
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sabun Cap Bango");
        product.setProductQuantity(10);
        productRepository.create(product);

        // delete product
        productRepository.deleteById("eb558e9f-1c39-460e-8860-71af6af63bd6");

        // positive case
        assertNull(productRepository.findById("eb558e9f-1c39-460e-8860-71af6af63bd6"));
        Iterator<Product> productIterator = productRepository.findAll();
        assertFalse(productIterator.hasNext());     // deletion succeeds

        // negative case
        Product product2 = new Product();
        product2.setProductId("id1");
        product2.setProductName("Sabun Cap Bambang");
        product2.setProductQuantity(10);
        productRepository.create(product2);

        productRepository.deleteById("12345");  // delete by the wrong product id (unexisted)
        assertNotNull(productRepository.findById("id1"));

        Iterator<Product> iteratorAfterWrongDeletion = productRepository.findAll();
        assertTrue(iteratorAfterWrongDeletion.hasNext());
    }

    @Test
    void testFindById_emptyRepo() {  // new
        assertNull(productRepository.findById("000"));  // empty repo
    }

    @Test
    void testFindById_returnProduct() { // new
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sabun Cap Bango");
        product.setProductQuantity(10);
        productRepository.create(product);

        assertEquals(product, productRepository.findById("eb558e9f-1c39-460e-8860-71af6af63bd6"));
    }

    @Test
    void testFindById_notFound() {   // new
        Product product = new Product();
        product.setProductId("id1");
        product.setProductQuantity(10);
        product.setProductName("Pasta Gigi");
        productRepository.create(product);

        // non-existing id
        assertNull(productRepository.findById("12345"));
    }

    @Test
    void testUpdate() {   // new
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Pasta Gigi");
        product.setProductQuantity(10);
        productRepository.create(product);

        Product updated = new Product();
        updated.setProductName("Pepsodent");
        updated.setProductQuantity(15);
        updated.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        productRepository.update(updated);

        assertEquals("Pepsodent", updated.getProductName());
        assertEquals(15, updated.getProductQuantity());
    }

    @Test
    void testUpdate_emptyRepo() {   // new
        Product product = new Product();
        product.setProductName("Sikat gigi");

        productRepository.update(product);
        assertNull(productRepository.findById("eb558e9f-1c39-460e-8860-71af6af63bd6"));
    }

    @Test
    void testUpdate_wrongIdProduct() {    // new
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-461e-8869-71af6af63bd6");
        product.setProductName("Pasta Gigi");
        product.setProductQuantity(10);
        productRepository.create(product);

        Product updated = new Product();
        updated.setProductId("12345");    // update produk yang tidak ada di list
        updated.setProductName("Pepsodent");
        updated.setProductQuantity(1);
        productRepository.update(updated);

        assertEquals("Pasta Gigi", productRepository.findById("eb558e9f-1c39-461e-8869-71af6af63bd6").getProductName());
    }

}