import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

public class SnakeTheGame extends JFrame {

    JPanel topPanel = new JPanel();
    JPanel gamePanel = new JPanel();
    JLabel info = new JLabel("Press space to start and move with arrow-keys");
    int gameRows = 12;
    int gameCols = 25;
    List<Grid> worm = new ArrayList<>();
    JLabel[][] labels = new JLabel[gameRows][gameCols];

    Grid position;
    Grid apple = new Grid(0,0);
    int snakeLength = 3;
    boolean haveEaten = false;
    char heading;
    boolean moved = true;
    int point = 1;
    int score = 0;
    JLabel showScore = new JLabel("Score: " + score);

    javax.swing.Timer timer;

    SnakeTheGame() {
        setTitle("SNAKE");
        topPanel.add(info);
        topPanel.setBackground(Color.CYAN);
        gamePanel.setLayout(new GridLayout(gameRows, gameCols));
        gamePanel.setBorder(new EtchedBorder());
        gamePanel.setBackground(new Color(173, 233, 173));
        add(topPanel,BorderLayout.NORTH);
        add(gamePanel,BorderLayout.CENTER);
        add(showScore, BorderLayout.SOUTH);

        addLabels();
        createSnake();
        writeStartPosition();
        shuffleApple();

        setLocation(500, 200);
        pack();
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        KeyAdapter myListener = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SPACE) timer.start();
                if (e.getKeyCode() == KeyEvent.VK_UP) changeDirection(KeyEvent.VK_UP);
                if (e.getKeyCode() == KeyEvent.VK_DOWN) changeDirection(KeyEvent.VK_DOWN);
                if (e.getKeyCode() == KeyEvent.VK_LEFT) changeDirection(KeyEvent.VK_LEFT);
                if (e.getKeyCode() == KeyEvent.VK_RIGHT) changeDirection(KeyEvent.VK_RIGHT);
            }
        };
        addKeyListener(myListener);

        ActionListener time = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                move();
            }
        };

        timer = new javax.swing.Timer(75, time);
    }

    public void addLabels() {
        for (int i = 0; i < gameRows; i++) {
            for (int j = 0; j < gameCols; j++) {
                labels[i][j] = new JLabel("", SwingConstants.CENTER);
//                labels[i][j].setBorder(new EtchedBorder()); // om detta används sätt fontsize till 20
                labels[i][j].setMinimumSize(new Dimension(25, 25));
                labels[i][j].setMaximumSize(new Dimension(25, 25));
                labels[i][j].setPreferredSize(new Dimension(25, 25));
                labels[i][j].setFont(new Font("Verdana", Font.BOLD, 25)); // sätt till 20 om border nyttjas
                gamePanel.add(labels[i][j]);
            }
        }
    }

    public void createSnake() {
        for (int i = 0; i < snakeLength; i++) {
            worm.add(new Grid(0, i));
            if (i == snakeLength - 1) position = new Grid(worm.get(i));
        }
    }

    public void writeStartPosition() {
        for (int i = 0; i < worm.size(); i++) {
            int row = worm.get(i).row;
            int col = worm.get(i).col;
            labels[row][col].setText("#");
        }
        heading = 'E';
    }

    public void shuffleApple() {
        labels[apple.row][apple.col].setForeground(null);
        int row;
        int col;
        Random rand = new Random();
        while (true) {
            row = rand.nextInt(gameRows);
            col = rand.nextInt(gameCols);
            if (position.row != row || position.col != col) break;
        }
        apple.setNewPos(row, col);
        labels[apple.row][apple.col].setText("@");
        labels[apple.row][apple.col].setForeground(Color.red);
    }

    public void changeDirection(int keyStroke) {
        if(moved) {
            if (keyStroke == KeyEvent.VK_UP && heading != 'S') heading = 'N';
            else if (keyStroke == KeyEvent.VK_DOWN && heading != 'N') heading = 'S';
            else if (keyStroke == KeyEvent.VK_LEFT && heading != 'E') heading = 'W';
            else if (keyStroke == KeyEvent.VK_RIGHT && heading != 'W') heading = 'E';
        }
        moved = false;
    }

    public void move() {
        if (heading == 'N') position.row--;
        if (heading == 'S') position.row++;
        if (heading == 'W') position.col--;
        if (heading == 'E') position.col++;
        moved = true;
        updateSnake();
    }

    public void updateSnake() {
        if (position.isEqualsTo(apple)) {
            haveEaten = true;
            addPoint();
            shuffleApple();
        } else haveEaten = false;

        worm.add(new Grid(position));

        if (!haveEaten) {
            labels[worm.get(0).row][worm.get(0).col].setText("");
            labels[apple.row][apple.col].setText("@");
            worm.remove(0);
        }

        try {
            if(labels[position.row][position.col].getText().equals("#")){
                JOptionPane.showMessageDialog(null,"Nu gick du visst in i dig själv.\nDin Score: "+score);
                reset();
            }
            labels[position.row][position.col].setText("#");
        } catch (IndexOutOfBoundsException e) {
            JOptionPane.showMessageDialog(null,"Nu gick du visst utanför banan.\nDin Score: "+score);
            reset();
        }
    }

    public void addPoint(){
        score += point;
        showScore.setText("Score: " + score);
    }

    public void reset(){
        for (int i = 0; i < gameRows; i++) {
            for (int j = 0; j < gameCols; j++) {
                labels[i][j].setText("");
            }
        }
        worm.clear();
        createSnake();
        writeStartPosition();
        shuffleApple();
        timer.stop();
        score = -1;
        addPoint();
    }



    public static void main(String[] args) {
        SnakeTheGame start = new SnakeTheGame();
    }
}
