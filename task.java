// 1. Подумать над структурой класса Ноутбук для магазина техники - выделить поля и методы. Реализовать в java.
// 2. Создать множество ноутбуков.
// 3. Написать метод, который будет запрашивать у пользователя критерий фильтрации и выведет ноутбуки, отвечающие фильтру.
// NoteBook notebook1 = new NoteBook
// NoteBook notebook2 = new NoteBook
// NoteBook notebook3 = new NoteBook
// NoteBook notebook4 = new NoteBook
// NoteBook notebook5 = new NoteBook

// Например: “Введите цифру, соответствующую необходимому критерию:
// 1 - ОЗУ
// 2 - Объем ЖД
// 3 - Операционная система
// 4 - Цвет

// Далее нужно запросить минимальные значения для указанных критериев - сохранить параметры фильтрации можно также в Map.
// Отфильтровать ноутбуки их первоначального множества и вывести проходящие по условиям.
// Класс сделать в отдельном файле
// приветствие
// Выбор параметра
// выбор конкретнее
// вывод подходящих

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

public class task {
    public static void main(String[] args) {
        ArrayList<Notebook> laptops = getLaptopList();
        HashMap<Integer, String> sortingValues = getSortingMap();
        HashMap<String, ArrayList<String>> allStr = getStringChoices(sortingValues, laptops);
        Scanner sc = new Scanner(System.in);

        int firstChoice = filterFirst(sortingValues, sc);

        int secondChoice = filterSecond(sortingValues, laptops, allStr, sc, firstChoice);

        filterFinal(firstChoice, secondChoice, allStr, laptops);

        sc.close();
    }

    
    public static ArrayList<Notebook> getLaptopList() {
        ArrayList<Notebook> al = new ArrayList<>();
        Notebook huawei = new Notebook("Huawei D90", "White", 16, 500, "DOS", "1000$");
        Notebook macbook = new Notebook("Apple MacBook Air 13\"", "Silver", 8, 200, "MacOS", "5000$");
        Notebook ibm = new Notebook("IBM NB80", "Black", 4, 100, "Windows 10 Home", "1500$");
        Notebook hp = new Notebook("hp 255 g9 5Y3X5EA", "Dark gray", 16, 500, "FreeDOS", "2000$");
        Notebook acer = new Notebook("Ноутбук Acer Nitro 5", "Black", 16, 1000, "DOS", "1500$");

        al.add(acer);
        al.add(hp);
        al.add(ibm);
        al.add(macbook);
        al.add(huawei);

        return al;
    }
    
    public static void filterFinal(int first, int second, HashMap<String, ArrayList<String>> strMap, ArrayList<Notebook> al) {
        ArrayList<Notebook> matching = new ArrayList<>();

        switch (first) {
            case 1:
                for (Notebook el: al)
                    if (el.ramGB >= second)
                        matching.add(el);
                break;
            case 2:
                for (Notebook el: al)
                    if (el.romGB >= second)
                        matching.add(el);
                break;
            case 3:
                if (strMap.get("Операционная система").size() <= second - 1 || second < 1) {
                    System.out.println("Такого выбора нет!");
                    break;
                }
                for (Notebook el: al)
                    if (strMap.get("Операционная система").get(second - 1).equals(el.os))
                        matching.add(el);
                break;
            case 4:
                if (strMap.get("Цвет").size() <= second - 1 || second < 1) {
                    System.out.println("Такого выбора нет!");
                    break;
                }
                for (Notebook el: al)
                    if (strMap.get("Цвет").get(second - 1).equals(el.color))
                        matching.add(el);
                break;
            case 5:
                if (strMap.get("Цена").size() <= second - 1 || second < 1) {
                    System.out.println("Такого выбора нет!");
                    break;
                }

                for (Notebook el: al)
                    if (strMap.get("Цена").get(second - 1).equals(el.price))
                        matching.add(el);
                break;
            default:
                break;
        }

        if (matching.size() < 1) System.out.println("Нет подходящих вариантов(");
        else {
            System.out.println("\n---- Все подходящие варианты ----\n");
            for (Notebook el: matching) el.getInfo();
            System.out.println("\n---------------------------------\n");
        }
    }

    
    public static int filterSecond(HashMap<Integer, String> hm, ArrayList<Notebook> al, HashMap<String, ArrayList<String>> strMap, Scanner sc, int firstChoice) {
        int choice = 0;

        if (firstChoice > 0 && firstChoice < 3) {
            System.out.println("Напишите минимально подходящее кол-во памяти (" + hm.get(firstChoice) + ").");
            System.out.print("Кол-во памяти (ГБ): ");

            choice = sc.nextInt();
            sc.nextLine();
        }

        else if (firstChoice > 2 && firstChoice < 6) {
            ArrayList<String> strChoices = strMap.get(hm.get(firstChoice));
            System.out.println("Выберите один из подходящих вариантов (" + hm.get(firstChoice) + "):");

            for (int i = 0; i < strChoices.size(); i++) {
                System.out.println((i + 1) + ". " + strChoices.get(i));
                }

            System.out.print("\nВаш выбор: ");
            choice = sc.nextInt();
            sc.nextLine();
        }

        else {
            System.out.println("Такого выбора нет!");
            }

        return choice;
    }

    
    public static HashMap<String, ArrayList<String>> getStringChoices(HashMap<Integer, String> hm, ArrayList<Notebook> al) {
        HashMap<String, ArrayList<String>> strMap = new HashMap<>();

        HashSet<String> oses = new HashSet<>();
        HashSet<String> colors = new HashSet<>();
        HashSet<String> prices = new HashSet<>();
        
        ArrayList<String> osList = new ArrayList<>();
        ArrayList<String> colorList = new ArrayList<>();
        ArrayList<String> priceList = new ArrayList<>();

        for (Notebook el: al) {
            oses.add(el.os);
            colors.add(el.color);
            prices.add(el.price);
        }

        osList.addAll(oses);
        colorList.addAll(colors);
        priceList.addAll(prices);

        strMap.put(hm.get(3), osList);
        strMap.put(hm.get(4), colorList);
        strMap.put(hm.get(5), priceList);
        

        return strMap;
    }

    
    public static int filterFirst(HashMap<Integer, String> hm, Scanner sc) {
        System.out.println("Выберите цифру, которая Вам больше нравится:");

        for (var el: hm.entrySet()) System.out.println(el.getKey() + ". " + el.getValue());

        System.out.print("\nВаш выбор: ");
        int choice = sc.nextInt();
        sc.nextLine();

        return choice;
    }
    
    public static HashMap<Integer, String> getSortingMap() {
        HashMap<Integer, String> select = new HashMap<>();
        select.put(1, "Объем ОЗУ");
        select.put(2, "Объем ЖД");
        select.put(3, "Операционная система");
        select.put(4, "Цвет");
        select.put(5, "Цена");
        return select;
    }  
}