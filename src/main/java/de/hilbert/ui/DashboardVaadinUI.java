package de.hilbert.ui;

import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.*;
import de.hilbert.entities.Stock;
import de.hilbert.services.CommonBootstrapService;
import de.hilbert.services.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.spring.VaadinUI;

import java.util.List;

/**
 * @author hilbert
 * @since 14.11.14
 */
@VaadinUI
public class DashboardVaadinUI extends UI {

    @Autowired
    private StockService stockService;

    @Autowired
    private CommonBootstrapService commonBootstrapService;

    private Button initializationButton = new Button("Bootstrap Data",
            clickEvent -> {
                commonBootstrapService.bootstrapStocks();
                Notification.show("bootstrapped");
            });

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        MenuBar barmenu = new MenuBar();

        // A feedback component
        final Label selection = new Label("-");

        // Define a common menu command for all the menu items.
        MenuBar.Command mycommand = new MenuBar.Command() {
            public void menuSelected(MenuBar.MenuItem selectedItem) {
                selection.setValue("Ordered a " +
                        selectedItem.getText() +
                        " from menu.");
            }
        };

        // Put some items in the menu hierarchically
        MenuBar.MenuItem beverages =
                barmenu.addItem("Beverages", null, null);
        MenuBar.MenuItem hot_beverages =
                beverages.addItem("Hot", null, null);
        hot_beverages.addItem("Tea", null, mycommand);
        hot_beverages.addItem("Coffee", null, mycommand);
        MenuBar.MenuItem cold_beverages =
                beverages.addItem("Cold", null, null);
        cold_beverages.addItem("Milk", null, mycommand);
        cold_beverages.addItem("Weissbier", null, mycommand);

        // Another top-level item
        MenuBar.MenuItem snacks =
                barmenu.addItem("Snacks", null, null);
        snacks.addItem("Weisswurst", null, mycommand);
        snacks.addItem("Bratwurst", null, mycommand);
        snacks.addItem("Currywurst", null, mycommand);

        // Yet another top-level item
        MenuBar.MenuItem services =
                barmenu.addItem("Services", null, null);
        services.addItem("Car Service", null, mycommand);


        HorizontalLayout rootMenu = new HorizontalLayout();
        rootMenu.addComponent(initializationButton);

        List<Stock> stocks = stockService.findAllStocks();

        final Table table = new Table("All Stocks in Database:");
        table.setPageLength(stocks.size());

        // Define two columns for the built-in container
        table.addContainerProperty("Symbol", String.class, null);
        table.addContainerProperty("current Price", String.class, null);

        for (Stock stock : stocks) {
            table.addItem(new Object[]{stock.getSymbol(), stock.getPrice()}, stock.getId());
        }

        //putting stuff together
        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.addComponent(barmenu);
        verticalLayout.addComponent(selection);
        verticalLayout.addComponent(rootMenu);
        verticalLayout.addComponent(table);

        setContent(verticalLayout);
    }
}
