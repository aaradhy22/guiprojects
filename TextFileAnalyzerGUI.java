package guiprojects.textcounter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class TextFileAnalyzerGUI extends JFrame {
    private JButton openButton;
    private JLabel fileLabel, lineLabel, wordLabel, charLabel;

    public TextFileAnalyzerGUI() {
        setTitle("Text File Analyzer");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(5, 1, 10, 10));

        openButton = new JButton("Open File");
        fileLabel = new JLabel("No file selected.");
        lineLabel = new JLabel("Lines: 0");
        wordLabel = new JLabel("Words: 0");
        charLabel = new JLabel("Characters: 0");

        openButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                chooseAndAnalyzeFile();
            }
        });

        add(openButton);
        add(fileLabel);
        add(lineLabel);
        add(wordLabel);
        add(charLabel);

        setVisible(true);
    }

    private void chooseAndAnalyzeFile() {
        JFileChooser fileChooser = new JFileChooser();
        int option = fileChooser.showOpenDialog(this);
        if (option == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            fileLabel.setText("File: " + file.getName());
            analyzeFile(file);
        }
    }

    private void analyzeFile(File file) {
        int wordCount = 0, charCount = 0, lineCount = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lineCount++;
                charCount += line.length();
                String[] words = line.split("\\s+");
                wordCount += words.length;
            }

            lineLabel.setText("Lines: " + lineCount);
            wordLabel.setText("Words: " + wordCount);
            charLabel.setText("Characters: " + charCount);

        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error reading file!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TextFileAnalyzerGUI::new);
    }
}
