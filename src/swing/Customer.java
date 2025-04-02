package swing;

public class Customer {
    private String name;
    private String numberPhone;
    private String address;
    private int loyaltyPoints;

    public Customer(String name, String numberPhone, String address, int loyaltyPoints) {
        this.name = name;
        this.numberPhone = numberPhone;
        this.address = address;
        this.loyaltyPoints = loyaltyPoints;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumberPhone() {
        return numberPhone;
    }

    public void setNumberPhone(String numberPhone) {
        this.numberPhone = numberPhone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getLoyaltyPoints() {
        return loyaltyPoints;
    }

    public void setLoyaltyPoints(int loyaltyPoints) {
        this.loyaltyPoints = loyaltyPoints;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Customer customer = (Customer) obj;
        return numberPhone != null && numberPhone.equals(customer.numberPhone);
    }

    @Override
    public int hashCode() {
        return (numberPhone != null) ? numberPhone.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "name='" + name + '\'' +
                ", numberPhone='" + numberPhone + '\'' +
                ", address='" + address + '\'' +
                ", loyaltyPoints=" + loyaltyPoints +
                '}';
    }
}
