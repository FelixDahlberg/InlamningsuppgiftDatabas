public class Cart {
    private int id;
    private Shoe shoe;
    private Orders orders;

    public Cart(int id, Shoe shoe, Orders orders) {
        this.id = id;
        this.shoe = shoe;
        this.orders = orders;
    }

    public Cart(Shoe shoe, Orders orders) {
        this.shoe = shoe;
        this.orders = orders;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Shoe getShoe() {
        return shoe;
    }

    public void setShoe(Shoe shoe) {
        this.shoe = shoe;
    }

    public Orders getOrders() {
        return orders;
    }

    public void setOrders(Orders orders) {
        this.orders = orders;
    }
}
