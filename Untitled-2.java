/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.nwmissouri.spring24.cs44542.sec03.arrayandarraylists.s567549assignment05;

/**
 * Class: 44542-03 Object-Oriented Programming
 *
 * @author Bharath Simha Reddy Kothapeta Description: Assignment - 5 Due:
 * 02/21/2024 I pledge that I have completed the programming assignment
 * independently. I have not copied the code from a student, internet, or any
 * other source. I will not share my source code with anyone under any
 * circumstances.
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class NorthwestDriver {

    private static boolean foodstatus = false;
    private static boolean bevaragestatus = false;

    public static void main(String[] args) {
        ArrayList<ArrayList<String>> item = new ArrayList<>();
        ArrayList<ArrayList<Double>> itemPrice = new ArrayList<>();

        String[] sectionsOrder = {"soda", "hibachi", "sushi", "specialRoll", "soup", "salad"};

        ArrayList<String> currentSection = new ArrayList<>();
        ArrayList<Double> currentPrices = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader("inputFile.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.equals("@@@")) {
                    continue;
                }

                if (line.isEmpty()) {
                    continue;
                }

                if (line.contains("-")) {
                    String[] parts = line.split(" ");
                    String itemName = parts[0];
                    double itemPriceValue = Double.parseDouble(parts[1]);

                    currentSection.add(itemName);
                    currentPrices.add(itemPriceValue);
                } else {
                    int index = getIndexForSection(line.toLowerCase(), sectionsOrder);
                    if (index != -1) {
                        currentSection = new ArrayList<>();
                        currentPrices = new ArrayList<>();
                        item.add(currentSection);
                        itemPrice.add(currentPrices);
                    } else {
                        String[] parts = line.split(" ");
                        currentSection.add(parts[0]);
                        currentPrices.add(Double.parseDouble(parts[1]));
                    }
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        Scanner myscanner = new Scanner(System.in);

        System.out.println("Welcome to the Northwest Restaurant.");
        System.out.println("1. Proceed to order");
        System.out.println("2. Look over the menu");

        int choice = myscanner.nextInt();

        switch (choice) {
            case 1:
                orderBeverages(myscanner, sectionsOrder, item, itemPrice);
                break;
            case 2:
                displayMenu(sectionsOrder, item, itemPrice);
                break;
            default:
                System.out.println("Your selection is invalid.");
                break;
        }
    }

    private static void displayMenu(String[] sectionsOrder, ArrayList<ArrayList<String>> item, ArrayList<ArrayList<Double>> itemPrice) {
        int values = 0;

        for (ArrayList<String> section : item) {
            values = Math.max(values, section.size());
        }

        System.out.println("---------------------------------------------------------------------------------------------------------------------------------------- ------------------------------------------------------");
        System.out.println("|                                                                                  Printing Menu                                                                                              |");
        System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");

        System.out.print("|");
        for (String sectionName : sectionsOrder) {
            System.out.printf("%25s", sectionName);
        }
        System.out.println();

        for (int i = 0; i < values; i++) {
            System.out.print("|");
            for (ArrayList<String> section : item) {
                if (i < section.size()) {
                    System.out.printf("%25s %4.2f", section.get(i), itemPrice.get(item.indexOf(section)).get(i));
                }
            }

            System.out.println();
        }

        System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
    }

    private static void orderBeverages(Scanner scanner, String[] sectionsOrder, ArrayList<ArrayList<String>> item, ArrayList<ArrayList<Double>> itemPrice) {
        int input;
        int beverageIndex = SectionIndex("soda", sectionsOrder);
        do {
            System.out.println("Are you ready to give the Beverages order");
            System.out.println("1. Yes");
            System.out.println("2. Later");
            System.out.println("3. No");
            input = scanner.nextInt();
            if (input != 1 && input != 2 && input != 3) {
                System.out.println("Your selection is invalid");
            }
            if (input == 2) {
                System.out.println("Thank you, will get back to you in a while.");
            }
        } while (input != 3 && input != 1);

        if (input == 1) {

            ArrayList<String> beverages = new ArrayList();
            ArrayList<Double> beveragePrices = new ArrayList();

            int beverageInput;
            String inputStr = "";
            do {
                System.out.println("Here are the list of Beverages:");
                for (int i = 0; i < item.get(beverageIndex).size(); i++) {
                    System.out.println((i + 1) + ". " + item.get(beverageIndex).get(i));
                }
                beverageInput = scanner.nextInt();
                if (beverageInput > 7 || beverageInput < 1) {
                    System.out.println("Your selection for beverages is invalid");
                    continue;
                } else {
                    beverages.add(item.get(beverageIndex).get(beverageInput - 1));
                    beveragePrices.add(itemPrice.get(beverageIndex).get(beverageInput - 1));
                }
                System.out.println("Do you want to order more beverages[yes/no]");
                inputStr = scanner.next();
                if (inputStr.equals("no")) {
                    break;
                }
            } while (beverageInput <= 7 && beverageInput >= 1 || inputStr.equals("yes"));
            BeverageTicket(beverages, beveragePrices);
            orderFood(sectionsOrder, item, itemPrice);
        } else if (input == 3) {
            orderFood(sectionsOrder, item, itemPrice);
        }

    }

    public static void customerstatus() {

        System.out.println("*********Customer status******");
        if (bevaragestatus == true && foodstatus == true) {
            System.out.println("\t\t\t" + "Regular Customer" + "\t\t\t");
        } else if (bevaragestatus != true && foodstatus == true) {
            System.out.println("\t\t\t" + "Food Lover" + "\t\t\t");
        } else if (bevaragestatus == true && foodstatus != true) {
            System.out.println("\t\t\t" + "Beverage Lover" + "\t\t\t");
        } else {
            System.out.println("|\t\t\t" + "Visitor" + "\t\t\t|");
        }
        System.out.println("");
        System.out.println("*********************");
    }

    public static void BeverageTicket(ArrayList<String> beverages, ArrayList<Double> prices) {

        bevaragestatus = true;
        System.out.println("*************Bevarage Ticket***************");
        System.out.println("| \t\t\tS/No\tBeverage\t\t\tQuantity\t\t\tPrice \t|");
        double sum = 0;
        for (int i = 0; i < beverages.size(); i++) {
            String mystring = beverages.get(i);
            boolean isProcessed = false;

            for (int j = 0; j < i; j++) {
                if (beverages.get(j).equals(mystring)) {
                    isProcessed = true;
                    break;
                }
            }
            if (!isProcessed) {
                int get = count(beverages, mystring);
                double price = prices.get(i);
                sum += price * get;
                System.out.println("| \t\t\t" + (i + 1) + "\t" + mystring + "\t\t\t\t  " + get + "\t\t\t\t" + (price) + "\t|");
            }

        }
        System.out.println("\t\t\t\t\t\t\t\t\t\t\t\t------------");
        sum = (sum * 2.50) / 100 + sum;
        System.out.println("\t\t\t\t\t\t\t\t\t\t\t\t" + String.format("%.2f", (sum)));
        System.out.println("\t\t\t\t\t\t\t\t\t\t\t\t-------------");

        System.out.println("");
        System.out.println("**********************************");
    }

    private static int count(ArrayList<String> list, String item) {
        int count = 0;
        for (String listItem : list) {
            if (listItem.equals(item)) {
                count++;
            }
        }
        return count;
    }

    private static double calculateTotalPriceWithTax(double totalPrice, double taxPercent) {
        double taxAmount = (taxPercent / 100) * totalPrice;
        return totalPrice + taxAmount;
    }

    private static double calculateTotalPrice(ArrayList<Double> prices) {
        double total = 0;
        for (Double price : prices) {
            total += price;
        }
        return total;
    }

    private static int SectionIndex(String sectionName, String[] sectionsOrder) {
        for (int i = 0; i < sectionsOrder.length; i++) {
            if (sectionName.equalsIgnoreCase(sectionsOrder[i])) {
                return i;
            }
        }
        return -1; 
    }

    private static int getIndexForSection(String section, String[] sectionsOrder) {
        for (int i = 0; i < sectionsOrder.length; i++) {
            if (section.equalsIgnoreCase(sectionsOrder[i])) {
                return i;
            }
        }
        return -1;
    }

    public static void FoodTicket(ArrayList<String> food, ArrayList<Double> foodprices) {
        foodstatus = true;

        System.out.println("***************Food Ticket*************");
        System.out.println("| \t\tS/No\t\t\tFood\t\tQuantity\t\tPrice   |");
        double sum = 0;
        for (int i = 0; i < food.size(); i++) {
            String r = food.get(i);

            boolean isProcessed = false;

            for (int j = 0; j < i; j++) {
                if (food.get(j).equals(r)) {
                    isProcessed = true;
                    break;
                }
            }
            if (!isProcessed) {
                int q = count(food, r);

                double p1 = foodprices.get(i);
                sum += p1 * q;
                System.out.println("| \t\t" + (i + 1) + "\t\t" + r + "\t\t" + q + "\t  " + (p1) + "\t  |");
            }

        }
        System.out.println("\t\t\t\t\t\t\t\t  ---------------");
        sum = (sum * 7.50) / 100 + sum;
        System.out.println("\t\t\t\t\t\t\t\t  " + String.format("%.2f", sum));
        System.out.println("\t\t\t\t\t\t\t\t  ---------------");

        System.out.println("");

        System.out.println("*****************************");

    }

    private static void orderFood(String[] sectionsOrder, ArrayList<ArrayList<String>> item, ArrayList<ArrayList<Double>> itemPrice) {
        int input;
        Scanner scan = new Scanner(System.in);
        ArrayList<String> food = new ArrayList<>();
        ArrayList<Double> foodPrices = new ArrayList<>();
        do {
            System.out.println("Are you ready to give the Food order?");
            System.out.println("1. Yes");
            System.out.println("2. Later");
            System.out.println("3. No");
            input = scan.nextInt();
            if (input != 1 && input != 2 && input != 3) {
                System.out.println("Your selection is invalid");
            }
            if (input == 2) {
                System.out.println("Thank you, will get back to you in a while.");
            }
        } while (input != 3 && input != 1);
        if (input == 3) {
            customerstatus();

        }
        if (input == 1) {
            int foodInput;
            String inputStr = "";
            int selectedFoodItem;
            do {
                System.out.println("Here are the list of varieties:");
                for (int i = 0; i < sectionsOrder.length; i++) {
                    if (i == 0) {
                        continue;
                    }
                    System.out.println((i) + ". " + sectionsOrder[i]);
                }
                foodInput = scan.nextInt();
                if (foodInput >= item.size() || foodInput < 1) {
                    System.out.println("Your selection for food is invalid");
                    continue;
                } else {
                       System.out.println("Here are the list of "+ sectionsOrder[foodInput]);
                    for (int i = 0; i < item.get(foodInput).size(); i++) {
                        System.out.println((i + 1) + ". " + item.get(foodInput).get(i));
                    }
                    selectedFoodItem = scan.nextInt();
                    if (foodInput >= item.size() || foodInput < 1) {
                        System.out.println("Your selection for food is invalid");
                        continue;
                    } else {
                        food.add(item.get(foodInput).get(selectedFoodItem - 1));
                        foodPrices.add(itemPrice.get(foodInput).get(selectedFoodItem - 1));
                    }
                }
                System.out.println("Do you want to order more food? (yes/no)");
                inputStr = scan.next().toLowerCase();
            } while (foodInput <= item.size() && foodInput >= 1 && inputStr.equals("yes"));

            FoodTicket(food, foodPrices);
            customerstatus();
        }
    }
}