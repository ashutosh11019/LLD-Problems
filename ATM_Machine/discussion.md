# ATM Machine Low Level Design

## Problem Statement
Design a Low Level Design for an ATM Machine that supports basic banking operations like User Authentication, Balance Inquiry, Cash Withdrawal, and Cash Deposit. The system should handle card validation, PIN verification, and cash management.

## Requirements
*   **User Authentication**: Verify card validity and PIN.
*   **Withdrawal**: Check balance, check machine cash availability, dispense cash, and update account.
*   **Deposit**: Accept cash (simplified) and update account.
*   **Balance Inquiry**: Check current account balance.
*   **Security**: Block card after 3 failed PIN attempts.
*   **Hardware Interface**: Simulate cash dispensing and inventory management.

---

## Core Entities & Responsibilities

### 1. ATM
The central controller of the system. It integrates the authentication layer (`Session`), the financial layer (`BankingService`), and the hardware layer (`CashDispenser`).
*   **Responsibilities**:
    *   Initialize sessions when a card is inserted.
    *   Delegate transaction processing.
    *   Manage card ejection.

### 2. Session
Represents a user's interaction session with the ATM.
*   **Responsibilities**:
    *   Holds the current `Card`.
    *   Tracks authentication state.
    *   Counts failed PIN attempts (blocks card after 3 failures).

### 3. Card
Represents the physical card used by the customer.
*   **Responsibilities**:
    *   Stores card number, encrypted PIN, and blocked status.
    *   Validates input PIN.
    *   Links to a bank `Account`.

### 4. Account
Represents the customer's bank account.
*   **Responsibilities**:
    *   Stores balance.
    *   Provides atomic operations for `debit` and `credit`.
    *   Checks for sufficient funds.
    *   **Note**: Uses `int` for monetary values (e.g., cents or smallest unit).

### 5. BankingService
A service layer acting as a facade for banking operations.
*   **Responsibilities**:
    *   Validates accounts (checks if card is blocked/valid).
    *   Delegates balance checks and updates to the `Account` entity.

### 6. CashInventory
Manages the physical cash inside the ATM.
*   **Responsibilities**:
    *   Tracks count of each `Denomination`.
    *   Checks if sufficient cash exists for a requested amount.
    *   Adds or deducts cash notes.

### 7. CashDispenser
Handles the logic of dispensing cash.
*   **Responsibilities**:
    *   Calculates the optimal breakdown of notes for a withdrawal amount (Greedy approach).
    *   Updates `CashInventory` upon successful dispensing.

### 8. Transaction (Abstract)
Base class for all ATM transactions.
*   **Responsibilities**:
    *   Defines the contract `execute(ATM atm)`.
    *   **Subclasses**: `WithdrawTransaction`, `DepositTransaction`, `BalanceInquiryTransaction`.

---

## Class Definitions

### ATM
```java
class ATM {
    - BankingService bankingService
    - CashDispenser cashDispenser
    - Session currentSession

    + void insertCard(Card card)
    + void processTransaction(Transaction transaction)
    + void ejectCard()
    + Session getCurrentSession()
}
```

### Session
```java
class Session {
    - Card card
    - int failedAttempts
    - boolean isAuthenticated

    + boolean authenticate(String inputPin)
    + boolean isAuthenticated()
    + void endSession()
}
```

### Card
```java
class Card {
    - String cardNumber
    - String encryptedPin
    - Account account
    - boolean isBlocked

    + boolean validatePin(String inputPin)
    + void block()
    + boolean isBlocked()
    + Account getAccount()
}
```

### Account
```java
class Account {
    - String accountNumber
    - int balance

    + boolean hasSufficientBalance(int amount)
    + void debit(int amount)
    + void credit(int amount)
    + int getBalance()
}
```

### BankingService
```java
class BankingService {
    + boolean validateAccount(Card card)
    + boolean hasSufficientBalance(Account account, int amount)
    + void debit(Account account, int amount)
    + void credit(Account account, int amount)
    + int getBalance(Account account)
}
```

### CashDispenser
```java
class CashDispenser {
    - CashInventory cashInventory

    + Map<Denomination, Integer> dispense(int amount)
    + CashInventory getCashInventory()
}
```

### CashInventory
```java
class CashInventory {
    - Map<Denomination, Integer> noteCountMap

    + boolean hasSufficientCash(int amount)
    + int getNoteCount(Denomination d)
    + void deductCash(Map<Denomination, Integer> notes)
    + void addCash(Map<Denomination, Integer> notes)
}
```

### Transaction (Hierarchy)
```java
abstract class Transaction {
    protected Card card
    protected int amount
    + abstract void execute(ATM atm)
}

class WithdrawTransaction extends Transaction {
    + void execute(ATM atm)
}

class DepositTransaction extends Transaction {
    + void execute(ATM atm)
}
```

---

## Key Processes

### 1. Withdrawal Flow
1.  **Authentication**: User inserts card -> `Session` created -> PIN entered -> `Session` validates PIN.
2.  **Transaction Initiation**: `WithdrawTransaction` created with amount.
3.  **Execution**:
    *   `ATM` calls `transaction.execute(this)`.
    *   **Validation**: Check `BankingService` for account validity and sufficient balance.
    *   **Cash Check**: `CashDispenser` checks `CashInventory`.
    *   **Dispense**: `CashDispenser` calculates notes, deducts from inventory.
    *   **Update Balance**: `BankingService` debits the `Account`.

### 2. Deposit Flow
1.  **Authentication**: (Same as above).
2.  **Execution**:
    *   **Update Balance**: `BankingService` credits the `Account`.
    *   **Update Inventory**: `CashInventory` is updated with new notes (simplified logic assuming specific denominations).

### 3. Handling Invalid PIN
*   `Session` increments `failedAttempts`.
*   If `failedAttempts >= 3`, `Card.block()` is called.
*   Subsequent transactions are rejected by `BankingService.validateAccount()` checking `isBlocked()`.
