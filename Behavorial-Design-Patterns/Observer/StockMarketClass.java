import java.util.*;

// Subject
interface Stock {
    void registerObserver(Investor investor);
    void removeObserver(Investor investor);
    void notifyObservers();
}

// Concrete Subject
class StockMarket implements Stock {
    private List<Investor> investors = new ArrayList<>();
    private String stockName;
    private double stockPrice;

    public StockMarket(String stockName, double stockPrice) {
        this.stockName = stockName;
        this.stockPrice = stockPrice;
    }

    public void setStockPrice(double stockPrice) {
        this.stockPrice = stockPrice;
        notifyObservers();
    }

    public double getStockPrice() {
        return stockPrice;
    }

    @Override
    public void registerObserver(Investor investor) {
        investors.add(investor);
    }

    @Override
    public void removeObserver(Investor investor) {
        investors.remove(investor);
    }

    @Override
    public void notifyObservers() {
        for (Investor investor : investors) {
            investor.update(stockName, stockPrice);
        }
    }
}

// Observer interface
interface Investor {
    void update(String stockName, double stockPrice);
}

// Concrete Observer
class ConcreteInvestor implements Investor {
    private String investorName;

    public ConcreteInvestor(String investorName) {
        this.investorName = investorName;
    }

    @Override
    public void update(String stockName, double stockPrice) {
        System.out.println("Hello, " + investorName + "! The stock price of " + stockName + " has changed to $" + stockPrice);
    }
}

// Client
public class StockMarketClass {
    public static void main(String[] args) {
        StockMarket appleStock = new StockMarket("Apple", 150.0);
        Investor investor1 = new ConcreteInvestor("Alice");
        Investor investor2 = new ConcreteInvestor("Bob");

        appleStock.registerObserver(investor1);
        appleStock.registerObserver(investor2);

        // Change in stock price
        appleStock.setStockPrice(155.0); // Notifies Alice and Bob

        // Another change in stock price
        appleStock.setStockPrice(160.0); // Notifies Alice and Bob

        // Bob is no longer interested
        appleStock.removeObserver(investor2);

        // Yet another change in stock price
        appleStock.setStockPrice(165.0); // Notifies only Alice
    }
}