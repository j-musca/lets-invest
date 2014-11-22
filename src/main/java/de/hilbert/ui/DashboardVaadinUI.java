package de.hilbert.ui;

import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.*;
import de.hilbert.entities.Stock;
import de.hilbert.services.CommonBootstrapService;
import de.hilbert.services.StockService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.spring.VaadinUI;

import java.util.List;

/**
 * @author hilbert
 * @since 14.11.14
 */
@VaadinUI
public class DashboardVaadinUI extends UI {

    public static Logger log = Logger.getLogger(DashboardVaadinUI.class);

    @Autowired
    private StockService stockService;

    @Autowired
    private CommonBootstrapService commonBootstrapService;

    private Button initializationButton = new Button("Bootstrap Data",
            clickEvent -> {
                commonBootstrapService.bootstrapStocks();
                Notification.show("bootstrapped");
            });

    Panel panel = new Panel();

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        //putting stuff together
        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.addComponent(createMainMenu());
        verticalLayout.addComponent(panel);
        changePage("");
        setContent(verticalLayout);
    }

    private MenuBar createMainMenu() {
        MenuBar barmenu = new MenuBar();

        // Define a common menu command for all the menu items.
        MenuBar.Command mycommand = selectedItem -> changePage(selectedItem.getText());

        // Put some items in the menu hierarchically
        barmenu.addItem(MENU_CAPTION_DASHBOARD, null, mycommand);
        barmenu.addItem(MENU_CAPTION_ALL_STOCKS, null, mycommand);
        barmenu.addItem(MENU_CAPTION_ADMINISTRATION, null, mycommand);

        return barmenu;
    }

    public final static String MENU_CAPTION_DASHBOARD = "Dashboard";
    public final static String MENU_CAPTION_ALL_STOCKS = "All Stocks";
    public final static String MENU_CAPTION_ADMINISTRATION = "Administration";

    private void changePage(String menuText) {
        switch (menuText) {
            case MENU_CAPTION_DASHBOARD:
                panel.setCaption(MENU_CAPTION_DASHBOARD);
                panel.setContent(createDashboardView());
                break;
            case MENU_CAPTION_ALL_STOCKS:
                panel.setCaption(MENU_CAPTION_ALL_STOCKS);
                panel.setContent(createAllStockView());
                break;
            case MENU_CAPTION_ADMINISTRATION:
                panel.setCaption(MENU_CAPTION_ADMINISTRATION);
                panel.setContent(createAdministrationView());
                break;
            default:
                panel.setCaption(MENU_CAPTION_DASHBOARD);
                panel.setContent(createDashboardView());
                break;
        }
    }

    private Component createAdministrationView() {
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.addComponent(initializationButton);
        return horizontalLayout;
    }

    private Component createDashboardView() {
        return new Label("Dashboard");
    }

    private Component createAllStockView() {
        HorizontalLayout horizontalLayout = new HorizontalLayout();

        List<Stock> stocks = stockService.findAllStocks();

        final Table table = new Table("All Stocks in Database:");
        table.setPageLength(stocks.size());

        // Define two columns for the built-in container
        table.addContainerProperty("Symbol", String.class, null);
        table.addContainerProperty("current Price", String.class, null);

        for (Stock stock : stocks) {
            table.addItem(new Object[]{stock.getSymbol(), stock.getPrice()}, stock.getId());
        }

        horizontalLayout.addComponent(table);
        return horizontalLayout;
    }
}
