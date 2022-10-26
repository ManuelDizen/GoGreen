package ar.edu.itba.paw.models;

public enum ProductStatus {
    AVAILABLE("available"),
    PAUSED("paused"),
    OUTOFSTOCK("outofstock"),
    DELETED("deleted");

    private final String statusName;

    ProductStatus(String statusName){
        this.statusName = statusName;
    }

    public long getId() {
        return ordinal();
    }

    public ProductStatus getById(long id){
        for(ProductStatus status : ProductStatus.values()){
            if(status.ordinal() == id) return status;
        }
        return null;
    }

    public String getStatusName() {
        return statusName;
    }
}
