package notepad.parkar;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.net.FileNameMap;
import java.io.*;

public class notepad extends JFrame implements ActionListener {

    JTextArea area;
    String text;
    notepad (){
        setTitle("notepad");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        ImageIcon notepadIcon = new ImageIcon(ClassLoader.getSystemResource("notepad/parkar/icon/download.jpg"));
        Image icon = notepadIcon.getImage();
        setIconImage(icon);

        JMenuBar menubar = new JMenuBar();
        menubar.setBackground(Color.LIGHT_GRAY);
        JMenu file = new JMenu("file");
        file.setFont(new Font("ARIAL", Font.BOLD, 20));

        JMenuItem newdoc = new JMenuItem("New");
        newdoc.addActionListener(this);
        newdoc.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
        JMenuItem open = new JMenuItem("open");
        open.addActionListener(this);
        open.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
        JMenuItem save = new JMenuItem("save");
        save.addActionListener(this);
        save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
        JMenuItem print = new JMenuItem("print");
        print.addActionListener(this);
        print.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.CTRL_MASK));
        JMenuItem exit = new JMenuItem("exit");
        exit.addActionListener(this);
        exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, ActionEvent.CTRL_MASK));
        file.add(newdoc);
        file.add(open);
        file.add(save);
        file.add(print);
        file.add(exit);
        menubar.add(file);

        JMenu edit = new JMenu("Edit");
        edit.setFont(new Font("ARIAL", Font.BOLD, 20));

        JMenuItem copy = new JMenuItem("copy");
        copy.addActionListener(this);
        copy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK));
        JMenuItem cut = new JMenuItem("cut");
        cut.addActionListener(this);
        cut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));
        JMenuItem paste = new JMenuItem("paste");
        paste.addActionListener(this);
        paste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, ActionEvent.CTRL_MASK));
        JMenuItem sall = new JMenuItem("select all");
        sall.addActionListener(this);
        sall.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.CTRL_MASK));
        edit.add(copy);
        edit.add(cut);
        edit.add(paste);
        edit.add(sall);
        menubar.add(edit);

        setJMenuBar(menubar);

         area = new JTextArea();
        area.setFont(new Font("ARIAL", Font.PLAIN, 18));
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        JScrollPane pane = new JScrollPane(area);
        pane.setBorder(BorderFactory.createEmptyBorder());
        add(pane);

        setVisible(true);
    }
    @Override
    public void actionPerformed(ActionEvent ae){
        if(ae.getActionCommand().equals("new")) {
            area.setText("");
        } else if (ae.getActionCommand().equals("open")) {
            JFileChooser chooser = new JFileChooser();
            chooser.setAcceptAllFileFilterUsed(false);
            FileNameExtensionFilter restrict = new FileNameExtensionFilter("only . txt files", "txt");
            chooser.addChoosableFileFilter(restrict);
            chooser.showOpenDialog(this);
            int action = chooser.showOpenDialog(this);
            if(action != JFileChooser.APPROVE_OPTION){
                return;
            }
            File file = chooser.getSelectedFile();
            try{
                BufferedReader reader = new BufferedReader(new FileReader(file));
                area.read(reader,null);
            }catch (Exception e){
                e.printStackTrace();
            }
        } else if (ae.getActionCommand().equals("save")) {
            JFileChooser saveas = new JFileChooser();
            saveas.setApproveButtonText("save");
            int action = saveas.showOpenDialog(this);
            if(action != JFileChooser.APPROVE_OPTION){
                return;
            }
            File filename = new File(saveas.getSelectedFile() + "txt");
            BufferedWriter outFile = null;
            try {
                outFile = new BufferedWriter(new FileWriter(filename));
                area.write(outFile);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        else if (ae.getActionCommand().equals("print")){
            try {
                area.print();

            }catch (Exception e){
                e.printStackTrace();
            }
        }
        else if (ae. getActionCommand().equals("exit")){
            System.exit(0);
        }
        else if (ae.getActionCommand().equals("copy")){
            text = area.getSelectedText();
        }
        else if (ae.getActionCommand().equals("paste")){
            area.insert(text,area.getCaretPosition());
        } else if (ae.getActionCommand().equals("cut")){
            text = area.getSelectedText();
            area.replaceRange("",area.getSelectionStart(), area.getSelectionEnd());
        }
        else if (ae.getActionCommand().equals("sall")){
            area.selectAll();
        }

    }
    public static void main(String[] args) {
        new notepad();
    }
}
