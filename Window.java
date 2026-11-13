import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Window {
    private final String[] menuOptions = {"Random", "Glider", "Blinker", "Pulsator", "f-Pentomino", "U", "Infinite"};
    private int width;
    private int height;

    private int boxSize;

    private final JFrame frame;
    private final JPanel panel;

    private final GameOfLife game;

    private Thread sim;

    private Timer timer;

    private boolean paintMode = false;

    public Window(int width, int height, GameOfLife game){
        this.game = game;
        this.width = width;
        this.height = height;
        //this.boxSize = width/game.getSIZE();
        //this.boxSize = panel.getWidth()/game.getSIZE();

        frame = new JFrame("Game Of Life");
        panel = new JPanel(){
            @Override
            protected void paintComponent(Graphics g){
                super.paintComponent(g);
                updateBoxSize();

                for(int i = 0; i < game.getWorld().length; i++){
                    for(int j = 0; j < game.getWorld()[i].length; j++){
                        if(game.getWorld()[i][j]){
                            g.setColor(Color.BLACK);
                            g.fillRect(i*boxSize, j*boxSize, boxSize, boxSize);
                        }else {
                            //g.setColor(new Color(0,0,0, 1));
                            g.drawRect(i * boxSize, j * boxSize, boxSize, boxSize);
                        }
                    }
                }
            }
        };

        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int x = (int)e.getX() / boxSize;
                int y = (int)e.getY() / boxSize;

                paintMode = !game.getWorld()[x][y];
                game.getWorld()[x][y] = paintMode;
                update();
            }
        });

        panel.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                int x = (int)e.getX() / boxSize;
                int y = (int)e.getY() / boxSize;

                game.getWorld()[x][y] = paintMode;
                update();
            }
        });

        frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e){
                updateBoxSize();
                panel.repaint();
            }
        });

        JComboBox menu = new JComboBox(menuOptions);
        menu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.clear();
                JComboBox cb = (JComboBox)e.getSource();
                String petName = (String)cb.getSelectedItem();
                switch (petName){
                    case ("Random"):
                        game.fillRandom();
                        break;
                    case ("Glider"):
                        game.centered(Preset.GLIDE.pattern);
                        break;
                    case ("Blinker"):
                        game.centered(Preset.BLINKER.pattern);
                        break;
                    case ("Pulsator"):
                        game.centered(Preset.PULSATOR.pattern);
                        break;
                    case ("f-Pentomino"):
                        game.centered(Preset.FPENTO.pattern);
                        break;
                    case ("U"):
                        game.centered(Preset.U.pattern);
                        break;
                    case ("Infinite"):
                        game.centered(Preset.INFINITE.pattern);
                        break;
                }
                update();
            }
        });

        JButton start = new JButton("Start");
        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sim = new Thread(() -> sim());
                sim.start();
            }
        });

        JButton clear = new JButton("Clear");
        clear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(timer.isRunning()) timer.stop();
                game.clear();
                panel.repaint();
            }
        });


        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(start);
        buttonPanel.add(clear);
        buttonPanel.add(menu);

        frame.add(new JPanel(), BorderLayout.WEST);
        frame.add(new JPanel(), BorderLayout.EAST);
        frame.add(new JPanel(), BorderLayout.NORTH);

        frame.add(panel, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);
        frame.setSize(width ,height);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public void setWindowSize(int width, int height){
        this.width = width;
        this.height = height;
        frame.setSize(width, height);
    }

    public void update(){
        panel.repaint();
    }

    public void updateBoxSize(){
        //boxSize = (int)w/game.getSIZE();
        boxSize = panel.getWidth()/game.getSIZE();
    }

    public void sim(){
        timer = new Timer(50, e -> {
            game.step();
            update();
        });
        timer.start();
    }
}
