package com.example;

/**
 * @author Nguyen Minh Nhat, Nguyen Cong Thinh, Nguyen Dang Ha, Don Tuan Duong
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.Date;

public class ShoppingCart implements isGift {
    private ArrayList<String[]> cart;
    private Set<Product> productSet;

    private boolean status;
    private List<String> allCoupon;

    public ShoppingCart() {
        this.cart = new ArrayList<String[]>();
        this.status = true;
        this.allCoupon = new ArrayList<>();
    }

    // Show cart details
    public void showCartDetail(ShoppingCart theCart) {
        // theCart.setProductSet(productSet); //set productSet to cart
        ArrayList<String[]> cart = theCart.getCart();

        String[] cartcoupon = cart.get(0);
        String[] couponInfo = getCoupon();
        String coupon = cartcoupon[0];
        if (cartcoupon[0].equals("null")) {
            coupon = "<No coupon>";
        }
        System.out.println("Cart coupon: " + coupon);

        System.out.println("All items in this cart:");
        int cartSize = cart.size();

        //var to be used return total
        double tax = 0;
        double fee = 0.0;
        double price = 0;
        double couponAmount = 0.0;
        String itemName = "";
        String itemQuantity = "";
        String itemGiftMessage = "";
        double totalAmount = 0.0;
        
        
        for (int i = 1; i < cartSize; i++) {
            String[] productItem = cart.get(i);
            itemName = productItem[0];
            itemQuantity = productItem[1];
            itemGiftMessage = productItem[2];

            for (Product p : productSet) {
                if (p.getName().equals(itemName)) {
                    //get coupon value, if any
                    if (couponInfo[0].equals("int")) {
                        couponAmount = (p.getPrice() * Integer.parseInt(couponInfo[1]))/100 * Integer.parseInt(itemQuantity);
                    } else if (couponInfo[0].equals("double")) {
                        couponAmount = Double.parseDouble(couponInfo[1]) * Integer.parseInt(itemQuantity);
                    } else if (couponInfo[0].equals("0")) {
                        couponAmount = 0;
                    }

                    price += p.getPrice() * Integer.parseInt(itemQuantity); //will be added up by the following product

                    tax += (p.getPrice() * p.taxPercentage())/100 * Integer.parseInt(itemQuantity); //will be added up by the following product

                    if (p instanceof PhysicalProduct) {
                        fee += p.getPrice() * ((PhysicalProduct) p).getWeight() * Integer.parseInt(itemQuantity) * 0.1; //will be added up by the following product
                    }
                }
            }

            if (itemGiftMessage.equals("null")) {
                itemGiftMessage = "<No gift message>";
            }
            System.out.println("Item name: " + itemName + ", Item quantity: " + itemQuantity + ", Item gift message: "+ itemGiftMessage);
            for (Product product : productSet) {
                if (product.getName().equals(itemName)) {
                    if (!product.getPriceCode().equals("null")) { // add coupon code to addCoupon
                        allCoupon.add(product.getPriceCode());
                    }
                    if (!product.getPercentCode().equals("null")) { // add coupon code to addCoupon
                        allCoupon.add(product.getPercentCode());
                    }
                }
            }
        }
        
        //return each value of the cart
        System.out.println("Cart price: " + price);
        System.out.println("Cart tax: " + tax);
        System.out.println("Shipping fee: " + fee);
        System.out.println("Discount for this cart: " + couponAmount);
        //return total amount of cart
        totalAmount += price + tax + fee - couponAmount;
        System.out.println("Cart total: " + totalAmount);

        System.out.println("Here are all the coupons that you can apply to this cart: " + allCoupon);
    }

    // add/change coupon method
    public void addCoupon() {
        Scanner sc = new Scanner(System.in);
        System.out.println("By entering a coupon, the previous coupon (if have) will be replaced with the new coupon!");
        while (true) {
            System.out.println("Enter coupon code OR enter QUIT/quit to go back:");
            String code = sc.nextLine();
            Boolean foundCoupon = false;
            Boolean canApply = false;
            if (allCoupon.contains(code)) { // check if input coupon can apply to this cart
                canApply = true;
            }
            if (code.equals("QUIT") || code.equals("quit")) { // If user enter quit then break/go back
                break;
            }
            if (!canApply) {
                System.out.println("Coupon: "+code+" cannot apply to this cart!");
                break;
            }
            for (Product product : productSet) {
                // check product price coupon
                if (product.getPriceCode().equals(code)) {
                    cart.get(0)[0] = code; // change old coupon to mew coupon
                    foundCoupon = true;
                    break;
                } else if (product.getPercentCode().equals(code)) { // check product percent coupon
                    cart.get(0)[0] = code; // change old coupon to new coupon
                    foundCoupon = true;
                    break;
                }
            }
            if (foundCoupon) {
                System.out.println("Coupon for this cart has been changed to: " + code);
                break;
            }
        }
    }

    // remove coupon method
    public void removeCoupon() {
        String noCode = "null";
        cart.get(0)[0] = noCode; // remove coupon and change to "null"
        System.out.println("\nCoupon has been removed for this cart!\n");
    }

    public void setData(String[] data) {
        cart.add(data);
    }

    public Set<Product> getProductSet(Set<Product> productSet) {
        return productSet;
    }

    public void setProductSet(Set<Product> productSet) {
        this.productSet = productSet;
    }

    // Override isGift interface methods
    @Override
    /**
     * In this method, 2 cases can happen, the first is to set a new gift message, and the second is to update the gift message to an item that already has a gift message.
     * In the second case, one item name can have many items in the cart and each one can have a different gift message, for example: pen1 - message one; pen1 - message two.
     * To solve the second case, I store all gift messages of one item in a list and use that list to access the correct item.
     */
    public void setMessage() {
        Scanner sc = new Scanner(System.in);
        boolean running = true;
        while (running) {
            try {
                System.out.println("Enter 1 to set a new message for items that do not have one");
                System.out.println("OR 2 to update the message for items that already have a gift message.");
                System.out.println("Or enter 0 to go back!");
                String option = sc.nextLine();

                // update gift message
                if (option.equals("2")) {
                    System.out.println("Enter Product name: ");
                    String productName = sc.nextLine();
                    List<String> messList = new ArrayList<>(); 
                    int num = 0;

                    // get all items with the name user entered
                    Boolean found = false;
                    for (int i = 1; i < cart.size(); i++) {
                        String[] itemData = cart.get(i);
                        if (!itemData[2].equals("null") && itemData[0].equals(productName)) {
                            num++;
                            messList.add(itemData[2]);
                            found = true;
                            System.out.println("Item: "+itemData[0]+", gift message: "+itemData[2]+" No."+num); // each item with gift message display with Number
                        } 
                    }
                    if (!found) {
                        System.out.println("No item with name: "+ productName +" in this cart!");  // when user input an invalid product name
                        running = false; // end this method and go back
                        break;
                    }
                    String number = "";
                    String itemMess = "";
                    while (true) {
                        System.out.println("Enter product item number to update message for that item");
                        number = sc.nextLine();
                        if (Integer.parseInt(number) <= messList.size()) {
                            itemMess = messList.get(Integer.parseInt(number)-1); // get item with gift message by number 
                            break;
                        }                        
                    }

                    System.out.println("Enter new message:");
                    String newMessage = sc.nextLine();

                    for (int i = 1; i < cart.size(); i++) {
                        String[] itemData = cart.get(i);
                        if (itemData[2].equals(itemMess)) { // check for item with the message(itemMess)
                            cart.get(i)[2] = newMessage;
                            System.out.println("Update gift message successfully!");
                        }
                    }

                // set new gift message
                } else if (option.equals("1")) {
                    System.out.println("Enter Product name: ");
                    String productName = sc.nextLine();
                    boolean found = false;

                    for (int i = 1; i < cart.size(); i++) {
                        String[] itemData = cart.get(i);

                        // if there is NO gift message for item user entered and item found in cart
                        if (itemData[2].equals("null") && itemData[0].equals(productName)) { 
                            System.out.println("Enter new gift message:");
                            String message = sc.nextLine();
                            if (itemData[1].equals("1")) { // check if item quantity only has 1, so do not need to split 1 item out to set gift message
                                cart.get(i)[2] = message;
                            } else { 
                                // if quantity of the item in cart is more than 1, then split 1 item out and set gift message
                                int quantity = Integer.parseInt(itemData[1])-1;
                                cart.get(i)[1] = Integer.toString(quantity); // split 1 item out of the item group
                                String[] giftItem = {itemData[0],"1",message}; 
                                setData(giftItem); // add item with gift message to cart
                            }
                            found = true;
                            System.out.println("Set new gift message successfully!");
                        }
                    }
                    if (!found) {
                        System.out.println("No product item with name: "+productName+" in this cart!"); // the item user entered does no exist in this cart
                    }
                } else {
                    break; // back to all features
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input!");
            }
        }
    }

    @Override
    public void getMessage() {
        System.out.println("Enter Product name: ");
        Scanner sc = new Scanner(System.in);
        String productName = sc.nextLine();

        boolean productFound = false;
        for (int i = 1; i < cart.size(); i++) { // loop all items in cart
            String[] itemData = cart.get(i);
            if (itemData[0].equals(productName) && !itemData[2].equals("null")) { // if user input = item name and check if the item has a gift message or not
                System.out.println("Product item: " + itemData[0] + "'s' gift message: " + itemData[2]);
                productFound = true;
            }
        }
        if (!productFound) {
            System.out.println("Product item: " + productName + " does not have a gift message!"); // If user enter an incorrect name
        }
    }

    public int taxPercentage(Product product) {

        String tax = product.getTax();
        int taxPercentage = 0;

        if (tax.equals("free")) { // free = 0
            taxPercentage = 0;
        } else if (tax.equals("standard")) { // standard = 10
            taxPercentage = 10;
        } else if (tax.equals("luxury")) { // luxury = 20
            taxPercentage = 20;
        }
        return taxPercentage;
    }

    public ArrayList<String[]> getCart() {
        return cart;
    }

    public void setCart(ArrayList<String[]> cart) {
        this.cart = cart;
    }

    public boolean addToCart() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter product name to add item to cart:");
        String name = sc.nextLine();
        boolean inCart = false; 
        boolean found = false;
        for (Product product : productSet) {
            if (product.getName().equals(name)) { // check product name with store(productSet)
                found = true;
                for (int i = 1; i < cart.size(); i++) {
                    String[] itemData = cart.get(i);

                    // if there is the item user entered in this cart and has no gift message
                    if (itemData[0].equals(name) && itemData[2].equals("null")) { 
                        inCart = true;
                        System.out.println("Enter quantity:");
                        String quantity = sc.nextLine();
                        if (product.getQuantity() < Integer.parseInt(quantity)) { // if quantity of the product in store is lower than quantity user want to add to cart
                            System.out.println("Product: "+ name+" quantity only has "+ product.getQuantity());
                            return false; // return false when quantity available is not enough
                        } else {
                            // if product quantity is enough for the amount user want to add to cart
                            cart.get(i)[1] = String.valueOf(Integer.parseInt(itemData[1]) + Integer.parseInt(quantity));
                            product.setQuantity(product.getQuantity() - Integer.parseInt(quantity)); // reduce quantity of the product minus the quantity user entered
                            System.out.println("Added "+quantity+" Item: "+ itemData[0]);
                            return true; // otherwise return true when successfully added to cart
                        }
                    } 
                }

                // if the product user entered is not in the cart
                if (!inCart) {
                    System.out.println("Enter quantity:");
                    String quantity = sc.nextLine();
                    for (Product p : productSet) {
                        if (p.getQuantity() < Integer.parseInt(quantity) && p.getName().equals(name)) { // if quantity of the product in store is lower than quantity user want
                            System.out.println("Product: "+ name+" quantity only has "+ product.getQuantity());
                            found = true;
                            return false; // return false when quantity available is not enough
                        } else if (p.getName().equals(name) && p.getQuantity() >= Integer.parseInt(quantity)) { // if have enough quantity
                            String[] item = {name,quantity,"null"};
                            setData(item); // add new item to cart
                            product.setQuantity(product.getQuantity() - Integer.parseInt(quantity)); // reduce quantity of the product minus the quantity user entered
                            System.out.println("Added "+quantity+" Item: "+ p.getName());
                            found = true;
                            return true; // otherwise return true when successfully added to cart
                        }
                    }
                }
            }
        }
        if (!found) {
            System.out.println("There is no product with name: "+name); // if there is no product with the name user entered
        }
        return false;
    }

    public boolean removeItem(ShoppingCart theCart) {
        ArrayList<String[]> cart = theCart.getCart();

        Scanner sc = new Scanner(System.in);
        System.out.println("Enter item name to remove");
        String removeCart = sc.nextLine();

        for (int i = 0; i < cart.size(); i++) {
            String[] productItem = cart.get(i);
            if (Arrays.asList(productItem).contains(removeCart)) { // check if cart has item with the name user entered
                int quantity = Integer.parseInt(productItem[1]);
                cart.remove(i);
                for (Product p : productSet) {
                    if (p.getName().equals(removeCart)) {
                        p.setQuantity(p.getQuantity()+quantity); // add quantity back to product in store when remove item in cart
                    }
                }
                return true; // return true when remove item successfully
            }
        }
        return false; // return false when remove item fail
    }

    public int calculateWeight() {
        int totalWeight = 0;
        for (int i = 1; i < cart.size(); i++) { // loop all items in cart
            String[] itemData = cart.get(i);
            double itemWeight = 0;
            for (Product product : productSet) {
                if (product instanceof PhysicalProduct) {
                    if (product.getName().equals(itemData[0])) {
                        itemWeight = ((PhysicalProduct) product).getWeight(); // get item weight
                        itemWeight *= Double.parseDouble(itemData[1]); // item weight * with quantity
                    }
                }
            }
            totalWeight += itemWeight;
        }
        return totalWeight;
    }

    public String[] getCoupon() {
        String coupon = cart.get(0)[0];
        for (Product product : productSet) {
            if (coupon.equals(product.getPercentCode())) { // check code is percent coupon or not
                String[] percent = {"int",Integer.toString(product.getPercentCoupon())};
                return percent;
            } else if (coupon.equals(product.getPriceCode())) { // check code is price coupon or not
                String[] price = {"double",String.valueOf(product.getCouponPrice())};
                return price;
            }
        }
        String[] noCoupon = {"0","0"}; // if no coupon code then return 0
        return noCoupon;
    }

    public void cartAmount(ShoppingCart theCart) {

        // theCart.setProductSet(productSet); //set productSet to cart
        List<String[]> cart = theCart.getCart();
        System.out.println("************************************" + "\n" 
        +"    * RECEIPT *" + "\n" 
    +"************************************");
        System.out.println("Purchase date: " + new Date());
        System.out.println("All items in this cart:");
        int cartSize = cart.size();

        String[] couponInfo = getCoupon();
        List<String> allCoupon = new ArrayList<>();

        //var to get value for each product
        double productPrice = 0;
        //var to be used return total
        double tax = 0;
        double fee = 0.0;
        double price = 0;
        double couponAmount = 0;
        String itemName = "";
        String itemQuantity = "";
        double totalWeight = 0.0;
        double totalAmount = 0.0;


        for (int i = 1; i < cartSize; i++) {
            String[] productItem = cart.get(i);
            itemName = productItem[0];
            itemQuantity = productItem[1];

            for (Product p : productSet) {
                if (p.getName().equals(itemName)) {
                    if (couponInfo[0].equals("int")) {
                        couponAmount = (p.getPrice() * Integer.parseInt(couponInfo[1]))/100 * Integer.parseInt(itemQuantity);
                    } else if (couponInfo[0].equals("double")) {
                        couponAmount = Double.parseDouble(couponInfo[1]) * Integer.parseInt(itemQuantity);
                    } else if (couponInfo[0].equals("0")) {
                        couponAmount = 0;
                    }

                    productPrice = p.getPrice() * Integer.parseInt(itemQuantity); //will be not added up the following product
                    price += p.getPrice() * Integer.parseInt(itemQuantity); //will be added up by the following product

                    tax += (p.getPrice() * p.taxPercentage())/100 * Integer.parseInt(itemQuantity); //will be added up by the following product

                    if (p instanceof PhysicalProduct) {
                        fee += p.getPrice() * ((PhysicalProduct) p).getWeight() * Integer.parseInt(itemQuantity) * 0.1; //will be added up by the following product
                    }
                }
            }   

            System.out.println("Item name: " + itemName + ", Item quantity: " + itemQuantity + ", Price: " + productPrice);
            for (Product product : productSet) {
                if (product.getName().equals(itemName)) {
                    if (!product.getPriceCode().equals("null")) {
                        allCoupon.add(product.getPriceCode());
                    }
                    if (!product.getPercentCode().equals("null")) {
                        allCoupon.add(product.getPercentCode());
                    }
                }
            }
        }
        System.out.println("\n");
        //return each value of the cart
        System.out.println("Cart price: " + price);
        System.out.println("Cart weight: " + totalWeight);
        System.out.println("Cart tax: " + tax);
        System.out.println("Shipping fee: " + fee);
        System.out.println("Discount for this cart: " + couponAmount);
        //return total amount of cart
        totalAmount += price + tax + fee - couponAmount;
        System.out.println("Cart total: " + totalAmount);
        System.out.println("************************************" + "\n" 
        +"    * THANK YOU FOR YOUR PURCHASE *" + "\n" 
        +"************************************");
        status = false;
    }

    public boolean getStatus() {
        return status;
    }
}
