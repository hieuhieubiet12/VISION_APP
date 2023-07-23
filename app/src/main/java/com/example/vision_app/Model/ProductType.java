package thiennhph18697.fpt.poly.md18202_pro1121_p301_ca1_vison.Model;

public class ProductType {
    private int id_type_product;
    private String name_typePro;
    private byte[] image_typePro;

    public ProductType() {
    }

    public ProductType(int id_type_product, String name_typePro, byte[] image_typePro) {
        this.id_type_product = id_type_product;
        this.name_typePro = name_typePro;
        this.image_typePro = image_typePro;
    }

    public int getId_type_product() {
        return id_type_product;
    }

    public void setId_type_product(int id_type_product) {
        this.id_type_product = id_type_product;
    }

    public String getName_typePro() {
        return name_typePro;
    }

    public void setName_typePro(String name_typePro) {
        this.name_typePro = name_typePro;
    }

    public byte[] getImage_typePro() {
        return image_typePro;
    }

    public void setImage_typePro(byte[] image_typePro) {
        this.image_typePro = image_typePro;
    }
}
