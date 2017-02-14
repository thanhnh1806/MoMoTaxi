package bhtech.com.cabbydriver.object;

public class BaseEnums {
    public enum UserType {
        UserTypeUser,
        UserTypeDriver,
    }

    public enum CarClass {
        CarClassRegular,
        CarClassMPV,
        CarClassLuxury,
        CarClassTukTuk,
    }

    public enum CarStatus {
        CarStatusRegular,
        CarStatusLocked
    }

    public enum CarDriverStatus {
        CarDriverStatusAvailable,
        CarDriverStatusDriving,
        CarDriverStatusDrivingToPassenger,
        CarDriverStatusWithPassenger,
        CarDriverStatusInMaintenance,
        CarDriverStatusBrokeDown,
        CarDriverStatusInRepair,
        CarDriverStatusOther,
    }

    public enum TaxiRequestStatus {
        TaxiRequestStatusPending, // requesting
        TaxiRequestStatusCancelled,
        TaxiRequestStatusDriverSelected,
        TaxiRequestStatusDrivingToPassenger,
        TaxiRequestStatusWaitingPickupPassenger,
        TaxiRequestStatusWithPassenger,
        TaxiRequestStatusCharged,
        TaxiRequestStatusPaid, ContantValuesObject, // end
    }

    public enum CardUserType {
        CardUserTypePersonal,
        CardUserTypeCompany
    }

    public enum CardType {
        CardTypeVISA,
        CardTypeMasterCard,
    }

    public enum PaymentType {
        PaymentTypeOnce,
        PaymentTypeMonthly,

    }

    public enum PaymentMode {
        PaymentByCreditCard,
        PaymentByCash
    }
}
