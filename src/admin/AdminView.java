package admin;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdminView {
    private final EntityManager entityManager = new EntityManager();
    private final UserDao userDao = new UserDao();
    private int entityIxd = 0;
    private int actionIdx = 0;

    public AdminView() throws SQLException {
    }

    public void createAdminWindow() {
        JPanel updateUserPanel = new JPanel();
        updateUserPanel.setBorder(new TitledBorder("Поиск по идентификатору"));
        updateUserPanel.setLayout(new BorderLayout());
        JPanel innerUpdate = new JPanel();
        innerUpdate.setLayout(new GridLayout(3, 1, 1, 1));
        updateUserPanel.setVisible(true);
        JLabel updateUserLabel = new JLabel("Введите id пользователя: ");
        TextField updateUserTextField = new TextField(20);
        JButton updateUserButton = new JButton("Обновить");
        innerUpdate.add(updateUserLabel);
        innerUpdate.add(updateUserTextField);
        innerUpdate.add(updateUserButton);
        updateUserPanel.add(innerUpdate, BorderLayout.NORTH);

        JPanel deleteUserPanel = new JPanel();
        deleteUserPanel.setBorder(new TitledBorder("Поиск по идентификатору"));
        deleteUserPanel.setLayout(new GridLayout(3, 1, 1, 1));
        deleteUserPanel.setVisible(true);
        JLabel deleteUserLabel = new JLabel("Введите id пользователя: ");
        TextField deleteUserTextField = new TextField(20);
        JButton deleteUserButton = new JButton("Удалить");
        deleteUserPanel.add(deleteUserLabel);
        deleteUserPanel.add(deleteUserTextField);
        deleteUserPanel.add(deleteUserButton);

        JPanel createUserPanel = new JPanel();
        createUserPanel.setBorder(new TitledBorder("Регистрация пользователя"));
        createUserPanel.setLayout(new GridLayout(5, 1, 1, 1));
        createUserPanel.setVisible(true);
        JLabel createUserLoginLabel = new JLabel("Введите логин пользователя: ");
        TextField createUserLoginTextField = new TextField(20);
        JLabel createUserPasswordLabel = new JLabel("Введите пароль пользователя: ");
        TextField createUserPasswordTextField = new TextField(20);
        JButton createUserButton = new JButton("Создать");
        createUserPanel.add(createUserLoginLabel);
        createUserPanel.add(createUserLoginTextField);
        createUserPanel.add(createUserPasswordLabel);
        createUserPanel.add(createUserPasswordTextField);
        createUserPanel.add(createUserButton);

        JPanel itemInnerPanel = new JPanel();

        JPanel createItemPanel = new JPanel();
        createItemPanel.setBorder(new TitledBorder("Атрибуты товара"));
        createItemPanel.setLayout(new BorderLayout());
        createItemPanel.setVisible(true);
        JComboBox switchItemComboBox = new JComboBox();
        JButton createButton = new JButton("Создать");
        createItemPanel.add(switchItemComboBox, BorderLayout.NORTH);
        createItemPanel.add(itemInnerPanel, BorderLayout.CENTER);
        createItemPanel.add(createButton, BorderLayout.SOUTH);

        JPanel updateItemPanel = new JPanel();
        updateItemPanel.setBorder(new TitledBorder("Поиск по идентификатору"));
        updateItemPanel.setLayout(new BorderLayout());
        JPanel innerUpdateItemPanel = new JPanel();
        innerUpdateItemPanel.setLayout(new GridLayout(4, 1, 1, 1));
        updateItemPanel.setVisible(true);
        innerUpdateItemPanel.setVisible(true);
        JLabel updateItemLabel = new JLabel("Введите id товара: ");
        TextField updateItemTextField = new TextField(20);
        JButton updateItemButton = new JButton("Обновить");
        JComboBox updateComboBox = new JComboBox();
        for (UsedForFront item : entityManager.getItemEntities()) {
            updateComboBox.addItem(item.getTitle());
        }
        innerUpdateItemPanel.add(updateComboBox);
        innerUpdateItemPanel.add(updateItemLabel);
        innerUpdateItemPanel.add(updateItemTextField);
        innerUpdateItemPanel.add(updateItemButton);
        updateItemPanel.add(innerUpdateItemPanel, BorderLayout.NORTH);

        JPanel deleteItemPanel = new JPanel();
        deleteItemPanel.setBorder(new TitledBorder("Поиск по идентификатору"));
        deleteItemPanel.setLayout(new GridLayout(4, 1, 1, 1));
        deleteItemPanel.setVisible(true);
        JLabel deleteItemLabel = new JLabel("Введите id товара: ");
        TextField deleteItemTextField = new TextField(20);
        JButton deleteItemButton = new JButton("Удалить");
        JComboBox deleteComboBox = new JComboBox();
        for (UsedForFront item : entityManager.getItemEntities()) {
            deleteComboBox.addItem(item.getTitle());
        }
        deleteItemPanel.add(deleteComboBox);
        deleteItemPanel.add(deleteItemLabel);
        deleteItemPanel.add(deleteItemTextField);
        deleteItemPanel.add(deleteItemButton);

        JPanel chooseEntityPanel = new JPanel();
        chooseEntityPanel.setBorder(new TitledBorder("Выберите область"));
        chooseEntityPanel.setVisible(true);
        JRadioButton userRadioButton = new JRadioButton("Пользователи", true);
        JRadioButton itemRadioButton = new JRadioButton("Товары");
        ButtonGroup entityButtonGroup = new ButtonGroup();
        entityButtonGroup.add(userRadioButton);
        entityButtonGroup.add(itemRadioButton);
        chooseEntityPanel.add(userRadioButton);
        chooseEntityPanel.add(itemRadioButton);

        JPanel chooseActionPanel = new JPanel();
        chooseActionPanel.setBorder(new TitledBorder("Выберите действие"));
        chooseActionPanel.setVisible(true);
        JRadioButton createRadioButton = new JRadioButton("Создать", true);
        JRadioButton updateRadioButton = new JRadioButton("Обновить");
        JRadioButton deleteRadioButton = new JRadioButton("Удалить");
        ButtonGroup actionButtonGroup = new ButtonGroup();
        actionButtonGroup.add(createRadioButton);
        actionButtonGroup.add(updateRadioButton);
        actionButtonGroup.add(deleteRadioButton);
        chooseActionPanel.add(createRadioButton);
        chooseActionPanel.add(updateRadioButton);
        chooseActionPanel.add(deleteRadioButton);

        JPanel mainPanel = new JPanel();
        mainPanel.setBorder(new TitledBorder("Администраторская"));
        mainPanel.setVisible(true);
        mainPanel.add(chooseEntityPanel);
        mainPanel.add(chooseActionPanel);
        mainPanel.add(createUserPanel);
        mainPanel.updateUI();

        JFrame frame = new JFrame("Администратор");
        frame.setSize(700, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.add(mainPanel);

        List<TextField> itemTextFields = new ArrayList<>();

        switchItemComboBox.addItemListener(ItemEvent -> {
            if (ItemEvent.getStateChange() == java.awt.event.ItemEvent.SELECTED) {
                itemInnerPanel.removeAll();
                itemTextFields.clear();

                itemInnerPanel.setBorder(new TitledBorder("Атрибуты: " + entityManager.getItemEntities()
                        .get(switchItemComboBox.getSelectedIndex()).getTitle()));

                List<String> attributes = entityManager.getItemEntities().get(switchItemComboBox.getSelectedIndex())
                        .getAttributes();

                itemInnerPanel.setLayout(new GridLayout(attributes.size(), 2, 1, 1));

                for (String attribute : attributes) {
                    itemInnerPanel.add(new JLabel(attribute));
                    TextField tf = new TextField(20);
                    itemInnerPanel.add(tf);
                    itemTextFields.add(tf);
                }

                createItemPanel.updateUI();
            }
        });

        entityManager.getItemEntities()
                .stream()
                .peek(e -> switchItemComboBox.addItem(e.getTitle()))
                .toArray();

        List<JPanel> userPanels = List.of(createUserPanel, updateUserPanel, deleteUserPanel);
        List<JPanel> itemPanels = List.of(createItemPanel, updateItemPanel, deleteItemPanel);

        List<List<JPanel>> panels = List.of(userPanels, itemPanels);

        userRadioButton.addActionListener(ActionListener -> {
            mainPanel.remove(panels.get(entityIxd).get(actionIdx));
            mainPanel.add(panels.get(0).get(actionIdx));
            mainPanel.updateUI();
            entityIxd = 0;
        });

        itemRadioButton.addActionListener(ActionListener -> {
            mainPanel.remove(panels.get(entityIxd).get(actionIdx));
            mainPanel.add(panels.get(1).get(actionIdx));
            mainPanel.updateUI();
            entityIxd = 1;
        });

        createRadioButton.addActionListener(ActionListener -> {
            mainPanel.remove(panels.get(entityIxd).get(actionIdx));
            mainPanel.add(panels.get(entityIxd).get(0));
            mainPanel.updateUI();
            actionIdx = 0;
        });

        updateRadioButton.addActionListener(ActionListener -> {
            mainPanel.remove(panels.get(entityIxd).get(actionIdx));
            mainPanel.add(panels.get(entityIxd).get(1));
            mainPanel.updateUI();
            actionIdx = 1;
        });

        deleteRadioButton.addActionListener(ActionListener -> {
            mainPanel.remove(panels.get(entityIxd).get(actionIdx));
            mainPanel.add(panels.get(entityIxd).get(2));
            mainPanel.updateUI();
            actionIdx = 2;
        });

        createUserButton.addActionListener(ActionListener -> {
            try {
                String login = createUserLoginTextField.getText();
                String password = createUserPasswordTextField.getText();

                User user = User.builder()
                        .login(login)
                        .password(password)
                        .build();

                userDao.createUser(user);
                createUserLoginTextField.setText("");
                createUserPasswordTextField.setText("");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });

        deleteUserButton.addActionListener(ActionListener -> {
            try {
                String login = deleteUserTextField.getText();
                userDao.deleteUserById(login);
                deleteUserTextField.setText("");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });

        List<TextField> userTextFields = List.of(new TextField(), new TextField());
        JButton loadUser = new JButton("Обновить");
        JPanel updateUser = new JPanel();
        updateUser.setLayout(new GridLayout(3, 2, 1, 1));
        updateUser.setVisible(true);

        updateUserButton.addActionListener(ActionListener -> {
            try {
                String login = updateUserTextField.getText();
                User user = userDao.getUserById(login);

                System.out.println(user);

                updateUser.add(new JLabel("Логин"));
                userTextFields.get(0).setText(user.getLogin());
                userTextFields.get(1).setText(user.getPassword());
                updateUser.add(userTextFields.get(0));
                updateUser.add(new JLabel("Пароль"));
                updateUser.add(userTextFields.get(1));
                updateUser.add(loadUser);

                updateUserPanel.add(updateUser, BorderLayout.CENTER);
                updateUserPanel.updateUI();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });

        loadUser.addActionListener(ActionListener -> {
            try {
                String login = userTextFields.get(0).getText();
                String password = userTextFields.get(1).getText();

                User user = User.builder()
                        .login(login)
                        .password(password)
                        .build();

                userTextFields.get(0).setText("");
                userTextFields.get(1).setText("");
                userDao.updateUserById(user);
                updateUserPanel.remove(updateUser);
                updateUserPanel.updateUI();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });

        List<TextField> updateTextFields = new ArrayList<>();
        JPanel updatePanel = new JPanel();
        updatePanel.setBorder(new TitledBorder("Товар"));
        updatePanel.setLayout(new BorderLayout());
        JButton updateButton = new JButton("Обновить");

        updateItemButton.addActionListener(ActionListener -> {
            try {
                UsedForFront itemType = entityManager.getItemEntities().get(updateComboBox.getSelectedIndex());
                UsedForFront item = entityManager.getItemById(itemType, Integer.valueOf(updateItemTextField.getText()));

                JPanel innerUpdatePanel = new JPanel();
                innerUpdatePanel.setLayout(new GridLayout(item.getAttributes().size(), 2, 1, 1));
                updatePanel.removeAll();

                Field[] fields = item.getClass().getDeclaredFields();

                for (int i = 0; i < item.getAttributes().size(); i++) {
                    innerUpdatePanel.add(new JLabel(item.getAttributes().get(i)));

                    fields[i].setAccessible(true);
                    Object o = fields[i].get(item);

                    TextField tf = new TextField(o.toString());
                    updateTextFields.add(tf);
                    innerUpdatePanel.add(tf);
                }

                updatePanel.add(updateButton, BorderLayout.SOUTH);
                updatePanel.add(innerUpdatePanel, BorderLayout.CENTER);
                updateItemPanel.add(updatePanel, BorderLayout.CENTER);
                updateItemPanel.updateUI();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            } catch (InstantiationException e) {
                throw new RuntimeException(e);
            }
        });

        updateButton.addActionListener(ActionListener -> {
            try {
                UsedForFront itemType = entityManager.getItemEntities().get(updateComboBox.getSelectedIndex());
                UsedForFront item = itemType.getClass().newInstance();
                int i = 0;

                for (Field field : item.getClass().getDeclaredFields()) {
                    field.setAccessible(true);

                    if (field.toString().contains("Integer")) {
                        field.set(item, Integer.valueOf(updateTextFields.get(i).getText()));
                    } else if (field.toString().contains("Double")) {
                        field.set(item, Double.valueOf(updateTextFields.get(i).getText()));
                    } else {
                        field.set(item, updateTextFields.get(i).getText());
                    }

                    i++;
                }

                int id = Integer.valueOf(updateItemTextField.getText());
                entityManager.updateItemById(item, id);

                updateItemTextField.setText("");
                updateItemPanel.remove(updatePanel);
                updateTextFields.clear();
                updateItemPanel.updateUI();

                for (Listener listener : Listeners.getListeners()) {
                    listener.update();
                }
            } catch (InstantiationException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (NoSuchFieldException e) {
                throw new RuntimeException(e);
            }
        });

        deleteItemButton.addActionListener(ActionListener -> {
            try {
                UsedForFront itemType = entityManager.getItemEntities().get(deleteComboBox.getSelectedIndex());
                int id = Integer.valueOf(deleteItemTextField.getText());
                entityManager.delete(itemType, id);
                deleteItemTextField.setText("");

                for (Listener listener : Listeners.getListeners()) {
                    listener.update();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (NoSuchFieldException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        });

        createButton.addActionListener(ActionListener -> {
            try {
                UsedForFront itemType = entityManager.getItemEntities().get(switchItemComboBox.getSelectedIndex());
                UsedForFront item = itemType.getClass().newInstance();

                int i = 0;

                for (Field field : item.getClass().getDeclaredFields()) {
                    field.setAccessible(true);

                    if (field.toString().contains("Integer")) {
                        field.set(item, Integer.valueOf(itemTextFields.get(i).getText()));
                    } else if (field.toString().contains("Double")) {
                        field.set(item, Double.valueOf(itemTextFields.get(i).getText()));
                    } else {
                        field.set(item, itemTextFields.get(i).getText());
                    }

                    itemTextFields.get(i++).setText("");
                }

                entityManager.save(item);
                mainPanel.updateUI();

                for (Listener listener : Listeners.getListeners()) {
                    listener.update();
                }
            } catch (InstantiationException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (NoSuchFieldException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
