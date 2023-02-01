package views;

import dao.CartDao;
import handlers.CartListeners;
import handlers.Listener;
import models.CartDto;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class CartView {
    private final CartDao cartDao = new CartDao();
    private final String login;

    public CartView(String login) throws SQLException {
        this.login = login;
    }
    public void createWindow() throws SQLException, NoSuchFieldException, IllegalAccessException {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        JPanel innerPanel = new JPanel();
        innerPanel.setBorder(new TitledBorder("Корзина"));

        JPanel amountPanel = new JPanel();
        amountPanel.setLayout(new GridLayout(1, 3, 1, 1));
        amountPanel.setBorder(new TitledBorder(""));

        Listener cartListener = () -> {
            innerPanel.removeAll();
            amountPanel.removeAll();

            List<CartDto> cartDtoList = cartDao.getCartDto(login);
            innerPanel.setLayout(new GridLayout(cartDtoList.size(), 3, 1, 1));

            for (CartDto cartDto : cartDtoList) {
                innerPanel.add(new JLabel(cartDto.getModel()));
                innerPanel.add(new JLabel(cartDto.getNumber().toString() + " шт."));
                innerPanel.add(new JLabel(cartDto.getCost().toString() + " руб."));
            }

            Integer amount = cartDtoList
                    .stream()
                    .mapToInt(value -> value.getCost() * value.getNumber())
                    .sum();

            amountPanel.add(new JLabel("Итого:"));
            amountPanel.add(new JLabel(""));
            amountPanel.add(new JLabel(amount.toString() + " руб."));

            innerPanel.updateUI();
            amountPanel.updateUI();
        };

        CartListeners.addListener(cartListener);

        mainPanel.add(innerPanel, BorderLayout.CENTER);

        mainPanel.add(amountPanel, BorderLayout.SOUTH);

        JFrame jFrame = new JFrame();
        jFrame.add(mainPanel);
        jFrame.setDefaultCloseOperation(3);
        jFrame.setVisible(true);
        jFrame.setSize(700, 200);

        cartListener.update();
    }
}
