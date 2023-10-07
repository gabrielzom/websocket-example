package kerp.sextou.websocketexample.vo;

public class Vote {
    private int number;
    private int quantity;

    public Vote() {}

    public Vote(int number, int quantity) {
        this.number = number;
        this.quantity = quantity;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
