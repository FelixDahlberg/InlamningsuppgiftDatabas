import java.util.*;

public class LoginAndOrderProgram {
     final Repository r = new Repository();
     final Scanner sc = new Scanner(System.in);

    public boolean loginOkOrNot(String email, String password){
       return r.getAllCustomers().stream().anyMatch(s -> s.getEmail().equals(email) && s.getPassword().equals(password));
    }
    public Customer getCustomerByEmailAndPassword(String email, String password){
        return r.getAllCustomers().stream().filter(c -> email.equals(c.getEmail()) && password.equals(c.getPassword())).findFirst().orElse(null);
    }

    public Customer loginScreen(){
        Customer returnCustomer;
        while (true) {

            String email;
            String password;
            System.out.println("skriv din email");
            email = sc.next();
            System.out.println("skriva lösenord");
            password = sc.next();
            if (loginOkOrNot(email, password)){
                System.out.println("success");
                returnCustomer = getCustomerByEmailAndPassword(email,password);
                break;
            }
            System.out.println("inloggning misslyckad");
        }
        return returnCustomer;
    }

    public Category pickCategory(){
        System.out.println("vilken kategori?");
        r.getAllCategorys().forEach(s -> System.out.println((r.getAllCategorys().indexOf(s) + 1) + ". " + s.getCategoryName()));
        return r.getAllCategorys().get(Integer.parseInt(sc.next()) - 1);
    }
    public Shoe shoesInCategory(Category category){
        List<Categories> categoriesList = r.getAllCategories().stream().filter( s -> s.getCategory() == category).toList();
        categoriesList.forEach(s -> System.out.println((categoriesList.indexOf(s) + 1) + ". " +
                s.getShoe().getBrand().getBrandName() + ", " +
                s.getShoe().getColor().getColor() + " storlek: " +
                s.getShoe().getSize().getSwedishSize()));
        return categoriesList.get(Integer.parseInt(sc.next()) - 1).getShoe();
    }
    public int getLastOrderId(){
        return Objects.requireNonNull(r.getAllOrders().stream().max(Comparator.comparingInt(Orders::getId)).orElse(null)).getId();
    }


    public void orderProgram(){
        r.updateDataFromDb();
        Customer activeCustomer;
        Shoe currentShoe;
        Orders currentOrder;
        Cart currentCart;
        int newOrderOrNot = 1;
        activeCustomer = loginScreen();
        while (true){
            currentShoe = shoesInCategory(pickCategory());
            currentOrder = new Orders(getLastOrderId() + newOrderOrNot,activeCustomer);
            currentCart = new Cart(currentShoe,currentOrder);
            r.callSPAddToCart(currentCart);
            r.updateDataFromDb();
            System.out.println("1. fortsätt shoppa?" +
                    "\n2. avsluta");
            int choice = sc.nextInt();
            switch (choice) {
                case 2:
                    System.exit(0);
                case 1:
                    newOrderOrNot = 0;
                    break;
            }
        }
    }


    public static void main(String[] args) {
        LoginAndOrderProgram m = new LoginAndOrderProgram();
        m.orderProgram();
    }
}