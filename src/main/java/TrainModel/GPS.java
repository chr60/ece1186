package TrainModel;
import TrackModel.*;

public class GPS {
	
	Block currBlock;
	Double distIntoBlock;
	
	/**
     * Constructor to create a new GPS object for current train.
     */
	public GPS()
	{
		
	}

	/**
     * Constructor to create a new GPS object for current train based on preset location
     * @param Block object passed to set new block location of train
     * @param Double argument passed to set new distance into block.
     */
	public GPS(Block currBlock, Double distIntoBlock)
	{
		this.currBlock = currBlock;
		this.distIntoBlock = distIntoBlock;
	}
	/**
     * Mutator to set current block
     * @param a Block argument is passed to set the new block location of the train
     */
	public void setCurrBlock(Block newBlock)
	{
		currBlock = newBlock;
	}
	
	/**
     * Accessor to get current block of train
     * @return a Block argument is returned to set the new block location of the train
     */
	public Block getCurrBlock(){
		return currBlock;
	}
	
	/**
     * Mutator to set current distance into block to nearest meter
     * @param a Double argument is passed to set the new distance location of the train
     */
	public void setDistIntoBlock(Double newDist)
	{
		distIntoBlock = newDist;
	}
	
	/**
     * Accessor to get current distance into block to nearest meter
     * @return a Double argument is passed to set the new distance location of the train
     */
	public Double getDistIntoBlock(){
		return distIntoBlock;
	}
	
}
