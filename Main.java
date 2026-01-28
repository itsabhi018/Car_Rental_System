import java.util.*;

class Car{
    private String CarId;
    private String Brand;
    private String Model;
    private int BasePrice;
    private Boolean isAvailable;

    public Car(String CarId, String Brand, String Model, int BasePrice){
        this.CarId=CarId;
        this.Brand=Brand;
        this.Model=Model;
        this.BasePrice=BasePrice;
        this.isAvailable=true;
    }

    public String getCarId(){
        return CarId;
    }
    public String getBrand(){
        return Brand;
    }
    public String getModel(){
        return Model;
    }
    public int getBasePrice(){
        return BasePrice;
    }
    public Boolean getisAvailable(){
        return isAvailable;
    }
    public void rent(){
        isAvailable=false;
    }
    public void returnCar(){
        isAvailable=true;
    }
    public double totalprice(int rentalDays){
        return rentalDays*BasePrice;
    }

}

class Customer{
    private String customerId;
    private String name;

    public Customer(String customerId, String name){
        this.customerId=customerId;
        this.name=name;
    }

    public String getcustomerId(){
        return customerId;
    }
    public String getname(){
        return name;
    }

}

class Rental{

    private Car car;
    private Customer customer;
    private int days;

    public Rental(Car car,Customer customer,int days){
        this.car=car;
        this.customer=customer;
        this.days=days;
    }

    public Car getCar(){
        return car;
    }
    public Customer getCustomer(){
        return customer;
    }
    public int getdays(){
        return days;
    }
}

class CarRentalSystem{
    private List<Car> cars;
    private List<Customer> customers;
    private List<Rental> rentals;

    public CarRentalSystem(){
        cars=new ArrayList<>();
        customers=new ArrayList<>();
        rentals=new ArrayList<>();
    }

     public void addCar(Car car){
        cars.add(car);
    }
    public void addCustomer(Customer customer){
        customers.add(customer);
    }
    public void rentCar(Car car, Customer customer, int days){
        if(car.getisAvailable()){
            car.rent();
            rentals.add(new Rental(car,customer,days));
        }
        else System.out.println("Car is not available for rent.");
    }
    public void returnCar(Car car){
        Rental rentalToRemove = null;
        for(Rental rental : rentals){
            if(rental.getCar()==car){
                rentalToRemove=rental;
                break;
            }
        }
        if(rentalToRemove != null){
            rentals.remove(rentalToRemove);
        }
        else System.out.println("Car not rented");
    }
    public void menu(){
        Scanner scanner = new Scanner(System.in);

        while (true) { 
            System.out.println("=== CAR RENTAL SYSTEM ===");
            System.out.println("1. RENT A CAR");
            System.out.println("2. RETURN A CAR");
            System.out.println("3. EXIT");
            System.out.println("ENTER YOUR CHOICE: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            if(choice == 1){
                System.out.println("\n== RENT A CAR ==\n");
                System.out.println("Enter you name : ");
                String customerName = scanner.nextLine();

                System.out.println("\nAVAILABLE CARS ");
                for(Car car : cars){
                    if(car.getisAvailable()){
                        System.out.println(car.getCarId() + " - " + car.getBrand() + " " + car.getModel());
                    }
                }

                System.out.println("Enter the car Id you want to rent : ");
                String carId = scanner.nextLine();

                System.out.println("Enter the number of days for rental : ");
                int rentalDays = scanner.nextInt();
                scanner.nextLine();

                Customer newcustomer = new Customer("CUS" + (customers.size()+1),customerName);
                addCustomer(newcustomer);

                Car selectedCar = null;
                for(Car car : cars){
                    if(car.getCarId().equals(carId) && car.getisAvailable()){
                        selectedCar = car;
                        break;
                    }
                }

                if(selectedCar != null){
                    double price = selectedCar.totalprice(rentalDays);
                    System.out.println("\n=== RENTAL SUMMARY ===");
                    System.out.println("Customer Id: " + newcustomer.getcustomerId());
                    System.out.println("Customer Name: " + newcustomer.getname());
                    System.out.println("Car Rented: " + selectedCar.getBrand() + " " + selectedCar.getModel());
                    System.out.println("Total Price: $" + price);

                    System.out.println("\nConfirm Rental(Y/N): ");
                    String Confirm = scanner.nextLine();

                    if(Confirm.equalsIgnoreCase("Y")){
                        rentCar(selectedCar, newcustomer, rentalDays);
                        System.out.println("Car rented successfully !");
                    }
                    else System.out.println("Rental cancelled.");
                }
                else System.out.println("Car not available.");
            }
            else if(choice==2){
                System.out.println("\n=== RETURN A CAR ===\n");
                System.out.println("Enter the Car Id you want to return: ");
                String returnCarId= scanner.nextLine();

                Car returncar = null;
                for(Car car: cars){
                    if(car.getCarId().equals(returnCarId)){
                        returncar=car;
                        break;
                    }
                }
                if(returncar != null){
                    Customer rentingCustomer = null;
                    for(Rental rental :rentals){
                        if(rental.getCar()==returncar){
                            rentingCustomer=rental.getCustomer();
                            break;
                        }
                    }
                    if(rentingCustomer != null){
                        returnCar(returncar);
                        System.out.println("Car returned by " + rentingCustomer.getname());
                    }
                    else System.out.println("This car was not rented.");
                }
                else System.out.println("Invalid Car Id.");
            }
            else if(choice==3){
                break;
            }
            else System.out.println("Invalid choice. Please try again.");
        }
        System.out.println("Thank you for using the Car Rental System. Goodbye!");
    }
}

public class Main{
    public static void main(String args[]){
        CarRentalSystem crs =new CarRentalSystem();

        Car car1 = new Car("C001","Mahindra","XUV700",2000);
        Car car2 = new Car("C002","Tata","Nexon",1000);
        Car car3 = new Car("C003","Maruti Suzuki","Fronx",1500);

        crs.addCar(car1);
        crs.addCar(car2);
        crs.addCar(car3);

        crs.menu();

    }
}
