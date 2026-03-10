package lk.ijse.recycle.bo;

import lk.ijse.recycle.bo.custom.impl.*;

public class BOFactory {

    private static BOFactory instance;

    private BOFactory() {
    }

    public static BOFactory getInstance() {
        return instance == null ? instance = new BOFactory() : instance;
    }

    public enum BOTypes {
        Customer,
        Delivery,
        Order,
        Payment,
        Product,
        RecycleItem,
        Material,
        Query
    }

    public SuperBO getBOFactory(BOTypes boType) {
        switch (boType) {
            case Customer:
                return new CustomerBOImpl();
            case Delivery:
                return new DeliveryBOImpl();
            case Order:
                return new OrderBOImpl();
            case Payment:
                return new PaymentBOImpl();
            case Product:
                return new ProductBOImpl();
            case RecycleItem:
                return new RecycleItemBOImpl();
            case Material:
                return new MaterialBOImpl();
            case Query:
                return new QueryBOImpl();
            default:
                return null;
        }

    }
}