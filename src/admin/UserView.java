package admin;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ItemListener;
import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class UserView {
    private final EntityManager em = new EntityManager();
    private int offset;
    private final List<String> sorts = List.of(
            Configs.SORT_BY_COST_ASC,
            Configs.SORT_BY_COST_DESC
    );
    private int sortIdx = 0;

    private UsedForFront filterConstraint;
    private UsedForFront itemConstraint;

    public UserView() throws SQLException {
    }

    public void createUserView() {
        JPanel filterPanel = new JPanel();
        filterPanel.setBorder(new TitledBorder("Фильтры"));
        filterPanel.setVisible(true);

        List<String> fieldNames = new ArrayList<>();

        List<JComboBox> comboBoxes = new ArrayList<>();

        Listener filterListener = () -> {
            int filterCount = em.getPageCount(filterConstraint);
            List<Map<String, Integer>> filters = em.getFilters(filterConstraint);

            for (int j = 0; j < filters.size(); j++) {
                ItemListener il = comboBoxes.get(j).getItemListeners()[0];

                comboBoxes.get(j).removeItemListener(il);
                comboBoxes.get(j).removeAllItems();

                for (String key : filters.get(j).keySet()) {
                    comboBoxes.get(j).addItem(key + ", " + filters.get(j).get(key));
                }

                Field field = filterConstraint.getClass().getDeclaredField(fieldNames.get(j));
                field.setAccessible(true);

                Object o = field.get(filterConstraint);

                if (o != null) {
                    if (filterCount == 0) {
                        comboBoxes.get(j).addItem(o + ", 0");
                    }

                    comboBoxes.get(j).setSelectedIndex(1);
                }

                comboBoxes.get(j).addItemListener(il);
            }

            filterPanel.updateUI();
        };

        JButton applyButton = new JButton("Применить");
        JButton cancelButton = new JButton("Сбросить фильтры");

        filterPanel.add(applyButton);
        filterPanel.add(cancelButton);

        JPanel itemPanel = new JPanel();
        itemPanel.setBorder(new TitledBorder("Товары"));
        itemPanel.setLayout(new BorderLayout());
        itemPanel.setVisible(true);

        JPanel itemInnerPanel = new JPanel();
        itemInnerPanel.setBorder(new TitledBorder("Названия товаров"));
        itemInnerPanel.setVisible(true);

        JPanel paginationPanel = new JPanel();
        paginationPanel.setBorder(new TitledBorder("Пагинация"));
        paginationPanel.setLayout(new GridLayout(1, 10, 1, 1));
        paginationPanel.setVisible(true);

        JPanel sortPanel = new JPanel();
        sortPanel.setBorder(new TitledBorder("Сортировка"));
        sortPanel.setLayout(new GridLayout(1, 2, 1, 1));
        sortPanel.setVisible(true);
        sortPanel.add(new JLabel("Тип сортировки"));
        String[] sortTypes = {"По возрастанию цены", "По убыванию цены"};
        JComboBox sortBox = new JComboBox(sortTypes);
        sortPanel.add(sortBox);

        itemPanel.add(itemInnerPanel, BorderLayout.CENTER);
        itemPanel.add(paginationPanel, BorderLayout.SOUTH);
        itemPanel.add(sortPanel, BorderLayout.NORTH);

        List<JButton> jButtonList = new ArrayList<>();

        Listener startPageListener = () -> {
            try {
                itemInnerPanel.removeAll();

                for (JButton button : jButtonList) {
                    button.setEnabled(true);
                }

                jButtonList.get(offset - 1).setEnabled(false);

                List<UsedForFront> itemListConstraint = em.getItemsByConstraint(itemConstraint, offset - 1, sorts.get(sortIdx));

                itemInnerPanel.setLayout(new GridLayout(itemListConstraint.size(), 2, 1, 1));

                for (UsedForFront usedForFront : itemListConstraint) {
                    itemInnerPanel.add(new JLabel(usedForFront.getModel()));
                    itemInnerPanel.add(new JLabel(usedForFront.getCost() + " руб."));
                }
            } catch (InstantiationException e) {
                throw new RuntimeException(e);
            }
        };

        Listener paginationListener = () -> {
            int itemCount = em.getPageCount(itemConstraint);
            int paginationCount = itemCount / 10;

            if (itemCount % 10 > 0) {
                paginationCount++;
            }

            if (paginationCount < jButtonList.size()) {
                int j = jButtonList.size();

                while (j > paginationCount) {
                    JButton pgButton = jButtonList.get(--j);
                    paginationPanel.remove(pgButton);
                    jButtonList.remove(pgButton);
                }

                offset = paginationCount;
            } else if (paginationCount > jButtonList.size()) {
                int j = jButtonList.size();

                while (paginationCount > j) {
                    JButton pgButton = new JButton("" + ++j);
                    jButtonList.add(pgButton);
                    paginationPanel.add(pgButton);
                }

                for (JButton pgButton : jButtonList) {
                    pgButton.addActionListener(ActionListener -> {
                        try {
                            offset = Integer.valueOf(pgButton.getText());
                            startPageListener.update();
                            itemInnerPanel.updateUI();
                        } catch (SQLException | NoSuchFieldException | IllegalAccessException ex) {
                            throw new RuntimeException(ex);
                        }
                    });
                }
            }

            paginationPanel.updateUI();
        };

        Listener applyButtonListener = () -> {
            for (String name : fieldNames) {
                try {
                    Field field = filterConstraint.getClass().getDeclaredField(name);
                    field.setAccessible(true);

                    Class valueClass;

                    if (field.toString().contains("Integer")) {
                        valueClass = Integer.class;
                    } else if (field.toString().contains("Double")) {
                        valueClass = Double.class;
                    } else {
                        valueClass = String.class;
                    }

                    Field itemField = itemConstraint.getClass().getDeclaredField(name);
                    itemField.setAccessible(true);
                    itemField.set(itemConstraint, valueClass.cast(field.get(filterConstraint)));
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }

            try {
                paginationListener.update();
            } catch (SQLException | NoSuchFieldException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }

            if (jButtonList.size() > 0) {
                offset = 1;
                startPageListener.update();
            } else {
                itemInnerPanel.removeAll();
            }

            itemInnerPanel.updateUI();
        };

        applyButton.addActionListener(ActionListener -> {
            try {
                applyButtonListener.update();
                applyButton.setEnabled(false);
            } catch (SQLException | IllegalAccessException | NoSuchFieldException e) {
                throw new RuntimeException(e);
            }
        });

        Listener cancelButtonListener = () -> {
            try {
                for (String fieldName : fieldNames) {
                    Field field = filterConstraint.getClass().getDeclaredField(fieldName);
                    field.setAccessible(true);
                    field.set(filterConstraint, null);
                }

                filterListener.update();
            } catch (NoSuchFieldException | IllegalAccessException | SQLException e) {
                throw new RuntimeException(e);
            }
        };

        cancelButton.addActionListener(ActionListener -> {
            try {
                cancelButtonListener.update();
                cancelButton.setEnabled(false);

                applyButton.setEnabled(checkApplyButton());
            } catch (SQLException | NoSuchFieldException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        });

        sortBox.addItemListener(ItemListener -> {
            if (ItemListener.getStateChange() == java.awt.event.ItemEvent.SELECTED) {
                sortIdx = sortBox.getSelectedIndex();

                if (offset > 0) {
                    try {
                        startPageListener.update();
                    } catch (SQLException | NoSuchFieldException | IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    itemInnerPanel.removeAll();
                }

                itemInnerPanel.updateUI();
            }
        });

        JPanel mainPanel = new JPanel();
        mainPanel.setVisible(true);
        mainPanel.setBorder(new TitledBorder("Товары"));
        mainPanel.setLayout(new BorderLayout());

        JComboBox itemSwitch = new JComboBox();
        JPanel switchItemPanel = new JPanel();

        List<UsedForFront> items = em.getItemEntities();

        switchItemPanel.setBorder(new TitledBorder("Тип товара"));
        switchItemPanel.setLayout(new GridLayout(1, 2, 1, 1));
        switchItemPanel.add(new JLabel("Выберите тип товара"));
        switchItemPanel.add(itemSwitch);

        Listener filterSwitchListener = () -> {
            for (int i = 0; i < filterConstraint.getAttributes().size(); i++) {
                final int idx = i;
                JComboBox comboBox = comboBoxes.get(idx);

                comboBox.addItemListener(ItemListener -> {
                    try {
                        if (ItemListener.getStateChange() == java.awt.event.ItemEvent.SELECTED) {
                            Field declaredField = filterConstraint.getClass().getDeclaredField(fieldNames.get(idx));
                            declaredField.setAccessible(true);
                            String itemName;

                            if (Configs.EMPTY_FILTER_FIELD.equals(comboBox.getSelectedItem().toString().split(",")[0])) {
                                itemName = null;
                            } else {
                                itemName = comboBox.getSelectedItem().toString().split(",")[0];
                            }

                            if (itemName == null) {
                                declaredField.set(filterConstraint, null);
                            } else {
                                if (declaredField.toString().contains("Integer")) {
                                    declaredField.set(filterConstraint, Integer.valueOf(itemName));
                                } else if (declaredField.toString().contains("Double")) {
                                    declaredField.set(filterConstraint, Double.valueOf(itemName));
                                } else {
                                    declaredField.set(filterConstraint, itemName);
                                }
                            }

                            cancelButton.setEnabled(!checkCancelButton());

                            applyButton.setEnabled(checkApplyButton());

                            filterListener.update();
                        }
                    } catch (NoSuchFieldException | IllegalAccessException | SQLException e) {
                        throw new RuntimeException(e);
                    }
                });
            }
        };

        itemSwitch.addItemListener(ItemListener -> {
            if (ItemListener.getStateChange() == java.awt.event.ItemEvent.SELECTED) {
                try {
                    filterPanel.removeAll();
                    comboBoxes.clear();

                    int idx = itemSwitch.getSelectedIndex();

                    filterConstraint = items.get(idx).getClass().newInstance();
                    itemConstraint = filterConstraint.getClass().newInstance();
                    fieldNames.clear();

                    for (Field field : filterConstraint.getClass().getDeclaredFields()) {
                        String[] names = field.toString().split("\\.");
                        String name = names[names.length - 1];

                        fieldNames.add(name);
                    }

                    filterPanel.setLayout(new GridLayout(items.get(idx).getAttributes().size() + 1, 2, 1, 1));

                    for (String attribute : items.get(idx).getAttributes()) {
                        JComboBox comboBox = new JComboBox();
                        comboBoxes.add(comboBox);
                        filterPanel.add(new JLabel(attribute));
                        filterPanel.add(comboBox);
                    }

                    filterSwitchListener.update();

                    filterPanel.add(applyButton);
                    filterPanel.add(cancelButton);

                    applyButton.setEnabled(false);
                    cancelButton.setEnabled(false);

                    applyButtonListener.update();
                    cancelButtonListener.update();
                    sortBox.setSelectedIndex(0);
                } catch (InstantiationException | IllegalAccessException | SQLException | NoSuchFieldException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        for (UsedForFront item : items) {
            itemSwitch.addItem(item.getTitle());
        }

        mainPanel.add(filterPanel, BorderLayout.WEST);
        mainPanel.add(itemPanel, BorderLayout.CENTER);
        mainPanel.add(switchItemPanel, BorderLayout.NORTH);

        JFrame frame = new JFrame("Пользователь");
        frame.setSize(1150, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.add(mainPanel);

        Listeners.addListener(() -> {
            try {
                filterListener.update();
                paginationListener.update();

                if (jButtonList.size() > 0) {
                    startPageListener.update();
                } else {
                    itemInnerPanel.removeAll();
                }

                itemInnerPanel.updateUI();
            } catch (SQLException | NoSuchFieldException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private boolean checkCancelButton() throws IllegalAccessException {
        for (Field field : filterConstraint.getClass().getDeclaredFields()) {
            field.setAccessible(true);

            if (field.get(filterConstraint) != null) {
                return false;
            }
        }

        return true;
    }

    private boolean checkApplyButton() throws IllegalAccessException {
        for (Field field : filterConstraint.getClass().getDeclaredFields()) {
            field.setAccessible(true);

            if (!Objects.equals(field.get(filterConstraint), field.get(itemConstraint))) {
                return true;
            }
        }

        return false;
    }
}
