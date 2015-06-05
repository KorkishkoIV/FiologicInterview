import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * Created by ivankorksiko on 03/06/15.
 */

enum State{
    STARTED,
    FILE_FOUND,
    FILE_BEGIN_COMPUTING,
    FILE_COMPUTED
}

public class MainWindow {
    private JPanel panel1;
    private JButton openButton;
    private JTextArea textArea1;
    private JButton cleanButton;
    private JButton computeButton;
    private JTable answersTable;
    private JComboBox categorySelection;
    private JTextField inputFileNameField;
    private JTable ageStatTable;
    private JTextField maleCountField;
    private JTextField femaleCountField;
    private JScrollPane scrollPane1;
    private JTable professionalTable;
    private JFileChooser fileChooser;
    private File computingFile;
    private Object[] tableData;
    private AbstractTableModel tableModel;
    private AbstractTableModel ageTableModel;
    private AbstractTableModel professionTableModel;
    private State state;
    private InterviewResult interviewResult;

    private static final String STRING_FILE_NOT_FOUND = "Файл не найден";

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }

    public void addLine(String line){
        textArea1.append(line + "\n");
    }

    public void addLine(){
        addLine("");
    }


    public void complete(){
        setState(State.FILE_COMPUTED);
        if (interviewResult != null){
            tableData = interviewResult.getAnswersFromCategory((String)categorySelection.getSelectedItem());
            tableModel.fireTableDataChanged();
            ageTableModel.fireTableDataChanged();
            professionTableModel.fireTableDataChanged();
            femaleCountField.setText(Integer.toString(interviewResult.getFemaleParticipants()));
            maleCountField.setText(Integer.toString(interviewResult.getMaleParticipants()));
        }
    }





    private static void createAndShowGUI() {
        final JFrame frame = new JFrame("MainWindow");
        final MainWindow window = new MainWindow();
        window.setState(State.STARTED);
        frame.setContentPane(window.panel1);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(640, 480));
        frame.pack();
        frame.setVisible(true);



        window.openButton.setText("Открыть");
        window.openButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int returnVal = window.fileChooser.showOpenDialog(frame);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    window.computingFile = window.fileChooser.getSelectedFile();
                    window.inputFileNameField.setText(window.computingFile.getAbsolutePath());
                    System.out.println("Selected file:" + window.computingFile.getAbsolutePath());
                    window.setState(State.FILE_FOUND);

                } else {
                    window.inputFileNameField.setText("Файл не выбран");
                    window.setState(State.STARTED);
                }
            }
        });

        window.inputFileNameField.setText("Файл не выбран");

        window.cleanButton.setText("Сбросить");
        window.cleanButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                window.textArea1.setText("");
                window.inputFileNameField.setText("Файл не выбран");
                window.setState(State.STARTED);
                window.categorySelection.setSelectedIndex(0);
                window.tableData = null;
                window.interviewResult = null;
                window.tableModel.fireTableDataChanged();
                window.ageTableModel.fireTableDataChanged();
                window.professionTableModel.fireTableDataChanged();
                window.femaleCountField.setText("");
                window.maleCountField.setText("");
            }
        });
        window.computeButton.setText("Обработать");
        window.computeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                window.setState(State.FILE_BEGIN_COMPUTING);
                Thread computingThread = new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        Main.compute(window.computingFile, window);
                    }
                };
                computingThread.start();
            }
        });



        window.fileChooser = new JFileChooser("/Users/ivankorksiko/Downloads/");
        window.fileChooser.setAcceptAllFileFilterUsed(false);
        window.fileChooser.setFileHidingEnabled(true);
        window.fileChooser.setFileFilter(new FileFilter() {
            @Override
            public boolean accept(File f) {
                if (f.isDirectory()) {
                    return true;
                }

                String fileExtension = null;
                String fileName = f.getName();
                int index = fileName.lastIndexOf('.');
                if (index > 0 && index < fileName.length() - 1) {
                    fileExtension = fileName.substring(index + 1).toLowerCase();
                }
                if (fileExtension == null) {
                    return false;
                }
                return fileExtension.equals("csv");
            }

            @Override
            public String getDescription() {
                return "CSV files";
            }
        });


    }

    public MainWindow(){

    }

    private void createUIComponents() {
        tableModel = new AbstractTableModel() {
            public int getRowCount() {
                if (tableData == null){
                    return 0;
                }
                return tableData.length;
            }

            public int getColumnCount() {
                return 2;
            }

            public Object getValueAt(int rowIndex, int columnIndex) {
                if (tableData != null){
                    Answer answer = (Answer)tableData[rowIndex];
                    if (columnIndex == 0){
                        return answer.answer;
                    }else{
                        return answer.count;
                    }
                }
                return null;
            }

            @Override
            public String getColumnName(int column) {
                if (column == 0){
                    return "Название";
                }else{
                    return "Количество";
                }
            }
        };
        answersTable = new JTable(tableModel);

        categorySelection = new JComboBox(Main.categories);
        categorySelection.setEditable(false);
        categorySelection.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (interviewResult != null) {
                    String category = (String) categorySelection.getSelectedItem();
                    tableData = interviewResult.getAnswersFromCategory(category);
                    tableModel.fireTableDataChanged();
                }
            }
        });

        categorySelection.setEnabled(false);


        professionTableModel = new AbstractTableModel() {
            public int getRowCount() {
                if (interviewResult != null){
                    return interviewResult.getProfessionalism().length;
                }
                return 0;
            }

            public int getColumnCount() {
                return 2;
            }

            public Object getValueAt(int rowIndex, int columnIndex) {
                if (interviewResult != null){
                    ProfessionRow profession = interviewResult.getProfessionalism()[rowIndex];
                    if (columnIndex == 0){
                        return profession.profession;
                    }else{
                        return profession.count;
                    }
                }
                return null;
            }

            @Override
            public String getColumnName(int column) {
                if (column == 0){
                    return "Род деятельности";
                }else{
                    return "Количество";
                }
            }
        };
        professionalTable = new JTable(professionTableModel);


         ageTableModel = new AbstractTableModel() {
             public int getRowCount() {
                 if (interviewResult != null){
                     return interviewResult.getAging().length;
                 }
                 return 0;
             }

             public int getColumnCount() {
                 return 2;
             }

             public Object getValueAt(int rowIndex, int columnIndex) {
                 if (interviewResult != null){
                     AgeRow ageRow = interviewResult.getAging()[rowIndex];
                     if (columnIndex == 0){
                         return ageRow.age;
                     }else{
                         return ageRow.count;
                     }
                 }
                 return null;
             }

             @Override
             public String getColumnName(int column) {
                 if (column == 0){
                     return "Возраст";
                 }else{
                     return "Количество";
                 }
             }
         };

        ageStatTable = new JTable(ageTableModel);
    }

    public void setState(State state){
        this.state = state;
        switch (state){
            case STARTED:
                openButton.setEnabled(true);
                cleanButton.setEnabled(false);
                computeButton.setEnabled(false);
                categorySelection.setEnabled(false);
                inputFileNameField.setText(STRING_FILE_NOT_FOUND);
                break;
            case FILE_FOUND:
                openButton.setEnabled(true);
                cleanButton.setEnabled(false);
                computeButton.setEnabled(true);
                categorySelection.setEnabled(false);
                break;
            case FILE_BEGIN_COMPUTING:
                openButton.setEnabled(false);
                cleanButton.setEnabled(false);
                computeButton.setEnabled(false);
                categorySelection.setEnabled(false);
                break;
            case FILE_COMPUTED:
                openButton.setEnabled(true);
                cleanButton.setEnabled(true);
                computeButton.setEnabled(false);
                categorySelection.setEnabled(true);
                break;
            default:
                break;

        }
    }

    public void setInterviewResult(InterviewResult interviewResult) {
        this.interviewResult = interviewResult;
    }
}
