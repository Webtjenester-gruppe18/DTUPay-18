package Control;
import Bank.InMemoryBankService;
import Database.ITokenDatabase;
import Database.IUserDatabase;
import Database.InMemoryTokenDatabase;
import Database.InMomoryUserDatabase;
import Exception.ExceptionContainer;
import Service.*;
import dtu.ws.fastmoney.BankServiceService;

public class ControlReg {
    private static ExceptionContainer exceptionContainer;
    private static IBankService bank;
    private static ITokenDatabase tokenDatabase;
    private static ITokenManager tokenManager;
    private static dtu.ws.fastmoney.BankService bankService;
    private static IPaymentService paymentService;
    private static IUserService userService;
    private static IUserDatabase userDatabase;
    private static IReportingService reportingService;

    public static IUserService getUserService() {
        if (userService == null) userService = new UserService();
        return userService;
    }

    public static IUserDatabase getUserDatabase() {
        if (userDatabase == null) userDatabase = new InMomoryUserDatabase();
        return userDatabase;
     }

    public static IBankService getBankService() {
        if (bank == null) bank = new BankService();
        return bank;
    }

    public static ExceptionContainer getExceptionContainer() {
        if (exceptionContainer == null) exceptionContainer = new ExceptionContainer();
        return exceptionContainer;
    }

    public static ITokenDatabase getTokenDatabase() {
        if (tokenDatabase == null) tokenDatabase = new InMemoryTokenDatabase();
        return tokenDatabase;
    }

    public static ITokenManager getTokenManager() {
        if (tokenManager == null) tokenManager = new TokenManager();
        return tokenManager;
    }

    public static dtu.ws.fastmoney.BankService getFastMoneyBankService() {
//        if (bankService == null) bankService = new BankServiceService().getBankServicePort();
        if (bankService == null) bankService = new InMemoryBankService();
        return bankService;
    }

    public static IPaymentService getPaymentService() {
        if (paymentService == null) paymentService = new PaymentService();
        return paymentService;
    }

    public static IReportingService getReportingService() {
        if (reportingService == null) reportingService = new ReportingService();
        return reportingService;
    }
}
