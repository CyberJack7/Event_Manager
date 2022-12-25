package Client;

import org.jdatepicker.DateLabelFormatter;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.model.UtilDateModel;

import java.awt.*;
import java.util.Locale;

public class calendarPanel {
    static JDatePickerImpl getCalendar(){
        UtilDateModel uiModel; // Модель даты
        JDatePanelImpl dtpPanel; // Панель даты
        DateLabelFormatter dlf; // Формат представления даты в компоненте
        Locale locale = new Locale("rus"); //локализация

        // Создание компонента даты
        uiModel = new UtilDateModel();
        dtpPanel = new JDatePanelImpl (uiModel, locale);
        dlf = new DateLabelFormatter("yyyy-MM-dd");
        JDatePickerImpl calendar = new JDatePickerImpl(dtpPanel, dlf);
        calendar.setPreferredSize(new Dimension(332, 20));
        return calendar;
    }
}
