// State interface
interface PhoneState {
    void pressButton();
}

// Concrete States
class UnlockedState implements PhoneState {
    private Phone phone;

    public UnlockedState(Phone phone) {
        this.phone = phone;
    }

    @Override
    public void pressButton() {
        phone.setState(new LockedState(phone));
        System.out.println("Phone is unlocked. Executing various functions...");
    }
}

class LockedState implements PhoneState {
    private Phone phone;

    public LockedState(Phone phone) {
        this.phone = phone;
    }

    @Override
    public void pressButton() {
        phone.setState(new UnlockedState(phone));
        System.out.println("Phone is locked. Showing unlock screen...");
    }
}

class LowChargeState implements PhoneState {
    private Phone phone;

    public LowChargeState(Phone phone) {
        this.phone = phone;
    }

    @Override
    public void pressButton() {
        System.out.println("Phone charge is low. Showing charging screen...");
    }
}

// Context
class Phone {
    private PhoneState state;
    private PhoneState unlockedState;
    private PhoneState lockedState;
    private PhoneState lowChargeState;

    public Phone() {
        unlockedState = new UnlockedState(this);
        lockedState = new LockedState(this);
        lowChargeState = new LowChargeState(this);
        state = lockedState; // Default state
    }

    public void setState(PhoneState state) {
        this.state = state;
    }

    public PhoneState getUnlockedState() {
        return unlockedState;
    }

    public PhoneState getLockedState() {
        return lockedState;
    }

    public PhoneState getLowChargeState() {
        return lowChargeState;
    }

    public void pressButton() {
        state.pressButton();
    }
}

// Client code
public class example1 {
    public static void main(String[] args) {
        Phone phone = new Phone();

        // Phone is initially locked
        // phone.pressButton();

        // Change state to unlocked
        phone.setState(phone.getUnlockedState());
        phone.pressButton();

        phone.pressButton();

        // Change state to low charge
        phone.setState(phone.getLowChargeState());
        phone.pressButton();

        // Change state back to locked
        phone.setState(phone.getLockedState());
        phone.pressButton();
    }
}
