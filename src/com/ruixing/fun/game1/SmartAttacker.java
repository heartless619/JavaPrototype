package com.ruixing.fun.game1;

/**
 * Algorithm implementation of player for game "5-in-a-row"
 * With the implemented algorithm, the player tries to make 5 connected pieces
 * without considering other player's move.
 * Always attacking, no defending. 
 * 
 * @author Ruixing
 */

import com.nosto.fun.game1.ArenaPosition;
import com.nosto.fun.game1.Piece;
import com.nosto.fun.game1.Player;

public class SmartAttacker implements Player, Cloneable {
    Piece myPiece;
    String name;
    boolean firstMove = true;
    private static final int SIZE = 18;     // size of the board
    
    /** Creates a new instance of SmartOpponent */
    public SmartAttacker(String name) {
        this.name = name;
    }

    public void setSide(Piece p) {
        myPiece = p;
    }

    public Piece getSide() {
        return myPiece;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public ArenaPosition move(Piece[][] board, ArenaPosition lastPosition) {
    	// first move is a random move
    	if( firstMove )
    	{
    		if( checkIfFirstMove(board.clone(), getSide() ) )
			{
    			firstMove = false;
    			return randomMove(board.clone(), lastPosition );	    		
			}
    	}
        int maxMyPiece = 0;
        int maxTemp = 0;
        int returnedHorizontalCoordinate = 0;
        int returnedVerticalCoordinate = 0;
        
        	// algorithm to decide where to make next move 
        	for(int i=0;i<SIZE;i++)
        	{
        		for(int j=0;j<SIZE;j++)
        		{

        			if( board[i][j] == null)
        			{
        				// check its neighboring positions, how many play's pieces are connected
        				maxMyPiece = checkMaxValue(i,j,board.clone(),getSide());
        				
        				if(maxMyPiece >= maxTemp)
        				{
        					maxTemp = maxMyPiece;
        					returnedHorizontalCoordinate = i;
        					returnedVerticalCoordinate = j;
        				}
        				
        			}
        			
        		}
        		
        	}
                       
            // end of algorithm

            return new ArenaPosition(returnedHorizontalCoordinate, returnedVerticalCoordinate);

      
    }

    public String toString() {
        return getName();
    }

    public int checkMaxValue(int x, int y, Piece[][] board, Piece cross_or_round){
    	int numberOfNeighbors = 5;
    	int FIVE_IN_A_ROW = 5;
    	int numberOfConnected = 0, maxNumber, maxTemp=0;
    	int xCoordinateTemp=x,yCoordinateTemp=y;
    	int xTemporaryValue=xCoordinateTemp,yTemporaryValue=yCoordinateTemp;
    	// check horizontal direction, how many connected marks
    	// right side
    	for(int i= 0; i<numberOfNeighbors; i++)
    	{
    		xTemporaryValue += 1; 
    		if( xTemporaryValue >= SIZE)
    		{
    			// out of the border of the board
    			break;      
    		}
    		if( board[xTemporaryValue][yTemporaryValue] == cross_or_round)
    		{
    			numberOfConnected++;
    		}
    		else
    		{
    			break;
    		}
    	}
    	// left side
    	xTemporaryValue=xCoordinateTemp; // reset the xTemporaryValue as x
    	for(int i=0; i<numberOfNeighbors; i++)
    	{
    		xTemporaryValue -= 1;
    		if( xTemporaryValue < 0)
    		{
    			// out of the border of the board
    			break;         
    		}
    		if( board[xTemporaryValue][yTemporaryValue] == cross_or_round)
    		{
    			numberOfConnected++;
    		}
    		else
    		{
    			break;
    		}
    	}
    	if(numberOfConnected<FIVE_IN_A_ROW)
    	{    		
    		maxTemp = numberOfConnected;
    	}
    	
    	// check vertical direction, how many connected marks
    	// upward side
    		// 	reset
    	xTemporaryValue = xCoordinateTemp; 	
    	yTemporaryValue = yCoordinateTemp; 	
    	numberOfConnected = 0;   
    	for(int i=0; i<numberOfNeighbors; i++)
    	{
    		yTemporaryValue-=1;
    		if(yTemporaryValue<0)
    		{
    			break;
    		}
    		if(board[xTemporaryValue][yTemporaryValue]== cross_or_round)
    		{
    			numberOfConnected++;
    		}
    		else
    		{
    			break;
    		}
    	}
    	// downward side 
    		// reset
    	yTemporaryValue = yCoordinateTemp;
    	for(int i=0; i<numberOfNeighbors; i++)
    	{
    		yTemporaryValue += 1;
    		if(yTemporaryValue >= SIZE )
    		{
    			break;
    		}
    		if(board[xTemporaryValue][yTemporaryValue]== cross_or_round)
    		{
    			numberOfConnected++;
    		}
    		else
    		{
    			break;
    		}
    	}
    	if(numberOfConnected>maxTemp & numberOfConnected<FIVE_IN_A_ROW )
    	{
    		maxTemp = numberOfConnected;
    	}
    	// check backward slash direction ("\"), how many connected marks
    	// up left direction
    	xTemporaryValue = xCoordinateTemp;
    	yTemporaryValue = yCoordinateTemp;
    	numberOfConnected = 0;
    	for(int i=0; i<numberOfNeighbors; i++)
    	{
    		xTemporaryValue -= 1;
    		yTemporaryValue -= 1;
    		if(yTemporaryValue<0 || xTemporaryValue<0 )
    		{
    			break;
    		}
    		if(board[xTemporaryValue][yTemporaryValue] == cross_or_round)
    		{
    			numberOfConnected++;
    		}
    		else
    		{
    			break;
    		}
    	}
    	// down right direction
    	xTemporaryValue = xCoordinateTemp;
    	yTemporaryValue = yCoordinateTemp;    	
    	for(int i=0; i<numberOfNeighbors; i++)
    	{
    		xTemporaryValue += 1;
    		yTemporaryValue += 1;
    		if(yTemporaryValue>=SIZE || xTemporaryValue>=SIZE )
    		{
    			break;
    		}
    		if(board[xTemporaryValue][yTemporaryValue] == cross_or_round)
    		{
    			numberOfConnected++;
    		}
    		else
    		{
    			break;
    		}
    	}
    	if(numberOfConnected>maxTemp & numberOfConnected<FIVE_IN_A_ROW )
    	{
    		maxTemp = numberOfConnected;
    	}
    	// check forward slash direction ("/"), how many connected marks
    	// up right direction
    	xTemporaryValue = xCoordinateTemp;
    	yTemporaryValue = yCoordinateTemp;
    	numberOfConnected = 0;
    	for(int i=0; i<numberOfNeighbors; i++)
    	{
    		xTemporaryValue += 1;
    		yTemporaryValue -= 1;
    		if(yTemporaryValue<0 || xTemporaryValue>=SIZE )
    		{
    			break;
    		}
    		if(board[xTemporaryValue][yTemporaryValue] == cross_or_round)
    		{
    			numberOfConnected++;
    		}
    		else
    		{
    			break;
    		}
    	}
    	// down left direction
    	xTemporaryValue = xCoordinateTemp;
    	yTemporaryValue = yCoordinateTemp;    	
    	for(int i=0; i<numberOfNeighbors; i++)
    	{
    		xTemporaryValue -= 1;
    		yTemporaryValue += 1;
    		if(yTemporaryValue>=SIZE || xTemporaryValue<0 )
    		{
    			break;
    		}
    		if(board[xTemporaryValue][yTemporaryValue] == cross_or_round)
    		{
    			numberOfConnected++;
    		}
    		else
    		{
    			break;
    		}
    	}
    	if(numberOfConnected>maxTemp & numberOfConnected<FIVE_IN_A_ROW )
    	{
    		maxTemp = numberOfConnected;
    	}
    	maxNumber = maxTemp;
    	return maxNumber;
    }
    
    public boolean checkIfFirstMove(Piece[][] board, Piece my_side){
    	for(int i=0;i<SIZE;i++)
    	{
    		for(int j=0;j<SIZE;j++)
    		{
    			if( board[i][j] == my_side )
    			{
    				// not the first move, return false
    				return false;
    			}
    		}
    	}
    	return true;
    }
    
    public ArenaPosition randomMove(Piece[][] board, ArenaPosition lastPosition) {
        if (Math.random() * 1000 <= 3) {
            return new ArenaPosition(-23235, 0);
        }
        while (true) {
            int x = (int) (Math.random() * board.length);
            int y = (int) (Math.random() * board.length);
            if (board[x][y] == null) {
                return new ArenaPosition(x, y);
            }
        }
    }
}
