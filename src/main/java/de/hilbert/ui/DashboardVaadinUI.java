package de.hilbert.ui;

import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.*;
import de.hilbert.services.CommonBootstrapService;
import de.hilbert.services.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.spring.VaadinUI;

import java.io.IOException;

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

    private Button showStocksButton = new Button("Show Stocks",
            clickEvent -> {
                 Notification.show(stockService.readGraph());
            });



    @Override
    protected void init(VaadinRequest vaadinRequest) {
        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.addComponent(initializationButton);
        verticalLayout.addComponent(showStocksButton);
        Label label = new Label(stockService.readGraph());
        verticalLayout.addComponent(label);

        setContent(verticalLayout);
    }
}
