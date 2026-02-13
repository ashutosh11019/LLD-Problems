package entities;

public class ATM {
    private BankingService bankingService;
    private CashDispenser cashDispenser;
    private Session currentSession;

    public ATM(BankingService bankingService, CashDispenser cashDispenser) {
        this.bankingService = bankingService;
        this.cashDispenser = cashDispenser;
    }


    public BankingService getBankingService(){
        return bankingService;
    }

    public CashDispenser getCashDispenser(){
        return cashDispenser;
    }

    public Session getCurrentSession(){
        return currentSession;
    }

    public void setSession(Session session){
        currentSession = session;
    }

    public void insertCard(Card card) {
        this.currentSession = new Session(card);
    }

    public void processTransaction(Transaction transaction) {
        if (currentSession == null) {
            throw new IllegalStateException("No active session");
        }
        if (!currentSession.isAuthenticated()) {
            throw new IllegalStateException("User not authenticated");
        }
        transaction.execute(this);
    }

    public void ejectCard() {
        if (currentSession != null) {
            currentSession.endSession();
            currentSession = null;
        }
    }

}
