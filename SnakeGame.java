import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.LinkedList;

public class SnakeGame extends JFrame implements ActionListener, KeyListener {
    private static final int TILE_SIZE = 20;
    private static final int GRID_SIZE = 20;

    private LinkedList<Point> snake;
    private Point food;
    private char direction;

    public SnakeGame() {
        setTitle("Snake Game");
        setSize(TILE_SIZE * GRID_SIZE, TILE_SIZE * GRID_SIZE);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        snake = new LinkedList<>();
        snake.add(new Point(GRID_SIZE / 2, GRID_SIZE / 2));
        direction = 'R'; // Initial direction is right

        spawnFood();

        Timer timer = new Timer(200, this);
        timer.start();

        addKeyListener(this);
        setFocusable(true);
    }

    private void spawnFood() {
        int x = (int) (Math.random() * GRID_SIZE);
        int y = (int) (Math.random() * GRID_SIZE);
        food = new Point(x, y);
    }

    private void move() {
        Point head = snake.getFirst();
        Point newHead;

        switch (direction) {
            case 'U':
                newHead = new Point(head.x, (head.y - 1 + GRID_SIZE) % GRID_SIZE);
                break;
            case 'D':
                newHead = new Point(head.x, (head.y + 1) % GRID_SIZE);
                break;
            case 'L':
                newHead = new Point((head.x - 1 + GRID_SIZE) % GRID_SIZE, head.y);
                break;
            case 'R':
                newHead = new Point((head.x + 1) % GRID_SIZE, head.y);
                break;
            default:
                return;
        }

        snake.addFirst(newHead);

        if (newHead.equals(food)) {
            spawnFood();
        } else {
            snake.removeLast();
        }

        // Check for collision with itself
        if (snake.size() > 1 && snake.subList(1, snake.size()).contains(newHead)) {
            gameOver();
        }
    }

    private void gameOver() {
        JOptionPane.showMessageDialog(this, "Game Over!", "Game Over", JOptionPane.INFORMATION_MESSAGE);
        System.exit(0);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        // Draw snake
        g.setColor(Color.GREEN);
        for (Point point : snake) {
            g.fillRect(point.x * TILE_SIZE, point.y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
        }

        // Draw food
        g.setColor(Color.RED);
        g.fillRect(food.x * TILE_SIZE, food.y * TILE_SIZE, TILE_SIZE, TILE_SIZE);

        Toolkit.getDefaultToolkit().sync();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                if (direction != 'D') {
                    direction = 'U';
                }
                break;
            case KeyEvent.VK_DOWN:
                if (direction != 'U') {
                    direction = 'D';
                }
                break;
            case KeyEvent.VK_LEFT:
                if (direction != 'R') {
                    direction = 'L';
                }
                break;
            case KeyEvent.VK_RIGHT:
                if (direction != 'L') {
                    direction = 'R';
                }
                break;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SnakeGame snakeGame = new SnakeGame();
            snakeGame.setVisible(true);
        });
    }
}
