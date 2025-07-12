import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        PlatformDAO platformDAO = new PlatformDAO();
        SubscriberDAO subscriberDAO = new SubscriberDAO();
        OrderDAO orderDAO = new OrderDAO();
        SubscriptionDAO subscriptionDAO = new SubscriptionDAO();
        PaymentDAO paymentDAO = new PaymentDAO();
        ProductDAO productDAO = new ProductDAO();

        while (true) {
            System.out.println("\nMAIN MENU - Select Table");
            System.out.println("1. Platform");
            System.out.println("2. Subscriber");
            System.out.println("3. Order");
            System.out.println("4. Subscription");
            System.out.println("5. Payment");
            System.out.println("6. Product");
            System.out.println("7. Exit");
            System.out.print("Enter table number: ");
            int tableChoice = sc.nextInt();
            sc.nextLine();

            if (tableChoice == 7) {
                System.out.println("Exiting... !");
                break;
            }

            System.out.println("\nCRUD MENU - Choose Operation");
            System.out.println("1. Insert");
            System.out.println("2. View All");
            System.out.println("3. Update");
            System.out.println("4. Delete");
            System.out.print("Enter operation number: ");
            int crudChoice = sc.nextInt();
            sc.nextLine();

            try {
                switch (tableChoice) {
                    case 1:
                        handlePlatform(sc, platformDAO, crudChoice);
                        break;
                    case 2:
                        handleSubscriber(sc, subscriberDAO, crudChoice);
                        break;
                    case 3:
                        handleOrder(sc, orderDAO, crudChoice);
                        break;
                    case 4:
                        handleSubscription(sc, subscriptionDAO, crudChoice);
                        break;
                    case 5:
                        handlePayment(sc, paymentDAO, crudChoice);
                        break;
                    case 6:
                        handleProduct(sc, productDAO, crudChoice);
                        break;
                    default:
                        System.out.println("Invalid table choice.");
                }
            } catch (SQLException e) {
                System.out.println("SQL Error: " + e.getMessage());
            }
        }

        sc.close();
    }

    private static void handlePlatform(Scanner sc, PlatformDAO platformDAO, int choice) throws SQLException {
        switch (choice) {
            case 1: // INSERT
                System.out.print("Platform ID: ");
                int pid = sc.nextInt(); sc.nextLine();
                System.out.print("Platform Name: ");
                String name = sc.nextLine();
                System.out.print("Platform URL: ");
                String url = sc.nextLine();
                System.out.print("Platform Description: ");
                String desc = sc.nextLine();

                Platform newPlatform = new Platform(pid, name, url, desc);
                platformDAO.insertPlatform(newPlatform);
                System.out.println("Platform inserted successfully!");
                break;

            case 2: // VIEW ALL
                List<Platform> platforms = platformDAO.getAllPlatforms();
                for (Platform p : platforms) {
                    System.out.println(p.getPlatformId() + " | " + p.getPlatformName() + " | " + p.getPlatformUrl() + " | " + p.getPlatformDescription());
                }
                break;

            case 3: // UPDATE
                System.out.print("Enter Platform ID to update: ");
                int upId = sc.nextInt(); sc.nextLine();
                System.out.print("New Name: ");
                String newName = sc.nextLine();
                System.out.print("New URL: ");
                String newUrl = sc.nextLine();
                System.out.print("New Description: ");
                String newDesc = sc.nextLine();

                Platform updatedPlatform = new Platform(upId, newName, newUrl, newDesc);
                platformDAO.updatePlatform(updatedPlatform);
                System.out.println("Platform updated successfully!");
                break;

            case 4: // DELETE
                System.out.print("Enter Platform ID to delete: ");
                int delId = sc.nextInt(); sc.nextLine();
                platformDAO.deletePlatform(delId);
                System.out.println("Platform deleted successfully!");
                break;

            default:
                System.out.println("Invalid operation for Platform table.");
        }
    }

    private static void handleSubscriber(Scanner sc, SubscriberDAO subscriberDAO, int choice) throws SQLException {
        switch (choice) {
            case 1: // INSERT
                System.out.print("Subscriber ID: ");
                int sid = sc.nextInt(); sc.nextLine();
                System.out.print("Name: ");
                String name = sc.nextLine();
                System.out.print("Email: ");
                String email = sc.nextLine();
                System.out.print("Address: ");
                String address = sc.nextLine();
                System.out.print("Phone: ");
                String phone = sc.nextLine();
                System.out.print("Subscription Start Date (yyyy-mm-dd): ");
                String startDate = sc.nextLine();
                System.out.print("Subscription End Date (yyyy-mm-dd): ");
                String endDate = sc.nextLine();
                System.out.print("Subscription Status (active/inactive): ");
                String status = sc.nextLine();
                
                Subscriber newSubscriber = new Subscriber(sid, name, email, address, phone, startDate, endDate, status);
                subscriberDAO.insertSubscriber(newSubscriber);
                System.out.println("Subscriber inserted successfully!");
                break;            

            case 2: // VIEW ALL
                List<Subscriber> subs = subscriberDAO.getAllSubscribers();
                System.out.println("👤 Subscriber List:");
                System.out.println("ID | Name | Email | Address | Phone | Start Date | End Date | Status");
                System.out.println("---------------------------------------------------------------------------------------");
                
                for (Subscriber s : subs) {
                    System.out.println(
                        s.getSubscriberId() + " | " +
                        s.getName() + " | " +
                        s.getEmail() + " | " +
                        s.getAddress() + " | " +
                        s.getPhone() + " | " +
                        s.getSubscriptionStartDate() + " | " +
                        s.getSubscriptionEndDate() + " | " +
                        s.getSubscriptionStatus()
                    );
                }
                break; 

            case 3: // UPDATE
                System.out.print("Enter Subscriber ID to update: ");
                int upId = sc.nextInt(); sc.nextLine();
                System.out.print("New Name: ");
                String newName = sc.nextLine();
                System.out.print("New Email: ");
                String newEmail = sc.nextLine();
                System.out.print("New Address: ");
                String newAddress = sc.nextLine();
                System.out.print("New Phone: ");
                String newPhone = sc.nextLine();
                System.out.print("New Subscription Start Date (yyyy-mm-dd): ");
                String newStartDate = sc.nextLine();
                System.out.print("New Subscription End Date (yyyy-mm-dd): ");
                String newEndDate = sc.nextLine();
                System.out.print("New Subscription Status (active/inactive): ");
                String newStatus = sc.nextLine();
                
                Subscriber updatedSubscriber = new Subscriber(upId, newName, newEmail, newAddress, newPhone, newStartDate, newEndDate, newStatus);
                subscriberDAO.updateSubscriber(updatedSubscriber);
                System.out.println("Subscriber updated successfully!");            
                break;

            case 4: // DELETE
                System.out.print("Enter Subscriber ID to delete: ");
                int delId = sc.nextInt(); sc.nextLine();
                subscriberDAO.deleteSubscriber(delId);
                System.out.println("Subscriber deleted successfully!");
                break;

            default:
                System.out.println("Invalid operation for Subscriber table.");
        }
    }

    private static void handleOrder(Scanner sc, OrderDAO orderDAO, int choice) throws SQLException {
        switch (choice) {
            case 1: // INSERT
                System.out.print("Order ID: ");
                int oid = sc.nextInt(); sc.nextLine();
                System.out.print("Subscriber ID: ");
                int sid = sc.nextInt(); sc.nextLine();
                System.out.print("Order Date (YYYY-MM-DD): ");
                String date = sc.nextLine();
                System.out.print("Amount: ");
                double amount = sc.nextDouble(); sc.nextLine();
                System.out.print("Status: ");
                String status = sc.nextLine();
                System.out.print("Payment Status: ");
                String payStatus = sc.nextLine();
                System.out.print("Delivery Date (YYYY-MM-DD): ");
                String delivery = sc.nextLine();
    
                Order newOrder = new Order(oid, sid, date, amount, status, payStatus, delivery);
                orderDAO.insertOrder(newOrder);
                System.out.println("Order inserted successfully!");
                break;
    
            case 2: // VIEW ALL
                List<Order> orders = orderDAO.getAllOrders();
                for (Order o : orders) {
                    System.out.println(o.getOrderId() + " | " + o.getSubscriberId() + " | " + o.getOrderDate() + " | " + o.getAmount() +
                            " | " + o.getStatus() + " | " + o.getPaymentStatus() + " | " + o.getDeliveryDate());
                }
                break;
    
            case 3: // UPDATE
                System.out.print("Enter Order ID to update: ");
                int upId = sc.nextInt(); sc.nextLine();
                System.out.print("New Subscriber ID: ");
                int newSid = sc.nextInt(); sc.nextLine();
                System.out.print("New Order Date (YYYY-MM-DD): ");
                String newDate = sc.nextLine();
                System.out.print("New Amount: ");
                double newAmt = sc.nextDouble(); sc.nextLine();
                System.out.print("New Status: ");
                String newStat = sc.nextLine();
                System.out.print("New Payment Status: ");
                String newPayStat = sc.nextLine();
                System.out.print("New Delivery Date (YYYY-MM-DD): ");
                String newDelivery = sc.nextLine();
    
                Order updatedOrder = new Order(upId, newSid, newDate, newAmt, newStat, newPayStat, newDelivery);
                orderDAO.updateOrder(updatedOrder);
                System.out.println("Order updated successfully!");
                break;
    
            case 4: // DELETE
                System.out.print("Enter Order ID to delete: ");
                int delId = sc.nextInt(); sc.nextLine();
                orderDAO.deleteOrder(delId);
                System.out.println("Order deleted successfully!");
                break;
    
            default:
                System.out.println("Invalid operation for Order table.");
        }
    }

    private static void handleSubscription(Scanner sc, SubscriptionDAO subscriptionDAO, int choice) throws SQLException {
        switch (choice) {
            case 1: // INSERT
                System.out.print("Subscription ID: ");
                int sid = sc.nextInt(); sc.nextLine();
                System.out.print("Subscriber ID: ");
                int subId = sc.nextInt(); sc.nextLine();
                System.out.print("Platform ID: ");
                int pid = sc.nextInt(); sc.nextLine();
                System.out.print("Subscription Plan: ");
                String plan = sc.nextLine();
                System.out.print("Start Date (YYYY-MM-DD): ");
                String start = sc.nextLine();
                System.out.print("End Date (YYYY-MM-DD): ");
                String end = sc.nextLine();
                System.out.print("Subscription Status (active/inactive): ");
                String status = sc.nextLine();
                System.out.print("Renewal Auto (true/false): ");
                boolean renewal = sc.nextBoolean(); sc.nextLine();
                
                Subscription s = new Subscription(sid, subId, pid, plan, start, end, status, renewal);
                subscriptionDAO.insertSubscription(s);
                System.out.println("Subscription inserted successfully!");
                break;
    
            case 2: // VIEW ALL
                List<Subscription> subs = subscriptionDAO.getAllSubscriptions();
                System.out.println("Subscriptions:");
                System.out.println("ID | Subscriber ID | Platform ID | Plan | Start Date | End Date | Status | Auto Renewal");
                System.out.println("------------------------------------------------------------------------------------------");
                
                for (Subscription sub : subs) {
                    System.out.println(
                        sub.getSubscriptionId() + " | " +
                        sub.getSubscriberId() + " | " +
                        sub.getPlatformId() + " | " +
                        sub.getSubscriptionPlan() + " | " +
                        sub.getStartDate() + " | " +
                        sub.getEndDate() + " | " +
                        sub.getSubscriptionStatus() + " | " +
                        (sub.isRenewalAuto() ? "Yes" : "No")
                    );
                }
                break;            
    
            case 3: // UPDATE
                System.out.print("Subscription ID to update: ");
                int upId = sc.nextInt(); sc.nextLine();
                System.out.print("New Subscriber ID: ");
                int newSubId = sc.nextInt(); sc.nextLine();
                System.out.print("New Platform ID: ");
                int newPlatId = sc.nextInt(); sc.nextLine();
                System.out.print("New Subscription Plan: ");
                String newPlan = sc.nextLine();
                System.out.print("New Start Date (YYYY-MM-DD): ");
                String newStart = sc.nextLine();
                System.out.print("New End Date (YYYY-MM-DD): ");
                String newEnd = sc.nextLine();
                System.out.print("New Subscription Status (active/inactive): ");
                String newStatus = sc.nextLine();
                System.out.print("New Renewal Auto (true/false): ");
                boolean newRenewal = sc.nextBoolean(); sc.nextLine();
                
                Subscription updated = new Subscription(
                    upId,
                    newSubId,
                    newPlatId,
                    newPlan,
                    newStart,
                    newEnd,
                    newStatus,
                    newRenewal
                );
                
                subscriptionDAO.updateSubscription(updated);
                System.out.println("Subscription updated successfully!");            
                break;
    
            case 4: // DELETE
                System.out.print("Subscription ID to delete: ");
                int delId = sc.nextInt(); sc.nextLine();
                subscriptionDAO.deleteSubscription(delId);
                System.out.println("Subscription deleted successfully!");
                break;
    
            default:
                System.out.println("Invalid operation for Subscription table.");
        }
    }

    private static void handlePayment(Scanner sc, PaymentDAO paymentDAO, int choice) throws SQLException {
        switch (choice) {
            case 1: // INSERT
                System.out.print("Payment ID: ");
                int pid = sc.nextInt(); sc.nextLine();
                System.out.print("Order ID: ");
                int oid = sc.nextInt(); sc.nextLine();
                System.out.print("Payment Date (YYYY-MM-DD): ");
                String date = sc.nextLine();
                System.out.print("Payment Status: ");
                String status = sc.nextLine();
                System.out.print("Payment Method: ");
                String method = sc.nextLine();
                System.out.print("Payment Amount: ");
                double amount = sc.nextDouble(); sc.nextLine();
                
                Payment payment = new Payment(pid, oid, date, status, method, amount);            
                paymentDAO.insertPayment(payment);
                System.out.println("Payment inserted successfully!");
                break;
    
            case 2: // VIEW ALL
                List<Payment> payments = paymentDAO.getAllPayments();
                System.out.println("💸 Payment List:");
                System.out.println("ID | Order ID | Method | Date | Status | Amount");
                System.out.println("---------------------------------------------------------");
                
                for (Payment p : payments) {
                    System.out.println(
                        p.getPaymentId() + " | " +
                        p.getOrderId() + " | " +
                        p.getPaymentMethod() + " | " +
                        p.getPaymentDate() + " | " +
                        p.getPaymentStatus() + " | ₹" +
                        p.getPaymentAmount()
                    );
                }
                break;     
    
            case 3: // UPDATE
                System.out.print("Payment ID to update: ");
                int upId = sc.nextInt(); sc.nextLine();
                System.out.print("New Order ID: ");
                int newOid = sc.nextInt(); sc.nextLine();
                System.out.print("New Payment Date (YYYY-MM-DD): ");
                String newDate = sc.nextLine();
                System.out.print("New Payment Status: ");
                String newStatus = sc.nextLine();
                System.out.print("New Payment Method: ");
                String newMethod = sc.nextLine();
                System.out.print("New Payment Amount: ");
                double newAmount = sc.nextDouble(); sc.nextLine();
                
                Payment updated = new Payment(upId, newOid, newDate, newStatus, newMethod, newAmount);
                paymentDAO.updatePayment(updated);
                System.out.println("Payment updated successfully!");      
                break;
    
            case 4: // DELETE
                System.out.print("Payment ID to delete: ");
                int delId = sc.nextInt(); sc.nextLine();
                paymentDAO.deletePayment(delId);
                System.out.println("Payment deleted successfully!");
                break;
    
            default:
                System.out.println("Invalid operation for Payment table.");
        }
    }

    private static void handleProduct(Scanner sc, ProductDAO productDAO, int choice) throws SQLException {
        switch (choice) {
            case 1: // INSERT
                System.out.print("Product ID: ");
                int pid = sc.nextInt(); sc.nextLine();
                System.out.print("Platform ID: ");
                int platformId = sc.nextInt(); sc.nextLine();
                System.out.print("Name: ");
                String name = sc.nextLine();
                System.out.print("Description: ");
                String desc = sc.nextLine();
                System.out.print("Price: ");
                double price = sc.nextDouble(); sc.nextLine();
                System.out.print("Category: ");
                String category = sc.nextLine();
                System.out.print("Is Subscription Plan Applicable? (true/false): ");
                boolean subApplicable = sc.nextBoolean(); sc.nextLine();
                
                Product product = new Product(pid, platformId, name, desc, price, category, subApplicable);
                productDAO.insertProduct(product);
                System.out.println("Product inserted successfully!");
                break;
    
            case 2: // VIEW ALL
                List<Product> products = productDAO.getAllProducts();
                System.out.println("Product List:");
                System.out.println("ID | Platform ID | Name | Description | Price | Category | Subscription Plan?");
                System.out.println("----------------------------------------------------------------------------------");
                for (Product p : products) {
                    System.out.println(
                        p.getProductId() + " | " +
                        p.getPlatformId() + " | " +
                        p.getName() + " | " +
                        p.getDescription() + " | ₹" +
                        p.getPrice() + " | " +
                        p.getCategory() + " | " +
                        (p.isSubscriptionPlanApplicable() ? "Yes" : "No")
                    );
                }
                break;
            
            case 3: // UPDATE
                System.out.print("Product ID to update: ");
                int upId = sc.nextInt(); sc.nextLine();
                System.out.print("New Platform ID: ");
                int newPlatformId = sc.nextInt(); sc.nextLine();
                System.out.print("New Name: ");
                String newName = sc.nextLine();
                System.out.print("New Description: ");
                String newDesc = sc.nextLine();
                System.out.print("New Price: ");
                double newPrice = sc.nextDouble(); sc.nextLine();
                System.out.print("New Category: ");
                String newCategory = sc.nextLine();
                System.out.print("Is Subscription Plan Applicable? (true/false): ");
                boolean newSubApplicable = sc.nextBoolean(); sc.nextLine();
                
                Product updated = new Product(upId, newPlatformId, newName, newDesc, newPrice, newCategory, newSubApplicable);
                productDAO.updateProduct(updated);
                System.out.println("Product updated successfully!");
                break;
    
            case 4: // DELETE
                System.out.print("Product ID to delete: ");
                int delId = sc.nextInt(); sc.nextLine();
                productDAO.deleteProduct(delId);
                System.out.println("Product deleted successfully!");
                break;
    
            default:
                System.out.println("Invalid operation for Product table.");
        }
    }    
}