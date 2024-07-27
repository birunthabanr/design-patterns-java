abstract class Approver {
    protected Approver nextApprover;

    public void setNextApprover(Approver nextApprover) {
        this.nextApprover = nextApprover;
    }

    public abstract void approveRequest(PurchaseRequest request);
}

class Manager extends Approver {
    private static final double APPROVAL_LIMIT = 1000.0;

    @Override
    public void approveRequest(PurchaseRequest request) {
        if (request.getAmount() <= APPROVAL_LIMIT) {
            System.out.println("Manager approved the purchase request for $" + request.getAmount());
        } else if (nextApprover != null) {
            nextApprover.approveRequest(request);
        }
    }
}

class Director extends Approver {
    private static final double APPROVAL_LIMIT = 10000.0;

    @Override
    public void approveRequest(PurchaseRequest request) {
        if (request.getAmount() <= APPROVAL_LIMIT) {
            System.out.println("Director approved the purchase request for $" + request.getAmount());
        } else if (nextApprover != null) {
            nextApprover.approveRequest(request);
        }
    }
}

class CEO extends Approver {
    @Override
    public void approveRequest(PurchaseRequest request) {
        System.out.println("CEO approved the purchase request for $" + request.getAmount());
    }
}

// Request class
class PurchaseRequest {
    private double amount;

    public PurchaseRequest(double amount) {
        this.amount = amount;
    }

    public double getAmount() {
        return amount;
    }
}

public class PurchaseApproval {
    public static void main(String[] args) {
        // Create handlers
        Approver manager = new Manager();
        Approver director = new Director();
        Approver ceo = new CEO();

        // Set up the chain of responsibility
        manager.setNextApprover(director);
        director.setNextApprover(ceo);

        // Create purchase requests
        PurchaseRequest request1 = new PurchaseRequest(500);
        PurchaseRequest request2 = new PurchaseRequest(5000);
        PurchaseRequest request3 = new PurchaseRequest(15000);

        // Process the requests
        manager.approveRequest(request1);  // Output: Manager approved the purchase request for $500.0
        manager.approveRequest(request2);  // Output: Director approved the purchase request for $5000.0
        manager.approveRequest(request3);  // Output: CEO approved the purchase request for $15000.0
    }
}