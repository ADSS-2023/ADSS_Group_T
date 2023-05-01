package BusinessLayer.Transport;

import java.util.*;


public class LogisticCenterController {

        private  LogisticCenter logisticCenter;

        public LogisticCenterController() {
            this.logisticCenter = new LogisticCenter();
        }

        /**
         * store products in the logistics center stocks
         *
         * @param newSupply - map with the amount for each product required to store
         */
        public void storeProducts(LinkedHashMap<Product, Integer> newSupply) {
            logisticCenter.storeProducts(newSupply);
        }

        /**
         * load products from the stock of the logistics center
         *
         * @param requestedSupply - map of the products and amounts required to load
         * @return map of products and amounts that are not available in the logistics center stock
         */
        public LinkedHashMap<Product, Integer> removeProductsFromStock(LinkedHashMap<Product, Integer> requestedSupply) {
            return logisticCenter.loadProductsFromStock(requestedSupply);
        }

        public HashMap<Product, Integer> getProductsInStock() {
            return logisticCenter.getProductsInStock();
        }


}

