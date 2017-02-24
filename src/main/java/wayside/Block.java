package proj1;

public class Block {
	
	  	
	  	private String Line;
	  	private String Segment;
	  	private int blockNum;
	  	private boolean Occupied;
	    private boolean Broken;
	    private boolean	Closed;
	    private Block nextBlock;
	    private Block prevBlock;
	    private Block upcomingBlock;
	    
	  public Block(int bnum){
		  setBlockNum(bnum);
	  }
	  public Block(String line, String segment, int bnum, boolean occupied){
		 setLine(line);
		 setSegment(segment);
		 setBlockNum(bnum);
	     setOccupied(false);
	     setBroken(false);
	     setClosed(false);
	     Occupied = occupied;
	   }
	public String getLine() {
		return Line;
	}
	public void setLine(String line) {
		Line = line;
	}
	public String getSegment() {
		return Segment;
	}
	public void setSegment(String segment) {
		Segment = segment;
	}
	public int getBlockNum() {
		return blockNum;
	}
	public void setBlockNum(int blockNum) {
		this.blockNum = blockNum;
	}
	public boolean isOccupied() {
		return Occupied;
	}
	public void setOccupied(boolean occupied) {
		Occupied = occupied;
	}
	public Block getNextBlock() {
		return nextBlock;
	}
	public void setNextBlock(Block nextBlock) {
		this.nextBlock = nextBlock;
	}
	public Block getUpcomingBlock() {
		return upcomingBlock;
	}
	public void setUpcomingBlock(Block upcomingBlock) {
		this.upcomingBlock = upcomingBlock;
	}
	public boolean isBroken() {
		return Broken;
	}
	public void setBroken(boolean broken) {
		Broken = broken;
	}
	public Block getPrevBlock() {
		return prevBlock;
	}
	public void setPrevBlock(Block prevBlock) {
		this.prevBlock = prevBlock;
	}
	public boolean isClosed() {
		return Closed;
	}
	public void setClosed(boolean closed) {
		Closed = closed;
	}
	  
	 
	  
}
