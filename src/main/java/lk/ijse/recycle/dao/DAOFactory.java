
package lk.ijse.recycle.dao;

import lk.ijse.recycle.dao.custom.impl.*;

public class DAOFactory {

    private static DAOFactory instance;

    private DAOFactory() {}

    public static DAOFactory getInstance() {
        return instance == null ? instance = new DAOFactory() : instance;
    }

    public enum DAOTypes {
        Customer,
        Delivery,
        Order,
        Product,
        Payment,
        Material,
        RecycleItems,
        Query

    }
    public SuperDAO getDAO(DAOTypes daoTypes) {
        switch (daoTypes) {
            case Customer:
                return new CustomerDAOImpl();
            case Delivery:
                return new DeliveryDAOImpl();
            case Order:
                return new OrderDAOImpl();
                case Product:
                    return new ProductDAOImpl();
                    case Payment:
                        return new PaymentDAOImpl();
                        case RecycleItems:
                            return new RecycleItemDAOImpl();
                            case Query:

                            default:
                                return null;
        }
    }
}

