package thiennhph18697.fpt.poly.md18202_pro1121_p301_ca1_vison.Model;

public class Product {
    private int id;
    private int idType;
    private String name;
    private double price;
    private byte[] image;
    private int quantity;
    private int status;
    private String description;

    public Product() {}

    public Product(int id, int idType, String name, double price, byte[] image, int quantity, int status, String description) {
        this.id = id;
        this.idType = idType;
        this.name = name;
        this.price = price;
        this.image = image;
        this.quantity = quantity;
        this.status = status;
        this.description = description;
    }
    public Product(int idType, String name, double price, byte[] image, int quantity, String description) {
        this.idType = idType;
        this.name = name;
        this.price = price;
        this.image = image;
        this.quantity = quantity;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdType() {
        return idType;
    }

    public void setIdType(int idType) {
        this.idType = idType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


}
