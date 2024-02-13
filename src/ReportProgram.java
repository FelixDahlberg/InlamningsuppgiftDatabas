import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

public class ReportProgram {
    final Repository r = new Repository();
    final Scanner sc = new Scanner(System.in);

    GetSpecificShoes shoesBySize = (shoe, searchWord) -> Integer.parseInt(searchWord) == shoe.getSize().getSwedishSize();
    GetSpecificShoes shoesByColor = (shoe, searchWord) -> searchWord.equalsIgnoreCase(shoe.getColor().getColor());
    GetSpecificShoes shoesByBrand = (shoe, searchWord) -> searchWord.equalsIgnoreCase(shoe.getBrand().getBrandName());

    public List<Shoe> getTheShoes(GetSpecificShoes gss, String searchWord){
        return  r.getAllShoes().stream()
                .filter(c -> gss.isThereAShoe(c, searchWord))
                .collect(Collectors.toList());
    }

    public Shoe getSpecificShoe(String color, String size, String brand) {
        List<Shoe> shoes = getTheShoes(shoesByColor,color);
        shoes.retainAll(getTheShoes(shoesBySize, size));
        shoes.retainAll(getTheShoes(shoesByBrand, brand));
        return shoes.isEmpty() ? null : shoes.get(0);
    }

    public void whoBoughtTheShoe(){
        List<Cart> sortedCartList;
        System.out.println("vilken storlek");
        String size = sc.next();
        System.out.println("vilken färg");
        String color = sc.next();
        System.out.println("vilket märke");
        String brand = sc.next();
        sortedCartList =  r.getAllCarts().stream().filter(s -> s.getShoe() == getSpecificShoe(color,size,brand)).toList();
        sortedCartList.forEach(s -> System.out.println(s.getOrders().getCustomer().getFirstname() + " " +
                s.getOrders().getCustomer().getLastname() + " " +
                s.getOrders().getCustomer().getStreetAddress()));
        System.out.println("1. tillbaka");
        sc.next();
    }

    public void orderCountForEachCustomer(){
        r.getAllCustomers().forEach(customer -> System.out.println(customer.getFirstname() + " " +
                customer.getLastname() + " antal ordrar: " +
                r.getAllOrders().stream().filter(order -> order.getCustomer().equals(customer)).count()));
        System.out.println("1. tillbaka");
        sc.next();
    }

    public void totalOrderSumByCity(){
        Map<Integer, List<String>> totalOrdersByCity = r.getAllOrders().stream().map(order -> order.getCustomer().getCity()).distinct()
                .collect(groupingBy(city -> r.getAllCarts().stream().filter(cart -> cart.getOrders().getCustomer().getCity().equals(city)).mapToInt(cart -> cart.getShoe().getPrice()).sum()));
        totalOrdersByCity.forEach((a,b) -> System.out.println(b + ": " + a + " kr"));
        System.out.println("1. Tillbaka");
        sc.next();
    }

    public void reportProgram(){
        r.updateDataFromDb();
        while (true){
            System.out.println("Vilken rapport vill du se?" +
                    "\n 1. Vem har köpt skon?" +
                    "\n 2. Hur många ordrar har varje kund gjort" +
                    "\n 3. Totala suman pengar spenderat per ort" +
                    "\n 4. Avsluta");

            int choice = sc.nextInt();
            switch (choice) {
                case 1:
                    whoBoughtTheShoe();
                    break;
                case 2:
                    orderCountForEachCustomer();
                    break;
                case 3:
                    totalOrderSumByCity();
                    break;
                case 4:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice");
                    break;
            }
        }
    }

    public static void main(String[] args) {
        ReportProgram rp = new ReportProgram();
        rp.reportProgram();

    }
}
