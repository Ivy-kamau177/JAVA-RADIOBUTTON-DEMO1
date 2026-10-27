import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

/**
 * RadioButtonDemo
 * Five radio buttons let the user choose a pet. The matching picture
 * (drawn in code, so no image files are needed) is displayed, and a
 * message box confirms the selection.
 */
public class RadioButtonDemo extends JFrame implements ActionListener {

    private static final String[] PETS = {"Bird", "Cat", "Dog", "Rabbit", "Pig"};
    private static final int[] MNEMONICS = {
        KeyEvent.VK_B, KeyEvent.VK_C, KeyEvent.VK_D, KeyEvent.VK_R, KeyEvent.VK_P
    };

    private final JRadioButton[] buttons = new JRadioButton[PETS.length];
    private final PetPanel petPanel = new PetPanel();

    public RadioButtonDemo() {
        super("RadioButtonDemo");
        setLayout(new BorderLayout(10, 10));

        JPanel radioPanel = new JPanel(new GridLayout(PETS.length, 1));
        ButtonGroup group = new ButtonGroup(); // only one button can be selected

        for (int i = 0; i < PETS.length; i++) {
            buttons[i] = new JRadioButton(PETS[i]);
            buttons[i].setMnemonic(MNEMONICS[i]);
            buttons[i].setActionCommand(PETS[i]);
            buttons[i].addActionListener(this);
            group.add(buttons[i]);
            radioPanel.add(buttons[i]);
        }

        buttons[4].setSelected(true);   // Pig selected by default
        petPanel.setPet("Pig");

        add(radioPanel, BorderLayout.LINE_START);
        add(petPanel, BorderLayout.CENTER);
        ((JComponent) getContentPane()).setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(420, 320);
        setLocationRelativeTo(null);
    }

    // Runs every time a radio button is clicked
    @Override
    public void actionPerformed(ActionEvent e) {
        String pet = e.getActionCommand();
        petPanel.setPet(pet);
        JOptionPane.showMessageDialog(this,
                "You selected: " + pet,
                "Your Selection",
                JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new RadioButtonDemo().setVisible(true));
    }
}

/** A panel that draws a simple cartoon of the chosen pet. */
class PetPanel extends JPanel {
    private String pet = "Pig";

    void setPet(String pet) {
        this.pet = pet;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        // centre a 200x200 drawing area
        g2.translate((getWidth() - 200) / 2, (getHeight() - 200) / 2);
        g2.setStroke(new BasicStroke(2f));

        switch (pet) {
            case "Bird":   drawBird(g2);   break;
            case "Cat":    drawCat(g2);    break;
            case "Dog":    drawDog(g2);    break;
            case "Rabbit": drawRabbit(g2); break;
            default:       drawPig(g2);    break;
        }
        g2.dispose();
    }

    private void fill(Graphics2D g, Color c, Shape s) {
        g.setColor(c);
        g.fill(s);
        g.setColor(Color.DARK_GRAY);
        g.draw(s);
    }

    private void eyes(Graphics2D g, int y) {
        g.setColor(Color.BLACK);
        g.fillOval(70, y, 12, 12);
        g.fillOval(118, y, 12, 12);
    }

    private void drawPig(Graphics2D g) {
        Color pink = new Color(255, 182, 193);
        fill(g, pink, new Polygon(new int[]{45, 70, 85}, new int[]{40, 15, 55}, 3));     // ears
        fill(g, pink, new Polygon(new int[]{155, 130, 115}, new int[]{40, 15, 55}, 3));
        fill(g, pink, new java.awt.geom.Ellipse2D.Double(40, 35, 120, 130));              // head
        eyes(g, 80);
        fill(g, new Color(255, 150, 170), new java.awt.geom.Ellipse2D.Double(70, 105, 60, 40)); // snout
        g.setColor(Color.DARK_GRAY);
        g.fillOval(84, 120, 8, 12);
        g.fillOval(108, 120, 8, 12);
    }

    private void drawCat(Graphics2D g) {
        Color gray = new Color(170, 170, 180);
        fill(g, gray, new Polygon(new int[]{40, 50, 90}, new int[]{70, 10, 45}, 3));      // ears
        fill(g, gray, new Polygon(new int[]{160, 150, 110}, new int[]{70, 10, 45}, 3));
        fill(g, gray, new java.awt.geom.Ellipse2D.Double(35, 35, 130, 130));              // head
        eyes(g, 80);
        g.setColor(new Color(255, 120, 140));
        g.fillPolygon(new int[]{92, 108, 100}, new int[]{108, 108, 118}, 3);               // nose
        g.setColor(Color.DARK_GRAY);
        g.drawLine(30, 110, 85, 115); g.drawLine(30, 130, 85, 122);                        // whiskers
        g.drawLine(170, 110, 115, 115); g.drawLine(170, 130, 115, 122);
    }

    private void drawDog(Graphics2D g) {
        Color brown = new Color(160, 110, 60);
        fill(g, new Color(110, 70, 30), new java.awt.geom.Ellipse2D.Double(25, 40, 40, 90)); // floppy ears
        fill(g, new Color(110, 70, 30), new java.awt.geom.Ellipse2D.Double(135, 40, 40, 90));
        fill(g, brown, new java.awt.geom.Ellipse2D.Double(45, 30, 110, 130));               // head
        eyes(g, 75);
        fill(g, new Color(230, 200, 160), new java.awt.geom.Ellipse2D.Double(70, 100, 60, 45)); // muzzle
        g.setColor(Color.BLACK);
        g.fillOval(90, 105, 20, 14);                                                         // nose
        fill(g, new Color(255, 105, 120), new java.awt.geom.Ellipse2D.Double(92, 132, 16, 22)); // tongue
    }

    private void drawRabbit(Graphics2D g) {
        Color white = new Color(245, 245, 245);
        fill(g, white, new java.awt.geom.Ellipse2D.Double(60, 0, 28, 90));                  // ears
        fill(g, white, new java.awt.geom.Ellipse2D.Double(112, 0, 28, 90));
        fill(g, new Color(255, 190, 200), new java.awt.geom.Ellipse2D.Double(68, 12, 12, 62));
        fill(g, new Color(255, 190, 200), new java.awt.geom.Ellipse2D.Double(120, 12, 12, 62));
        fill(g, white, new java.awt.geom.Ellipse2D.Double(40, 70, 120, 100));               // head
        eyes(g, 100);
        g.setColor(new Color(255, 120, 140));
        g.fillOval(94, 125, 12, 9);                                                          // nose
        g.setColor(Color.DARK_GRAY);
        g.drawLine(100, 134, 100, 145);
        g.drawArc(88, 138, 12, 10, 180, 180);
        g.drawArc(100, 138, 12, 10, 180, 180);
    }

    private void drawBird(Graphics2D g) {
        Color yellow = new Color(255, 215, 0);
        fill(g, yellow, new java.awt.geom.Ellipse2D.Double(40, 40, 120, 120));              // body
        fill(g, new Color(240, 190, 0), new java.awt.geom.Ellipse2D.Double(40, 90, 50, 40)); // wing
        g.setColor(Color.BLACK);
        g.fillOval(110, 75, 14, 14);                                                         // eye
        fill(g, Color.ORANGE, new Polygon(new int[]{150, 190, 150}, new int[]{90, 105, 120}, 3)); // beak
        g.setColor(Color.DARK_GRAY);
        g.drawLine(85, 160, 85, 185);                                                        // legs
        g.drawLine(115, 160, 115, 185);
    }
}
