// Person.java
public class Person {
    // Required parameters
    private final String firstName;
    private final String lastName;

    // Optional parameters
    private final int age;
    private final String phone;
    private final String address;

    private Person(PersonBuilder builder) {
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.age = builder.age;
        this.phone = builder.phone;
        this.address = builder.address;
    }

    // Getters for the fields, no setters to keep immutability
    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getAge() {
        return age;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    @Override
    public String toString() {
        return "Person [firstName=" + firstName + ", lastName=" + lastName + 
               ", age=" + age + ", phone=" + phone + ", address=" + address + "]";
    }

    public static class PersonBuilder {
        // Required parameters
        private final String firstName;
        private final String lastName;

        // Optional parameters - initialized to default values
        private int age = 0;
        private String phone = "";
        private String address = "";

        public PersonBuilder(String firstName, String lastName) {
            this.firstName = firstName;
            this.lastName = lastName;
        }

        public PersonBuilder age(int age) {
            this.age = age;
            return this;
        }

        public PersonBuilder phone(String phone) {
            this.phone = phone;
            return this;
        }

        public PersonBuilder address(String address) {
            this.address = address;
            return this;
        }

        public Person build() {
            return new Person(this);
        }
    }

    public static void main(String[] args) {
        Person person = new Person.PersonBuilder("John", "Doe")
                .age(30)
                .phone("123-456-7890")
                .address("123 Main St")
                .build();

        System.out.println(person);
    }
}
