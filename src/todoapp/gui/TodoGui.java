package todoapp.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import todoapp.task.Task;
import todoapp.task.TaskManager;
import todoapp.utils.XmlSavings;

public class TodoGui {

    private TaskManager taskManager;
    private DefaultListModel<Task> listModel;
    private JList<Task> taskList;

    public TodoGui() {
        taskManager = new TaskManager();
        listModel = new DefaultListModel<>();
        createAndShowGUI();
    }

    private void createAndShowGUI() {
        JFrame frame = new JFrame("Yapılacaklar Listesi");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 600);

        Container container = frame.getContentPane();
        container.setLayout(new BorderLayout());

        taskList = new JList<>(listModel);
        taskList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        taskList.setBackground(Color.BLACK);
        taskList.setForeground(Color.YELLOW);
        container.add(new JScrollPane(taskList), BorderLayout.CENTER);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2));
        panel.setBackground(Color.BLACK);
        panel.setVisible(false);

        JTextField taskNameField = new JTextField();
        taskNameField.setBackground(Color.DARK_GRAY);
        taskNameField.setForeground(Color.YELLOW);

        JTextField descriptionField = new JTextField();
        descriptionField.setBackground(Color.DARK_GRAY);
        descriptionField.setForeground(Color.YELLOW);

        JTextField dateField = new JTextField();
        dateField.setBackground(Color.DARK_GRAY);
        dateField.setForeground(Color.YELLOW);

        JLabel labelTaskName = new JLabel("Görev Adı:");
        labelTaskName.setForeground(Color.YELLOW);

        JLabel labelDescription = new JLabel("Görev Açıklaması:");
        labelDescription.setForeground(Color.YELLOW);

        JLabel labelDate = new JLabel("Tarih Limiti (yyyy-MM-dd):");
        labelDate.setForeground(Color.YELLOW);

        JButton saveButton = new JButton("Görev Ekle");
        saveButton.setBackground(Color.DARK_GRAY);
        saveButton.setForeground(Color.YELLOW);

        panel.add(labelTaskName);
        panel.add(taskNameField);
        panel.add(labelDescription);
        panel.add(descriptionField);
        panel.add(labelDate);
        panel.add(dateField);
        panel.add(new JLabel()); // Empty cell
        panel.add(saveButton);

        container.add(panel, BorderLayout.NORTH);

        JButton addButton = new JButton("Görev Ekle");
        addButton.setBackground(Color.DARK_GRAY);
        addButton.setForeground(Color.YELLOW);

        JButton deleteButton = new JButton("Sil");
        deleteButton.setBackground(Color.DARK_GRAY);
        deleteButton.setForeground(Color.YELLOW);

        JButton clearButton = new JButton("Temizle");
        clearButton.setBackground(Color.DARK_GRAY);
        clearButton.setForeground(Color.YELLOW);

        JButton completeButton = new JButton("Tamamla");
        completeButton.setBackground(Color.DARK_GRAY);
        completeButton.setForeground(Color.YELLOW);

        JButton loadButton = new JButton("Yükle");
        loadButton.setBackground(Color.DARK_GRAY);
        loadButton.setForeground(Color.YELLOW);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 5));
        buttonPanel.setBackground(Color.BLACK);
        buttonPanel.add(deleteButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(completeButton);
        buttonPanel.add(addButton);
        buttonPanel.add(loadButton);

        container.add(buttonPanel, BorderLayout.SOUTH);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panel.setVisible(true);
            }
        });

        File file = new File("tasks.xml");
        List<Task> loadedTasks = XmlSavings.loadTasks(file);
        for (Task task : loadedTasks) {
            listModel.addElement(task);
        }
        
        taskList.setModel(listModel);
        
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String taskName = taskNameField.getText();
                String description = descriptionField.getText();
                String dateString = dateField.getText();

                try {
                    Date date = new SimpleDateFormat("yyyy-MM-dd").parse(dateString);
                    if (!taskName.isEmpty() && !description.isEmpty()) {
                        Task task = Task.buildTask(listModel.getSize() + 1, taskName, description, date, false);
                        taskManager.addTask(task);
                        listModel.addElement(task);
                        taskNameField.setText("");
                        descriptionField.setText("");
                        dateField.setText("");
                        panel.setVisible(false);
                        saveTasksToXml(); 
                    } else {
                        JOptionPane.showMessageDialog(frame, "Görev adı ve açıklaması boş bırakılamaz.");
                    }
                } catch (ParseException ex) {
                    JOptionPane.showMessageDialog(frame, "Tarih yanlış girildi. Lütfen Yıl(xxxx)-ay(xx)-gün(xx). şeklinde girin");
                }
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = taskList.getSelectedIndex();
                if (selectedIndex != -1) {
                    Task task = listModel.getElementAt(selectedIndex);
                    taskManager.removeTask(task);
                    listModel.remove(selectedIndex);
                    saveTasksToXml(); 
                } else {
                    JOptionPane.showMessageDialog(frame, "Önce silinecek görevi seçin");
                }
            }
        });

        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                taskManager.clearAllTasks();
                listModel.clear();
                saveTasksToXml(); // XML'e kaydet
            }
        });

        completeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = taskList.getSelectedIndex();
                if (selectedIndex != -1) {
                    Task task = listModel.getElementAt(selectedIndex);
                    task.setChecked(true);
                    taskManager.updateTask(task);
                    taskList.repaint();
                    saveTasksToXml(); 
                } else {
                    JOptionPane.showMessageDialog(frame, "Önce tamamlanacak ");
                }
            }
        });

        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	 String filePath = "tasks.xml";
                 File fileToLoad = new File(filePath);
                 loadTasksFromXml(fileToLoad);
                    loadTasksFromXml(fileToLoad);
                }
            
        });

        loadTasksFromXml(new File("tasks.xml")); 
        frame.setVisible(true);
    }

    private void saveTasksToXml() {
        List<Task> tasks = new ArrayList<>();
        for (int i = 0; i < listModel.getSize(); i++) {
            tasks.add(listModel.getElementAt(i));
        }
        XmlSavings.saveTasks(tasks);
    }
    
    private void loadTasksFromXml(File file) {
        List<Task> tasks = XmlSavings.loadTasks(file);
        listModel.clear();
        for (Task task : tasks) {
            listModel.addElement(task);
        }
    }
}