package com.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.Date;

public class ShoppingCart implements isGift {
    private ArrayList<String[]> cart;
    private Set<Product> productSet;

    private boolean open = true;
    private List<String> allCoupon;

    public ShoppingCart() {
        this.cart = new ArrayList<String[]>();
        this.open = true;
        this.allCoupon = new ArrayList<>();
    }

    // Show cart details
    public void showCartDetail(ShoppingCart theCart) {
        // theCart.setProductSet(productSet); //set productSet to cart
        ArrayList<String[]> cart = theCart.getCart();

        String[] cartcoupon = cart.get(0);
        String coupon = cartcoupon[0];
        if (cartcoupon[0].equals("null")) {
            coupon = "<No coupon>";
        }
        System.out.println("Cart coupon: " + coupon);
        System.out.println("All items in this cart:");
        int cartSize = cart.size();

        // List<String> allCoupon = new ArrayList<>();

        //var to get value for each product
        double productPrice = 0;
        double productTax = 0;
        double productFee = 0;
        //var to be used return total
        double tax = 0;
        double fee = 0.0;
        double price = 0;
        String itemName = "";
        String itemQuantity = "";
        String itemGiftMessage = "";
        double totalWeight = 0.0;
        double totalAmount = 0.0;
        
        
        for (int i = 1; i < cartSize; i++) {
            String[] productItem = cart.get(i);
            itemName = productItem[0];
            itemQuantity = productItem[1];
            itemGiftMessage = productItem[2];

            for (Product p : productSet) {
                if (p.getName().equals(itemName)) {

                    productPrice = p.getPrice() * Integer.parseInt(itemQuantity); //will be not added up the following product
                    price += p.getPrice() * Integer.parseInt(itemQuantity); //will be added up by the following product

                    productTax += (p.getPrice() * p.taxPercentage())/100 * Integer.parseInt(itemQuantity); //will be not added up the following product
                    tax += (p.getPrice() * p.taxPercentage())/100 * Integer.parseInt(itemQuantity); //will be added up by the following product

                    if (p instanceof PhysicalProduct) {
                        productFee = p.getPrice() * ((PhysicalProduct) p).getWeight() * Integer.parseInt(itemQuantity) * 0.1; //will be not added up the following product
                        fee += p.getPrice() * ((PhysicalProduct) p).getWeight() * Integer.parseInt(itemQuantity) * 0.1; //will be added up by the following product
                    }
                }
            }

            //return value for each item in cart
            //System.out.println("Item name: " + itemName + ", Item quantity: " + itemQuantity + ", Price: " + productPrice);
            

            if (itemGiftMessage.equals("null")) {
                itemGiftMessage = "<No gift message>";
            }
            System.out.println("Item name: " + itemName + ", Item quantity: " + itemQuantity + ", Item gift message: "
                    + itemGiftMessage);
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
        
        //return each value of the cart
        System.out.println("Cart price: " + price);
        System.out.println("Cart tax: " + tax);
        System.out.println("Shipping fee: " + fee);
        //return total amount of cart
        totalAmount += price + tax + fee;
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
                            System.out.println("Item: "+itemData[0]+", gift message: "+itemData[2]+" No."+num);
                        } 
                    }
                    if (found == false) {
                        System.out.println("No item with name: "+ productName +" in this cart!");
                        running = false;
                        break;
                    }
                    String number = "";
                    String itemMess = "";
                    while (true) {
                        System.out.println("Enter product item number to update message for that item");
                        number = sc.nextLine();
                        if (Integer.parseInt(number) <= messList.size()) {
                            itemMess = messList.get(Integer.parseInt(number)-1);
                            break;
                        }                        
                    }

                    System.out.println("Enter new message:");
                    String newMessage = sc.nextLine();

                    for (int i = 1; i < cart.size(); i++) {
                        String[] itemData = cart.get(i);
                        if (itemData[2].equals(itemMess)) {
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
                        if (itemData[2].equals("null") && itemData[0].equals(productName)) { // if there is NO gift message for item user entered
                            System.out.println("Enter new gift message:");
                            String message = sc.nextLine();
                            if (itemData[1].equals("1")) {
                                cart.get(i)[2] = message;
                            } else {
                                int quantity = Integer.parseInt(itemData[1])-1;
                                cart.get(i)[1] = Integer.toString(quantity);
                                String[] giftItem = {itemData[0],"1",message};
                                setData(giftItem);
                            }
                            found = true;
                            System.out.println("Set new gift message successfully!");
                        }
                    }
                    if (!found) {
                        System.out.println("No product item with name: "+productName+" in this cart!");
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

        if (tax.equals("free")) {
            taxPercentage = 0;
        } else if (tax.equals("standard")) {
            taxPercentage = 10;
        } else if (tax.equals("luxury")) {
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

    public void addToCart() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter product name to add item to cart:");
        String name = sc.nextLine();
        boolean inCart = false; 
        boolean found = false;
        for (Product product : productSet) {
            if (product.getName().equals(name)) {
                found = true;
                for (int i = 1; i < cart.size(); i++) {
                    String[] itemData = cart.get(i);
                    if (itemData[0].equals(name) && itemData[2].equals("null")) {
                        inCart = true;
                        System.out.println("Enter quantity:");
                        String quantity = sc.nextLine();
                        if (product.getQuantity() < Integer.parseInt(quantity)) {
                            System.out.println("Product: "+ name+" quantity only has "+ product.getQuantity());
                        } else {
                            cart.get(i)[1] = String.valueOf(Integer.parseInt(itemData[1]) + Integer.parseInt(quantity));
                            product.setQuantity(product.getQuantity() - Integer.parseInt(quantity));
                            System.out.println("Added "+quantity+" Item: "+ itemData[0]);
                        }
                    } 
                }
                if (!inCart) {
                    System.out.println("Enter quantity:");
                    String quantity = sc.nextLine();
                    for (Product p : productSet) {
                        if (p.getQuantity() < Integer.parseInt(quantity) && p.getName().equals(name)) {
                            System.out.println("Product: "+ name+" quantity only has "+ product.getQuantity());
                            found = true;
                        } else if (p.getName().equals(name) && p.getQuantity() >= Integer.parseInt(quantity)) {
                            String[] item = {name,quantity,"null"};
                            setData(item);
                            product.setQuantity(product.getQuantity() - Integer.parseInt(quantity));
                            System.out.println("Added "+quantity+" Item: "+ p.getName());
                            found = true;
                        }
                    }
                }
            }
        }
        if (!found) {
            System.out.println("There is no product with name: "+name);
        }
    }

    public boolean removeItem(ShoppingCart theCart) {
        ArrayList<String[]> cart = theCart.getCart();

        Scanner sc = new Scanner(System.in);
        System.out.println("Enter item name to remove");
        String removeCart = sc.nextLine();

        for (int i = 0; i < cart.size(); i++) {
            String[] productItem = cart.get(i);
            if (Arrays.asList(productItem).contains(removeCart)) {
                int quantity = Integer.parseInt(productItem[1]);
                cart.remove(i);
                for (Product p : productSet) {
                    if (p.getName().equals(removeCart)) {
                        p.setQuantity(p.getQuantity()+quantity);
                    }
                }
                return true;
            }
        }
        return false;
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
        List<String> allCoupon = new ArrayList<>();
        //var to get value for each product
        double productPrice = 0;
        double productTax = 0;
        double productFee = 0;
        //var to be used return total
        double tax = 0;
        double fee = 0.0;
        double price = 0;
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

                    productPrice = p.getPrice() * Integer.parseInt(itemQuantity); //will be not added up the following product
                    price += p.getPrice() * Integer.parseInt(itemQuantity); //will be added up by the following product

                    productTax += (p.getPrice() * p.taxPercentage())/100 * Integer.parseInt(itemQuantity); //will be not added up the following product
                    tax += (p.getPrice() * p.taxPercentage())/100 * Integer.parseInt(itemQuantity); //will be added up by the following product

                    if (p instanceof PhysicalProduct) {
                        productFee = p.getPrice() * ((PhysicalProduct) p).getWeight() * Integer.parseInt(itemQuantity) * 0.1; //will be not added up the following product
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
        //return total amount of cart
        totalAmount += price + tax + fee;
        System.out.println("Cart total: " + totalAmount);
        System.out.println("************************************" + "\n" 
        +"    * THANK YOU FOR YOUR PURCHASE *" + "\n" 
        +"************************************");
        open = false;
    }

    public boolean getStatus() {
        return open;
    }
}
