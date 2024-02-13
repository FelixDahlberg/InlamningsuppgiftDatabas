public class Categories {
    private int id;
    private Category category;
    private Shoe shoe;

    public Categories(int id, Category category, Shoe shoe) {
        this.id = id;
        this.category = category;
        this.shoe = shoe;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Shoe getShoe() {
        return shoe;
    }

    public void setShoe(Shoe shoe) {
        this.shoe = shoe;
    }
}
