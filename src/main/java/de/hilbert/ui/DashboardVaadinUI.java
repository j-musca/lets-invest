package de.hilbert.ui;

import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
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

    private Button initializationButton = new Button("Caption",
            clickEvent -> {
                try {
                    stockService.importSomeStocks();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                stockService.readGraph();
            });


    @Override
    protected void init(VaadinRequest vaadinRequest) {
        setContent(initializationButton);
    }
}
