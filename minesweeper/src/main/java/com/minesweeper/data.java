package com.minesweeper;

public class data {          
    private String bombs;      
    private String attempts;    
    private String time;        
    private String winner;     

    public data(String[] attr) {     
        this.bombs = attr[0];
        this.attempts = attr[1];
        this.time = attr[2];
        this.winner = attr[3];
    }

    public String getBombs() {      
        return bombs;
    }

    public String getAttempts() {   
        return attempts;
    }

    public String getTime() {    
        return time;
    }

    public String getWinner() {     
        return winner;
    }
}

