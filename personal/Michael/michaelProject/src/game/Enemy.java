package game;

import java.util.Random;

import world.Tile;
import world.World;

public class Enemy {
	public Enemy(World world, int coffee_x, int coffee_y){
		Random rand = new Random();
		int  n = rand.nextInt(4) + 1;
		Random rand2 = new Random();
		int n2 = rand2.nextInt(100) + 1; 
		if (n2 % 20 == 0){
			if (n <= 2){
				if (n==1 && coffee_x!=32 && world.getTile(coffee_x+1, coffee_y)==Tile.test_tile){
					world.setTile(Tile.test_tile, coffee_x, coffee_y);
					coffee_x = coffee_x + 1; 
				}
				else if (n == 2 && coffee_x != 0 && world.getTile(coffee_x-1, coffee_y)==Tile.test_tile){
					world.setTile(Tile.test_tile, coffee_x, coffee_y);
					coffee_x = coffee_x - 1;
				}
				world.setTile(Tile.duck, coffee_x, coffee_y);
			}
			else if (n > 2){
				if(n == 3 && coffee_y != 32 && world.getTile(coffee_x, coffee_y+1)==Tile.test_tile){
					world.setTile(Tile.test_tile, coffee_x, coffee_y);
					coffee_y = coffee_y + 1;
				}
				else if (n == 4 && coffee_y !=0 && world.getTile(coffee_x, coffee_y-1)==Tile.test_tile){
					world.setTile(Tile.test_tile, coffee_x, coffee_y);
					coffee_y = coffee_y - 1;
				}
				world.setTile(Tile.duck2, coffee_x, coffee_y);
			}
		}
	}
	
}
