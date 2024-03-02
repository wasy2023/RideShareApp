package UI;
import service.service;

import java.util.Objects;
import java.util.Scanner;
// implement user sign up and make the relation between service and database more tight
// implement filters in which to use user


public class UI {
    private static service service = new service();
    public static void main(String[] args)
    {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Sign up(1) or Sign in(2)");
        int request = scanner.nextInt();
        if(request == 1)
            SignUp();
        else if (request == 2)
            while(!LoginIn()){}
        else
            service.loadExamples();
       // service service = new service();
        boolean running = true;
        printmenu();
        while(running)
        {
            Exception exception = new Exception();
            System.out.println("->");
            int command = scanner.nextInt();
            if(command == 0)
            {
                running=false;
            }
            else {
                if (command == 1) {
                    System.out.println("Car rental shop");
                    printmenu();
                } else if (command == 2) {
                    service.print_available_cars();
                } else if (command == 3) {
                    System.out.println("What car would you want ? ( check the available cars if necessary)");
                    System.out.println("Input the id of the car :");
                    int id = scanner.nextInt();
                    scanner.nextLine();
                    System.out.println("Input the number of people:");
                    int nop= scanner.nextInt();
                    scanner.nextLine();
                    System.out.println("Input the distance:");
                    int duration = scanner.nextInt();
                    System.out.println("Input the date of format dd-mm-yyyy or if you want it now write (now)");
                    String date = scanner.next();
                    String hour="";
                    if(!Objects.equals(date, "now")) {
                        System.out.println("What hour?");
                        scanner.nextLine();
                        hour = scanner.nextLine();
                    }
                    service.reserve_car(id,duration,nop,date,hour);
                    service.refresh();
                    //System.out.println("car reserved:" + car_model);
                } else if (command == 8) {
                    System.out.println("Please input the passcode:");
                    boolean running2 = true;
                    int pass = scanner.nextInt();
                    try {
                        if (pass == 1234) {
                            printManagerMenu();
                            while (running2) {
                                System.out.println("->");
                                int command1 = scanner.nextInt();
                                if (command1 == 1) {
                                    System.out.println("Input the data for the new car:");
                                    System.out.println("Id:");
                                    int id = scanner.nextInt();
                                    scanner.nextLine();
                                    System.out.println("Model:");
                                    String model = scanner.nextLine();
                                    System.out.println("Fuel type:");
                                    System.out.println("1->Hybrid  2-> GAS  3->DIESEL");
                                    //scanner.nextLine();
                                    int fuel = scanner.nextInt();
                                    service.add_car(id, model, fuel);
                                    System.out.println("Car successfully added!");
                                } else if (command1 == 2) {
                                    System.out.println("Input the data for the car to be removed:");
                                    System.out.println("Id:");
                                    int id = scanner.nextInt();
                                    service.remove_car(id);
                                } else if (command1 == 3) {

                                    System.out.println("Input the id of the car:");
                                    int id = scanner.nextInt();

                                    System.out.println("Input the new model:");
                                    scanner.nextLine();
                                    String model = scanner.nextLine();

                                    service.Update_car(id, model);
                                } else if (command1 == 0) {
                                    running2 = false;
                                } else {
                                    System.out.println("Invalid command!");
                                }


                            }
                        }
                        else
                            throw exception;
                    }
                    catch (Exception e)
                    {
                        System.out.println("Incorrect password !!");
                    }
                }
                else if(command == 5)
                {
                    System.out.println("All cars:");
                    service.print_all_cars();
                }
                else if(command == 6)
                {
                    service.print_all_reserved();
                }
                else if(command == 7)
                {
                    System.out.println("Input the id of the reservation (Check all reserved cars)");
                    int id = scanner.nextInt();
                    service.remove_reservation(id);
                    service.refresh();
                }
                else if(command == 4)
                {
                    System.out.println("Input the id of the reservation");
                    int revid= scanner.nextInt();
                    scanner.nextLine();
                    System.out.println("Input the new duration");
                    int dur = scanner.nextInt();
                    scanner.nextLine();
                    System.out.println("Input the new number of people");
                    int nop = scanner.nextInt();
                    scanner.nextLine();
                    System.out.println("Input the id of the new car");
                    int carid = scanner.nextInt();
                    service.update_reservation(revid,dur,nop,carid);
                    service.refresh();
                }
                else if(command==9)
                {
                    System.out.println("In which type of file do you want to save the data?");
                    System.out.println("1-> text file  or  2->binary file  or  3->database");
                    int file_type = scanner.nextInt();
                    if(file_type!=3)
                        service.saveToFile(file_type);
                    else
                        service.saveToDataBase();
                } else if (command == 10) {
                    filterby();
                } else if (command == 11) {
                    service.retriveFromDB();
                }
            }

        }
    }

    public static void filterby()
    {
        Scanner scanner = new Scanner(System.in);
        int loop = 1;
        System.out.println("What kind of search do you want to perform?");
        System.out.println("1. Show only the cars with a specific car type");
        System.out.println("2. Show only available cars");
        System.out.println("3. Show the cars with a specific model");
        System.out.println("4. Show the reservations newer by a specific date");
        System.out.println("5. Show upcoming reservations");
        System.out.println("6. Show today's reservations");
        System.out.println("0. Exit");
        while(loop==1) {
            int type = scanner.nextInt();
            if(type == 0) loop=0;
            else
                service.filterBY(type);
        }
    }
    public static boolean SignUp(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Input your username:");
        String username = scanner.nextLine();
        if(service.searchUser(username))
        {
            boolean ok =true;
            while(ok) {
                System.out.println("Input your password:");
                String password1 = scanner.nextLine();
                System.out.println("Confirm your password:");
                String password2 = scanner.nextLine();
                if(password1.equals(password2)) {
                    service.addUser(username,password1);
                    return true;
                }
                else
                    System.out.println("Password doesn't match, rewrite please!");
            }


        }
        return false;
    }
    public static boolean LoginIn()
    {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Input your credentials:");
        System.out.println("Username: ");
        String username = scanner.nextLine();
        System.out.println("Password: ");
        String password = scanner.nextLine();
        boolean ok = service.searchUser(username,password);
        if(ok)
            System.out.println("Successfully logged in !");
        else
            System.out.println("Can't log in!( Maybe you need to sign up!)");
        service.printUserDetails();
        return ok;

    }
    public static void printmenu()
    {
        System.out.println("Menu:");
        System.out.println("1. Print the menu");
        System.out.println("2. See available cars");
        System.out.println("3. Rent a car");
        System.out.println("4. Update reservation");
        System.out.println("5. Show all cars");
        System.out.println("6. Show all reservations");
        System.out.println("7. Remove reservation");
        System.out.println("8. Manage the shop(authorized personnel)");
        System.out.println("9. Save in txt/bin file ");;
        System.out.println("10. Filters");
        System.out.println("11. Get data from database");
        System.out.println("0. Exit");
    }
    public static void printManagerMenu()
    {
        System.out.println("Welcome!");
        System.out.println("What changes would you like to make?");
        System.out.println("1. Add a new car");
        System.out.println("2. Remove a car");
        System.out.println("3. Update a car");
        System.out.println("0. Exit");
    }

}
