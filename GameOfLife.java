import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class GameOfLife {
    private boolean[][] world;
    private final int SIZE;

    public GameOfLife(int n) {
        this.SIZE = n;
        world = new boolean[SIZE][SIZE];
    }

    public GameOfLife(int n, boolean[][] init){
        this.SIZE = n;
        world = new boolean[SIZE][SIZE];

        initial(init);
    }

    public int getSIZE(){
        return SIZE;
    }

    public boolean[][] getWorld(){
        return world;
    }

    public void initial(boolean[][] init){
        for(int i = 0; i < init.length; i++){
            for(int j = 0; j < init[i].length; j++){
                try {
                    if (init[i][j]) world[j][i] = true;
                }catch (Exception e){}
            }
        }
    }

    private boolean check(int y, int x){
        int neighbours = 0;

        for(int i = -1; i < 2; i++){
            for(int j = -1; j < 2; j++){
                if(i == 0 && j == 0) continue;
                try {
                    if (world[y + j][x + i]) neighbours++;
                } catch (Exception e) {}
            }
        }

        if((neighbours == 2 || neighbours == 3) && world[y][x]) return true;
        if(neighbours == 3 && !world[y][x]) return true;
        return false;
    }

    public void step(){
        boolean[][] temp = new boolean[world.length][world[0].length];
        for(int i = 0; i < world.length; i++){
            for(int j = 0; j < world[i].length; j++){
                temp[i][j] = check(i, j);
            }
        }
        world = temp;
    }

    public void clear(){
        for (boolean[] booleans : world) {
            Arrays.fill(booleans, false);
        }
    }

    public void loop(){
        while (true) {
            printWorld();
            System.out.println();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            step();
        }
    }

    public void loop(int n){
        for(int i = 0; i < n; i++){
            printWorld();
            System.out.println();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            step();
        }
    }

    public void fillRandom(){
        Random r = new Random();
        for(int i = 0; i < world.length; i++){
            for(int j = 0; j < world[i].length; j++){
                int p = r.nextInt(1000);
                if(p < 315){
                    world[i][j] = true;
                }else {
                    world[i][j] = false;
                }
            }
        }
    }

    public void centered(boolean[][] pattern){
        boolean[][] out = new boolean[this.SIZE][this.SIZE];
        int yOffset = this.SIZE/2;
        int xOffset = this.SIZE/2 - pattern[0].length/2;
            for(int i = 0; i < pattern.length; i++){
                for(int j = 0 ; j < pattern[i].length; j++){
                    out[i+yOffset][j+xOffset] = pattern[i][j];
                }
            }
            this.initial(out);
    }

    public void printWorld(){
        for(int i = 0; i < world.length; i++){
            for(int j = 0; j < world[i].length; j++){
                if (world[i][j]) {
                    System.out.print("◼");
                } else {
                    System.out.print("◻");
                }
            }
            System.out.println();
        }
    }
}
