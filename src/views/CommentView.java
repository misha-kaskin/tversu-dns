package views;

import dao.CommentDao;
import handlers.CommentListeners;
import handlers.Listener;
import models.Comment;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommentView {
    private static final Integer INSET = 35;
    private static final Integer STRING_SIZE = 55;
    private static final Integer STRING_SHIFT = 5;

    private Comment currentComment;
    private boolean isAnswer = false;

    private final CommentDao commentDao = new CommentDao();

    public CommentView() throws SQLException {
    }

    public void createWindow() throws SQLException, NoSuchFieldException, IllegalAccessException {
        JFrame jFrame = new JFrame("ОТЗЫВЫ");
        jFrame.setSize(700, 900);
        jFrame.setVisible(true);
        jFrame.setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        mainPanel.setBorder(new TitledBorder("Отзывы"));

        Map<JLabel, Comment> commentMap = new HashMap<>();

        JPopupMenu menu = new JPopupMenu();
        JMenuItem answerMenu = new JMenuItem("Ответить");

        Listener lst = () -> {
            List<Comment> comments = commentDao.findComments("gpu", 1);
            mainPanel.removeAll();
            commentMap.clear();

            for (int i = 0; i < comments.size(); i++) {
                JPanel innerPanel = new JPanel();
                innerPanel.setLayout(new GridLayout(1, 1, 1, 1));
                innerPanel.setBorder(new TitledBorder(comments.get(i).getAuthor()));

                Insets insets = new Insets(0, INSET * comments.get(i).getLevel(), 0, 0);
                GridBagConstraints constraints = new GridBagConstraints();
                constraints.gridx = 0;
                constraints.gridy = i;
                constraints.insets = insets;

                String str = formatString(
                        comments.get(i).getText(),
                        STRING_SIZE - STRING_SHIFT * comments.get(i).getLevel()
                        //STRING_SIZE
                );

                JLabel jLabel = new JLabel(str);
                commentMap.put(jLabel, comments.get(i));

                innerPanel.add(jLabel);
                mainPanel.add(innerPanel, constraints);
            }

            for (JLabel jLabel : commentMap.keySet()) {
                jLabel.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        currentComment = commentMap.get(jLabel);
                        menu.show(e.getComponent(), e.getX(), e.getY());
                    }
                });
            }

            mainPanel.updateUI();
        };

        JButton cancelButton = new JButton("Отменить");
        cancelButton.setEnabled(false);

        answerMenu.addActionListener(ActionListener -> {
            int id = currentComment.getId();
            currentComment = Comment.builder().build();
            currentComment.setParent(id);
            isAnswer = true;
            cancelButton.setEnabled(true);
        });

        menu.add(answerMenu);

        CommentListeners.addListener(lst);
        lst.update();

        jFrame.add(mainPanel, BorderLayout.CENTER);

        JPanel textPanel = new JPanel();
        textPanel.setBorder(new TitledBorder("Поле ввода"));
        textPanel.setLayout(new GridBagLayout());
        JTextArea ta = new JTextArea(8, 52);
        JButton jb = new JButton("Отправить");
        textPanel.add(ta);
        textPanel.add(jb);

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 2;

        textPanel.add(cancelButton, constraints);

        cancelButton.addActionListener(ActionListener -> {
            isAnswer = false;
            cancelButton.setEnabled(false);
        });

        jb.addActionListener(ActionListener -> {
            String text = ta.getText();
            int parentId;

            if (isAnswer) {
                parentId = currentComment.getParent();
            } else {
                parentId = -1;
            }

            Comment saveComment = new Comment();
            saveComment.setAuthor("misha");
            saveComment.setText(text);
            saveComment.setParent(parentId);
            saveComment.setItemId(1);
            saveComment.setItemType("gpu");

            isAnswer = false;
            cancelButton.setEnabled(false);

            try {
                if (!text.isEmpty()) {
                    commentDao.addComment(saveComment);
                    ta.setText("");

                    for (Listener listener : CommentListeners.getListeners()) {
                        listener.update();
                    }
                }
            } catch (SQLException | NoSuchFieldException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        });

        jFrame.add(textPanel, BorderLayout.SOUTH);
        textPanel.updateUI();
    }

    private String formatString(String string, int size) {
        StringBuilder result = new StringBuilder("<html>")
                .append("<style>")
                .append("p {")
                .append("font-family: monospace;")
                .append("}")
                .append("</style><p>");
        String[] strings = string.split(" ");
        int length = 0;

        for (String str : strings) {
            length += str.length() + 1;

            if (length > size) {
                length = length - str.length() - 1;

                while (length < size) {
                    result.append("&nbsp");
                    length++;
                }

                length = str.length() + 1;

                result.append("<br>")
                        .append(str)
                        .append(" ");
            } else {
                result.append(str)
                        .append(" ");
            }
        }

        while (length < size) {
            result.append("&nbsp");
            length++;
        }

        return result.append("</p></html>").toString();
    }
}
