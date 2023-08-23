public class Main {
    public static void main(String[] args) {
        MoneyKeeper keeper = new MoneyKeeper();

        keeper.addMonth("Dec", "december.tsv");
        keeper.addMonth("Jan", "january.tsv");
        keeper.addMonth("Feb", "february.tsv");

        // вывод статистики для любого месяца, кроме первого в списке загруженных
        keeper.printStat("Jan");

    }
}