

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class Maco {

    Color darkBlue = new Color(27, 40, 79);
    Font heading = new Font("Arial Rounded MT Bold", Font.BOLD, 40);
    Font subHeading = new Font("Arial Rounded MT Bold", Font.BOLD, 28);
    Font body = new Font("Arial Rounded MT Bold", Font.BOLD, 20);
    Font subBody = new Font("Arial Rounded MT Bold", Font.BOLD, 16);

    Boolean animationDone = false;
    int choice = 1;
    

    String[] wakeUp = {
        "offScreen", "offScreen", "offScreen", "offScreen", "offScreen", "offScreen", "onScreen", "onScreen", "logoScreen", "logoScreen", "logoScreen", "KHScreen", "KHScreen", "KHScreen", "onScreen", "onScreen", "default","closeEye", "yawn1", "yawn2", "yawn3", "yawn1", "closeEye", "normal"
    };
    int[] wakeUpBeep = {
         0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0
    };

    String[] yawn = {
        "default", "default", "default","closeEye", "yawn1", "yawn2", "yawn1", "closeEye", "default"
    };

    String[] happy = {
        "normal", "normal", "happy1", "happy2", "happy1", "happy2", "happy1", "normal"
    };
    int[] happyBeep = {
        0, 0, 0, 1, 0, 1, 0, 0
    };
    String[] game = {
        "game1", "game2", "game3", "game4", "game5", "game6", "game7", "game8" , "game9" , "game10" , "game11", "default", "normal"
    };
    int[] gameBeep = {
        0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0
    };

    public static void playSound(String path) {
        String fileName = new String("audio/" + path + ".wav");
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(fileName));
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    JPanel backGround;
    JPanel facePanel = new JPanel();

    
    JLabel face = new JLabel();
    JLabel button = new JLabel();
    
     JPanel makeBackGround(String filename){
        ImageIcon bgImage = new ImageIcon(filename);
        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(bgImage.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };
        backgroundPanel.setLayout(null);
        return backgroundPanel;
    }

    void animateFace(String[] arrayString, int[] beep){
        animationDone = false;
        Timer timer = new Timer();
        final int[] index = {0};
        TimerTask change = new TimerTask() {
            
            @Override
            public void run() {
                if(beep[index[0]] == 1){
                    playSound("littleBeep");
                }
                changeMacoFace(arrayString[index[0]]);
                index[0]++;
                
                if(index[0] >= arrayString.length){
                    
                    animationDone = true;
                    timer.cancel();
                }
                
            }
        };
        timer.scheduleAtFixedRate(change, 0, 500);
    }
  
    void changeMacoFace(String filename){
        ImageIcon orig = new ImageIcon("assets/" + filename + ".png");
        Image scaledimage = orig.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
        ImageIcon finalIcon = new ImageIcon(scaledimage);
        face.setIcon(finalIcon);
        facePanel.repaint();
    }

    void changeButton(int choice){
        ImageIcon orig = new ImageIcon("assets/buttons/"+ choice + ".png");
        Image scaledimage = orig.getImage().getScaledInstance(200, 48, Image.SCALE_SMOOTH);
        ImageIcon finalIcon = new ImageIcon(scaledimage);
        button.setIcon(finalIcon);
        facePanel.repaint();
    }

    void makeApp(){
        JFrame appFrame = new JFrame("Hey Mac'O");
        appFrame.setLayout(null);
        backGround = makeBackGround("assets/backDrop.png");
        backGround.setLayout(new BorderLayout());

        

        JButton leftButton = new JButton();
        leftButton.setBounds(86, 262, 30, 30);
    
        leftButton.addActionListener((e) -> {
         
                choice--;
                if(choice < 1){
                    choice = 1;
                }
                changeButton(choice);
         
        });
        leftButton.setOpaque(false);
        leftButton.setBorderPainted(false);
        facePanel.add(leftButton);

        JButton rightButton = new JButton();
        rightButton.setBounds(183, 262, 30, 30);
  
        rightButton.addActionListener((e) -> {
        
                choice++;
                if(choice > 4){
                    choice = 4;
                }
                changeButton(choice); 
            
        });
        rightButton.setOpaque(false);
        rightButton.setBorderPainted(false);
        facePanel.add(rightButton);

        JButton enterButton = new JButton();
        enterButton.setBounds(134, 262, 30, 30);
  
        enterButton.addActionListener((e) -> {
            if(animationDone){
                switch (choice) {
                    case 1:
                        animateFace(happy, happyBeep);
                        animationDone = false;
                        break;
                    case 2:
                        animateFace(game, gameBeep);
                        animationDone = false;
                        break;
                    case 3:
                        animateFace(happy, happyBeep);
                        animationDone = false;
                        break;
                    case 4:
                        animateFace(game, gameBeep);
                        animationDone = false;
                        break;
                
                    default:
                        break;
                }
            }
        });
        enterButton.setOpaque(false);
        enterButton.setBorderPainted(false);
        facePanel.add(enterButton);



        appFrame.setLayout(new BorderLayout());
        appFrame.setSize(300, 330);
        appFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        appFrame.setResizable(false);
        appFrame.getContentPane().setBackground(Color.black);
        appFrame.setLocationRelativeTo(null);

        button.setBounds(50, 200, 215, 60);
        facePanel.add(button);

        face.setHorizontalAlignment(JLabel.CENTER);
        facePanel.setLayout(null);
        facePanel.setPreferredSize(new Dimension(300, 300));
        facePanel.setOpaque(false);
        facePanel.add(face);
        face.setBounds(125, 104, 80, 80);
     
        facePanel.setBackground(null);

        backGround.add(facePanel, BorderLayout.CENTER);
        appFrame.setContentPane(backGround);
        appFrame.setVisible(true);
        animateFace(wakeUp, wakeUpBeep);
        changeButton(choice);
    
    }

    public static void main(String[] args) {
        Maco maco = new Maco();
        maco.makeApp();
    }
}
