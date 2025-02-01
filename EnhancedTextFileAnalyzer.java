package guiprojects.textcounter;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.*;

public class EnhancedTextFileAnalyzer extends JFrame {
    private JButton openButton;
    private JLabel fileLabel, lineLabel, wordLabel, charLabel;
    private JTextArea filePreview;

    public EnhancedTextFileAnalyzer() {
        setTitle("Text File Analyzer");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);  // Center the window
        setLayout(new BorderLayout(10, 10));

        // Create panel for file selection
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout(10, 10));
        openButton = new JButton("📂 Open File");
        openButton.setFont(new Font("Arial", Font.BOLD, 14));
        openButton.addActionListener(this::chooseAndAnalyzeFile);
        topPanel.add(openButton, BorderLayout.WEST);

        fileLabel = new JLabel("No file selected.");
        topPanel.add(fileLabel, BorderLayout.CENTER);

        add(topPanel, BorderLayout.NORTH);

        // File preview area
        filePreview = new JTextArea(5, 30);
        filePreview.setEditable(false);
        filePreview.setFont(new Font("Consolas", Font.PLAIN, 14));
        filePreview.setBorder(new EmptyBorder(5, 5, 5, 5));
        JScrollPane scrollPane = new JScrollPane(filePreview);
        add(scrollPane, BorderLayout.CENTER);

        // Panel for statistics
        JPanel statsPanel = new JPanel();
        statsPanel.setLayout(new GridLayout(3, 1, 10, 10));
        statsPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        lineLabel = new JLabel("📄 Lines: 0");
        wordLabel = new JLabel("🔤 Words: 0");
        charLabel = new JLabel("✏️ Characters: 0");

        lineLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        wordLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        charLabel.setFont(new Font("Arial", Font.PLAIN, 14));

        statsPanel.add(lineLabel);
        statsPanel.add(wordLabel);
        statsPanel.add(charLabel);
        add(statsPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void chooseAndAnalyzeFile(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();
        int option = fileChooser.showOpenDialog(this);
        if (option == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            fileLabel.setText("📂 File: " + file.getName());
            analyzeFile(file);
        }
    }

    private void analyzeFile(File file) {
        int wordCount = 0, charCount = 0, lineCount = 0;
        StringBuilder previewText = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            int previewLines = 0;
            while ((line = reader.readLine()) != null) {
                lineCount++;
                charCount += line.length();
                wordCount += line.split("\\s+").length;

                // Store first 5 lines for preview
                if (previewLines < 5) {
                    previewText.append(line).append("\n");
                    previewLines++;
                }
            }

            lineLabel.setText("📄 Lines: " + lineCount);
            wordLabel.setText("🔤 Words: " + wordCount);
            charLabel.setText("✏️ Characters: " + charCount);
            filePreview.setText(previewText.toString());

        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error reading file!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(EnhancedTextFileAnalyzer::new);
    }
}

