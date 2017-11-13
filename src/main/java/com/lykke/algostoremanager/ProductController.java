//package net.itransformers.dockerservicemanager;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RestController;
//import springfox.documentation.schema.Model;
//
//@RestController
//@RequestMapping("/product")
//public class ProductController {
//
//    private String productService;
//
//    @Autowired
//    public void setProductService(String productService) {
//        this.productService = productService;
//    }
//
//
//    @RequestMapping(value = "/get", method= RequestMethod.GET)
//    public String list(Model model){
//       // Iterable productList = productService();
//        return productService;
//    }
//
////    @RequestMapping(value = "/show/{id}", method= RequestMethod.GET)
////    public Product showProduct(@PathVariable Integer id, Model model){
////       Product product = productService.getProductById(id);
////        return product;
////    }
//
//
//    @RequestMapping(value = "/add", method = RequestMethod.POST)
//    public ResponseEntity saveProduct(@RequestBody String product){
//        productService = product;
//        return new ResponseEntity("Product saved successfully", HttpStatus.OK);
//    }
//
//
//    @RequestMapping(value = "/update/{id}", method = RequestMethod.PUT)
//    public ResponseEntity updateProduct(@PathVariable Integer id, @RequestBody Product product){
//        Product storedProduct = productService.getProductById(id);
//        storedProduct.setDescription(product.getDescription());
//        storedProduct.setImageUrl(product.getImageUrl());
//        storedProduct.setPrice(product.getPrice());
//        productService.saveProduct(storedProduct);
//        return new ResponseEntity("Product updated successfully", HttpStatus.OK);
//    }
//
//
//    @RequestMapping(value="/delete/{id}", method = RequestMethod.DELETE)
//    public ResponseEntity delete(@PathVariable Integer id){
//        productService.deleteProduct(id);
//        return new ResponseEntity("Product deleted successfully", HttpStatus.OK);
//
//    }
//
//}