package de.mobanisto.wintoast;

public class Aumi {

    private String companyName;
    private String productName;
    private String subProduct;
    private String versionInformation;

    public Aumi(String companyName, String productName, String subProduct, String versionInformation) {
        this.companyName = companyName;
        this.productName = productName;
        this.subProduct = subProduct;
        this.versionInformation = versionInformation;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getProductName() {
        return productName;
    }

    public String getSubProduct() {
        return subProduct;
    }

    public String getVersionInformation() {
        return versionInformation;
    }
}
