import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ReportGenerator {
    public static void generateReport(int id, String name, String product, int quantity, String type) throws IOException {
        int price = 0;
        double discount = 0.75;
            switch (product) {
                case "Bread":
                    price = 8;
                    break;
                case "Cake":
                    price = 36;
                    break;
                case "Cookies":
                    price = 4;
                    break;
            }

        if (type.equals("Individual")) {
            String reportContent = String.format(
                    "Bakery Company\nDate: %s\nCustomer Name: %s\nProduct: %s\nQuantity: %d\nPrice: %d PLN",
                    new SimpleDateFormat("yyyy-MM-dd").format(new Date()), name, product, quantity, price * quantity);

            FileWriter writer = new FileWriter(name + ".txt");
            writer.write(reportContent);
            writer.close();
        } else {
            String reportContent = String.format(
                    "Bakery Company\nDate: %s\nCustomer Name: %s\nProduct: %s\nQuantity: %d\nPrice: %g PLN",
                    new SimpleDateFormat("yyyy-MM-dd").format(new Date()), name, product, quantity, price * quantity * discount);

            FileWriter writer = new FileWriter(name + ".txt");
            writer.write(reportContent);
            writer.close();
        }
    }
}
