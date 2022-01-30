package com.company;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.Date;

public class Main {

    static ArrayList<Person> Pers = new ArrayList<Person>();

    public static void main(String[] args) throws ClassNotFoundException, SQLException, ParseException {
        Class.forName("org.sqlite.JDBC");
        Connection conn = DriverManager.getConnection("jdbc:sqlite:src/DB.s3db");
        Statement statmt = conn.createStatement();
        statmt.execute("CREATE TABLE if not exists 'Persons' ('id' INTEGER PRIMARY KEY AUTOINCREMENT, 'fio' text, 'birthday' DATE, 'post' text);");
        PersLoad();
        while (true) {
            System.out.print("Дни рождения в ближайшую неделю\n");
            SortDate();
            System.out.println("╔══════════════════════════════╦═════╦══════════╗");
            System.out.println("║             FIO              ║BIRTH║   POST   ║");
            System.out.println("╠══════════════════════════════╬═════╬══════════╣");
            for(int i = 0; (i<Pers.size()); i++){
                if (Pers.get(i).getDayCount()<7) Pers.get(i).PrintTable();
                else break;
            }

            System.out.print("1. Ввод данных\n2. Вывод данных\n3. Сохранение/Загрузка\n0. Выход\n> "); int a = Scani();
            while (a == 1){
                System.out.print("1. Добавить\n2. Редактировать\n3. Удалить\n0. Назад\n> "); int b = Scani();
                if (b == 1){
                    System.out.print("Добавить:\nФИО>\t\t\t");  String newFIO = ScanS();
                    System.out.print("Год рождения>\t");        int BY = Scani();
                    System.out.print("Месяц рождения>\t");      int BM = Scani();
                    System.out.print("День рождения>\t");       int BD = Scani();
                    System.out.print("Должность>\t\t");         String newPost = ScanS();
                    Pers.add(new Person(newFIO,new Date(BY,BM-1,BD),newPost));
                    continue;
                } else if (b == 2){
                    System.out.print("Редактировать:\nФИО для редактирования>\t");  String oldFIO = ScanS();
                    int p = FindIndex(oldFIO);
                    if (p != -1){
                        System.out.print("Введите новые данные:\n");
                        System.out.print("Новое ФИО>\t\t");             String newFIO = ScanS();
                        System.out.print("Год рождения>\t");            int BY = Scani();
                        System.out.print("Месяц рождения>\t");          int BM = Scani();
                        System.out.print("День рождения>\t");           int BD = Scani();
                        System.out.print("Должность>\t\t");             String newPost = ScanS();
                        Pers.get(p).FIO = newFIO;
                        Pers.get(p).Birthday = new Date(BY, BM-1, BD);
                        Pers.get(p).Post = newPost;
                        System.out.print("Успешно обновлено :)`\n");
                    } else {
                        System.out.print("Введённое ФИО не найдено :(`\n");
                    }
                    continue;
                } else if (b == 3){
                    System.out.print("Удалить:\nФИО для удаления>\t");  String oldFIO = ScanS();
                    int p = FindIndex(oldFIO);
                    if (p != -1){
                        Pers.remove(p);
                        System.out.print("Успешно удалено :)`\n");
                    } else {
                        System.out.print("Введённое ФИО не найдено :(`\n");
                    }
                    continue;
                } else if (b == 0){
                    break;
                }
            }

            while (a == 2){
                System.out.print("1. Дни рождения сегодня\n2. Дни рождения в ближайшие (n) дней\n3. Все дни рождения\n4. Ближайшие (n) дней рождений\n0. Назад\n> "); int b = Scani();
                if (b == 1){
                    SortDate();
                    System.out.println("╔═════════════[ДНИ РОЖДЕНИЯ СЕГОДНЯ]═╦══════════╗");
                    System.out.println("║             FIO              ║BIRTH║   POST   ║");
                    System.out.println("╠══════════════════════════════╬═════╬══════════╣");
                    for (int i = 0; i<Pers.size(); i++){
                        if ((Pers.get(i).Birthday.getDate() == new Date().getDate())&&(Pers.get(i).Birthday.getMonth() == new Date().getMonth())) Pers.get(i).PrintTable();
                    }
                }else if (b == 2){
                    System.out.print("(n)> ");  int n = Scani();
                    SortDate();
                    System.out.println("╔══════════════════════════════╦═════╦══════════╗");
                    System.out.println("║             FIO              ║BIRTH║   POST   ║");
                    System.out.println("╠══════════════════════════════╬═════╬══════════╣");
                    for(int i = 0; (i<Pers.size()); i++){
                        if (Pers.get(i).getDayCount()<n) Pers.get(i).PrintTable();
                        else break;
                    }
                }else if (b == 3){
                    SortDate();
                    System.out.println("╔═══════════════[ВСЕ ДНИ РОЖДЕНИЯ]═══╦══════════╗");
                    System.out.println("║             FIO              ║BIRTH║   POST   ║");
                    System.out.println("╠══════════════════════════════╬═════╬══════════╣");
                    for (Person item: Pers) {
                        item.PrintTable();
                    }
                }else if (b == 4){
                    System.out.print("(n)> ");  int n = Scani();
                    SortDate();
                    System.out.println("╔══════════════════════════════╦═════╦══════════╗");
                    System.out.println("║             FIO              ║BIRTH║   POST   ║");
                    System.out.println("╠══════════════════════════════╬═════╬══════════╣");
                    for(int i = 0; ((i<n)&&(i<Pers.size())); i++){
                        Pers.get(i).PrintTable();
                    }
                }else if (b == 0){
                    break;
                }
            }
            while (a == 3) {
                System.out.print("1. Сохранить в БД\n2. Загрузить из БД\n0. Назад\n> "); int b = Scani();
                if(b == 1){
                    PersSave();
                }else if(b == 2){
                    PersLoad();
                }else if (b == 0){
                    break;
                }
            }
            if (a == 0){
                System.out.print("Сохранить изменения в БД?(Y/n) > "); String s = ScanS();
                if ((s == "Y")||(s == "y")||(s == "Д")||(s == "д")||(s == "+")||(s == "Да")||(s == "да")||(s == "ДА")||(s == "Yes")||(s == "yes")) PersSave();
                break;
            }
        }
    }

    public static String ScanS(){
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    public static int Scani(){
        Scanner scanner = new Scanner(System.in);
        if (scanner.hasNextInt()) return scanner.nextInt();
        return 0;
    }

    static int FindIndex(String name){
        for (int i = 0; i<Pers.size(); i++) {
            if (Pers.get(i).FIO == name) return i;
        }
        return -1;
    }

    static void SortDate(){
        for (int i = 0; i<Pers.size(); i++) {
            for (int j = 0; j<Pers.size(); j++) {
                if (Pers.get(i).getDayCount() < Pers.get(j).getDayCount()){
                    Person pi = Pers.get(i);
                    Person pj = Pers.get(j);
                    Pers.remove(i);
                    Pers.add(i,pj);
                    Pers.remove(j);
                    Pers.add(j,pi);
                }
            }
        }
    }

    static void PersLoad() throws ClassNotFoundException, SQLException, ParseException {
        Class.forName("org.sqlite.JDBC");
        Connection conn = DriverManager.getConnection("jdbc:sqlite:src/DB.s3db");;
        Statement statmt = conn.createStatement();
        ResultSet resSet;
        Pers.removeAll(Pers);
        resSet = statmt.executeQuery("SELECT * FROM Persons");
        while(resSet.next())
        {
            int id = resSet.getInt("id");
            String name = resSet.getString("fio");
            Date BD = new SimpleDateFormat("yyyy-MM-dd").parse(LocalDate.parse(resSet.getString("birthday").substring(4,11) + resSet.getString("birthday").substring(24,28), DateTimeFormatter.ofPattern("MMM dd yyyy",Locale.ENGLISH)).toString());
            String post = resSet.getString("post");
            Pers.add(new Person(name,BD,post));
        }
        System.out.println("Данные загружены из БД");
    }

    static void PersSave() throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        Connection conn = DriverManager.getConnection("jdbc:sqlite:src/DB.s3db");
        Statement statmt = conn.createStatement();
        statmt.execute("CREATE TABLE if not exists 'Persons' ('id' INTEGER PRIMARY KEY AUTOINCREMENT, 'fio' text, 'birthday' DATE, 'post' text);");
        ResultSet resSet;
        statmt.execute("DELETE FROM Persons;");
        for (Person item: Pers) {
            statmt.execute("INSERT INTO 'Persons' ('fio', 'birthday', 'post') VALUES ('"+item.FIO+"', '"+item.Birthday+"', '"+item.Post+"');");
        }
        System.out.println("Данные сохранены в БД");
    }
}