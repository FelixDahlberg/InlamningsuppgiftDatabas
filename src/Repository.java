import java.io.FileInputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Repository {

    private List<Color> allColors = new ArrayList<>();
    private List<Brand> allBrands = new ArrayList<>();
    private List<Customer> allCustomers = new ArrayList<>();
    private List<Sizes> allSizes = new ArrayList<>();
    private List<Category> allCategorys = new ArrayList<>();
    private List<Shoe> allShoes = new ArrayList<>();
    private List<Orders> allOrders = new ArrayList<>();
    private List<Categories> allCategories = new ArrayList<>();
    private List<Cart> allCarts = new ArrayList<>();


    private final Properties p = new Properties();

    public Repository(){
        try {
            p.load(new FileInputStream("src/Setting.properties"));
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void getAllCustomersFromDB(){
        List<Customer> allCustomers = new ArrayList<>();

        try (Connection con = DriverManager.getConnection(p.getProperty("connectionString"),
                p.getProperty("name"),
                p.getProperty("password"));
             CallableStatement stm = con.prepareCall("select * from customer")){
            stm.execute();
            ResultSet rs = stm.getResultSet();

            while (rs.next()) {

                int id = rs.getInt("id");
                String firstname = rs.getString("firstname");
                String lastname = rs.getString("lastname");
                String email = rs.getString("email");
                String  password = rs.getString("password");
                String city = rs.getString("city");
                int ZipCode = rs.getInt("ZipCode");
                String streetAddress = rs.getString("streetAddress");
                allCustomers.add(new Customer(id,firstname,lastname, email, password, city, ZipCode, streetAddress));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        setAllCustomers(allCustomers);
    }
    public void getAllColorsFromDB(){
        List<Color> allColors = new ArrayList<>();

        try (Connection con = DriverManager.getConnection(p.getProperty("connectionString"),
                p.getProperty("name"),
                p.getProperty("password"));
             CallableStatement stm = con.prepareCall("select * from Color")){
            stm.execute();
            ResultSet rs = stm.getResultSet();

            while (rs.next()) {

                int id = rs.getInt("id");
                String color = rs.getString("color");
                allColors.add(new Color(id,color));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        setAllColors(allColors);
    }
    public void getAllBrandsFromDB(){
        List<Brand> allBrands = new ArrayList<>();

        try (Connection con = DriverManager.getConnection(p.getProperty("connectionString"),
                p.getProperty("name"),
                p.getProperty("password"));
             CallableStatement stm = con.prepareCall("select * from Brand")){
            stm.execute();
            ResultSet rs = stm.getResultSet();

            while (rs.next()) {

                int id = rs.getInt("id");
                String brandName = rs.getString("brandName");
                allBrands.add(new Brand(id,brandName));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        setAllBrands(allBrands);
    }

    public void getAllSizesFromDB(){
        List<Sizes> allSizes = new ArrayList<>();

        try (Connection con = DriverManager.getConnection(p.getProperty("connectionString"),
                p.getProperty("name"),
                p.getProperty("password"));
             CallableStatement stm = con.prepareCall("select * from Sizes")){
            stm.execute();
            ResultSet rs = stm.getResultSet();

            while (rs.next()) {

                int id = rs.getInt("id");
                int swedishSize = rs.getInt("swedishSize");
                allSizes.add(new Sizes(id,swedishSize));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        setAllSizes(allSizes);
    }
    public void getAllCategorysFromDB(){
        List<Category> allCategorys = new ArrayList<>();

        try (Connection con = DriverManager.getConnection(p.getProperty("connectionString"),
                p.getProperty("name"),
                p.getProperty("password"));
             CallableStatement stm = con.prepareCall("select * from Category")){
            stm.execute();
            ResultSet rs = stm.getResultSet();

            while (rs.next()) {

                int id = rs.getInt("id");
                String categoryName = rs.getString("categoryName");
                allCategorys.add(new Category(id,categoryName));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        setAllCategorys(allCategorys);
    }

    public void getAllShoesFromDB(){
        List<Shoe> allShoes = new ArrayList<>();

        try (Connection con = DriverManager.getConnection(p.getProperty("connectionString"),
                p.getProperty("name"),
                p.getProperty("password"));
             CallableStatement stm = con.prepareCall("select * from Shoe")){
            stm.execute();
            ResultSet rs = stm.getResultSet();

            while (rs.next()) {

                int id = rs.getInt("id");
                int brandId = rs.getInt("brandId");
                Brand brand = getAllBrands().stream().filter(b -> b.getId() == brandId).findFirst().orElse(null);
                int colorId = rs.getInt("colorid");
                Color color = getAllColors().stream().filter(b -> b.getId() == colorId).findFirst().orElse(null);
                int sizesId = rs.getInt("sizesid");
                Sizes sizes = getAllSizes().stream().filter(b -> b.getId() == sizesId).findFirst().orElse(null);
                int price = rs.getInt("price");
                int stock = rs.getInt("stock");

                allShoes.add(new Shoe(id,brand, color, sizes, price, stock));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        setAllShoes(allShoes);
    }

    public void getAllOrdersFromDB(){
        List<Orders> allOrders = new ArrayList<>();

        try (Connection con = DriverManager.getConnection(p.getProperty("connectionString"),
                p.getProperty("name"),
                p.getProperty("password"));
             CallableStatement stm = con.prepareCall("select * from Orders")){
            stm.execute();
            ResultSet rs = stm.getResultSet();

            while (rs.next()) {

                int id = rs.getInt("id");
                int customerId = rs.getInt("customerId");
                Customer customer = getAllCustomers().stream().filter(b -> b.getId() == customerId).findFirst().orElse(null);
                allOrders.add(new Orders(id,customer));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        setAllOrders(allOrders);
    }

    public void getAllCartsFromDB(){
        List<Cart> allCarts = new ArrayList<>();

        try (Connection con = DriverManager.getConnection(p.getProperty("connectionString"),
                p.getProperty("name"),
                p.getProperty("password"));
             CallableStatement stm = con.prepareCall("select * from Cart")){
            stm.execute();
            ResultSet rs = stm.getResultSet();

            while (rs.next()) {

                int id = rs.getInt("id");
                int shoeId = rs.getInt("shoeId");
                Shoe shoe = getAllShoes().stream().filter(b -> b.getId() == shoeId).findFirst().orElse(null);
                int ordersId = rs.getInt("ordersId");
                Orders orders = getAllOrders().stream().filter(b -> b.getId() == ordersId).findFirst().orElse(null);
                allCarts.add(new Cart(id,shoe,orders));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        setAllCarts(allCarts);
    }
    public void getAllCategoriesFromDB(){
        List<Categories> allCategories = new ArrayList<>();

        try (Connection con = DriverManager.getConnection(p.getProperty("connectionString"),
                p.getProperty("name"),
                p.getProperty("password"));
             CallableStatement stm = con.prepareCall("select * from Categories")){
            stm.execute();
            ResultSet rs = stm.getResultSet();

            while (rs.next()) {

                int id = rs.getInt("id");
                int categoryId = rs.getInt("categoryId");
                Category category = getAllCategorys().stream().filter(b -> b.getId() == categoryId).findFirst().orElse(null);
                int shoeId = rs.getInt("shoeId");
                Shoe shoe = getAllShoes().stream().filter(b -> b.getId() == shoeId).findFirst().orElse(null);

                allCategories.add(new Categories(id,category, shoe));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        setAllCategories(allCategories);
    }

    public void updateDataFromDb(){
        getAllCustomersFromDB();
        getAllColorsFromDB();
        getAllSizesFromDB();
        getAllBrandsFromDB();
        getAllCategorysFromDB();
        getAllShoesFromDB();
        getAllCategoriesFromDB();
        getAllOrdersFromDB();
        getAllCartsFromDB();

    }

    public void callSPAddToCart(Cart cart){

        try (Connection con = DriverManager.getConnection(p.getProperty("connectionString"),
                p.getProperty("name"),
                p.getProperty("password"));
             CallableStatement stm = con.prepareCall("CALL addToCart(?,?,?)")){

            stm.setInt(1, cart.getOrders().getCustomer().getId());
            stm.setInt(2, cart.getOrders().getId());
            stm.setInt(3, cart.getShoe().getId());
            stm.executeQuery();
        }
        catch (SQLException e){
            System.out.println(e.getMessage() +"("+e.getErrorCode()+")");
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public List<Color> getAllColors() {
        return allColors;
    }

    public void setAllColors(List<Color> allColors) {
        this.allColors = allColors;
    }

    public List<Brand> getAllBrands() {
        return allBrands;
    }

    public void setAllBrands(List<Brand> allBrands) {
        this.allBrands = allBrands;
    }

    public List<Customer> getAllCustomers() {
        return allCustomers;
    }

    public void setAllCustomers(List<Customer> allCustomers) {
        this.allCustomers = allCustomers;
    }

    public List<Sizes> getAllSizes() {
        return allSizes;
    }

    public void setAllSizes(List<Sizes> allSizes) {
        this.allSizes = allSizes;
    }

    public List<Category> getAllCategorys() {
        return allCategorys;
    }

    public void setAllCategorys(List<Category> allCategorys) {
        this.allCategorys = allCategorys;
    }

    public List<Shoe> getAllShoes() {
        return allShoes;
    }

    public void setAllShoes(List<Shoe> allShoes) {
        this.allShoes = allShoes;
    }

    public List<Orders> getAllOrders() {
        return allOrders;
    }

    public void setAllOrders(List<Orders> allOrders) {
        this.allOrders = allOrders;
    }

    public List<Categories> getAllCategories() {
        return allCategories;
    }

    public void setAllCategories(List<Categories> allCategories) {
        this.allCategories = allCategories;
    }

    public List<Cart> getAllCarts() {
        return allCarts;
    }

    public void setAllCarts(List<Cart> allCarts) {
        this.allCarts = allCarts;
    }
}
