import java.util.ArrayList;
import java.util.HashMap;

public class MoneyKeeper {

    FileReader file = new FileReader();
    public HashMap<String, ArrayList<Sale>> monthToSales = new HashMap<>();
    public ArrayList<String> monthNames = new ArrayList<>();

    public void addMonth(String monthName, String fileName) {

        ArrayList<Sale> sales = new ArrayList<>();

        ArrayList<String> lines = file.readFileContents(fileName);
        for (String line : lines) {
            String[] parts = line.split("\t"); // day, category, money
            int day = Integer.parseInt(parts[0]);
            String category = parts[1];
            int money = Integer.parseInt(parts[2]);

            Sale sale = new Sale(day, category, money);
            sales.add(sale);
        }

        monthToSales.put(monthName, sales);
        monthNames.add(monthName);
    }

    public void printStat(String monthName) {
        ArrayList<Sale> curSales = monthToSales.get(monthName);

        int curIndex = monthNames.indexOf(monthName);
        // нельзя запрашивать статистику для первого месяца в списке (индекс 0)
        String prevMonthName = monthNames.get(curIndex - 1);
        ArrayList<Sale> prevSales = monthToSales.get(prevMonthName);

        HashMap<String, Integer> curCatToSum = sumSales(curSales);
        HashMap<String, Integer> prevCatToSum = sumSales(prevSales);

        System.out.println("Категория -- " + prevMonthName + " -- " + monthName +
                " -- %");
        for (String category : curCatToSum.keySet()) {
            int curMoney = curCatToSum.get(category);
            int prevMoney = prevCatToSum.getOrDefault(category, 0);

            String percentMsg;
            if (prevMoney != 0) {
                percentMsg = (curMoney - prevMoney) * 100 / prevMoney + "%";
                percentMsg = (curMoney > prevMoney) ? "+" + percentMsg : percentMsg;
            } else {
                percentMsg = "--%";
            }

            System.out.println(category + " -- " + prevMoney + " -- " +
                    curMoney + " -- " + percentMsg);
        }
    }

    HashMap<String, Integer> sumSales(ArrayList<Sale> sales) {
        HashMap<String, Integer> catToSum = new HashMap<>();
        for (Sale sale : sales) {
            String category = sale.category;
            int money = sale.money;

//            if (!curCatToSum.containsKey(category)) {
//                curCatToSum.put(category, money);
//            } else {
//                curCatToSum.put(category, curCatToSum.get(category) + money);
//            }

            // более изящная запись в одну строку
            catToSum.put(category, catToSum.getOrDefault(category, 0) + money);
        }
        return catToSum;
    }



}
